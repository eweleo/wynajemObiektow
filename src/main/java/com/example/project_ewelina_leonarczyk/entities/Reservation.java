package com.example.project_ewelina_leonarczyk.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Reservation extends AbstractEntity {

    private LocalDate start;
    private LocalDate end;

    @ManyToOne
    @JoinColumn(name = "landlord")
    private User landlord;

    @ManyToOne
    @JoinColumn(name = "object_for_rent")
    private ObjectForRent objectForRent;

    @ManyToOne
    @JoinColumn(name = "tenant")
    private User tenant;

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getLandlord() {
        return landlord.getName();
    }

    public int getObjectForRent() {
        return objectForRent.getId();
    }

    public String getTenant() {
        return tenant.getName();
    }

}
