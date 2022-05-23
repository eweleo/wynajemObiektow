package com.example.project_ewelina_leonarczyk.repository;

import com.example.project_ewelina_leonarczyk.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    @Query("SELECT a FROM Reservation a WHERE a.objectForRent = :objectId AND ((a.start BETWEEN :dateStart AND :dateEnd) OR (a.end BETWEEN :dateStart AND :dateEnd))")
    List<Reservation> findReservationsByDate(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd, @Param("objectId") Object objectId);

}

