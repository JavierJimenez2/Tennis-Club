/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class LoginController implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public HBox buttonsZone;

    @FXML
    private Pane pane;
    private TextField emailValid;
    private TextField passwordValid;
    private Button doneButton;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {





    }


}