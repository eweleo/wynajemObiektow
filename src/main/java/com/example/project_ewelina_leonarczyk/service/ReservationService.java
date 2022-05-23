package com.example.project_ewelina_leonarczyk.service;

import com.example.project_ewelina_leonarczyk.entities.ObjectForRent;
import com.example.project_ewelina_leonarczyk.entities.Reservation;
import com.example.project_ewelina_leonarczyk.entities.User;
import com.example.project_ewelina_leonarczyk.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ObjectForRentService objectForRentService;

    public List<Reservation> findByUserName(String name) {
        List<Reservation> list = reservationRepository.findAll();
        return list.stream().filter(reservation -> reservation.getTenant().equals(name)).collect(Collectors.toList());
    }

    public List<Reservation> findByObjectId(int id) {
        ObjectForRent objectForRent = objectForRentService.findById(id);
        List<Reservation> list = reservationRepository.findAll();
        return list.stream().filter(reservation -> reservation.getObjectForRent() == objectForRent.getId())
                .collect(Collectors.toList());
    }

    public Reservation getById(int id) {
        return reservationRepository.getById(id);
    }

    public Reservation create(String userName, int objectId, LocalDate start, LocalDate end) {

        ObjectForRent objectForRent = objectForRentService.findById(objectId);
        User tenant = userService.findByName(userName);
        User landlord = userService.findByName(objectForRent.getOwner());
        Reservation reservation = new Reservation(start, end, landlord, objectForRent, tenant);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }


    public String validationDate(int objectId, LocalDate start, LocalDate end) {
        ObjectForRent objectForRent = objectForRentService.findById(objectId);
        if (start.isBefore(LocalDate.now())) {
            return "Date must be in the future";
        } else if (end.isBefore(start)) {
            return "Start date of reservations must be before end date";
        } else if (start.isEqual(end)) {
            return "Start date of reservation can not be equal to end date";
        } else if (reservationRepository.findReservationsByDate(start, end, objectForRent).size() != 0) {
            return "This object is not available in the date ";
        }
        return "OK";
    }

    public boolean existById(int id) {
        return reservationRepository.existsById(id);
    }

    public String validationUpdateDate(int reservationId, int objectId, LocalDate start, LocalDate end) {
        if (objectId == 0) {
            objectId = reservationRepository.getById(reservationId).getObjectForRent();
        }
        if (start == null) {
            start = reservationRepository.getById(reservationId).getStart();
        }
        if (end == null) {
            end = reservationRepository.getById(reservationId).getEnd();
        }
        ObjectForRent objectForRent = objectForRentService.findById(objectId);
        List list = reservationRepository.findReservationsByDate(start, end, objectForRent);
        if (start.isBefore(LocalDate.now())) {
            return "Date must be in the future";
        } else if (end.isBefore(start)) {
            return "Start date of reservations must be before end date";
        } else if (start.isEqual(end)) {
            return "Start date of reservation can not be equal to end date";
        } else if (!(list.size() == 1 && list.contains(reservationRepository.getById(reservationId)))) {
            return "This object is not available in the date ";
        }
        return "OK";
    }

    public Reservation update(int reservationId, int objectId, LocalDate start, LocalDate end) {
        Reservation reservation = reservationRepository.getById(reservationId);
        if (objectId != 0) {
            reservation.setObjectForRent(objectForRentService.findById(objectId));
        }
        if (start != null) {
            reservation.setStart(start);
        }
        if (end != null) {
            reservation.setEnd(end);
        }
        return reservationRepository.save(reservation);
    }
}