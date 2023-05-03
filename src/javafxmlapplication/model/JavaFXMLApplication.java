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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmlapplication.controller.FXMLSignUpController;


public class JavaFXMLApplication extends Application {

    Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("../view/FXMLSignUp.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
//        Scene scene = new Scene(root);
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
        FXMLSignUpController controller = loader.getController();
        ImageView imagebg = controller.getImage();
        if (stage.isMaximized()) {
            imagebg.setFitWidth(stage.getWidth());
            imagebg.setFitHeight(stage.getHeight());
        } else {
            imagebg.setFitWidth(600);
            imagebg.setFitHeight(400);
        }
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }


    public Stage getStage() {
        return stage;

    }
}
