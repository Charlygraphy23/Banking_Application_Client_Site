package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Transitions.Shaker;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;
import sample.model.User;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SignupController {

    @FXML
    private BorderPane signUppane;

    @FXML
    private Circle personactiveCircle;

    @FXML
    private Label indexone;

    @FXML
    private Label pesonalLabel;

    @FXML
    private Circle accountactiveCircle;

    @FXML
    private Label indextwo;

    @FXML
    private Label accountLabel;

    @FXML
    private Label backtoLoginButton;

    @FXML
    private VBox personalDetailPane;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXComboBox<String> idCard;

    @FXML
    private JFXTextField cardNo;

    @FXML
    private JFXTextField mobileno;

    @FXML
    private JFXTextField emailId;

    @FXML
    private JFXComboBox<String> gender;

    @FXML
    private JFXComboBox<String> meritalStatus;

    @FXML
    private JFXComboBox<String> religion;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXTextField age;

    @FXML
    private JFXTextField pinCode;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextField address;

    @FXML
    private Label nextButton;

    @FXML
    private VBox accountDetailspane;

    @FXML
    private JFXTextField ifsccode;

    @FXML
    private JFXTextField accountNumber;

    @FXML
    private JFXTextField accuntBalance;

    @FXML
    private JFXComboBox<String> accounttype;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXComboBox<String> securityq;

    @FXML
    private JFXTextField answars;

    @FXML
    private ImageView photo;

    @FXML
    private JFXButton uploadImage;

    @FXML
    private Label previousButton;

    @FXML
    private JFXButton subminbutton;

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        if(stage.isMaximized())stage.setMaximized(false);
        else stage.setMaximized(true);
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private String[] sIdcard={"Aadhar Card","Votar Card","Driving Licence","PAN Card"};
    private String[] sGender={"Male","Female","Other"};
    private String[] sMerital={"Single","Married"};
    private String[] sReligion={"Hindu","Muslim","Christan","Sikh","Jain","Buddha"};
    private String[] sAccunType={"Savings Account (SB)","Current Account (CB)","Zero Balance"};
    private String[] sSecurityQ={"What was the name of your elementary / primary school?","In what city or town does your nearest sibling live?","What time of the day were you born? (hh:mm)"};
    private ObservableList<String> observableArrayIdCard;
    private ObservableList<String> observableArrayGender;
    private ObservableList<String> observableArrayMerital;
    private ObservableList<String> observableArrayReligion;
    private ObservableList<String> observableListAccunttype;
    private ObservableList<String> observableListSequrityQ;
    private File file;
    private InputStream inputStream;
    private  DatabaseHandeller handeller;

    @FXML
    void initialize() {



        observableArrayIdCard = FXCollections.observableArrayList(sIdcard);
        observableArrayGender = FXCollections.observableArrayList(sGender);
        observableArrayMerital = FXCollections.observableArrayList(sMerital);
        observableArrayReligion = FXCollections.observableArrayList(sReligion);
        observableListAccunttype = FXCollections.observableArrayList(sAccunType);
        observableListSequrityQ = FXCollections.observableArrayList(sSecurityQ);
        idCard.setItems(observableArrayIdCard);gender.setItems(observableArrayGender);meritalStatus.setItems(observableArrayMerital);religion.setItems(observableArrayReligion);accounttype.setItems(observableListAccunttype);securityq.setItems(observableListSequrityQ);

        dob.setOnAction(e->{
            if(dob.getValue()!=null){
                LocalDate currentDate=LocalDate.now();
                LocalDate dateOfBirth=dob.getValue();
                Period pAge=Period.between(dateOfBirth,currentDate);
                age.setText(String.valueOf(pAge.getYears()));
            }
        });

        mobileno.setOnKeyReleased(e->{
            if(mobileno.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE)){
                mobileno.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                mobileno.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(mobileno);
            }
        });

        firstName.setOnKeyReleased(e->{
            if(firstName.getText().matches("[A-Z[a-z]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.SPACE)){
                firstName.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                firstName.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(firstName);
            }
        });

        lastName.setOnKeyReleased(e->{
            if(lastName.getText().matches("[A-Z[a-z]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.SPACE)){
                lastName.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                lastName.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(lastName);
            }
        });

        pinCode.setOnKeyReleased(e->{
            if(pinCode.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE)){
                pinCode.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                pinCode.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(pinCode);
            }
        });

        accuntBalance.setOnKeyReleased(e->{
            if(accuntBalance.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE)){
                accuntBalance.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                accuntBalance.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(accuntBalance);
            }
        });







        backtoLoginButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/loginview.fxml"));
            Stage stage=new Stage();
            try {
                stage.setScene(new Scene((Parent) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            backtoLoginButton.getScene().getWindow().hide();
            Stage stage1=(Stage) backtoLoginButton.getScene().getWindow();
            if(stage1.isMaximized())stage.setMaximized(true);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
            stage.show();

        });
        personactiveCircle.setFill(Paint.valueOf("#f44336"));
        indexone.setTextFill(Paint.valueOf("#ffffff"));

        previousButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            accountactiveCircle.setFill(Paint.valueOf("#ffffff"));
            indextwo.setTextFill(Paint.valueOf("#000000"));
            personactiveCircle.setFill(Paint.valueOf("#f44336"));
            indexone.setTextFill(Paint.valueOf("#ffffff"));
            personalDetailPane.setVisible(true);
            accountDetailspane.setVisible(false);
        });

        pesonalLabel.setOnMouseClicked(new PersonLabel());
        accountLabel.setOnMouseClicked(new Accountlabel());
        nextButton.setOnMouseClicked(new NextPage());
        uploadImage.setOnAction(new Upload());
        subminbutton.setOnAction(new SubmitUser());






    }               // End of Initialize
    //
    ///
    //
    //
    //

    private class PersonLabel implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            personactiveCircle.setFill(Paint.valueOf("#f44336"));
            indexone.setTextFill(Paint.valueOf("#ffffff"));
            accountactiveCircle.setFill(Paint.valueOf("#ffffff"));
            indextwo.setTextFill(Paint.valueOf("#000000"));
            personalDetailPane.setVisible(true);
            accountDetailspane.setVisible(false);
        }
    }


    private class Accountlabel implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if(mobileno.getText().matches("^\\d{10}$")){
                if(emailId.getText().matches("\\S+@\\S+")) {
                    accountactiveCircle.setFill(Paint.valueOf("#f44336"));
                    indextwo.setTextFill(Paint.valueOf("#ffffff"));
                    personactiveCircle.setFill(Paint.valueOf("#ffffff"));
                    indexone.setTextFill(Paint.valueOf("#000000"));
                    personalDetailPane.setVisible(false);
                    accountDetailspane.setVisible(true);
                    handeller=new DatabaseHandeller();
                    try {
                        ResultSet resultSet=handeller.getAccuntNo();
                        String acc=null;
                        while (resultSet.next()){
                            acc= resultSet.getString(1);}

                        if(acc!=null){
                            int a=Integer.parseInt(acc)+1;
                            accountNumber.setText(String.valueOf(a));
                        }
                        else {
                            accountNumber.setText("0001");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Please Enter Email Properly");
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                    alert.show();
                }
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please Enter Mobile No Properly");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                alert.setHeaderText(null);
                Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                alert.show();}
        }
    }


    private class NextPage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if(mobileno.getText().matches("^\\d{10}$")){
                if(emailId.getText().matches("\\S+@\\S+")) {
                    personactiveCircle.setFill(Paint.valueOf("#ffffff"));
                    indexone.setTextFill(Paint.valueOf("#000000"));
                    accountactiveCircle.setFill(Paint.valueOf("#f44336"));
                    indextwo.setTextFill(Paint.valueOf("#ffffff"));
                    personalDetailPane.setVisible(false);
                    accountDetailspane.setVisible(true);
                    handeller=new DatabaseHandeller();
                    try {
                        ResultSet resultSet=handeller.getAccuntNo();
                        String acc=null;
                            while (resultSet.next()){
                              acc= resultSet.getString(1);  }

                            if(acc!=null){
                                int a=Integer.parseInt(acc)+1;
                                accountNumber.setText(String.valueOf(a));
                            }
                        else {
                            accountNumber.setText("13340");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Please Enter Email Properly");
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                    alert.show();
                }
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please Enter Mobile No Properly");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                alert.setHeaderText(null);
                Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                alert.show();}
        }
    }

    private class Upload implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser=new FileChooser();
            file=fileChooser.showOpenDialog(uploadImage.getScene().getWindow());
            if(file!=null){
                try {
                    photo.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SubmitUser implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            handeller=new DatabaseHandeller();
            ResultSet resultSet=null;
            try {
            if(!firstName.getText().equals("") && !lastName.getText().equals("") && !idCard.getValue().equals("") && !cardNo.getText().equals("") && !mobileno.getText().equals("") && !emailId.getText().equals("") && !gender.getValue().equals("") && !meritalStatus.getValue().equals("") && !religion.getValue().equals("") && dob.getValue() != null && !pinCode.getText().equals("") && !city.getText().equals("") && !address.getText().equals("")
                   && !accuntBalance.getText().equals("") && !accounttype.getValue().equals("") && !username.getText().equals("") && !password.getText().equals("") && !securityq.getValue().equals("") && !answars.getText().equals("") && file!=null ){

                        Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());
                        inputStream=new FileInputStream(file);
                        User user=new User(firstName.getText(),lastName.getText(),idCard.getValue(),cardNo.getText(),mobileno.getText(),emailId.getText(),gender.getValue(),meritalStatus.getValue(),religion.getValue(),dob.getValue().toString(),age.getText(),pinCode.getText(),city.getText(),address.getText(),timestamp);
                        Accounts accounts=new Accounts(accountNumber.getText(),ifsccode.getText(),accuntBalance.getText(),accounttype.getValue(),username.getText(),password.getText(),securityq.getValue(),answars.getText(),inputStream,timestamp);

                        if(handeller.getUsernameVerify(accounts)==1){
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Username Already Exists");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                            alert.show();
                        }
                        else if(handeller.getMobileVerify(user)==1){
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Mobile No Already Exists");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                            alert.show();
                        }
                        else if(handeller.getEmailVerify(user)==1){
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Email Address Already Exists");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                            alert.show();
                        }
                        else {
                            handeller.userPersonalDetailsSignup(user);
                            handeller.userAccountDetailsSignup(accounts);
                            System.out.println("Account Succesfully add");
                            clearAll();
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("!! Details Successfully Added to Data !!");
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.setHeaderText(null);
                            alert.setOnCloseRequest(event1 -> {
                                backToLogin();
                            });
                            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                            alert.show();
                        }
                    }
            else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Plesse provide Valid Details");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                alert.setHeaderText(null);
                Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                alert.show();
            }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void backToLogin() {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/loginview.fxml"));
        Stage stage=new Stage();
        try {
            stage.setScene(new Scene((Parent) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backtoLoginButton.getScene().getWindow().hide();
        Stage stage1=(Stage) backtoLoginButton.getScene().getWindow();
        if(stage1.isMaximized())stage.setMaximized(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().addAll(new Image("/sample/assets/biz_slt_bankABC.max-752x423.jpg"));
        stage.show();
    }

    private void clearAll() {
        firstName.setText("");
        lastName.setText("");
        idCard.getSelectionModel().clearSelection();
        cardNo.setText("");
        mobileno.setText("");
        emailId.setText("");
        gender.getSelectionModel().clearSelection();
        meritalStatus.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        pinCode.setText("");
        city.setText("");
        address.setText("");
        file = null;
        accuntBalance.setText("");
        accounttype.getSelectionModel().clearSelection();
        username.setText("");
        password.setText("");
        securityq.getSelectionModel().clearSelection();
        answars.setText("");
    }
}
