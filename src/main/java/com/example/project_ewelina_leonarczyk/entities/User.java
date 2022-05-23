package com.example.project_ewelina_leonarczyk.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "uniqueName", columnNames = {"name"})})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends AbstractEntity {
    @NotBlank
    private String name;
}
