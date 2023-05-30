/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafxmlapplication.model.JavaFXMLApplication;
import model.ClubDAOException;
import model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class LogIn implements Initializable {

//get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public HBox buttonsZone;
    public PasswordField password;
    public Button returnButton;

    @FXML
    private Pane pane;
    @FXML
    private TextField username;
    @FXML
    private Button loginButton;
    private boolean correctFormat;
    @FXML
    private VBox fieldsInputs;
    @FXML
    private VBox field1;
    @FXML
    private VBox field2;
    @FXML
    private Label goToSingUpLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //        only show above when focus
        TextField[] textFields = {username, password};
        for ( TextField textField : textFields ) {
            extracted(textField.getPromptText(), textField);
        }

//        si ha entrado en name y no ha puesto nada
        TextField[] requiredFields =   {username, password};
        for ( TextField requiredField : requiredFields ) {
            correctFields(requiredField.getPromptText(), requiredField);
        }

        goToSingUpLabel.setOnMouseClicked(mouseEvent -> {
            JavaFXMLApplication.changeScene("SignUp.fxml");
        });

        JavaFXMLApplication.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginButton.fire();
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
//                if it inside a gridpane,
                fieldsInputs.getChildren().set(fieldsInputs.getChildren().indexOf(name), namePane);
                namePane.getChildren().addAll(name, nameLabel);

            } else {
//                nameLabel.setVisible(false);
            }
        });
    }



    @FXML
    public void loginButton(ActionEvent eventLog) throws ClubDAOException, IOException {
        String user = username.getText();
        String pass = password.getText();
        //Club club = JavaFXMLApplication.getCurrentClub();
        if ( JavaFXMLApplication.getCurrentClub().existsLogin(user) ) {
            Member member = JavaFXMLApplication.getCurrentClub().getMemberByCredentials(user, pass);
            if (member != null) {
                JavaFXMLApplication.setCurrentMember(member);
                JavaFXMLApplication.changeScene("Reservas.fxml");
            }else {
                JavaFXMLApplication.dialogBox("error", "Error", "Usuario o contraseña incorrectos");
                username.setText("");
                password.setText("");
            }
        } else {
            JavaFXMLApplication.dialogBox("error", "Error", "Usuario o contraseña incorrectos");
            username.setText("");
            password.setText("");
        }
    }

    public void returnAction(ActionEvent actionEvent) {
        JavaFXMLApplication.changeScene("Main.fxml");
    }
}