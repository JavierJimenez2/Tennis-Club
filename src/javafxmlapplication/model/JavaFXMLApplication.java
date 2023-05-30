/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.model;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class JavaFXMLApplication extends Application {

    //si no quereis crear usuario --> usuario: mrg, contraseña: marc123


    private static Scene scene;
    private static Member member;
    private static Club club;

    private static ObservableList<ImageView> avatarList;

    private static Member guestMember;
    //private static Stack<Scene> sceneStack;    //cambio de pantalla (return)

    public static ObservableList<ImageView> getAvatarList() {
        return avatarList;
    }

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
            System.setErr(new PrintStream("NUL"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


    }

    public static void addAvatarList(ImageView avatar) {
        JavaFXMLApplication.avatarList.add(avatar);
    }


    public static Scene getScene() {
        return scene;
    }

    public static ObservableList<ImageView> getAvatars() {

        return avatarList;
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




//        get the list of avatars that are in the folder lib/tenisClub.jar/avatars/*.png

        avatarList = FXCollections.observableArrayList();
        File folder = new File("src/javafxmlapplication/view/css/img/avatars");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for ( File listOfFile : listOfFiles ) {
            if ( listOfFile.isFile() ) {
                ImageView img = new ImageView("/avatars/" + listOfFile.getName());
                img.setFitHeight(40);
                img.setFitWidth(40);
                img.setPreserveRatio(true);
                img.setSmooth(true);
                img.setCache(true);
                avatarList.add(img);
            }
        }


//        recorre todas las imagenes de la carpeta avatars y las añade a la lista
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



        // get array of images





        //sceneStack = new Stack<>();   //inisialisation of Stack for saving previous scenes

    }


}
