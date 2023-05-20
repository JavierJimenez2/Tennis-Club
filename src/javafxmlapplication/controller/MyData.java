/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafxmlapplication.model.JavaFXMLApplication;
import model.Booking;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class MyData implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public ImageView profilePic;
    public Button returnButton;
    public ImageView profileImage;
    public Button selection;
    public TextField name;
    public TextField lastname;
    public TextField username;
    public PasswordField password;
    public TextField telephone;
    public TextField creditcard;
    public TextField csc;
    public Button modify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


   }




    private void showConfirmationWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Dialog");
        alert.setHeaderText("Are you sure you want to make this changes?");
        alert.setContentText("Click OK to confirm.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType == buttonTypeYes) {
                System.out.println("Confirmed!");

            } else if(buttonType == buttonTypeNo){
                System.out.println("Canceled.");
            }
        });
    }


    public void returnAction(ActionEvent actionEvent) {
    }

    public void selectionButton(ActionEvent actionEvent) {
    }

    public void modifyButton(ActionEvent actionEvent) {
    }
}