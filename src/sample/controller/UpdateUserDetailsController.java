package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Transitions.Shaker;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;
import sample.model.Funds;
import sample.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class UpdateUserDetailsController {

    @FXML
    private ImageView uProfilePic;

    @FXML
    private JFXButton uUploadImageButton;

    @FXML
    private Label uIdLabel;

    @FXML
    private GridPane homePagePane;

    @FXML
    private JFXTextArea uFristname;

    @FXML
    private JFXTextArea uLastname;

    @FXML
    private JFXComboBox<String> uIdcardType;

    @FXML
    private JFXTextArea uIdCardNo;

    @FXML
    private JFXTextArea uMobileNo;

    @FXML
    private JFXTextArea uEmailId;

    @FXML
    private JFXComboBox<String> uGender;

    @FXML
    private JFXComboBox<String> uMeritalStatus;

    @FXML
    private JFXComboBox<String> uReligion;

    @FXML
    private JFXDatePicker uDob;

    @FXML
    private JFXTextArea uAge;

    @FXML
    private JFXTextArea uPincode;

    @FXML
    private JFXTextArea uCity;

    @FXML
    private JFXTextArea uAddress;

    @FXML
    private JFXTextArea uUsername;

    @FXML
    private JFXTextArea uPassword;

    @FXML
    private JFXComboBox<String> uAccountType;

    @FXML
    private JFXTextArea cardIdTypeTextArea;

    @FXML
    private JFXTextArea uIfsc;

    @FXML
    private JFXTextArea uAccountNo;

    @FXML
    private JFXTextArea uBalance;

    @FXML
    private JFXTextArea uSecurityQuestion;

    @FXML
    private JFXTextArea uAnswar;

    @FXML
    private JFXTextArea uPin;

    @FXML
    private JFXTextArea uLastupdate;

    @FXML
    private FontAwesomeIconView uHomeButton;

    @FXML
    private FontAwesomeIconView uTransactionHistoryButton;

    @FXML
    private Label uEditButton;

    @FXML
    private JFXButton uSubmitButtion;

    @FXML
    private JFXButton uCancleButton;

    @FXML
    private JFXTextArea genderTextArea;

    @FXML
    private JFXTextArea meritalStatusTextArea;

    @FXML
    private JFXTextArea religionTextArea;

    @FXML
    private JFXTextArea dobTextArea;

    @FXML
    private JFXTextArea accountTypeTextArea;

    @FXML
    private TableView<Funds> statementTableViewandPane;

    @FXML
    private TableColumn<Funds, String> accountTypeIndex;

    @FXML
    private TableColumn<Funds, String> accountNumberIndex;

    @FXML
    private TableColumn<Funds, String> dateIndex;

    @FXML
    private TableColumn<Funds, Integer> creditIndex;

    @FXML
    private TableColumn<Funds, Integer> debitIndex;

    @FXML
    private TableColumn<Funds, Integer> balanceIndex;



    @FXML
    void close(MouseEvent event) {
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
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

    private DatabaseHandeller handeller;
    private InputStream inputStream;
    private File file;

    private String[] sIdcard={"Aadhar Card","Votar Card","Driving Licence","PAN Card"};
    private String[] sGender={"Male","Female","Other"};
    private String[] sMerital={"Single","Married"};
    private String[] sReligion={"Hindu","Muslim","Christan","Sikh","Jain","Buddha"};
    private String[] sAccunType={"Savings Account (SB)","Current Account (CB)","Zero Balance"};
    private ObservableList<String> observableArrayIdCard;
    private ObservableList<String> observableArrayGender;
    private ObservableList<String> observableArrayMerital;
    private ObservableList<String> observableArrayReligion;
    private ObservableList<String> observableListAccunttype;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {


        observableArrayIdCard = FXCollections.observableArrayList(sIdcard);
        observableArrayGender = FXCollections.observableArrayList(sGender);
        observableArrayMerital = FXCollections.observableArrayList(sMerital);
        observableArrayReligion = FXCollections.observableArrayList(sReligion);
        observableListAccunttype = FXCollections.observableArrayList(sAccunType);
        uIdcardType.setItems(observableArrayIdCard);
        uGender.setItems(observableArrayGender);
        uMeritalStatus.setItems(observableArrayMerital);
        uReligion.setItems(observableArrayReligion);
        uAccountType.setItems(observableListAccunttype);

        accountNumberIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("accountno"));
        accountTypeIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("accountType"));
        balanceIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("totalammount"));
        creditIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("credit"));
        debitIndex.setCellValueFactory(new PropertyValueFactory<Funds,Integer>("debit"));
        dateIndex.setCellValueFactory(new PropertyValueFactory<Funds,String>("date"));



        uDob.setOnAction(e->{
            if(uDob.getValue()!=null){
                LocalDate currentDate=LocalDate.now();
                LocalDate dateOfBirth=uDob.getValue();
                Period pAge=Period.between(dateOfBirth,currentDate);
                uAge.setText(String.valueOf(pAge.getYears()));
            }
        });

        uPin.setVisible(false);
        uAnswar.setVisible(false);
        uEditButton.setOnMouseClicked(new EditProfile());
        uHomeButton.setOnMouseClicked(event -> {
            homePagePane.setVisible(true);
            uEditButton.setVisible(true);
            statementTableViewandPane.setVisible(false);

            if(uFristname.isEditable()){
                uSubmitButtion.setVisible(true);
                uCancleButton.setVisible(true);
                uUploadImageButton.setVisible(true);
                uPin.setVisible(true);
                uAnswar.setVisible(true);
            }
            else {
                uSubmitButtion.setVisible(false);
                uCancleButton.setVisible(false);
                uUploadImageButton.setVisible(false);
                uPin.setVisible(false);
                uAnswar.setVisible(false);
            }
        });

        uTransactionHistoryButton.setOnMouseClicked(event -> {
            homePagePane.setVisible(false);
            uEditButton.setVisible(false);
            uSubmitButtion.setVisible(false);
            uCancleButton.setVisible(false);
            uUploadImageButton.setVisible(false);
            statementTableViewandPane.setVisible(true);

        });

        uCancleButton.setOnAction(new Cancle());
        uSubmitButtion.setOnAction(new UpdateUSerDetails());
        retrivedata();

        uMobileNo.setOnKeyReleased(e->{
            if(uMobileNo.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE)){
                uMobileNo.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                uMobileNo.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(uMobileNo);
            }
        });

        uFristname.setOnKeyReleased(e->{
            if(uFristname.getText().matches("[A-Z[a-z]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.SPACE)){
                uFristname.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                uFristname.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(uFristname);
            }
        });

        uLastname.setOnKeyReleased(e->{
            if(uLastname.getText().matches("[A-Z[a-z]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.SPACE)){
                uLastname.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                uLastname.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(uLastname);
            }
        });

        uPincode.setOnKeyReleased(e->{
            if(uPincode.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.BACK_SPACE)){
                uPincode.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }
            else {
                uPincode.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(uPincode);
            }
        });


        uUploadImageButton.setOnAction(new UploadImage());



    }               ///    End of Initialize

    //
    //
    //
    //
    //

    private void retrivedata() throws SQLException, ClassNotFoundException {

        handeller=new DatabaseHandeller();
        ResultSet resultSet=handeller.getUserInformation();
        while (resultSet.next()){
            uIdLabel.setText(String.valueOf("ID - "+resultSet.getInt("id")));
            uFristname.setText(resultSet.getString("firstname"));
            uLastname.setText(resultSet.getString("lastname"));
            uIdcardType.setValue(resultSet.getString("idcard"));
            uIdCardNo.setText(resultSet.getString("idcardno"));
            uMobileNo.setText(resultSet.getString("mobileno"));
            uEmailId.setText(resultSet.getString("emailid"));
            uGender.setValue(resultSet.getString("gender"));
            uMeritalStatus.setValue(resultSet.getString("meritalstatus"));
            uReligion.setValue(resultSet.getString("religion"));
            uDob.setValue(LocalDate.parse(resultSet.getString("dob")));
            uAge.setText(resultSet.getString("age"));
            uPincode.setText(resultSet.getString("pincode"));
            uCity.setText(resultSet.getString("city"));
            uAddress.setText(resultSet.getString("address"));
            uLastupdate.setText(String.valueOf(resultSet.getTimestamp("lastupdatetime")));

            dobTextArea.setText(resultSet.getString("dob"));
            genderTextArea.setText(resultSet.getString("gender"));
            meritalStatusTextArea.setText(resultSet.getString("meritalstatus"));
            religionTextArea.setText(resultSet.getString("religion"));
            cardIdTypeTextArea.setText(resultSet.getString("idcard"));

        }


        ResultSet staff=handeller.getAccountInformation();
        while (staff.next()){
            uIfsc.setText(staff.getString("ifsc"));
            uAccountNo.setText(staff.getString("accountno"));
            uBalance.setText(staff.getString("balance"));
            uAccountType.setValue(staff.getString("accounttype"));
            uUsername.setText(staff.getString("username"));
            uPassword.setText(staff.getString("password"));
            uSecurityQuestion.setText(staff.getString("securityq"));
            uAnswar.setText(staff.getString("answar"));
            uProfilePic.setImage(new Image(staff.getBinaryStream("photo")));
            inputStream=staff.getBinaryStream("photo");
            uPin.setText(staff.getString("pin"));

            accountTypeTextArea.setText(staff.getString("accounttype"));

        }

        ResultSet transactions=handeller.getTransaction();
        ObservableList<Funds> observableListTransactions=FXCollections.observableArrayList();
        while (transactions.next()){
            Funds funds=new Funds();
            funds.setAccountno(transactions.getString("accountno"));
            funds.setAccountType(transactions.getString("accounttype"));
            funds.setDate(transactions.getString("transactiondate"));
            funds.setDebit(transactions.getInt("debit"));
            funds.setCredit(transactions.getInt("credit"));
            funds.setTotalammount(transactions.getInt("totalammount"));
            observableListTransactions.addAll(funds);
        }
        statementTableViewandPane.setItems(observableListTransactions);

    }


    private class EditProfile implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {


            uSubmitButtion.setVisible(true);
            uCancleButton.setVisible(true);
            uUploadImageButton.setVisible(true);

            uFristname.setEditable(true);
            uLastname.setEditable(true);
            uIdCardNo.setEditable(true);
            uMobileNo.setEditable(true);
            uEmailId.setEditable(true);
            uDob.setEditable(true);
            uPincode.setEditable(true);
            uCity.setEditable(true);
            uAddress.setEditable(true);
            uPassword.setEditable(true);

            accountTypeTextArea.setVisible(false);
            dobTextArea.setVisible(false);
            genderTextArea.setVisible(false);
            meritalStatusTextArea.setVisible(false);
            religionTextArea.setVisible(false);
            cardIdTypeTextArea.setVisible(false);

            uAccountType.setVisible(true);
            uGender.setVisible(true);
            uDob.setVisible(true);
            uMeritalStatus.setVisible(true);
            uReligion.setVisible(true);
            uIdcardType.setVisible(true);



            handeller=new DatabaseHandeller();
            ResultSet resultSet= null;
            try {
                resultSet = handeller.getManager();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            int c=0;
            while (true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if(resultSet.getString(1).equals("Manager"))c++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(c>0){
                uPin.setEditable(true);
                uPin.setVisible(true);
                uAnswar.setVisible(true);
                uAnswar.setEditable(true);
            }
        }
    }

    private class Cancle implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            accountTypeTextArea.setVisible(true);
            dobTextArea.setVisible(true);
            genderTextArea.setVisible(true);
            meritalStatusTextArea.setVisible(true);
            religionTextArea.setVisible(true);
            cardIdTypeTextArea.setVisible(true);

            uAccountType.setVisible(false);
            uGender.setVisible(false);
            uDob.setVisible(false);
            uMeritalStatus.setVisible(false);
            uReligion.setVisible(false);
            uIdcardType.setVisible(false);

            uSubmitButtion.setVisible(false);
            uCancleButton.setVisible(false);
            uUploadImageButton.setVisible(false);

            uFristname.setEditable(false);
            uLastname.setEditable(false);
            uIdCardNo.setEditable(false);
            uMobileNo.setEditable(false);
            uEmailId.setEditable(false);
            uDob.setEditable(false);
            uPincode.setEditable(false);
            uCity.setEditable(false);
            uAddress.setEditable(false);
            uPassword.setEditable(false);

            uPin.setEditable(false);
            uAnswar.setEditable(false);


            // Retrive Database Back to Normal

            try {
                retrivedata();
                uPin.setVisible(false);
                uAnswar.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private class UpdateUSerDetails implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            handeller=new DatabaseHandeller();
            ResultSet resultSet=null;
            try {
                if(uMobileNo.getText().matches("^\\d{10}$")){
                    if(uEmailId.getText().matches("\\S+@\\S+")){
                if(!uFristname.getText().equals("") && !uLastname.getText().equals("") && !uIdcardType.getValue().equals("") && !uIdCardNo.getText().equals("") && !uMobileNo.getText().equals("") && !uEmailId.getText().equals("") && !uGender.getValue().equals("") && !uMeritalStatus.getValue().equals("") && !uReligion.getValue().equals("") && uDob.getValue()!=null && !uPincode.getText().equals("") && !uCity.getText().equals("") && !uAddress.getText().equals("")
                        && !uAccountNo.getText().equals("") && !uAccountType.getValue().equals("") && !uUsername.getText().equals("") && !uPassword.getText().equals("") && !uSecurityQuestion.getText().equals("") && !uAnswar.getText().equals("")){

                    Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());
                    if(file!=null){
                        inputStream=new FileInputStream(file);
                    }
                    User user=new User(uFristname.getText(),uLastname.getText(),uIdcardType.getValue(),uIdCardNo.getText(),uMobileNo.getText(),uEmailId.getText(),uGender.getValue(),uMeritalStatus.getValue(),uReligion.getValue(),uDob.getValue().toString(),uAge.getText(),uPincode.getText(),uCity.getText(),uAddress.getText(),timestamp);
                    Accounts accounts=new Accounts(uAccountNo.getText(),uIfsc.getText(),uBalance.getText(),uAccountType.getValue(),uUsername.getText(),uPassword.getText(),uSecurityQuestion.getText(),uAnswar.getText(),inputStream,timestamp);
                    accounts.setPin(uPin.getText());


                        handeller.getUpdateUser(user);
                        handeller.getUpdateAccount(accounts);
                        System.out.println("Account Succesfully add");
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("!! Details Successfully Added to Data !!");
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                        alert.setHeaderText(null);
                        alert.setOnCloseRequest(event1 -> {
                           cancleAll();
                        });
                        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().setAll(new Image("/sample/assets/u.png"));
                        alert.show();
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
                    }
                    else {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Email Id Not Valid");
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                        alert.setHeaderText(null);
                        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().setAll(new Image("/sample/assets/download.png"));
                        alert.show();
                        }
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Mobile number Should have 10 Digits");
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

    private void cancleAll() {
        accountTypeTextArea.setVisible(true);
        dobTextArea.setVisible(true);
        genderTextArea.setVisible(true);
        meritalStatusTextArea.setVisible(true);
        religionTextArea.setVisible(true);
        cardIdTypeTextArea.setVisible(true);

        uAccountType.setVisible(false);
        uGender.setVisible(false);
        uDob.setVisible(false);
        uMeritalStatus.setVisible(false);
        uReligion.setVisible(false);
        uIdcardType.setVisible(false);

        uSubmitButtion.setVisible(false);
        uCancleButton.setVisible(false);
        uUploadImageButton.setVisible(false);

        uFristname.setEditable(false);
        uLastname.setEditable(false);
        uIdCardNo.setEditable(false);
        uMobileNo.setEditable(false);
        uEmailId.setEditable(false);
        uDob.setEditable(false);
        uPincode.setEditable(false);
        uCity.setEditable(false);
        uAddress.setEditable(false);
        uPassword.setEditable(false);

        uPin.setEditable(false);
        uAnswar.setEditable(false);


        // Retrive Database Back to Normal

        try {
            retrivedata();
            uPin.setVisible(false);
            uAnswar.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class UploadImage implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser=new FileChooser();
            file=fileChooser.showOpenDialog(uUploadImageButton.getScene().getWindow());
            if(file!=null) {
                try {
                    uProfilePic.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

