package javafxmlapplication.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafxmlapplication.model.JavaFXMLApplication;
import javafxmlapplication.model.layouts.BootstrapColumn;
import javafxmlapplication.model.layouts.BootstrapPane;
import javafxmlapplication.model.layouts.BootstrapRow;
import javafxmlapplication.model.layouts.Breakpoint;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafxmlapplication.model.JavaFXMLApplication.changeScene;


public class Reservas implements Initializable {


    ////////////////////////////////parte de reservas/////////////////////////////////////////////////////////////////////////////
    @FXML
    public ScrollPane scrollPane;
    ///////////////////parte de MisReservas//////////////////////////////////////////////////////////////////////////////////////////
    public Button deleteButton;
    public ListView<Booking> listView;
    public Button returnButton;
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
    private Circle circleAvatar;
    @FXML
    private TabPane intPane;
    @FXML
    private HBox frameTop;
    @FXML
    private Label userTopLabel;
    @FXML
    private HBox menuBar;
    @FXML
    private BorderPane pane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Text textDate;
    @FXML
    private HBox imageBox;
    @FXML
    private VBox root;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    private Member member;
    private Club club;
    private boolean guest;
    private ObservableList<Booking> myObservableBookingList = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /////////////////////////////////parte de reservas///////////////////////////////////////////////////////////////////////////////////
        guest = false;
        try {
            club = Club.getInstance();
        } catch (ClubDAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assert club != null;
        member = JavaFXMLApplication.getCurrentMember();
        if ( member == null ) {
            guest = true;
            member = club.getMemberByCredentials("jjr", "1234567");
            JavaFXMLApplication.setCurrentMember(member);
            MisReservasTab.disableProperty().set(true);
            MisReservasTab.tooltipProperty().set(new Tooltip("For view your bookings you need to Log In"));
        }

//        Circle circle = new Circle(26, 30, 26);
//        circle.setStroke(Color.WHITE);
//        circle.setStrokeWidth(2);
//        circle.setFill(Color.WHITE);
        circleAvatar.setStroke(Color.WHITE);
        avatar.setVisible(false);
        if ( guest ) {
            appTitle.setText("GreenBall");
            avatar.setImage(new javafx.scene.image.Image("/javafxmlapplication/view/css/img/icons/avatar_icon.png"));
//            avatar.setClip(circle);
            circleAvatar.setFill(new ImagePattern(new javafx.scene.image.Image("/javafxmlapplication/view/css/img" +
                    "/icons/avatar_icon.png")));
            userTopLabel.setText("Guest");
        } else {
            appTitle.setText("GreenBall");
            avatar.setImage(member.getImage());
//            avatar.setDisable(true);
//            crop the image
//            avatar.setClip(circle);
//            crop the image into a square preserving the center and the ratio
            ImageView imageView = new ImageView(member.getImage());
            imageView.setPreserveRatio(true);
            ImagePattern pattern = new ImagePattern(imageView.getImage());
            circleAvatar.setFill(pattern);
            userTopLabel.setText(member.getNickName());
        }
        Label profile = new Label("Profile");
        Label logout = new Label("Logout");
        if ( guest ) logout.setText("Log In");

        if ( guest ) {
            choice.getItems().addAll(logout);
        } else {
            choice.getItems().addAll(profile, logout);
        }

        choice.setOnAction((event) -> {
            if ( choice.getValue() == profile ) {
                changeScene("MyData.fxml");
            } else if ( choice.getValue() == logout ) {
                if ( !guest ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                set graphic in css bi bi-exclamation-circle-fill

                    alert.setTitle("Logout");
                    alert.setHeaderText("Are you sure you want to logout?");
                    alert.setContentText("You will be redirected to the login page.");
                    ButtonType buttonTypeOne = new ButtonType("Log Out");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel");
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if ( result.get() == buttonTypeOne ) {
                        member = JavaFXMLApplication.getCurrentMember();
                        JavaFXMLApplication.setCurrentMember((Member) null);
                        changeScene("Login.fxml");
                    }
                } else {
                    changeScene("Login.fxml");
                }
            }
        });

//      Code for disabling the past days.
        DatePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            };
        });
        DatePicker.setValue(LocalDate.now());
//        do not show the week numbers
        DatePicker.setShowWeekNumbers(false);

//        set the date format
//        get the stage
//        No mostrar el año en el datepicker


        courtView();


        ///////////////////////////////parte de MisReservas////////////////////////////////////////////////////////////////

//        myReservationsTab();
    }

    private void myReservationsTab() {
        List<Booking> bookingData = JavaFXMLApplication.getCurrentClub().getUserBookings(JavaFXMLApplication.getCurrentMember().getNickName());

        //create observable list using FXCollections
        myObservableBookingList = FXCollections.observableArrayList(bookingData);

        //link the booking observable list with listView
        listView.setItems(myObservableBookingList);

        //Modify cell factory to display object Booking
        listView.setCellFactory(c -> new bookingListCell());

        //in case of nothing selected disable delete
//        deleteButton.disableProperty().bind(
//                Bindings.equal(-1,
//                        listView.getSelectionModel().selectedIndexProperty()));
    }

    //////////parte reservas///////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    public void ChoiceBoxHover(javafx.scene.input.MouseEvent mouseEvent) {
        choice.show();
    }

    public void courtView() {
        VBox vRoot = new VBox();
        vRoot.setSpacing(10);
        vRoot.setAlignment(Pos.CENTER);

        vRoot.getChildren().add(makeView());


//        scrollPane = new ScrollPane(root);
//        set root as the content of the scrollpane
//        scrollPane.setContent(root);

//        click on the button to scroll to the bottom
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        give the scroll a style class

        scrollPane.setContent(vRoot);


    }


    private BootstrapPane makeView() {
        BootstrapPane bootstrapPane = new BootstrapPane();
//        give a margin of 10px
        bootstrapPane.setId("bootstrapPane");
        bootstrapPane.setPadding(new Insets(10));
        int gap = 20;
        bootstrapPane.setVgap(gap);
        bootstrapPane.setHgap(gap);

        BootstrapRow row = new BootstrapRow();

        club = JavaFXMLApplication.getCurrentClub();
        if ( club == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Club not found");
            alert.showAndWait();
        } else {
            List<Court> courts = club.getCourts();
            int i = 0;
            for ( Court court : courts ) {
                i++;
                row.addColumn(createColumn(createWidget("Court " + i, courtSlots(court))));
            }
            bootstrapPane.addRow(row);
        }

        return bootstrapPane;
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
//        set Hgrow
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

    private Node createItem(RowReservation row) {
        HBox item = new HBox(); //HBox containing at the same time HBox right and HBox left;
        item.setAlignment(Pos.CENTER);
        item.getStyleClass().add("item");
        Circle circle = new Circle(5.0); // Circle that indicates with its color if slot reserved or not.
        Color circleColor;

//  Labels for either free, or user that reserved the time slot if reserved.

//  Free label:
        Label freeLabel = new Label("Free");
        Label unavailableLabel = new Label("Not Free");

//  User Label:

        Label userLabel = new Label("@");
        if ( !(row.getMember() == null) ) {
            userLabel.setText("@" + row.getMember().getNickName());
        }
        userLabel.setStyle("-fx-font-size: 14px;");

        boolean sameDay = row.getMadeForDay().isBefore(LocalDate.now().plusDays(1)) && row.getMadeForDay().isAfter(LocalDate.now().minusDays(1));
//  Sets the initial color of the ball depending on the state of the RowReservation object.
        if ( row.isReserved() ) {
            item.getStyleClass().add("reserved");
            circleColor = Color.RED;
        } else if ( sameDay && row.getFromTime().isBefore(LocalTime.now()) ) {
            item.getStyleClass().add("unavailable");
            circleColor = Color.RED;
        } else {
            item.getStyleClass().add("free");
            circleColor = Color.DARKGREEN;
        }
//  HBox that contains the time slot.
        HBox left = new HBox();
        HBox.setHgrow(left, Priority.ALWAYS);

//  time in 24h format e.g. 13:00 - 14:00
        LocalTime toTime = row.getFromTime().plusHours(1);
        String time = row.getFromTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + toTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        left.getChildren().add(new Label(time));

//  Settings of the HBox containing the elements representing the RowReservation object.
        HBox right = new HBox();
        right.setSpacing(15);
        right.setMinWidth(80);
        right.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(right, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.NEVER);
//        comprobar si es el mismo dia que el dia de las reservas
        if ( row.isReserved() ) {
            right.getChildren().add(userLabel);
        } else if (sameDay && row.getFromTime().isBefore(LocalTime.now()) ) {
            right.getChildren().add(unavailableLabel);
        } else {
            right.getChildren().add(freeLabel);
        }
        right.getChildren().add(circle);

//  Set the color of the circle depending on isReserved():
        circle.setFill(circleColor);

        item.getChildren().addAll(left, right);

            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if ( !row.isReserved() ) {
                        //                            alert de informacion que me diga que para reservar debe iniciar sesión
                        if ( guest ) {
                            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                            alert1.setTitle("Information");
                            alert1.setHeaderText("You must be logged in to reserve");
                            alert1.setContentText("Do you want to log in?");
                            ButtonType buttonTypeOne = new ButtonType("Log in");
                            ButtonType buttonTypeCancel = new ButtonType("Cancel");
                            alert1.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                            Optional<ButtonType> result = alert1.showAndWait();
                            if ( result.get() == buttonTypeOne ) {
                                JavaFXMLApplication.setCurrentMember((Member) null);
                                changeScene("Login.fxml");
                            }
                        } else {


                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Are you sure you want to reserve?");
                            String content =
                                    "Date: " + DatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                                            "Time" +
                                            ": " + row.getFromTime() + " - " + row.getFromTime().plusHours(1) +
                                            "\n" + "Court: " + row.getCourt().getName() + "\n" + "Member: " + row.getMember().getNickName() + "\n";
                            alert.setContentText(content);
//                    change the button names
                            ButtonType buttonTypeOne = new ButtonType("Reserve");
                            ButtonType buttonTypeCancel = new ButtonType("Cancel");
                            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                            Optional<ButtonType> result = alert.showAndWait();
                            if ( result.get() == buttonTypeOne ) {


                            try {
                                assert club != null;

                                LocalDateTime now = LocalDateTime.now();
                                LocalDate value = DatePicker.getValue();
                                LocalTime fromTime = row.getFromTime();
                                boolean paid = row.getPaid();
                                Court court = row.getCourt();

                                    club.registerBooking(now, value, fromTime, paid, court, row.getMember());
                                    //
                                    row.setReserved(true);
                                    item.getStyleClass().remove("free");
                                    item.getStyleClass().add("reserved");
                                    right.getChildren().remove(freeLabel);
                                    right.getChildren().add(0, userLabel);
                                    circle.setFill(Color.RED);
                                } catch (ClubDAOException e) {
                                    alert.close();
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                    alert2.setTitle("Error");
                                    alert2.setHeaderText("Reservation could not be made");
                                    alert2.setContentText("Please try again later");
                                    alert2.showAndWait();
                                    e.printStackTrace();
                                }


                            } else {
                                alert.close();
                            }
                        }
                    }
//


                }
            });
            return item;
        }

    private List<RowReservation> courtSlots(Court court) {
        LocalTime[] hours = new LocalTime[13];
        int f = 9;
        for ( int i = 0; i < 13; i++ ) {
            hours[i] = LocalTime.of(f, 0);
            f++;
        }


            RowReservation[] rowReservations = new RowReservation[hours.length];

            List<Booking> courtBookings = club.getCourtBookings(court.getName(), DatePicker.getValue());
            for ( Booking booking : courtBookings ) {
                for ( int i = 0; i < rowReservations.length; i++ ) {
                    if ( booking.getFromTime().equals(hours[i]) ) {
                        rowReservations[i] = new RowReservation(booking.getBookingDate(), booking.getMadeForDay(),
                                booking.getFromTime(),
                                booking.getPaid(), booking.getCourt(), booking.getMember());
                        rowReservations[i].setReserved(true);
                    }
                }
            }
            int i = 0;
            while (i < rowReservations.length) {
                if ( guest ) {
                    member = null;
                }
                if ( rowReservations[i] == null ) {
                    rowReservations[i] = new RowReservation(LocalDateTime.now(), DatePicker.getValue(), hours[i],
                            true, court, member);
                    rowReservations[i].setReserved(false);
                }

                i++;
            }

            return Arrays.asList(rowReservations);
        }


    public void datePickerAction(ActionEvent actionEvent) {
//        when date is changed, the court slots are updated
        courtView();
    }

    ////////////////////////////parte mis reservas///////////////////////////////////////////////////////////////////////
    public void deleteAction(ActionEvent actionEvent) {
        long daysDifference = ChronoUnit.DAYS.between(listView.getSelectionModel().getSelectedItem().getMadeForDay(), LocalDate.now());
        //
        if ( LocalDate.now().isAfter(listView.getSelectionModel().getSelectedItem().getMadeForDay()) || (LocalDate.now().isEqual(listView.getSelectionModel().getSelectedItem().getMadeForDay()) && (!listView.getSelectionModel().getSelectedItem().getFromTime().isAfter(LocalTime.now()) || !listView.getSelectionModel().getSelectedItem().getFromTime().plusHours(1).isAfter(LocalTime.now()))) ) {   //checks if booking was of a previous day
            JavaFXMLApplication.dialogBox("error", "Cancel Error", "Court was already used. Can't cancel reservation.");
            //return;
            //check if canceled reservation is in less than 24h
            //listView.getSelectionModel().getSelectedItem().getBookingDate(), LocalDateTime.now()
        } else if ( (daysDifference == 1 && listView.getSelectionModel().getSelectedItem().getFromTime().isBefore(LocalTime.now())) || daysDifference < 1 ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Are you sure you want to continue?");
            alert.setContentText("A 10$ fee will be charged due to your reservation being in less than 24h.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if ( buttonType == buttonTypeYes ) {
                    showConfirmationWindow();
                } else if ( buttonType == buttonTypeNo ) {
                    JavaFXMLApplication.dialogBox("info", "Process Canceled", "Reservation deletion canceled.");

                    }
                });
            } else {
                showConfirmationWindow();
            }


        }

        private void showConfirmationWindow () {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Dialog");
            alert.setHeaderText("Are you sure you want to delete?");
            alert.setContentText("Click OK to confirm.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if ( buttonType == buttonTypeYes ) {

                    try {
                        JavaFXMLApplication.getCurrentClub().removeBooking(listView.getSelectionModel().getSelectedItem());  //removes booking from database
                        myObservableBookingList.remove(listView.getSelectionModel().getSelectedItem());  //removes booking from listView
                        JavaFXMLApplication.dialogBox("success", "Succes", "Booking removed successfully.");
                    } catch (ClubDAOException e) {
                        JavaFXMLApplication.dialogBox("error", "Error removing booking", "Error trying to cancel booking.");
                    }
                } else if ( buttonType == buttonTypeNo ) {
                    JavaFXMLApplication.dialogBox("info", "Process Canceled", "Reservation deletion canceled.");
                }
            });
        }

        public void myReservationsAction (Event event){
            myReservationsTab();
        }

        public void reserveAction (Event event){
            courtView();
        }

        class bookingListCell extends ListCell<Booking> {
            @Override
            protected void updateItem(Booking booking, boolean empty) {
                super.updateItem(booking, empty);
                if ( empty || booking == null ) {
                    setText(null);
                } else {
                    setText(booking.getMadeForDay() + "          " + booking.getCourt().getName() + "          " + booking.getFromTime() + " - " + booking.getFromTime().plusHours(1) + "          " + booking.getPaid());

            }

        }
    }


}
