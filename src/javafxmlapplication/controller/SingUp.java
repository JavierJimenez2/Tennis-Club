/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import com.sun.javafx.iio.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxmlapplication.model.JavaFXMLApplication;
import model.*;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;


public class SingUp implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static boolean correctFormat = true;
    public Button returnButton;
    public TextField csc;
    @FXML
    public VBox field1;
    @FXML
    public VBox field2;

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
    private PasswordField password;

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

    @FXML
    private VBox fieldsInputs;
    @FXML
    private ComboBox<ImageView> samplesBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        profileImage.setImage() is in javafxmlapplication/view/css/img/icons/user.png
        profileImage.setImage(new Image("javafxmlapplication/view/css/img/icons/avatar_icon.png"));




        samplesBox.getItems().addAll(JavaFXMLApplication.getAvatars());



//        only show above when focus
        TextField[] textFields = {name, lastname, username, password, creditcard, csc, telephone};
        for ( TextField textField : textFields ) {
            extracted(textField.getPromptText(), textField);
        }

//        si ha entrado en name y no ha puesto nada
        TextField[] requiredFields = {name, lastname, username, password,telephone};
        for ( TextField requiredField : requiredFields ) {
            correctFields(requiredField.getPromptText(), requiredField);
        }

        JavaFXMLApplication.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                register.fire();
            }
        });

        try {
            System.setErr(new PrintStream("NUL"));
        } catch (Exception e) {
        }
        samplesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                profileImage.setImage(newValue.getImage());
                samplesBox.getItems().removeAll(samplesBox.getItems());

                samplesBox.getItems().addAll(JavaFXMLApplication.getAvatars());

            }
        });


    }

    private void correctFields(String Prompt,TextField name) {
        name.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if ( !t1 && name.getText().isEmpty() ) {
                name.getStyleClass().add("error");
                correctFormat = false;
                name.setStyle("-fx-border-color: red;-fx-prompt-text-fill: red;");
                name.setPromptText(Prompt+" is required");
            } else {
                name.setPromptText("");
                name.setStyle("-fx-border-color: #08861d;-fx-prompt-text-fill: #08861d;-fx-font-fill: #08861d;");
                correctFormat = true;
            }
        });
    }

    private void extracted(String s, TextField name) {
        Label nameLabel = new Label(s);
        nameLabel.setVisible(false);
        name.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if ( t1 ) {
                nameLabel.setVisible(true);
                StackPane namePane = new StackPane();
                StackPane.setAlignment(nameLabel, Pos.TOP_LEFT);
                nameLabel.paddingProperty().setValue(new javafx.geometry.Insets(1));
                TranslateTransition translateTransition = new TranslateTransition();
                translateTransition.setDuration(javafx.util.Duration.millis(200));
                translateTransition.setNode(nameLabel);
                translateTransition.setFromX(0);
                translateTransition.setFromY(0);
                translateTransition.setToX(0);
                translateTransition.setToY(-9);
                translateTransition.play();
                nameLabel.getStyleClass().add("inputPane");
                name.setPromptText("");

//        translate namePane in the border of the text field
                int index=0;
                if(name.equals(creditcard)){
                    index = field1.getChildren().indexOf(name);
                    if ( index != -1 ) {
                        field1.getChildren().set(index, namePane);
                        namePane.getChildren().addAll(name, nameLabel);
                    }
                } else if ( name.equals(csc) ) {
                    index = field2.getChildren().indexOf(name);
                    if ( index != -1 ) {
                        field2.getChildren().set(index, namePane);
                        namePane.getChildren().addAll(name, nameLabel);
                    }
                }else{
                    index = fieldsInputs.getChildren().indexOf(name);
                    if ( index != -1 ) {
                        fieldsInputs.getChildren().set(index, namePane);
                        namePane.getChildren().addAll(name, nameLabel);
                    }
                }

            } else {
//                nameLabel.setVisible(false);
            }
        });
    }


    @FXML
    void selectionButton(ActionEvent event) {
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

    @FXML
    void registerButton(ActionEvent eventReg) throws ClubDAOException, IOException {
        String name = this.name.getText();
        String lastname = this.lastname.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String creditcard = this.creditcard.getText();
        String Scsc = this.csc.getText();
        String telephone = this.telephone.getText();
        int Icsc;


        ///////////////////name checkings/////////////////////////////////////////////////////////////
        if ( !name.matches("[a-zA-Z ]*") || name.isEmpty()) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Name Field. Remember to write just characters.");
            this.name.setText("");
            this.name.requestFocus();
            correctFormat = false;
        }



        ///////////////////lastname checkings/////////////////////////////////////////////////////////////
        if ( !lastname.matches("[a-zA-Z ]*") || lastname.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Last Name Field. Remember to write just characters.");
            this.lastname.setText("");
            this.lastname.requestFocus();
            correctFormat = false;
        }

        ///////////////////telephone checkings/////////////////////////////////////////////////////////////
        if ( !telephone.matches("^[0-9]+$") || telephone.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Telephone Number Field. Remember to write just numbers.");
            this.telephone.setText("");
            this.telephone.requestFocus();
            correctFormat = false;
        }

        /////////////////////username checkings///////////////////////////////////////////////////////////
        if ( JavaFXMLApplication.getCurrentClub().existsLogin(username) ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Username Field. Username already in use.");
            this.username.setText("");
            this.username.requestFocus();
            correctFormat = false;
        }
        if ( username.matches(".*\\s.*") ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Username Field. Remember it can't contain spaces.");
            this.username.setText("");
            this.username.requestFocus();
            correctFormat = false;
        }

//        if  username has more than 10 characters
        if ( username.length() > 6) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Username Field. Remember it can't contain more " +
                    "than 6 characters.");
            this.username.setText("");
            this.username.requestFocus();
            correctFormat = false;
        }

        if ( username.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Username Field. Remember it can't be empty.");
            this.username.setText("");
            this.username.requestFocus();
            correctFormat = false;
        }

        /////////////////////password checkings///////////////////////////////////////////////////////////
        if ( password.length() < 6 ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Password Field. Remember it should contain at least 6 characters.");
            this.password.setText("");
            this.password.requestFocus();
            correctFormat = false;
        }
        /////////////////////credit card checkings///////////////////////////////////////////////////////////
        if ( creditcard.length() != 16 && !creditcard.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Credit Card Field. Remember it should contain 16 numb ers.");
            this.creditcard.setText("");
            this.creditcard.requestFocus();
            correctFormat = false;
        }
        if ( !creditcard.matches("\\d+") && !creditcard.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Credit Card Field. Remember it should contain 16 numbers.");
            this.creditcard.setText("");
            this.creditcard.requestFocus();
            correctFormat = false;
        }

        if ( (Scsc.length() != 3 || !Scsc.matches("^[0-9]+$")) && !Scsc.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in CSC Field. Remember it should contain 3 digits.");
            this.csc.setText("");
            this.csc.requestFocus();
            correctFormat = false;
        }

        if ( !creditcard.isEmpty() && Scsc.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in CSC Field. Remember to write the CSC.");
            correctFormat = false;
            this.csc.requestFocus();
        }
        if ( creditcard.isEmpty() && !Scsc.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Credit Card Field. Remember to write the Credit Card.");
            correctFormat = false;
            this.creditcard.requestFocus();
        }

        if ( !creditcard.isEmpty() && !Scsc.isEmpty() ){
//            confirmation , as you insert your credit card your reservation will be paid automatically
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("As you insert your credit card your reservation will be paid automatically.");
            alert.setContentText("If you want to pay later, please leave the credit card field empty.\nAre you sure " +
                    "you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
            } else {
                // ... user chose CANCEL or closed the dialog
                correctFormat = false;
            }
        }


        if ( !correctFormat ) {
            return;
        }
        if ( !Scsc.isEmpty() ) {
            Icsc = Integer.parseInt(Scsc);          //hay que mandar un int al constructor aunque no haya targ/csc puesto
        } else {                                     //asi que mando 0.    0 = null --> hay que hacer cambios convenientes al
            Icsc = 0;                              //al mostrar datos -> if 0 then show "".
        }
        //Club club = Club.getInstance();
        JavaFXMLApplication.getCurrentClub().registerMember(name, lastname, telephone, username, password, creditcard, Icsc, profileImage.getImage());

        JavaFXMLApplication.dialogBox("success", "Success", "You have been registered successfully");
        JavaFXMLApplication.changeScene("LogIn.fxml");


    }

    @FXML
    void selectSampleAction(ActionEvent event) {
//        profileImage.setImage(samplesBox.getValue().getImage());
//        samplesBox.setPromptText("Avatar selected");
//        int pos = samplesBox.getSelectionModel().getSelectedIndex();
////        samplesBox.getItems().removeAll(samplesBox.getItems());
//        samplesBox.getItems().clear();
//        samplesBox.setItems(JavaFXMLApplication.getAvatars());
////        samplesBox.getItems().addAll(JavaFXMLApplication.getAvatars());
//        samplesBox.getSelectionModel().select(pos);
//        samplesBox.setPromptText("Select an avatar");
////        samplesBox.getCellFactory();
//
//
//
    }

    public void returnAction(ActionEvent actionEvent) {
        JavaFXMLApplication.changeScene("Main.fxml");
    }
}