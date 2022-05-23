package com.example.project_ewelina_leonarczyk.service;

import com.example.project_ewelina_leonarczyk.entities.User;
import com.example.project_ewelina_leonarczyk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByName(String name) {
        List<User> list = userRepository.findAll();
        return list.stream().filter(user -> user.getName().equals(name)).findAny().get();
    }

    public boolean existByName(String name) {
        List<User> list = userRepository.findAll();
        return list.stream().anyMatch(user -> user.getName().equals(name));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
