package com.example.project_ewelina_leonarczyk.repository;

import com.example.project_ewelina_leonarczyk.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
