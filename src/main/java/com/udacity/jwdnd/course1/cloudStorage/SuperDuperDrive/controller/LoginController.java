package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// controller 层处理get get request 并分配到login.html
@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String loginView(){
        return "login";
    }

//    remove the logout() method since Spring Security already has a /logout
//    endpoint instantiated in which it will handle the user's logout request
//    @PostMapping("/logout")
//    public String logout() {
//        return "login";
//    }
}
