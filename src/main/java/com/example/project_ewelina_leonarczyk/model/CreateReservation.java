package com.example.project_ewelina_leonarczyk.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateReservation {
    private String userName;
    private int objectId;
    private LocalDate start;
    private LocalDate end;
}
