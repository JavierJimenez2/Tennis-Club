package javafxmlapplication.controller;


import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

//        double width = primaryStage.getWidth();
//        double height = primaryStage.getHeight();
//        primaryStage.close();
//        primaryStage.setTitle("Reservation");
//        primaryStage.setScene(new Scene(scrollPane, width, height));
//
//        primaryStage.getIcons().add(new Image(
//                Objects.requireNonNull(getClass().getResourceAsStream("./layouts/img/EdenCodingIcon.png"))
//        ));
//
//
//        primaryStage.show();

    }


    private BootstrapPane makeView() {
        BootstrapPane bootstrapPane = new BootstrapPane();
        bootstrapPane.setPadding(new Insets(20));
        int gap = 25;
        bootstrapPane.setVgap(gap);
        bootstrapPane.setHgap(gap);
//        set margin

        BootstrapRow row = new BootstrapRow();

        row.addColumn(createTitleColumn());
        for ( int i = 1; i <= 6; i++ ) {
            row.addColumn(createColumn(createWidget("Court " + i, seedImportant())));
        }

        bootstrapPane.addRow(row);
        return bootstrapPane;
    }

    private BootstrapColumn createTitleColumn() {
        Label title = new Label("Courts available");
        title.setTextFill(Color.BLACK);

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

    private Node createWidget(String title, List<ToDo> items) {
        VBox widget = new VBox();
        widget.getStyleClass().add("widget");
        HBox titleLabel = new HBox();
        titleLabel.getStyleClass().add("titleWidget");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.getChildren().add(new Label(title));
        widget.getChildren().add(titleLabel);
        widget.getChildren().add(new Separator(Orientation.HORIZONTAL));

        for ( ToDo todo : items ) {
            widget.getChildren().add(createItem(todo));
        }
        return widget;
    }

    private Node createItem(ToDo todo) {
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
        return item;
    }

    private List<ToDo> seedImportant() {
        String[] hours = new String[13];
        int f = 9;
        for ( int i = 0; i < 13; i++ ) {
            hours[i] = f + ":00 - " + (f + 1) + ":00";
            f++;
        }

        ToDo[] toDos = new ToDo[hours.length];
        int i = 0;
        while (i < hours.length) {
            toDos[i] = new ToDo(hours[i], LocalDate.now(), true);
            i++;
            if ( i < hours.length ) {
                toDos[i] = new ToDo(hours[i], LocalDate.now(), false);
                i++;
            }
        }

        return Arrays.asList(toDos);
    }
    
}
