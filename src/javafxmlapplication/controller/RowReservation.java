package javafxmlapplication.controller;

import javafx.scene.paint.Color;
import model.Booking;
import model.Court;
import model.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RowReservation extends Booking {
    private LocalDateTime bookingDate;
    private LocalDate madeForDay;
    private LocalTime fromTime;
    private Boolean paid;
    private Court court;

    private Member member;
    protected boolean reserved;

    public RowReservation(LocalDateTime bookingDate, LocalDate madeForDay, LocalTime fromHour, boolean paid,
                          Court court, Member member) {
        super(bookingDate, madeForDay, fromHour, paid, court, member);
    }


    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
