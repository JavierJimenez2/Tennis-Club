package javafxmlapplication.controller;


import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
//import color.Color;
import javafx.beans.binding.Bindings;
import java.awt.Color;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafxmlapplication.model.layouts.BootstrapColumn;
import javafxmlapplication.model.layouts.BootstrapPane;
import javafxmlapplication.model.layouts.BootstrapRow;
import javafxmlapplication.model.layouts.Breakpoint;


public class Reservas implements Initializable {

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Tab MisReservasTab;

    @FXML
    private Tab ReservasTab;

    @FXML
    private Label appTitle;

    @FXML
    private ImageView avatar;

    @FXML
    private ComboBox<Label> choice;

    @FXML
    private TabPane intPane;

    @FXML
    private HBox menuBar;

    @FXML
    private BorderPane pane;

    @FXML
    private StackPane stackPane;

    @FXML
    private Text textDate;

    @FXML
    private VBox root;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice.getItems().addAll(
                new Label("Profile"),
                new Label("Settings"),
                new Label("Dark Mode"),
                new Label("Logout")
        );

//      Code for disabling the past days.
        DatePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });

//        get the stage

        courtView();
    }

    @FXML
    public void ChoiceBoxHover(javafx.scene.input.MouseEvent mouseEvent) {
        choice.show();
    }

    public void courtView(){
        BootstrapPane root1 = makeView();
        root.getChildren().add(root1);

        anchorPane.getChildren().add(root);

//        scrollPane = new ScrollPane(root);
//        set root as the content of the scrollpane
//        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    }


    private BootstrapPane makeView() {
        BootstrapPane bootstrapPane = new BootstrapPane();
//        bootstrapPane.setPadding(new Insets(20));
        int gap = 25;
        bootstrapPane.setVgap(gap);
        bootstrapPane.setHgap(gap);
//        set margin

        BootstrapRow row = new BootstrapRow();

//        row.addColumn(createTitleColumn());
        for ( int i = 1; i <= 6; i++ ) {
            row.addColumn(createColumn(createWidget("Court " + i, courtSlots())));
        }

        bootstrapPane.addRow(row);
        return bootstrapPane;
    }

    private BootstrapColumn createTitleColumn() {
        Label title = new Label("Courts available");
        title.setAlignment(Pos.CENTER);

        //style
        title.getStyleClass().add("app-title2");

        //add to column
        BootstrapColumn titleColumn = new BootstrapColumn(title);
        titleColumn.setBreakpointColumnWidth(Breakpoint.XSMALL, 13);

        return titleColumn;
    }

    private BootstrapColumn createColumn(Node widget) {
        BootstrapColumn column = new BootstrapColumn(widget);
        column.setBreakpointColumnWidth(Breakpoint.XSMALL, 13);
        column.setBreakpointColumnWidth(Breakpoint.SMALL, 6);
        column.setBreakpointColumnWidth(Breakpoint.LARGE, 3);
        column.setBreakpointColumnWidth(Breakpoint.XLARGE, 2);
        return column;
    }

    private Node createWidget(String title, List<RowReservation> items) {
        VBox widget = new VBox();
        widget.getStyleClass().add("widget");
        HBox titleLabel = new HBox();
        titleLabel.getStyleClass().add("titleWidget");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.getChildren().add(new Label(title));
        widget.getChildren().add(titleLabel);
        widget.getChildren().add(new Separator(Orientation.HORIZONTAL));

//        Aqui se agregan los items de row reservation
        for ( RowReservation todo : items ) {
            widget.getChildren().add(createItem(todo));

        }
        return widget;
    }

    private Node createItem(RowReservation todo) {
        HBox item = new HBox();
        item.getStyleClass().add("item");
        if ( todo.free ) {
            item.getStyleClass().add("free");
        } else {
            item.getStyleClass().add("reserved");
        }

        HBox left = new HBox();
        HBox.setHgrow(left, Priority.ALWAYS);
        left.getChildren().add(new Label(todo.title));

        HBox right = new HBox();
        right.setSpacing(15);
        right.setMinWidth(80);
        right.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(right, Priority.NEVER);
        right.getChildren().add(new Label(todo.dueBy.format(DateTimeFormatter.ofPattern("dd-MMM"))));
        right.getChildren().add(new Circle(5, todo.status));

        item.getChildren().addAll(left, right);
        item.setOnMouseClicked(event -> {
//            TODO tio va reservar
            if ( todo.free ) {
                todo.free = false;
                item.getStyleClass().remove("free");
                item.getStyleClass().add("reserved");
            } else {
                todo.free = true;
                item.getStyleClass().remove("reserved");
                item.getStyleClass().add("free");
            }
        });
        return item;
    }

    private List<RowReservation> courtSlots() {
        String[] hours = new String[13];
        int f = 9;
        for ( int i = 0; i < 13; i++ ) {
            hours[i] = f + ":00 - " + (f + 1) + ":00";
            f++;
        }

        RowReservation[] rowReservations = new RowReservation[hours.length];
        int i = 0;
        while (i < hours.length) {
// TODO Restricciones
//
            rowReservations[i] = new RowReservation(hours[i], LocalDate.now(), true);
            i++;
        }

        return Arrays.asList(rowReservations);
    }
    
}
