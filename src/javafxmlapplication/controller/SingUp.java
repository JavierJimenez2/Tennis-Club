/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafxmlapplication.model.JavaFXMLApplication;
import model.ClubDAOException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SingUp implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static boolean correctFormat = true;
    public Button returnButton;
    public TextField csc;

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        profileImage.setImage() is in javafxmlapplication/view/css/img/icons/user.png
        profileImage.setImage(new javafx.scene.image.Image("javafxmlapplication/view/css/img/icons/avatar_icon.png"));



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
                namePane.setAlignment(nameLabel, Pos.TOP_LEFT);
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
                fieldsInputs.getChildren().set(fieldsInputs.getChildren().indexOf(name), namePane);
                namePane.getChildren().addAll(name, nameLabel);
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
        if ( !name.matches("[a-zA-Z]*") || name.isEmpty() ) {
            JavaFXMLApplication.dialogBox("error", "Error", "Error in Name Field. Remember to write just characters.");
            this.name.setText("");
            this.name.requestFocus();
            correctFormat = false;
        }

        ///////////////////lastname checkings/////////////////////////////////////////////////////////////
        if ( !lastname.matches("[a-zA-Z]*") || lastname.isEmpty() ) {
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

    public void returnAction(ActionEvent actionEvent) {
        JavaFXMLApplication.changeScene("Main.fxml");
    }
}