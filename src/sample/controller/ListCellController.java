package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;
import sample.model.Funds;
import sample.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ListCellController extends JFXListCell<Accounts> {

    @FXML
    private AnchorPane cellpane;

    @FXML
    private Label cUsernmae;

    @FXML
    private Label cUserId;

    @FXML
    private Label cUserAccountNo;

    @FXML
    private Label cUseraBalance;

    @FXML
    private Label cLastUpdate;

    @FXML
    private Label ifsc;

    @FXML
    private Label cMoreInformation;

    @FXML
    private ImageView fundTransferImageView;

    @FXML
    private HBox fundTransferPane;

    @FXML
    private JFXTextField ammountTextField;

    @FXML
    private JFXButton amountTransferButton;

    private FXMLLoader loader=null;
    private  int index=-1,bal=-1;
    private DatabaseHandeller handeller;


    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Accounts accounts, boolean empty) {
        super.updateItem(accounts, empty);

        if(empty || accounts==null )
        {
            setText(null);
            setGraphic(null);
        }

        else {
            if(loader==null){
                loader=new FXMLLoader(getClass().getResource("/sample/view/listcellview.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                cUsernmae.setText("Username - " + accounts.getUsername());
                cUserId.setText("UserId - " + String.valueOf(accounts.getUserId()));
                cUserAccountNo.setText("Account No - "+accounts.getAccuntNo());
                ifsc.setText("Ifsc - "+accounts.getIfscno());
                cUseraBalance.setText("Balance - " + accounts.getBalance());
                cLastUpdate.setText("Last update " + String.valueOf(accounts.getLastUpdate()));

                cMoreInformation.setOnMouseClicked(e->{
                    LoginController.lUserId=getListView().getSelectionModel().getSelectedItem().getUserId();
                    System.out.println(LoginController.lUserId);
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/updateuserdetails.fxml"));
                    Stage stage=new Stage(StageStyle.TRANSPARENT);
                    try {
                        stage.setScene(new Scene(loader.load()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
                    stage.show();
                });

                fundTransferImageView.setOnMouseClicked(e->{
                    fundTransferPane.setVisible(true);
                });
                index=getListView().getSelectionModel().getSelectedIndex();

                amountTransferButton.setOnAction(e->{
                    handeller=new DatabaseHandeller();
                    String accNo=getListView().getSelectionModel().getSelectedItem().getIfscno()+getListView().getSelectionModel().getSelectedItem().getAccuntNo();
                    try {
                        ResultSet set=handeller.getBalance(getListView().getSelectionModel().getSelectedItem().getUserId());
                        while (set.next()){
                            bal=Integer.parseInt(set.getString("balance"))+Integer.parseInt(ammountTextField.getText());
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    Funds funds=new Funds(accNo, LocalDate.now().toString(),"BANK","000001",Integer.parseInt(ammountTextField.getText()),0,bal);

                    try {
                       handeller.updateAmountUser(bal,getListView().getSelectionModel().getSelectedItem().getUserId());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        handeller.setCreditFund(funds,getListView().getSelectionModel().getSelectedItem().getUserId());

                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Amount Transfer Successfully");
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                        alert.setHeaderText(null);
                        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                        alert.setOnCloseRequest(event -> {
                            fundTransferPane.setVisible(false);
                            ammountTextField.setText("");

                            cUseraBalance.setText("Balance - "+bal);
                        });
                        alert.show();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });


                setGraphic(cellpane);
                setText(null);


        }

    }

}
