package com.example.project_ewelina_leonarczyk.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateReservation {
    private int objectId;
    private LocalDate start;
    private LocalDate end;

}
