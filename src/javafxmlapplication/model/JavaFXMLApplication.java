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
import javafx.stage.Stage;

import java.util.HashMap;


public class JavaFXMLApplication extends Application {

    Stage stage;
    private static Scene scene;
    private static HashMap<String,Parent> roots = new HashMap<>(); //para link
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("../view/FXMLInicio.fxml"));
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

        loader = new FXMLLoader(getClass().getResource("../view/FXMLReservation.fxml"));
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

        stage.setMinWidth(680);
        stage.setMinHeight(500);
        scene.getStylesheets().add("javafxmlapplication/view/css/styles.css");
        scene.getStylesheets().add("javafxmlapplication/view/css/bootstrapfx.css");
//        get the width and print it
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }


   /* static void setRoot(Parent root){       //para link
        scene.setRoot(root);
    }
    public static void setRoot(String key){
        Parent root = roots.get(key);
        if(root != null){
            setRoot(root);
        } else{
            System.err.printf("Scene not found", key);
        }
    }*/

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
