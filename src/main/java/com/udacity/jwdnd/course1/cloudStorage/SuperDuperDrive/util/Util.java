package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Util {
    private final UserService userService;

    public Util(UserService userService) {
        this.userService = userService;
    }

    public User getUserByAuth(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUser(username);
    }
}
