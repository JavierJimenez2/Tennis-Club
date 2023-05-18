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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafxmlapplication.model.JavaFXMLApplication;

import javafx.beans.binding.Bindings;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.Booking;
import model.Club;
import model.Member;


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
    public ImageView profilePic;
    public Button returnButton;

    ////////////////////////////////////////////////////////////////////////////////////
    //listView observable
    private ObservableList<Booking> myObservableBookingList = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set User's profile image
        profilePic.setImage(JavaFXMLApplication.getCurrentMember().getImage());



        ArrayList<Booking> bookingData = JavaFXMLApplication.getCurrentClub().getBookings();

        //create observable list using FXCollections
        myObservableBookingList = FXCollections.observableArrayList(bookingData);

        //link the booking observable list with listView
        listView.setItems(myObservableBookingList);

        //Modify cell factory to display object Booking
        listView.setCellFactory(c -> new bookingListCell());

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


    public void deleteAction(ActionEvent actionEvent) {
        showConfirmationWindow();
    }

    private void showConfirmationWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Dialog");
        alert.setHeaderText("Are you sure you want to delete?");
        alert.setContentText("Click OK to confirm.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType == buttonTypeYes) {
                System.out.println("Confirmed!");
                myObservableBookingList.remove(listView.getSelectionModel().getSelectedItem());  //removes booking from listView
                //Club.removeBooking(Booking b);  //removes booking from database
            } else if(buttonType == buttonTypeNo){
                System.out.println("Canceled.");
            }
        });
    }

    public void returnAction(ActionEvent actionEvent) {
        JavaFXMLApplication.returnScene(actionEvent);
    }

    class bookingListCell extends ListCell<Booking>{
        @Override
        protected  void updateItem(Booking booking, boolean empty){
            super.updateItem(booking,empty);
            if(empty || booking == null){
                setText(null);
            }else{
                setText("By: " + booking.getMember().toString() + ".Date: " + booking.getBookingDate().toString() + ". Paid: " +
                        booking.getPaid());
            }

        }
    }
}