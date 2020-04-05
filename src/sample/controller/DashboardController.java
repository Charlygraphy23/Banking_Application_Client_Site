package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.xdevapi.DbDoc;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Transitions.Shaker;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;
import sample.model.Funds;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PropertyPermission;

public class DashboardController {

    @FXML
    private Label accountInformationLabel;

    @FXML
    private Label miniStatementLabel;

    @FXML
    private Label transactionHistoryLabel;

    @FXML
    private Label transferFundLabel;

    @FXML
    private Label changePin;

    @FXML
    private Circle profilephotoCorcle;

    @FXML
    private Label fullNameplate;

    @FXML
    private Label usernamePlate;

    @FXML
    private FontAwesomeIconView logOutButton;

    @FXML
    private FontAwesomeIconView homeButton;

    @FXML
    private AnchorPane ministatementPane;

    @FXML
    private BorderPane pinverificationPane;

    @FXML
    private GridPane homePage;

    @FXML
    private Label accountNumber;

    @FXML
    private Label balance;

    @FXML
    private Label accountType;

    @FXML
    private JFXComboBox<String> accountTypeCombo;

    @FXML
    private Label religion;

    @FXML
    private BorderPane fundTransferPane;

    @FXML
    private VBox transferAmountVboxPane;

    @FXML
    private JFXTextField accountNumberTextBox;

    @FXML
    private JFXTextField amountTextBox;

    @FXML
    private JFXButton transferButton;

    @FXML
    private Circle bankLogoTransferFund;

    @FXML
    private TableView<Funds> miniStatementTable;

    @FXML
    private TableColumn<Funds, String> dateofTransactionIndex;

    @FXML
    private TableColumn<Funds, Integer> debitAmountIndex;

    @FXML
    private TableColumn<Funds, Integer> creditAmountIndex;

    @FXML
    private TableView<Funds> tStatemantTable;

    @FXML
    private AnchorPane transactionHistoryPane;

    @FXML
    private TableColumn<Funds, String> tDateIndex;

    @FXML
    private TableColumn<Funds, String> tAccountypeIndex;

    @FXML
    private TableColumn<Funds, String> tAccountNumberIndex;

    @FXML
    private TableColumn<Funds, Integer> tCreditIndex;

    @FXML
    private TableColumn<Funds, Integer> tDebitIndex;

    @FXML
    private TableColumn<Funds, Integer> tBalanceIndex;

    @FXML
    private JFXPasswordField pintextField;

    @FXML
    private Label one;

    @FXML
    private Label two;

    @FXML
    private Label three;

    @FXML
    private Label four;

    @FXML
    private Label five;

    @FXML
    private Label six;

    @FXML
    private Label seven;

    @FXML
    private Label eight;

    @FXML
    private Label nine;

    @FXML
    private Label backspaceButton;

    @FXML
    private Label zero;

    @FXML
    private Label confirm;

    @FXML
    private Label accountVerifyLAbel;

    @FXML
    private BorderPane pinChangePane;

    @FXML
    private JFXPasswordField oldPasswordField;

    @FXML
    private JFXPasswordField newPinField;

    @FXML
    private JFXPasswordField confirmpinField;

    @FXML
    private JFXButton changeButton;

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stage.isMaximized()) stage.setMaximized(false);
        else stage.setMaximized(true);
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private DatabaseHandeller handeller;
    private String pin=null;
    private int totalAmount=0;
    private ObservableList<Funds> observableList;
    private int amount=0;
    private String pinn=null;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException, IOException {

        dateofTransactionIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("date"));
        debitAmountIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("debit"));
        creditAmountIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("credit"));
        observableList=FXCollections.observableArrayList();

        tDateIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("date"));
        tAccountypeIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("accountType"));
        tAccountNumberIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("accountno"));
        tCreditIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("credit"));
        tDebitIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("debit"));
        tBalanceIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("totalammount"));


        accountInformationLabel.setOpacity(1);
        accountInformationLabel.setOnMouseClicked(new AccountInformation());
        miniStatementLabel.setOnMouseClicked(new MniStatement());
        transactionHistoryLabel.setOnMouseClicked(new TransactionHistory());
        transferFundLabel.setOnMouseClicked(new TransferFund());
        changePin.setOnMouseClicked(new ChangePin());

        handeller=new DatabaseHandeller();
        ResultSet userSet=handeller.getUserInformation();
        while (userSet.next()){
            religion.setText(userSet.getString("religion"));
            fullNameplate.setText(userSet.getString("firstname")+" "+userSet.getString("lastname"));
        }                           // User Information

        ResultSet accountSet=handeller.getAccountInformation();
        String bal=null;
        while (accountSet.next()){
            usernamePlate.setText("Welcome Back - "+accountSet.getString("username"));
            accountNumber.setText(accountSet.getString("ifsc")+accountSet.getString("accountno"));
            bal=accountSet.getString("balance");
            accountType.setText(accountSet.getString("accounttype"));
            profilephotoCorcle.setFill(new ImagePattern(new Image(accountSet.getBinaryStream("photo"))));
            pin=accountSet.getString("pin");
            totalAmount=Integer.parseInt(accountSet.getString("balance"));
        }


        if(pin.equals("") || pin == null){
            System.out.println("In Pin");
            Parent root=FXMLLoader.load(getClass().getResource("/sample/view/pinview.fxml"));
            Stage stage=new Stage(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
            PauseTransition pauseTransition=new PauseTransition(Duration.seconds(2));
            pauseTransition.setOnFinished(event -> {
                stage.show();
            });
            pauseTransition.play();

        }


        if(!bal.equals("")){balance.setText(bal+".00 INR");     }
        else balance.setText("0");

        amountTextBox.setOnKeyReleased(e->{
            if( amountTextBox.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE)){amountTextBox.setStyle("-fx-background-color: white; -fx-text-fill: black");}
            else {
                amountTextBox.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(amountTextBox);
            }
        });

        transferButton.setOnAction(new FundTransfer());
        homeButton.setOnMouseClicked(new AccountInformation());
        logOutButton.setOnMouseClicked(new Logout());


        one.setOnMouseClicked(e->{
            pintextField.appendText("1");
        });
        two.setOnMouseClicked(e->{
            pintextField.appendText("2");
        });
        three.setOnMouseClicked(e->{
            pintextField.appendText("3");
        });
        four.setOnMouseClicked(e->{
            pintextField.appendText("4");
        });
        five.setOnMouseClicked(e->{
            pintextField.appendText("5");
        });
        six.setOnMouseClicked(e->{
            pintextField.appendText("6");
        });
        seven.setOnMouseClicked(e->{
            pintextField.appendText("7");
        });
        eight.setOnMouseClicked(e->{
            pintextField.appendText("8");
        });
        nine.setOnMouseClicked(e->{
            pintextField.appendText("9");
        });
        zero.setOnMouseClicked(e->{
            pintextField.appendText("0");
        });
        backspaceButton.setOnMouseClicked(e->{
            pintextField.setText("");
        });

        confirm.setOnMouseClicked(new ConfirmPin());


        newPinField.setOnKeyReleased(e->{
            if(newPinField.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.TAB) ) {newPinField.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }else{newPinField.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(newPinField);}
        });

        confirmpinField.setOnKeyReleased(e->{
            if(confirmpinField.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.TAB) ) {confirmpinField.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }else{confirmpinField.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(confirmpinField);}
        });

        oldPasswordField.setOnKeyReleased(e->{
            if(oldPasswordField.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.TAB) ) {oldPasswordField.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }else{oldPasswordField.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(oldPasswordField);}
        });

    }           /// End of Initialize
    //
    //
    //
    //

    private class AccountInformation implements EventHandler< MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            clearAll();
            accountInformationLabel.setOpacity(1);
            miniStatementLabel.setOpacity(0.7);
            transactionHistoryLabel.setOpacity(0.7);
            transferFundLabel.setOpacity(0.7);
            changePin.setOpacity(0.7);

            homePage.setVisible(true);
            fundTransferPane.setVisible(false);
            ministatementPane.setVisible(false);
            transactionHistoryPane.setVisible(false);
            pinChangePane.setVisible(false);
            observableList.clear();
        }
    }

    private class MniStatement implements EventHandler< MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            clearAll();
            accountInformationLabel.setOpacity(0.7);
            miniStatementLabel.setOpacity(1);
            transactionHistoryLabel.setOpacity(0.7);
            transferFundLabel.setOpacity(0.7);
            changePin.setOpacity(0.7);

            homePage.setVisible(false);
            fundTransferPane.setVisible(false);
            ministatementPane.setVisible(true);
            transactionHistoryPane.setVisible(false);
            pinChangePane.setVisible(false);
            observableList.clear();

            handeller=new DatabaseHandeller();
            try {
                ResultSet resultSet=handeller.getTransaction();

                while (resultSet.next()){
                    Funds funds=new Funds();
                    funds.setDate(resultSet.getString("transactiondate"));
                    funds.setDebit(resultSet.getInt("debit"));
                    funds.setCredit(resultSet.getInt("credit"));
                    observableList.addAll(funds);
                }
                ObservableList<Funds> fundsObservableList=FXCollections.observableArrayList();
                if(observableList.size()>5){
                    int c=0;
                    for(int i=observableList.size()-1;c<5;i--){
                        Funds funds=new Funds();
                        funds.setDate(observableList.get(i).getDate());
                        funds.setDebit(observableList.get(i).getDebit());
                        funds.setCredit(observableList.get(i).getCredit());
                        fundsObservableList.addAll(funds);
                        c++;
                    }
                }else if(observableList.size()<5) {
                    for(int i=observableList.size()-1;i>=0;i--){
                        Funds funds=new Funds();
                        funds.setDate(observableList.get(i).getDate());
                        funds.setDebit(observableList.get(i).getDebit());
                        funds.setCredit(observableList.get(i).getCredit());
                        fundsObservableList.addAll(funds);
                    }
                }
                miniStatementTable.setItems(fundsObservableList);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private class TransactionHistory implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            clearAll();
            accountInformationLabel.setOpacity(0.7);
            miniStatementLabel.setOpacity(0.7);
            transactionHistoryLabel.setOpacity(1);
            transferFundLabel.setOpacity(0.7);
            changePin.setOpacity(0.7);

            homePage.setVisible(false);
            fundTransferPane.setVisible(false);
            ministatementPane.setVisible(false);
            transactionHistoryPane.setVisible(true);
            pinChangePane.setVisible(false);
            observableList.clear();

            handeller=new DatabaseHandeller();
            try {
                ResultSet resultSet=handeller.getTransaction();

                while (resultSet.next()){
                    Funds funds=new Funds();
                    funds.setDate(resultSet.getString("transactiondate"));
                    funds.setAccountType(resultSet.getString("accounttype"));
                    funds.setAccountno(resultSet.getString("accountno"));
                    funds.setCredit(resultSet.getInt("credit"));
                    funds.setDebit(resultSet.getInt("debit"));
                    funds.setTotalammount(resultSet.getInt("totalammount"));
                    observableList.addAll(funds);
                }
                tStatemantTable.setItems(observableList);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private class TransferFund implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            clearAll();
            accountInformationLabel.setOpacity(0.7);
            miniStatementLabel.setOpacity(0.7);
            transactionHistoryLabel.setOpacity(0.7);
            transferFundLabel.setOpacity(1);
            changePin.setOpacity(0.7);

            homePage.setVisible(false);
            fundTransferPane.setVisible(true);
            ministatementPane.setVisible(false);
            transactionHistoryPane.setVisible(false);
            transferAmountVboxPane.setVisible(true);
            pinChangePane.setVisible(false);
            observableList.clear();

            bankLogoTransferFund.setFill(new ImagePattern(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg")));
            String listOfAccountTyeps[]={"Savings Account (SB)","Current Account (CB)","Zero Balance","upi","imps"};
            ObservableList<String> list= FXCollections.observableArrayList(listOfAccountTyeps);
            accountTypeCombo.setItems(list);

            accountNumberTextBox.setOnKeyReleased(e->{
               if( accountNumberTextBox.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE)){accountNumberTextBox.setStyle("-fx-background-color: white; -fx-text-fill: black");}
               else {
                   accountNumberTextBox.setStyle("-fx-background-color: red; -fx-text-fill: white");
                   new Shaker(accountNumberTextBox);
               }
            });
        }

    }

    private class ChangePin implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
             clearAll();
            accountInformationLabel.setOpacity(0.7);
            miniStatementLabel.setOpacity(0.7);
            transactionHistoryLabel.setOpacity(0.7);
            transferFundLabel.setOpacity(0.7);
            changePin.setOpacity(1);

            homePage.setVisible(false);
            fundTransferPane.setVisible(false);
            ministatementPane.setVisible(false);
            transactionHistoryPane.setVisible(false);
            pinChangePane.setVisible(true);
            observableList.clear();

            handeller=new DatabaseHandeller();

            try {
                ResultSet resultSet=handeller.getPin();
                while (resultSet.next()){
                   pinn=resultSet.getString("pin");
                }

            changeButton.setOnAction(e->{
                if(oldPasswordField.getText().equals(pinn)){
                    if(!newPinField.getText().equals("") && !newPinField.getText().equals("")){
                        if(newPinField.getText().matches("^\\d{4}$")) {
                            if (newPinField.getText().equals(confirmpinField.getText())) {

                                DatabaseHandeller handeller=new DatabaseHandeller();
                                Accounts accounts=new Accounts();
                                accounts.setPin(confirmpinField.getText());
                                try {
                                    handeller.updatePin(accounts);

                                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Pin Successfully Added \n You Can change it In change Pin Section");
                                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                                    alert.setHeaderText(null);
                                    Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                                    stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                                    alert.show();
                                    alert.setOnCloseRequest(ee->{
                                       homePage.setVisible(true);
                                       pinChangePane.setVisible(false);
                                       newPinField.setText("");
                                       oldPasswordField.setText("");
                                       confirmpinField.setText("");
                                    });

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                } catch (ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }

                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("!! New pin and Confirm pin Should Same !!");
                                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                                alert.setHeaderText(null);
                                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                                stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                                alert.show();
                            }
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("!! Pin Should be 4 digits !!");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                            alert.show();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("!! Enter Details Properly !!");
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                        alert.setHeaderText(null);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("!! Please Enter your pin Correctly !!");
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                    alert.show();
                }
            });


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearAll() {
        accountTypeCombo.getSelectionModel().clearSelection();
        accountNumberTextBox.setText("");
        amountTextBox.setText("");
        pintextField.setText("");
        amountTextBox.setText("");
        oldPasswordField.setText("");
        newPinField.setText("");
        confirmpinField.setText("");
        accountVerifyLAbel.setVisible(false);
        pinverificationPane.setVisible(false);
    }


    private class FundTransfer implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            if(accountTypeCombo.getValue() != null &&!accountNumberTextBox.getText().equals("") && !amountTextBox.getText().equals("")) {
                amount=totalAmount-Integer.parseInt(amountTextBox.getText());

                if(amount<0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("!! You don't have enough balance !!");
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                    alert.show();
                }
                else {
                    handeller=new DatabaseHandeller();
                    try {
                        ResultSet resultSet=handeller.getAccountNumber();

                        int c=0;
                        int userId=0;
                        while (resultSet.next()){
                            String acc=resultSet.getString("ifsc")+resultSet.getString("accountno");
                            if(accountNumberTextBox.getText().equals(acc) && accountTypeCombo.getValue().equals(resultSet.getString("accounttype"))){
                                c++;
                                userId=resultSet.getInt("userid");
                            }
                        }

                        if(c>0 && userId!=LoginController.lUserId){
                            ResultSet set=handeller.getUserName(userId);
                            while (set.next()){
                                accountVerifyLAbel.setText(set.getString("firstname")+" "+set.getString("lastname"));
                            }
                            accountVerifyLAbel.setVisible(true);
                            PauseTransition pauseTransition=new PauseTransition(Duration.seconds(2));
                            pauseTransition.play();
                            pauseTransition.setOnFinished(e->{
                                pinverificationPane.setVisible(true);
                                transferAmountVboxPane.setVisible(false);
                            });
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("!! Enter A Valid Account Number Or AccountType !!");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                            alert.show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Enter Details Properly !!");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                alert.show();
            }
        }
    }

    private class ClearAll implements EventHandler<javafx.scene.control.DialogEvent> {
        @Override
        public void handle(DialogEvent event) {
            accountTypeCombo.getSelectionModel().clearSelection();
            accountNumberTextBox.setText("");
            amountTextBox.setText("");
            pintextField.setText("");
            amountTextBox.setText("");
            accountVerifyLAbel.setVisible(false);

        }
    }

    private class Logout implements EventHandler< MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/loginview.fxml"));
            Stage stage=new Stage();
            try {
                stage.setScene(new Scene((Parent) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            logOutButton.getScene().getWindow().hide();
            Stage stage1=(Stage) logOutButton.getScene().getWindow();
            if(stage1.isMaximized())stage.setMaximized(true);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
            stage.show();
        }
    }

    private class ConfirmPin implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if(!pintextField.equals("")){
                handeller=new DatabaseHandeller();
                try {
                    ResultSet resultSet=handeller.getPin();
                    String pin=null;
                    while (resultSet.next()){
                        pin=resultSet.getString(1);
                    }
                    if (pin.equals(pintextField.getText())) {
                            Funds funds = new Funds(accountNumber.getText(), String.valueOf(LocalDate.now()), accountTypeCombo.getValue(), accountNumberTextBox.getText(), 0, Integer.parseInt(amountTextBox.getText()), amount);
                            try {
                                handeller.setFund(funds);
                                handeller.updateAmountUser(amount);
                                balance.setText(String.valueOf(amount) + ".00 INR");
                                totalAmount = amount;


                                int totalBalance=0,userId=0;
                                String accNo=null,accounttyp=null;

                                ResultSet set=handeller.getBenifisharyAccount();
                                while (set.next()){
                                    String acc=set.getString("ifsc")+set.getString("accountno");
                                    if(accountNumberTextBox.getText().equals(acc)){
                                        accNo=acc;
                                        totalBalance=Integer.parseInt(set.getString("balance"));
                                        userId=set.getInt("userid");
                                        accounttyp=set.getString("accounttype");
                                    }
                                }
                                int creditAmount=Integer.parseInt(amountTextBox.getText());
                                int updateTotalAmmount=totalBalance+creditAmount;

                                handeller.updateAmountUser(updateTotalAmmount,userId);

                                Funds funds1=new Funds(accNo,LocalDate.now().toString(),accounttyp,accountNumber.getText(),creditAmount,0,updateTotalAmmount);
                                handeller.setCreditFund(funds1,userId);



                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Amount has been transferred Successfully");
                                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                                alert.setHeaderText(null);
                                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                                stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                                alert.show();
                                alert.setOnCloseRequest(new ClearAll());
                                pinverificationPane.setVisible(false);
                                transferAmountVboxPane.setVisible(true);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("!! Pin Incorrect !!");
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                        alert.setHeaderText(null);
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                        alert.show();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
