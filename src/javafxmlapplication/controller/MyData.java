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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafxmlapplication.model.JavaFXMLApplication;
import model.Booking;
import model.ClubDAOException;
import model.Member;

import java.io.File;
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
    private static boolean correctFormat = true;
    private static boolean check = false;
    private static File selectedFile = null;
    public ImageView profilePic;
    public Button returnButton;
    public ImageView profileImage;
    public Button selection;
    public TextField name;
    public TextField lastname;

    public PasswordField password;
    public TextField telephone;
    public TextField creditcard;
    public TextField csc;
    public Button modify;
    public TextField username;
    @FXML
    private ComboBox<ImageView> samplesBox;
    private boolean savedData;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     profileImage.setImage(new javafx.scene.image.Image("javafxmlapplication/view/css/img/icons/user.png"));


     samplesBox.getItems().addAll(JavaFXMLApplication.getAvatars());


    name.setText(JavaFXMLApplication.getCurrentMember().getName());
    lastname.setText(JavaFXMLApplication.getCurrentMember().getSurname());
    username.setText(JavaFXMLApplication.getCurrentMember().getNickName());
    password.setText(JavaFXMLApplication.getCurrentMember().getPassword());
    telephone.setText(JavaFXMLApplication.getCurrentMember().getTelephone());
    if(!JavaFXMLApplication.getCurrentMember().getCreditCard().isEmpty() && JavaFXMLApplication.getCurrentMember().getSvc() != 0){
        creditcard.setText(JavaFXMLApplication.getCurrentMember().getCreditCard());
        csc.setText(Integer.toString(JavaFXMLApplication.getCurrentMember().getSvc()));
    } else{
        creditcard.setPromptText("Credit Card Number (Optional)");
        csc.setPromptText("Credit Card Number (Optional)");
    }
    profileImage.setImage(JavaFXMLApplication.getCurrentMember().getImage());
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
        if ( !savedData ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Dialog");
            alert.setHeaderText("Are you sure you want to go back? The changes won't be saved.");
            alert.setContentText("Click OK to confirm.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if(buttonType == buttonTypeYes) {
                    System.out.println("Confirmed!");
                    JavaFXMLApplication.changeScene("Reservas.fxml");
                } else if(buttonType == buttonTypeNo){
                    System.out.println("Canceled.");
                }
            });
        }
        JavaFXMLApplication.changeScene("Reservas.fxml");
    }

    public void selectionButton(ActionEvent event) {
//        Select an image from the computer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        if ( selectedFile != null ) {
            String path = "src/javafxmlapplication/view/css/img/profiles/" + selectedFile.getName();
            JavaFXMLApplication.copyFile(selectedFile, new File(path));
            profileImage.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        }else {
            profileImage.setImage(new javafx.scene.image.Image("javafxmlapplication/view/css/img/icons/avatar_icon" +
                    ".png"));
        }
    }

    public void modifyButton(ActionEvent actionEvent) {
        savedData = true;
        String name = this.name.getText();
        String lastname = this.lastname.getText();
        String password = this.password.getText();
        String creditcard = this.creditcard.getText();
        String Scsc = this.csc.getText();
        String telephone = this.telephone.getText();




        ///////////////////name checkings/////////////////////////////////////////////////////////////
        if(!name.matches("[a-zA-Z]*") || name.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Name Field. Remember to write just characters.");
            this.name.setText("");
            correctFormat = false;
        }

        ///////////////////lastname checkings/////////////////////////////////////////////////////////////
        if(!lastname.matches("[a-zA-Z]*") || lastname.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Last Name Field. Remember to write just characters.");
            this.lastname.setText("");
            correctFormat = false;
        }

        ///////////////////telephone checkings/////////////////////////////////////////////////////////////
        if(!telephone.matches("^[0-9]+$") || telephone.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Telephone Number Field. Remember to write just numbers.");
        }

        /////////////////////password checkings///////////////////////////////////////////////////////////
        if(password.length() < 6){
            JavaFXMLApplication.dialogBox("error","Error","Error in Password Field. Remember it should contain at least 6 characters.");
            this.password.setText("");
            correctFormat = false;
        }
        /////////////////////credit card checkings///////////////////////////////////////////////////////////
        if(creditcard.length() != 16 && !creditcard.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Credit Card Field. Remember it should contain 16 numb ers.");
            this.creditcard.setText("");
            correctFormat = false;
        }
        if(!creditcard.matches("\\d+") && !creditcard.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Credit Card Field. Remember it should contain 16 numbers.");
            this.creditcard.setText("");
            correctFormat = false;
        }

        if((Scsc.length() != 3 || !Scsc.matches("^[0-9]+$")) && !Scsc.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in CSC Field. Remember it should contain 3 digits.");
            this.csc.setText("");
            correctFormat = false;
        }

        if(!creditcard.isEmpty() && Scsc.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in CSC Field. Remember to write the CSC.");
            correctFormat = false;
        }
        if(creditcard.isEmpty() && !Scsc.isEmpty()){
            JavaFXMLApplication.dialogBox("error","Error","Error in Credit Card Field. Remember to write the Credit Card.");
            correctFormat = false;
        }



        if(!correctFormat){
            return;
        }
        //confirmation Window
        showConfirmationWindowMD();
        if(!check){
            return;
        }




       //update of the fields
        if(!name.equals(JavaFXMLApplication.getCurrentMember().getName())){
            JavaFXMLApplication.getCurrentMember().setName(name);
        }
        if(!lastname.equals(JavaFXMLApplication.getCurrentMember().getSurname())){
            JavaFXMLApplication.getCurrentMember().setSurname(lastname);
        }
        if(!telephone.equals(JavaFXMLApplication.getCurrentMember().getTelephone())){
            JavaFXMLApplication.getCurrentMember().setTelephone(telephone);
        }

        if(!password.equals(JavaFXMLApplication.getCurrentMember().getPassword())){
            JavaFXMLApplication.getCurrentMember().setPassword(password);
        }
        if (!creditcard.equals(JavaFXMLApplication.getCurrentMember().getCreditCard())){
            JavaFXMLApplication.getCurrentMember().setCreditCard(creditcard);
        }

        if(JavaFXMLApplication.getCurrentMember().getSvc() == 0) {
            if(!Scsc.isEmpty()){
                JavaFXMLApplication.getCurrentMember().setSvc(Integer.parseInt(Scsc));
            }
        } else {
            if (!Scsc.equals(Integer.toString(JavaFXMLApplication.getCurrentMember().getSvc()))) {
                JavaFXMLApplication.getCurrentMember().setSvc(Integer.parseInt(Scsc));
            }
        }


        //if(selectedFile != null){
            JavaFXMLApplication.getCurrentMember().setImage(profileImage.getImage());
            //new javafx.scene.image.Image(selectedFile.toURI().toString())
        //}

    }

    private void showConfirmationWindowMD(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Dialog");
        alert.setHeaderText("Are you sure you want to make these changes?");
        alert.setContentText("Click OK to confirm.");

        ButtonType buttonTypeYes = new ButtonType("OK");
        ButtonType buttonTypeNo = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType == buttonTypeYes) {

               check = true;

            } else if(buttonType == buttonTypeNo){

                check = false;
            }
        });
    }

    @FXML
    void selectSampleAction(ActionEvent event) {
        profileImage.setImage(samplesBox.getValue().getImage());
        samplesBox.setPromptText("Avatar selected");
        int pos = samplesBox.getSelectionModel().getSelectedIndex();
        samplesBox.getItems().removeAll(samplesBox.getItems());
        samplesBox.getItems().addAll(JavaFXMLApplication.getAvatars());
        samplesBox.getSelectionModel().select(pos);
        samplesBox.setPromptText("Select an avatar");
        samplesBox.getCellFactory();



    }
}