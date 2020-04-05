package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.Transitions.ReverseSlider;
import sample.Transitions.Slider;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Predicate;

public class StaffDashboardController {

    @FXML
    private AnchorPane welcomeScreen;

    @FXML
    private Circle wPhoto;

    @FXML
    private Label wUsername;

    @FXML
    private Label wNextButton;

    @FXML
    private BorderPane staffPane;

    @FXML
    private Circle hProfilePic;

    @FXML
    private Label hFullNameplate;

    @FXML
    private Label hEmailAddress;

    @FXML
    private Label hUsername;

    @FXML
    private Label hMobileno;

    @FXML
    private Label hDateofBirth;

    @FXML
    private Label hAge;

    @FXML
    private Label hDesignation;

    @FXML
    private Label hGender;

    @FXML
    private Label hDateofJoinning;

    @FXML
    private AnchorPane homeScreen;

    @FXML
    private AnchorPane accountDetailsScreen;

    @FXML
    private JFXButton hHomeButton;

    @FXML
    private JFXButton hAccounDetailsButton;

    @FXML
    private Label hAddress;

    @FXML
    private JFXTextField searchAccountNo;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXListView<Accounts> listOfAllAccounts;



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

    private DatabaseHandeller handeller;
    private ObservableList<Accounts> accountsObservableList;
    private int index=-1;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        wNextButton.setOnMouseClicked(new NextButtonClicked());
        hHomeButton.setOnAction(e->{
            homeScreen.setVisible(true);
            accountDetailsScreen.setVisible(false);
        });

        hAccounDetailsButton.setOnAction(e->{
            homeScreen.setVisible(false);
            accountDetailsScreen.setVisible(true);

            handeller=new DatabaseHandeller();
            try {
                ResultSet getAccountResultSet = handeller.getAllAccounts();
                accountsObservableList = FXCollections.observableArrayList();
                while (getAccountResultSet.next()) {
                    Accounts accounts = new Accounts();
                    accounts.setUsername(getAccountResultSet.getString("username"));
                    accounts.setUserId(getAccountResultSet.getInt("userid"));
                    accounts.setIfscno(getAccountResultSet.getString("ifsc"));
                    accounts.setAccuntNo(getAccountResultSet.getString("accountno"));
                    accounts.setBalance(getAccountResultSet.getString("balance"));
                    accounts.setLastUpdate(getAccountResultSet.getTimestamp("latupdate"));
                    accountsObservableList.addAll(accounts);
                }
                listOfAllAccounts.setItems(accountsObservableList);
                listOfAllAccounts.setCellFactory(ListCellController -> new ListCellController());
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        handeller=new DatabaseHandeller();
        ResultSet resultSet=handeller.getStaffInformation();
        while (resultSet.next()){
            wPhoto.setFill(new ImagePattern(new Image(resultSet.getBinaryStream("photo"))));
            wUsername.setText("Welcome - "+resultSet.getString("username"));

            hProfilePic.setFill(new ImagePattern(new Image(resultSet.getBinaryStream("photo"))));
            hFullNameplate.setText(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
            hUsername.setText("Username - "+resultSet.getString("username"));
            hMobileno.setText("Mobile No - "+resultSet.getString("mobileno"));
            hDateofBirth.setText("Date of birth - "+resultSet.getString("dob"));
            hAge.setText("Age - "+resultSet.getString("age"));
            hDesignation.setText("Designation - "+resultSet.getString("designation"));
            hGender.setText("Gender - "+hGender);
            hDateofJoinning.setText("Date of joinning - "+resultSet.getString("dateofjoinning"));
            hEmailAddress.setText("Email - "+resultSet.getString("emailid"));
            hAddress.setText("Address - "+resultSet.getString("address"));
        }


        searchButton.setOnAction(e->{
            for(int i=0;i<accountsObservableList.size();i++){
                if(accountsObservableList.get(i).getAccuntNo().equals(searchAccountNo.getText())){
                    index=i;
                }
            }

            if(index==-1){
                listOfAllAccounts.setItems(null);
            }
            else {
                System.out.println("Index " + index);
                listOfAllAccounts.getSelectionModel().select(index);
                listOfAllAccounts.getFocusModel().focus(index);
                ObservableList<Accounts> newValue = FXCollections.observableArrayList();
                newValue.addAll(accountsObservableList.get(index));
                listOfAllAccounts.setItems(newValue);
                listOfAllAccounts.setCellFactory(ListCellController -> new ListCellController());
                index=-1;
            }

        });

        searchAccountNo.setOnKeyReleased(e->{
            if(searchAccountNo.getText().equals("")){
                try {
                    ResultSet getAccountResultSet = handeller.getAllAccounts();
                    accountsObservableList = FXCollections.observableArrayList();
                    while (getAccountResultSet.next()) {
                        Accounts accounts = new Accounts();
                        accounts.setUsername(getAccountResultSet.getString("username"));
                        accounts.setUserId(getAccountResultSet.getInt("userid"));
                        accounts.setIfscno(getAccountResultSet.getString("ifsc"));
                        accounts.setAccuntNo(getAccountResultSet.getString("accountno"));
                        accounts.setBalance(getAccountResultSet.getString("balance"));
                        accounts.setLastUpdate(getAccountResultSet.getTimestamp("latupdate"));
                        accountsObservableList.addAll(accounts);
                    }
                    listOfAllAccounts.setItems(accountsObservableList);
                    listOfAllAccounts.setCellFactory(ListCellController -> new ListCellController());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

            else {
                for(int i=0;i<accountsObservableList.size();i++){
                    if(accountsObservableList.get(i).getAccuntNo().equals(searchAccountNo.getText())){
                        index=i;
                    }
                }
                if(index!=-1){

                    ObservableList<Accounts> newValue = FXCollections.observableArrayList();
                    newValue.addAll(accountsObservableList.get(index));
                    listOfAllAccounts.setItems(newValue);
                    listOfAllAccounts.setCellFactory(ListCellController -> new ListCellController());
                    index=-1;
                }
                else {

                    listOfAllAccounts.setItems(null);
                }
            }
        });

    }                       //  End of Initialize

    //
    //
    //
    //
    //



    private class NextButtonClicked implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {


            new ReverseSlider(welcomeScreen);
            staffPane.setVisible(true);
            new Slider(staffPane,welcomeScreen);


        }
    }
}
