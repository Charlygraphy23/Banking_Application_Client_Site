package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;
import sample.model.Staff;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private BorderPane loginPane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton createAccountButton;

    @FXML
    private JFXCheckBox adminChechbox;

    @FXML
    private JFXCheckBox userCheckBox;

    @FXML
    void close(MouseEvent event) {
            System.exit(0);
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        if(stage.isMaximized()){
            stage.setMaximized(false);
        }
        else stage.setMaximized(true);
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public static int lUserId=0;
    public static int sUserId=0;

    @FXML
    void initialize() {
        Tooltip usernameTooltip=new Tooltip("Please Enter You Username");
        username.setTooltip(usernameTooltip);
        Tooltip passwordTooltip=new Tooltip("Please Enter You Password");
        password.setTooltip(passwordTooltip);

        adminChechbox.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            userCheckBox.setSelected(false);
        });
        userCheckBox.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            adminChechbox.setSelected(false);
        });

        loginButton.setOnAction(new Login());
        createAccountButton.setOnAction(new Signup());



    }                           // End of Intialize;
            //
    ///
    //
    //




    private class Login implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if(!username.getText().equals("") && !password.getText().equals("")) {
                if (adminChechbox.isSelected()) {

                    // Admin part

                    DatabaseHandeller handeller = new DatabaseHandeller();
                    Staff staff=new Staff();
                    staff.setUsername(username.getText());
                    staff.setPassword(password.getText());
                    try {
                        int c=0;
                        ResultSet resultSet=handeller.getStaffs(staff);
                        while (resultSet.next()){
                            sUserId=resultSet.getInt("idstaffs");
                            c++;
                        }

                        if(c>0){

                            FXMLLoader loader=new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/view/staffdashboard.fxml"));
                            Stage stage=new Stage(StageStyle.TRANSPARENT);
                            stage.setScene(new Scene(loader.load()));
                            Stage stage1=(Stage) ((Node)event.getSource()).getScene().getWindow();
                            stage1.getScene().getWindow().hide();
                            if(stage1.isMaximized()){
                                stage.setMaximized(true);
                            }
                            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
                            stage.show();

                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("!!! Password or Username is Incorrect !!!");
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (userCheckBox.isSelected()) {

                    // User part

                    DatabaseHandeller handeller = new DatabaseHandeller();
                    Accounts accounts = new Accounts();
                    accounts.setUsername(username.getText());
                    accounts.setPassword(password.getText());
                    try {
                        ResultSet resultSet = handeller.getUserAccount(accounts);
                        int c=0;
                        while (resultSet.next()){
                            lUserId=resultSet.getInt("userid");
                            c++;
                        }
                        if(c>0){
                            FXMLLoader loader=new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/view/dashbord.fxml"));
                            Stage stage=new Stage(StageStyle.UNDECORATED);
                            stage.setScene(new Scene(loader.load()));
                            Stage stage1=(Stage) ((Node)event.getSource()).getScene().getWindow();
                            stage1.getScene().getWindow().hide();
                            if(stage1.isMaximized()){
                                stage.setMaximized(true);
                            }
                            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
                            stage.show();}
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("!!! Password or Username is Incorrect !!!");
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Select User Type (Staff / User)");
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter Username and Password Properly");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                alert.show();
            }
        }
    }

    private class Signup implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
             Stage stage=new Stage();
            try {
                Parent root=FXMLLoader.load(getClass().getResource("/sample/view/signupview.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }

            createAccountButton.getScene().getWindow().hide();
            Stage stage2=(Stage) createAccountButton.getScene().getWindow();
            if(stage2.isMaximized()){
                stage.setMaximized(true);
            }
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
            stage.show();
        }
    }
}
