/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Club;
import model.Member;

import java.io.File;
import java.io.IOException;


public class JavaFXMLApplication extends Application {

    //si no quereis crear usuario --> usuario: mrg, contraseña: marc123


    private static Scene scene;
    private static Member member;
    private static Club club;

    private static Member guestMember;
    //private static Stack<Scene> sceneStack;    //cambio de pantalla (return)

    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }


    public static Member getCurrentMember() {
        return member;
    }

    public static void setCurrentMember(Member member) {
        JavaFXMLApplication.member = member;
    }

    public static void setCurrentMember(Club club) {
        JavaFXMLApplication.club = club;
    }

    public static Club getCurrentClub() {
        return club;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    public static void dialogBox(String type, String title, String message) {
        Alert alert;
        switch (type) {
            case "info":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            case "error":
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            case "warning":
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case "success":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            default:
                alert = new Alert(Alert.AlertType.NONE);
                break;
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void copyFile(File selectedFile, File file) {

        try {
            java.nio.file.Files.copy(
                    selectedFile.toPath(),
                    file.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Member getGuest() {
        guestMember = club.getMemberByCredentials("guest", "1");
        return guestMember;
    }

    public static void changeScene(String s) {
        FXMLLoader loader = new FXMLLoader(JavaFXMLApplication.class.getResource("/javafxmlapplication/view/" + s));
        try {
            setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void changeScene1(String s) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("javafxmlapplication/view/" + s));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(root);

    }

    @Override
    public void start(Stage stage) throws Exception {
        member = null;
        club = Club.getInstance();
        club.setName("GreenBall");

        //ToDo change to main
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));

        Parent root = loader.load();

        scene = new Scene(root);

        stage.setMinWidth(980);
        stage.setMinHeight(600);
        scene.getStylesheets().add("javafxmlapplication/view/css/styles.css");
        scene.getStylesheets().add("javafxmlapplication/view/css/bootstrapfx.css");
        stage.setScene(scene);
        stage.setTitle("GreenBall");
        stage.setMaximized(true);
//        set the icon of the application
        stage.getIcons().add(new Image("/javafxmlapplication/view/css/img/icons/app-icon.png"));
//  set the top bar of the application to the color of the background
        stage.show();


        //sceneStack = new Stack<>();   //inisialisation of Stack for saving previous scenes

    }


}
