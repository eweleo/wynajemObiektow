package com.example.project_ewelina_leonarczyk.service;


import com.example.project_ewelina_leonarczyk.entities.ObjectForRent;
import com.example.project_ewelina_leonarczyk.repository.ObjectForRentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ObjectForRentService {

    private final ObjectForRentRepository objectForRentRepository;

    public boolean existById(int id) {
        return objectForRentRepository.existsById(id);
    }

    public ObjectForRent findById(int id) {
        return objectForRentRepository.getById(id);
    }

    public List<ObjectForRent> findAll() {
        return objectForRentRepository.findAll();
    }
}
