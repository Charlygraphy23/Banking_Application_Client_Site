package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Transitions.Shaker;
import sample.database.DatabaseHandeller;
import sample.model.Accounts;

import java.sql.SQLException;

public class PinpageController {

    @FXML
    private Label usernamePlate;

    @FXML
    private JFXPasswordField newPinTextbox;

    @FXML
    private JFXPasswordField confirmPinTextbox;

    @FXML
    private JFXButton pinSubmintButton;

    @FXML
    void close(MouseEvent event) {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
    }

    @FXML
    void initialize() {

        newPinTextbox.setOnKeyReleased(e->{
            if(newPinTextbox.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.TAB) ) {newPinTextbox.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }else{newPinTextbox.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(newPinTextbox);}
        });

        confirmPinTextbox.setOnKeyReleased(e->{
            if(confirmPinTextbox.getText().matches("\\d+") || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.TAB) ) {confirmPinTextbox.setStyle("-fx-background-color: white; -fx-text-fill: black");
            }else{confirmPinTextbox.setStyle("-fx-background-color: red; -fx-text-fill: white");
                new Shaker(confirmPinTextbox);}
        });

       pinSubmintButton.setOnAction(e->{
           if(!newPinTextbox.getText().equals("") && !confirmPinTextbox.getText().equals("")){
               if(newPinTextbox.getText().matches("^\\d{4}$")) {
                   if (newPinTextbox.getText().equals(confirmPinTextbox.getText())) {

                       DatabaseHandeller handeller=new DatabaseHandeller();
                       Accounts accounts=new Accounts();
                       accounts.setPin(confirmPinTextbox.getText());
                       try {
                           handeller.updatePin(accounts);

                           Alert alert=new Alert(Alert.AlertType.INFORMATION);
                           alert.setContentText("Pin Successfully Added \n You Can change it In change Pin Section");
                           alert.getButtonTypes().setAll(ButtonType.CLOSE);
                           alert.setHeaderText(null);
                           Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                           stage.getIcons().setAll(new Image("/sample/assets/u.png"));

                           alert.show();
                           alert.setOnCloseRequest(event->{
                               pinSubmintButton.getScene().getWindow().hide();
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
       });

    }

}
