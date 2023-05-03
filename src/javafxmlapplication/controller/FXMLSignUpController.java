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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class FXMLSignUpController implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imagebg;

    private Stage stage;

    @FXML
    private Pane pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagebg.setPreserveRatio(true);
        imagebg.setSmooth(true);
        imagebg.setCache(true);
        pane.setPrefWidth(600);
        pane.setPrefHeight(400);

        pane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            imagebg.setX((pane.getWidth() - imagebg.getFitWidth())/2);
            imagebg.setFitWidth(pane.getWidth()+100);
            if (pane.getWidth() == MAXWIDTH) {
                imagebg.setFitWidth(MAXWIDTH);
                imagebg.setX((pane.getWidth() - imagebg.getFitWidth())/2);
                imagebg.setY((pane.getHeight() - imagebg.getFitHeight())/2);


            }

        });
        pane.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            imagebg.setY((pane.getHeight() - imagebg.getFitHeight())/2);
//            imagebg.setY(0);
            imagebg.setFitHeight(pane.getHeight()+100);
            if (pane.getHeight() == MAXHEIGHT) {
                imagebg.setFitHeight(MAXHEIGHT);
                imagebg.setFitWidth(MAXWIDTH);

            }
        });


    }

    public ImageView getImage(){
        return imagebg;
    }

}