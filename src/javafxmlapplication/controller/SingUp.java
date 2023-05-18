/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafxmlapplication.model.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SingUp implements Initializable {

    //get pc screen size
    private static final double MAXWIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double MAXHEIGHT = Screen.getPrimary().getBounds().getHeight();
    public Button returnButton;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        profileImage.setImage() is in javafxmlapplication/view/css/img/icons/user.png
        profileImage.setImage(new javafx.scene.image.Image("javafxmlapplication/view/css/img/icons/user.png"));


    }


    @FXML
    void selectionButton(ActionEvent event) {
//        Select an image from the computer

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            profileImage.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    void registerButton(ActionEvent eventReg) throws ClubDAOException, IOException {
        String name = this.name.getText();
        String lastname = this.lastname.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String creditcard = this.creditcard.getText();
        int svc = 123;
        String telephone = this.telephone.getText();
        Club club = Club.getInstance();
        Member result = club.registerMember(name, lastname, telephone, username, password, creditcard, svc, profileImage.getImage());
        if (result != null) {
            JavaFXMLApplication.dialogBox("success", "Success", "You have been registered successfully");
            JavaFXMLApplication.changeScene(eventReg, "LogIn.fxml");
        } else {
            JavaFXMLApplication.dialogBox("error", "Error", "You have not been registered successfully");
            JavaFXMLApplication.changeScene(eventReg, "SingUp.fxml");
        }

    }

    public void returnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        JavaFXMLApplication.returnScene(stage);
    }
}