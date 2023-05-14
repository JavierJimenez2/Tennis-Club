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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;


public class SingUp implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();

    @FXML
    private HBox buttonsZone;

    @FXML
    private TextField creditcard;

    @FXML
    private VBox intPane;

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private HBox pane;

    @FXML
    private TextField password;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button register;

    @FXML
    private Button selection;

    @FXML
    private TextField telephone;

    @FXML
    private TextField username;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {





    }


    @FXML
    void selectionButton(ActionEvent event) {

    }

    @FXML
    void registerButton(ActionEvent event) {

    }


}