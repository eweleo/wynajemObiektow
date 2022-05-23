package com.example.project_ewelina_leonarczyk.controller;


import com.example.project_ewelina_leonarczyk.entities.ObjectForRent;
import com.example.project_ewelina_leonarczyk.entities.Reservation;
import com.example.project_ewelina_leonarczyk.entities.User;
import com.example.project_ewelina_leonarczyk.model.CreateReservation;
import com.example.project_ewelina_leonarczyk.model.UpdateReservation;
import com.example.project_ewelina_leonarczyk.service.ObjectForRentService;
import com.example.project_ewelina_leonarczyk.service.ReservationService;
import com.example.project_ewelina_leonarczyk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final ObjectForRentService objectForRentService;


    @PostMapping(value = "/reservations/user")
    public ResponseEntity<?> getReservationForUser(@RequestBody User user) {
        if (!userService.existByName(user.getName())) {
            return ResponseEntity.badRequest().body(new String("This user dont exist"));
        }
        return new ResponseEntity<>(reservationService.findByUserName(user.getName()), HttpStatus.OK);
    }

    @PostMapping(value = "/reservations/object_for_rent")
    public ResponseEntity<?> getReservationForObject(@RequestBody ObjectForRent objectForRent) {
        if (!objectForRentService.existById(objectForRent.getId())) {
            return ResponseEntity.badRequest().body(new String("This object dont exist"));
        }
        return new ResponseEntity<>(reservationService.findByObjectId(objectForRent.getId()), HttpStatus.OK);
    }


    @PostMapping(value = "/reservations")
    public ResponseEntity<?> createReservation(@RequestBody CreateReservation newReservation) {
        String validationDateMessage = reservationService.validationDate(newReservation.getObjectId(),
                newReservation.getStart(), newReservation.getEnd());
        if (!validationDateMessage.equals("OK")) {
            return ResponseEntity.badRequest().body(validationDateMessage);
        }
        Reservation reservation = reservationService.create(newReservation.getUserName(),
                newReservation.getObjectId(), newReservation.getStart(), newReservation.getEnd());
        return ResponseEntity.created(URI.create("/" + reservation.getId())).body(reservation);
    }

    @PatchMapping(value = "/reservations/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable int id, @RequestBody UpdateReservation toUpdate) {
        if (!reservationService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        String validationDateMessage = reservationService.validationUpdateDate
                (id, toUpdate.getObjectId(), toUpdate.getStart(), toUpdate.getEnd());
        if (!validationDateMessage.equals("OK")) {
            return ResponseEntity.badRequest().body(validationDateMessage);
        }
        Reservation reservation = reservationService.update(id, toUpdate.getObjectId(), toUpdate.getStart(), toUpdate.getEnd());
        return ResponseEntity.ok(reservation);
    }

}
