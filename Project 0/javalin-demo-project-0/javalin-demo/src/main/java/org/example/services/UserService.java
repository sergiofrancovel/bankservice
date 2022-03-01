package org.example.services;

import org.example.dao.UserRepository;
import org.example.models.User;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}
