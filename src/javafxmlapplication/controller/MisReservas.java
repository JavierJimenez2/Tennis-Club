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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafxmlapplication.model.JavaFXMLApplication;
import model.Booking;
import javafx.beans.binding.Bindings;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class MisReservas implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public Button deleteButton;
    public SplitPane intPane;
    public Pane pane;
    public ListView<Booking> listView;

    ////////////////////////////////////////////////////////////////////////////////////
    //listView observable
    private ObservableList<Booking> myObservableBookingList = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Booking> bookingData = new ArrayList<>();

        //create observable list using FXCollections
        myObservableBookingList = FXCollections.observableArrayList(bookingData);

        //link the booking observable list with listView
        listView.setItems(myObservableBookingList);

        //Modify cell factory to display object Booking
        listView.setCellFactory(c -> new ListCell<>());

        //in case of nothing selected disable delete
        deleteButton.disableProperty().bind(
                Bindings.equal(-1,
                      listView.getSelectionModel().selectedIndexProperty()));;

        //listView focus listener
    /* listView.focusedProperty().addListener((a, b, c) -> {
            if(listView.isFocused()){

            }

        } */


   }



}