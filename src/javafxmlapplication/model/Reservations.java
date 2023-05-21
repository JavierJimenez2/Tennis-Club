package javafxmlapplication.model;

import javafx.application.Application;
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
import javafx.stage.Stage;
import javafxmlapplication.model.layouts.BootstrapColumn;
import javafxmlapplication.model.layouts.BootstrapPane;
import javafxmlapplication.model.layouts.BootstrapRow;
import javafxmlapplication.model.layouts.Breakpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Reservations extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        change the variables of the css file


        BorderPane root = new BorderPane();
        root.getStyleClass().add("background");


        HBox menuBar = new HBox();
        menuBar.getStyleClass().add("menu-bar");
        menuBar.setPadding(new Insets(20));
        menuBar.setSpacing(10);
        menuBar.setAlignment(Pos.CENTER_LEFT);
        Label AppTitle = new Label("Slash");
        AppTitle.getStyleClass().add("app-title");
        ImageView avatar = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/javafxmlapplication/view/css/img/icons/avatar_icon.png"))
        ));
        avatar.setPreserveRatio(true);
        avatar.getStyleClass().add("avatar");
        avatar.setFitHeight(60);
//        set the alignment of the avatar to the right
        //set the apptitle to the left

//        set a padding right to the avatar
        HBox.setMargin(avatar, new Insets(0, 20, 0, 0));

//      use the avatar as a choice box
        ComboBox<Label> choiceBox = new ComboBox<>();
//        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(
                new Label("Profile"),
                new Label("Settings"),
                new Label("Dark Mode"),
                new Label("Logout")
        );

//


        choiceBox.setOnAction(event -> {
            Label selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            if ( selectedItem != null ) {
                System.out.println(selectedItem.getText());
            }
        });

//        on click, open the choice box
        choiceBox.setOnMouseClicked(event -> {
            choiceBox.show();
        });
//        choiceBox.setOnMouseExited(event -> {
//            choiceBox.hide();
//        });



//        get style class from the choice box
        choiceBox.setPrefWidth(30);
        choiceBox.setPrefHeight(30);
        choiceBox.setPadding(new Insets(0, 0, 0, 0));
        choiceBox.setOpacity(0);


//        choicebox behind the avatar
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(avatar, choiceBox);
        HBox.setMargin(stackPane, new Insets(0, 20, 0, 0));


        HBox.setHgrow(AppTitle, Priority.ALWAYS);
        HBox.setHgrow(stackPane, Priority.SOMETIMES);


        menuBar.getChildren().add(AppTitle);
        menuBar.getChildren().add(stackPane);

//        menuBar.getChildren().add(avatar);
        root.setTop(menuBar);

        BootstrapPane root1 = makeView();
        root.setCenter(root1);


        // SI ESTÁ COMENTADO EL ESTILO, ES PARA QUE SE VEA MÁS VISUAL EL LAYOUT EN DESARROLLO.
        root.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("./layouts/css/styles.css")).toExternalForm());

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        primaryStage.setTitle("Reservations");
        primaryStage.setScene(new Scene(scrollPane, 800, 600));
        primaryStage.setMaximized(true);

        primaryStage.getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("./layouts/img/tennisBallIcon.png"))
        ));


        primaryStage.show();
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
        Label title = new Label("Reservations");

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

    private static class ToDo {
        String title;
        LocalDate dueBy;
        Color status;
        boolean free;

        ToDo(String title, LocalDate dueBy, boolean free) {
            this.title = title;
            this.dueBy = dueBy;
            this.free = free;
            if ( !free ) {
                this.status = Color.RED;
            } else {
                this.status = Color.GREEN;
            }
        }
    }
}
