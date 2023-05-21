package javafxmlapplication.controller;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public class ToDo {
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
