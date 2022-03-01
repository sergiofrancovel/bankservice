package org.example.services;

import io.javalin.core.security.RouteRole;
import org.example.models.Roles;
import org.example.models.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class AuthService {
    private UserService userService;
    private JWTService tokenService;

    public AuthService(UserService userService, JWTService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public String authenticate(String username, String password) {
        User user = userService.getUserByUsername(username);

        if(user == null || !(user.getPassword().equals(password))) {
            return null;
        }

        String token = tokenService.generate(username);
        return token;
    }

    public boolean authorize(User user, Set<RouteRole> requiredRoles) {
        for(Roles role : user.getRoles()) {
            if(requiredRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
