/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author svalero
 */
public class FXMLSignUpController2 implements Initializable {


    @FXML
    private Button Accept;

    @FXML
    private Button Cancel;

    @FXML
    private Label Email;

    @FXML
    private TextField EmailField;

    @FXML
    private Label IncorrectEmail;

    @FXML
    private Label IncorrectMatch;

    @FXML
    private Label IncorrectPassword;

    @FXML
    private Label Password;

    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField Password2Field;

    @FXML
    private Label RepeatPassword;

    @FXML
    private Text SignUp;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;

    //private BooleanBinding validFields;

    //When to strings are equal, compareTo returns zero
    private final int EQUALS = 0;


    /**
     * Updates the boolProp to false.Changes to red the background of the edit.
     * Makes the error label visible and sends the focus to the edit.
     *
     * @param errorLabel label added to alert the user
     * @param textField  edit text added to allow user to introduce the value
     * @param boolProp   property which stores if the value is correct or not
     */
    private void manageError(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, textField);
        textField.requestFocus();


    }

    private void checkEqualPasswords() {
        if (PasswordField.textProperty().getValue().compareTo(Password2Field.textProperty().getValue()) != EQUALS) {
            showErrorMessage(IncorrectMatch, Password2Field);
            equalPasswords.setValue(Boolean.FALSE);
            PasswordField.textProperty().setValue("");
            Password2Field.textProperty().setValue("");
            PasswordField.requestFocus();
        } else {
            manageCorrect(IncorrectMatch, Password2Field, equalPasswords);
        }
    }

    private void checkEditEmail() {
        if (!Utils.checkEmail(EmailField.textProperty().getValue())) {
            manageError(IncorrectEmail, EmailField, validEmail);
        } else {
            manageCorrect(IncorrectEmail, EmailField, validEmail);
        }
    }

    private void checkEditPassword() {
        if (!Utils.checkPassword(PasswordField.textProperty().getValue())) {
            manageError(IncorrectPassword, PasswordField, validPassword);
        } else {
            manageCorrect(IncorrectPassword, PasswordField, validPassword);
        }
    }

    /**
     * Updates the boolProp to true. Changes the background
     * of the edit to the default value. Makes the error label invisible.
     *
     * @param errorLabel label added to alert the user
     * @param textField  edit text added to allow user to introduce the value
     * @param boolProp   property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, textField);

    }

    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     *
     * @param errorLabel
     * @param textField
     */
    private void showErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }

    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     *
     * @param errorLabel
     * @param textField
     */
    private void hideErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }


    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();
        equalPasswords = new SimpleBooleanProperty();

        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);


        EmailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEditEmail();
            }
        });


        PasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEditPassword();
            }
        });


        Password2Field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEqualPasswords();
            }
        });

        Accept.disableProperty().bind(validEmail.not().or(validPassword.not().or(equalPasswords.not())));

        Accept.setOnAction(event -> {
            System.out.println("Email: " + EmailField.textProperty().getValue());
            System.out.println("Password: " + PasswordField.textProperty().getValue());
            EmailField.textProperty().setValue("");
            PasswordField.textProperty().setValue("");
            Password2Field.textProperty().setValue("");
            validEmail.setValue(Boolean.FALSE);
            validPassword.setValue(Boolean.FALSE);
            equalPasswords.setValue(Boolean.FALSE);
        });

        Cancel.setOnAction(event -> {
            Cancel.getScene().getWindow().hide();
        });


    }


}