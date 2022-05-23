package com.example.project_ewelina_leonarczyk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "objects_for_rent")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ObjectForRent extends AbstractEntity {

    @NotBlank
    private String name;
    @NotBlank
    private double price;
    @NotBlank
    private double area;
    @NotBlank
    private String describe;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    public String getOwner() {
        return owner.getName();
    }
}
