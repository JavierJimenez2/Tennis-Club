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
import javafx.stage.Screen;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class FXMLInicioController implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();

    @FXML
    private Pane pane;
    private Button reservationButton;
    private Button logInButton;
    private Button signUpButton;
    private Button infoButton;
    private Label infoLabel;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {





    }


    private void infoClick(ActionEvent event){
        infoLabel.setText("To make a resrvation first Log In, " +
                "or Sign Up if you don't have an account ");
    }
    private void infoRelease(ActionEvent event){
        infoLabel.setText("");
    }


}