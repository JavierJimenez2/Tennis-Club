package javafxmlapplication.controller;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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


    private static final int MAX_ITEMS = 10;
    //////////////////////////////// RESERVAS SECTION /////////////////////////////////////////////////////////////////////////////
    @FXML
    public ScrollPane scrollPane;
    ///////////////////MisReservas SECTION//////////////////////////////////////////////////////////////////////////////////////////
    public Button deleteButtonMR;
    public ListView<Booking> listViewMR;
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
    @FXML
    private TableView<Booking> tableReservas;
    @FXML
    private TableColumn<Booking, String> dateCol;
    @FXML
    private TableColumn<Booking, String> courtCol;
    @FXML
    private TableColumn<Booking, String> seCol;
    @FXML
    private TableColumn<Booking, String> paidCol;
    private Member member;
    private Club club;
    private boolean guest;
    private ObservableList<Booking> myObservableBookingList = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /////////////////////////////////   RESERVAS SECTION    ///////////////////////////////////////////////////////////////////////////////////

        guest = false;
        try {
            club = Club.getInstance();
        } catch (ClubDAOException | IOException e) {
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

        circleAvatar.setStroke(Color.WHITE);
        avatar.setVisible(false);
        if ( guest ) {
            appTitle.setText("GreenBall");
            avatar.setImage(new javafx.scene.image.Image("/javafxmlapplication/view/css/img/icons/avatar_icon.png"));
            circleAvatar.setFill(new ImagePattern(new javafx.scene.image.Image("/javafxmlapplication/view/css/img" + "/icons/avatar_icon.png")));
            userTopLabel.setText("Guest");
        } else {
            appTitle.setText("GreenBall");
            avatar.setImage(member.getImage());

//            crop the image into a square preserving the center and the ratio
            ImageView imageView = new ImageView(member.getImage());
            imageView.setPreserveRatio(true);
            ImagePattern pattern = new ImagePattern(imageView.getImage());
            circleAvatar.setFill(pattern);
            userTopLabel.setText(member.getNickName());
        }
        Label profile = new Label("Profile");
        Label init = new Label("Main Page");
        Label logout = new Label("Logout");
        if ( guest ) logout.setText("Log In");

        if ( guest ) {
            choice.getItems().removeAll(choice.getItems());
            choice.getItems().addAll(init, logout);
        } else {
            choice.getItems().removeAll(choice.getItems());
            choice.getItems().addAll(profile, logout);
        }

        choice.setOnAction((event) -> {
            if ( choice.getValue() == profile ) {
                changeScene("MyData.fxml");
            } else if ( choice.getValue() == init ) {
                changeScene("Main.fxml");
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
                    } else {
                        initialize(url, resourceBundle);
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

//        Set the date format
//        Get the stage
//        Not show the year in the DatePicker


        courtView();


        /////////////////////////////// RESERVATIONS SECTION ////////////////////////////////////////////////////////////////


    }

    //        quitar todos los key events
    private void myReservationsTab() {
        List<Booking> bookingData = JavaFXMLApplication.getCurrentClub().getUserBookings(JavaFXMLApplication.getCurrentMember().getNickName());
        listViewMR.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //create observable list using FXCollections
        myObservableBookingList = FXCollections.observableArrayList(bookingData);


        //link the booking observable list with listView
        listViewMR.setItems(myObservableBookingList);

        //Modify cell factory to display object Booking
        listViewMR.setCellFactory(c -> new bookingListCell());

        //in case of nothing selected disable delete
        deleteButtonMR.disableProperty().bind(Bindings.equal(-1, listViewMR.getSelectionModel().selectedIndexProperty()));

        if ( myObservableBookingList.size() > MAX_ITEMS ) {
            myObservableBookingList.remove(0, myObservableBookingList.size() - MAX_ITEMS);
        }
        listViewMR.setPrefHeight(10 * 25);
    }

    ////////// RESERVATIONS ///////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    public void ChoiceBoxHover(javafx.scene.input.MouseEvent mouseEvent) {
        choice.show();
    }

    public void courtView() {
        VBox vRoot = new VBox();
        vRoot.setSpacing(10);
        vRoot.setAlignment(Pos.CENTER);

        vRoot.getChildren().add(makeView());

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

//      give a margin of 10px
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

//      set Hgrow
        widget.getStyleClass().add("widget");

        HBox titleLabel = new HBox();
        titleLabel.getStyleClass().add("titleWidget");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.getChildren().add(new Label(title));
        widget.getChildren().add(titleLabel);
        widget.getChildren().add(new Separator(Orientation.HORIZONTAL));

    //  Here, items of RowReservation type are added:
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
        } else if ( sameDay && row.getFromTime().isBefore(LocalTime.now()) ) {
            right.getChildren().add(unavailableLabel);
        } else {
            right.getChildren().add(freeLabel);
        }
        right.getChildren().add(circle);

//  Set the color of the circle depending on isReserved():
        circle.setFill(circleColor);

        item.getChildren().addAll(left, right);

        item.setOnMouseClicked(mouseEvent -> {
            if ( !row.isReserved() ) {

            //  Compares if the selected slot is before the current hour. If it is before, the reservation cannot take place.
                if(DatePicker.getValue().isEqual(LocalDate.now()) && !row.getFromTime().isAfter(LocalTime.now()))
                    return;

            //  Alert that informs the user that he must Log In before making a reservation.
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
//////////////////////// BOOKING CONDITIONS ///////////////////////////////////////////////////////////////////////////////////////////////

                    // First, a List containing the reservations of the user is created to compare that the time
                    // to reserve is not consecutive with another previous reservation
                    List<Booking> listOfReservations = club.getUserBookings(member.getNickName());
                    Booking elementToCompare;
                    Booking elementToCompare2;
                    for ( int i = 0; i < listOfReservations.size(); i++ ) {
                        elementToCompare = listOfReservations.get(i);
                        if ( elementToCompare.getMadeForDay().toString().equals(row.getMadeForDay().toString())) {
                            // If the day and the court are the same, we compare hours:
                            // There are three cases where it shouldn't let the reservation continue:
                            //      1st case: when the two previous hours have been reserved by the same user.
                            //      2nd case: when the two hours after have been reserved by the same user.
                            //      3rd case: when the previous and the consecutive hour have already been reserved.

                            // In the following comparisons, we check in which case we are and if we pass one of the two first ifs,
                            // we enter a new for loop in which we traverse the list again searching checking if we are in any of the
                            // specified cases.
                            if(row.getFromTime().toString().equals(elementToCompare.getFromTime().toString())) {
                                JavaFXMLApplication.dialogBox("error", "Error in booking", "Another court has already been booked at this time.");
                                return;
                            }
                            if ( row.getFromTime().minusHours(1).toString().equals(elementToCompare.getFromTime().toString())) {
                                for(int j = 0; j < listOfReservations.size(); j++) {
                                    elementToCompare2 = listOfReservations.get(j);
                                    if(row.getFromTime().minusHours(2).toString().equals(elementToCompare2.getFromTime().toString())
                                    || row.getFromTime().plusHours(1).toString().equals(elementToCompare2.getFromTime().toString())) {
                                        JavaFXMLApplication.dialogBox("error", "Error in booking", "Three hours cannot be reserved consecutively.");
                                        return;
                                    }
                                }
                            } else if (row.getFromTime().plusHours(1).toString().equals(elementToCompare.getFromTime().toString())) {
                                for(int j = 0; j < listOfReservations.size(); j++) {
                                    elementToCompare2 = listOfReservations.get(j);
                                    if(row.getFromTime().plusHours(2).toString().equals(elementToCompare2.getFromTime().toString())
                                            || row.getFromTime().minusHours(1).toString().equals(elementToCompare2.getFromTime().toString())) {
                                        JavaFXMLApplication.dialogBox("error", "Error in booking", "Three hours cannot be reserved consecutively.");
                                        return;
                                    }
                                }
                            }
                        }
                    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Are you sure you want to reserve?");
                    String content = "Date: " + DatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + "Time" + ": " + row.getFromTime() + " - " + row.getFromTime().plusHours(1) + "\n" + "Court: " + row.getCourt().getName() + "\n" + "Member: " + row.getMember().getNickName() + "\n";
                    alert.setContentText(content);

                //  change the button names
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

                        // Setting paid to true if user included credit card.
                            row.setPaid(member.checkHasCreditInfo());

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


        });
        return item;
    } // end of createItem.

    private List<RowReservation> courtSlots(Court court) {
        LocalTime[] hours = new LocalTime[13];
        int f = 9;
        for ( int i = 0; i < 13; i++ ) {
            hours[i] = LocalTime.of(f, 0);
            f++;
        }


        RowReservation[] rowReservations = new RowReservation[hours.length];

        assert club != null;
        assert court != null;
        List<Booking> courtBookings = club.getCourtBookings(court.getName(), DatePicker.getValue());
        for ( Booking booking : courtBookings ) {
            for ( int i = 0; i < rowReservations.length; i++ ) {
                if ( booking.getFromTime().equals(hours[i]) ) {
                    rowReservations[i] = new RowReservation(booking.getBookingDate(), booking.getMadeForDay(), booking.getFromTime(), booking.getPaid(), booking.getCourt(), booking.getMember());
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
                rowReservations[i] = new RowReservation(LocalDateTime.now(), DatePicker.getValue(), hours[i], true, court, member);
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

    ////////////////////////////    MY RESERVATIONS    ///////////////////////////////////////////////////////////////////////
    public void deleteAction(ActionEvent actionEvent) {
        long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), listViewMR.getSelectionModel().getSelectedItem().getMadeForDay());
        //
        if ( LocalDate.now().getYear() == listViewMR.getSelectionModel().getSelectedItem().getMadeForDay().getYear() ) {
            if ( LocalDate.now().isAfter(listViewMR.getSelectionModel().getSelectedItem().getMadeForDay()) || (LocalDate.now().isEqual(listViewMR.getSelectionModel().getSelectedItem().getMadeForDay()) && (!listViewMR.getSelectionModel().getSelectedItem().getFromTime().isAfter(LocalTime.now()) || !listViewMR.getSelectionModel().getSelectedItem().getFromTime().plusHours(1).isAfter(LocalTime.now()))) ) {   //checks if booking was of a previous day
                JavaFXMLApplication.dialogBox("error", "Cancel Error", "Court was already used. Can't cancel reservation.");

        //  check if canceled reservation is in less than 24h
            } else if ( (daysDifference == 1 && listViewMR.getSelectionModel().getSelectedItem().getFromTime().isBefore(LocalTime.now())) || daysDifference < 1 ) {
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
        } else {
            showConfirmationWindow();
        }


    }

    private void showConfirmationWindow() {
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
                    JavaFXMLApplication.getCurrentClub().removeBooking(listViewMR.getSelectionModel().getSelectedItem());  //removes booking from database
                    myObservableBookingList.remove(listViewMR.getSelectionModel().getSelectedItem());  //removes booking from listView
                    JavaFXMLApplication.dialogBox("success", "Succes", "Booking removed successfully.");
                } catch (ClubDAOException e) {
                    JavaFXMLApplication.dialogBox("error", "Error removing booking", "Error trying to cancel booking.");
                }
            } else if ( buttonType == buttonTypeNo ) {
                JavaFXMLApplication.dialogBox("info", "Process Canceled", "Reservation deletion canceled.");
            }
        });
    }

    public void myReservationsAction(Event event) {

        myReservationsTab();
        /* removes all actions onKeyPressed of the scene */

    }

    public void reserveAction(Event event) {
        courtView();
    }

}
    class bookingListCell extends ListCell<Booking> {
        @Override
        protected void updateItem(Booking booking, boolean empty) {
            super.updateItem(booking, empty);
            if ( empty || booking == null || getIndex() >= 10 ) {
                setText(null);
                setGraphic(null);
            } else {
                String tabs = "\t";
//                tab of 90/12=7.5
                String tab1 = "                 ";
                String tab2 = "                                         ";
                String tab3 = "                                      ";
                String tab4 = "                                             ";
            //  set the text tab with the column of the grid pane that is above
                String madeForDay = booking.getMadeForDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String courtName = booking.getCourt().getName().replace("Pista","Court");
                String fromTime = booking.getFromTime() + " - " + booking.getFromTime().plusHours(1);
                String paid = booking.getPaid()?"✅":"❌";
                setText(tab1+madeForDay + tab2 + courtName + tab3 + fromTime + tab4 + paid);
            }


        }
    }



