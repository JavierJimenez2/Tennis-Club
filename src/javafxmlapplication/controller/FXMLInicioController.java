/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class FXMLInicioController implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public Text signUpText;
    public Text Title;

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





    }



    public void infoClick(javafx.scene.input.MouseEvent event) {
        infoLabel.setText("To make a resrvation first Log In, " +
                "or Sign Up if you don't have an account ");
    }

    public void infoRelease(javafx.scene.input.MouseEvent event) {
        infoLabel.setText("");

    }
}