/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ETORPER
 */
public class FXMLReservationController implements Initializable {

    @FXML
    private SplitPane GlobalSplitPane;
    @FXML
    private AnchorPane SuperiorVPane;
    @FXML
    private Text SlashLogo;
    @FXML
    private ImageView AvatarLogo;
    @FXML
    private AnchorPane InferiorVPane;
    @FXML
    private GridPane CourtGrid;
    @FXML
    private ImageView Court00;
    @FXML
    private ImageView Court10;
    @FXML
    private ImageView Court20;
    @FXML
    private ImageView Court01;
    @FXML
    private ImageView Court11;
    @FXML
    private ImageView Court21;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
