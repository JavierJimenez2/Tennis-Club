/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;


import javafxmlapplication.model.JavaFXMLApplication;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class Main implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();

    public Text signUpText;
    public Text Title;
    public int count = 0;

    @FXML
    private Pane pane;
    @FXML
    private Button reservationButton;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button infoButton;
    @FXML
    private Label infoLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoLabel.setWrapText(true);
    }


    public void infoClicked(MouseEvent event) {
//

//        Dialog window
        JavaFXMLApplication.dialogBox("info","Information","To make a reservation first Log In, or Sign Up if you don't have an account");
    }


    public void goReservations(ActionEvent event) throws IOException {
////        JavaFXMLApplication.changeScene(event, "Reservation.fxml");
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
////        JavaFXMLApplication.saveScene(//currentScene);
//        Reservations reservations = new Reservations();
//        reservations.start(stage);
        JavaFXMLApplication.changeScene("Reservas.fxml");
    }

    public void goLogIn(ActionEvent event) throws IOException {
        JavaFXMLApplication.changeScene("LogIn.fxml");
    }

    public void goSignUp(ActionEvent event) throws IOException {
        JavaFXMLApplication.changeScene("SingUp.fxml");
    }

    public void infoClick(MouseEvent mouseEvent) {
    }

    public void infoRelease(MouseEvent mouseEvent) {
    }
}