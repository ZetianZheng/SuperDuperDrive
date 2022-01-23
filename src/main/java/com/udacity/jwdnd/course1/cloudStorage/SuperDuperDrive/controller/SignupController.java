package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignupController {
    // TODO 问老师为什么是final？
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {return "signup";}

    //TODO 再看一遍signup的视频
    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        // 判断用户名是否可用：
        if (!userService.isUserNameAvailable(user.getUsername())){
            signupError = "The username already exists";
        }
        // 可用， 创建用户：
        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            // 插入操作返回的是插入成功的列数，如果返回小于0 说明给数据库插入出了错误。
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError != null) {
            // 如果没有注册成功，给model注入signupError 变量，
            model.addAttribute("signupError", signupError);
        } else{
            model.addAttribute("signupSuccess", true);
        }
        // 返回渲染的页面。
        return "signup";
    }
}
