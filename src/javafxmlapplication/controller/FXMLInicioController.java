/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafxmlapplication.controller.*;


/**
 * @author svalero
 */
public class FXMLInicioController implements Initializable {

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
        count++;
        if (count % 2 == 0) {
            infoLabel.setText("");
            infoButton.setText("Show");
        } else {
            infoLabel.setText("To make a resrvation first Log In, or Sign Up if you don't have an account ");
            infoButton.setText("Hide");
        }
    }

    public void goReservations(MouseEvent event) {
        Stage currentStage = (Stage) reservationButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FXMLReservation.fxml"));
        FXMLReservationController reservationController = new FXMLReservationController();
        loader.setController(reservationController);
        FXMLReservationController sg = loader.getController();
        System.out.println(sg);
        try {
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.show();
        }catch (IOException ioException){
            System.out.println("No stage loaded");
        }
    }

    public void goLogIn(MouseEvent event) {
       /* Stage currentStage = (Stage) logInButton.getScene().getWindow();
        //switchToView(currentStage,"Login.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        LogInController logInController = new LogInController();
        loader.setController(logInController);
        try{
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        currentStage.setScene(newScene);
        currentStage.show();
        }catch(IOEXception ioException){
        System.out.println("No stage loaded");
        }*/
    }

    public void goSignUp(MouseEvent event){
        Stage currentStage = (Stage) signUpButton.getScene().getWindow();
        //switchToView(currentStage,"SignUp.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SignUp.fxml"));
        SignUpController signUpController = new SignUpController();
        loader.setController(signUpController);
       SignUpController sg = loader.getController();
        System.out.println(sg);
        try {
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.show();
        }catch (IOException ioException){
            System.out.println("No stage loaded");
        }
    }

    /*private void switchToView(Stage currentStage,String fxmlFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent root = loader.load();
        Scene newScene = new Scene(root);
        currentStage.setScene(newScene);
        currentStage.show();
    }*/
}