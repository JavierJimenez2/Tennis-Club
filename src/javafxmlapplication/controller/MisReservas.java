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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafxmlapplication.model.JavaFXMLApplication;

import javafx.beans.binding.Bindings;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.Booking;
import model.Club;
import model.ClubDAOException;
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

       if( listView.getSelectionModel().getSelectedItem().isOlderForDay(LocalDate.now())){   //checks if booking was of a previous day
          JavaFXMLApplication.dialogBox("error","Cancel Error","Court was already used. Can't cancel reservation.");
          return;
       }

       //check if canceled reservation is in less than 24h
       if(Duration.between(listView.getSelectionModel().getSelectedItem().getBookingDate(), LocalDateTime.now()).compareTo(Duration.ofHours(24)) <= 0){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Warning Dialog");
           alert.setHeaderText("Are you sure you want to continue?");
           alert.setContentText("A 10$ fee will be charged due to your reservation being in less than 24h.");

           ButtonType buttonTypeYes = new ButtonType("Yes");
           ButtonType buttonTypeNo = new ButtonType("No");

           alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

           alert.showAndWait().ifPresent(buttonType -> {
               if(buttonType == buttonTypeYes) {
                   System.out.println("Confirmed!");
               } else if(buttonType == buttonTypeNo){
                   System.out.println("Canceled.");
                   return;
               }
           });
       }


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
               try {
                   JavaFXMLApplication.getCurrentClub().removeBooking(listView.getSelectionModel().getSelectedItem());  //removes booking from database
               }catch (ClubDAOException e){System.out.println("Error removing the booking.");}
            } else if(buttonType == buttonTypeNo){
                System.out.println("Canceled.");
            }
        });
    }

    /*public void returnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        JavaFXMLApplication.returnScene(stage);
    }*/

    class bookingListCell extends ListCell<Booking>{
        @Override
        protected  void updateItem(Booking booking, boolean empty){
            super.updateItem(booking,empty);
            if(empty || booking == null){
                setText(null);
            }else{
                setText( booking.getMadeForDay() + "" +  booking.getCourt() + "" + booking.getFromTime() + " - " + booking.getFromTime().plusHours(1) + "" + booking.getPaid());
            }

        }
    }
}