/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.model;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.HashMap;


public class JavaFXMLApplication extends Application {

    private static Scene scene;
    private static HashMap<String, Parent> roots = new HashMap<>(); //para link

    public static void setRoot(Parent root) {
        scene.setRoot(root);
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



    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
        Parent root = loader.load();
        //roots.put("inicio",root);  //add "FXMLInicio" to scenes map
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 2.1- adding remaining scenes to map      para link
       /* loader = new FXMLLoader(getClass().getResource("../view/SignUp.fxml"));
        root = loader.load();
        roots.put("SignUp",root);

        loader = new FXMLLoader(getClass().getResource("../view/LogIn.fxml"));
        root = loader.load();
        roots.put("LogIn",root);

        loader = new FXMLLoader(getClass().getResource("../view/Reservation.fxml"));
        root = loader.load();
        roots.put("Reservation",root);*/
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
//        listener del stage width y height

//        FXMLSignUpController controller = loader.getController();
//        controller.setStage((Stage) root.getScene().getWindow());

//        lock the stage resize a determinate size

//        stage.
//        stage min size to 200x400
//        if maximaze button is cliked

//        listener for know if the stage is maximazed
        stage.setMinWidth(680);
        stage.setMinHeight(500);
        scene.getStylesheets().add("javafxmlapplication/view/css/styles.css");
        scene.getStylesheets().add("javafxmlapplication/view/css/bootstrapfx.css");
//        get the width and print it
        stage.setScene(scene);
        stage.setTitle("GreenBall");
        stage.show();
    }


    public static void changeScene(ActionEvent event, String s) {
        FXMLLoader loader = new FXMLLoader(JavaFXMLApplication.class.getResource("../view/" + s));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }




        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        stage.setMinWidth(680);
        stage.setMinHeight(500);
        scene.getStylesheets().add("javafxmlapplication/view/css/styles.css");
        scene.getStylesheets().add("javafxmlapplication/view/css/bootstrapfx.css");
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }

}
