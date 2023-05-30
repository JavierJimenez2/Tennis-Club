package javafxmlapplication.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafxmlapplication.model.JavaFXMLApplication;

public class Reservation {

    @FXML
    private ImageView AvatarLogo;

    @FXML
    private ImageView Court00;

    @FXML
    private ImageView Court01;

    @FXML
    private ImageView Court10;

    @FXML
    private ImageView Court11;

    @FXML
    private ImageView Court20;

    @FXML
    private ImageView Court21;

    @FXML
    private GridPane CourtGrid;

    @FXML
    private SplitPane GlobalSplitPane;

    @FXML
    private AnchorPane InferiorVPane;

    @FXML
    private Text SlashLogo;

    @FXML
    private AnchorPane SuperiorVPane;

    @FXML
    void initialize() {

        if ( JavaFXMLApplication.getCurrentMember() != null ) {
            if ( JavaFXMLApplication.getCurrentMember().getImage() != null ) {
                AvatarLogo.setImage(JavaFXMLApplication.getCurrentMember().getImage());
            }

        }
    }


    }
