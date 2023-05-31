/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;




import javafxmlapplication.model.JavaFXMLApplication;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;


/**
 * @author svalero
 */
public class Main implements Initializable {

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
    private VBox intPane;
    @FXML
    private StackPane stackPane;
    private Circle spinner;
    private VBox spinnerBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinner = new Circle(30);
        spinner.setId("spinner");
        spinnerBox = new VBox();
        spinnerBox.getChildren().add(spinner);
        spinnerBox.setId("spinnerBox");
        spinnerBox.setAlignment(javafx.geometry.Pos.CENTER);
        stackPane.getChildren().add(1, spinnerBox);
        spinnerBox.setVisible(false);
//        set black to the hole screen
        pane.setStyle("-fx-background-color: #000000");
    }

    private static void rotate(Circle spinner, VBox spinnerBox) {
        spinnerBox.setVisible(true);
        spinnerBox.getChildren().add(1, new Label("Loading..."));


        Animation animation = new Transition() {
            {
                setCycleDuration(javafx.util.Duration.millis(2000));
            }

            @Override
            protected void interpolate(double frac) {
                final int length = 10;
                int index = (int) (frac * (length + 1));
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < index; i++) {
                    builder.append(".");
                }
                spinner.setRotate(frac * 360);
            }
        };
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }


    public void infoClicked(MouseEvent event) {
//

//        Dialog window
        JavaFXMLApplication.dialogBox("info","Information","To make a reservation first Log In, or Sign Up if you don't have an account.");
    }

    void showProgressIndicator() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        pane.getChildren().add(progressIndicator);
    }

    void hideProgressIndicator() {
        pane.getChildren().remove(pane.getChildren().size() - 1);
    }
    public void goReservations(ActionEvent event){
        rotate(spinner, spinnerBox);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
                JavaFXMLApplication.changeScene("Reservations.fxml");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
//        JavaFXMLApplication.changeScene("Reservations.fxml");
    }

    public void goLogIn(ActionEvent event){
        JavaFXMLApplication.changeScene("LogIn.fxml");
    }

    public void goSignUp(ActionEvent event){
        JavaFXMLApplication.changeScene("SignUp.fxml");
    }

    public void infoClick(MouseEvent mouseEvent) {
    }

    public void infoRelease(MouseEvent mouseEvent) {
    }
}