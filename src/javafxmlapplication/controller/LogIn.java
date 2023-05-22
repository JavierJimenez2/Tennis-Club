/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafxmlapplication.model.JavaFXMLApplication;
import javafxmlapplication.model.Reservations;
import model.Club;
import model.ClubDAOException;
import model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class LogIn implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public HBox buttonsZone;
    public PasswordField password;
    public Button returnButton;

    @FXML
    private Pane pane;
    @FXML
    private TextField username;
    @FXML
    private Button doneButton;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }



    @FXML
    public void loginButton(ActionEvent eventLog) throws ClubDAOException, IOException {
        String user = username.getText();
        String pass = password.getText();
        //Club club = JavaFXMLApplication.getCurrentClub();
        if(JavaFXMLApplication.getCurrentClub().existsLogin(user)){
        Member member = JavaFXMLApplication.getCurrentClub().getMemberByCredentials(user, pass);
            // No usamos el "Reservation.fxml".
            // JavaFXMLApplication.changeScene(eventLog, "Reservation.fxml");
            JavaFXMLApplication.setCurrentMember(member);
            Stage stage = (Stage) ((Node) eventLog.getSource()).getScene().getWindow();
            Reservations reservations = new Reservations();
            reservations.start(stage);
        } else {
            JavaFXMLApplication.dialogBox("error", "Error", "Usuario o contraseña incorrectos");
            username.setText("");
            password.setText("");
        }
    }

    public void returnAction(ActionEvent actionEvent) {
        JavaFXMLApplication.changeScene(actionEvent,"Main.fxml");
    }
}