package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.constants.Messages.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    private Logger logger = LoggerFactory.getLogger(SignupController.class);

    @GetMapping
    public String signupView(@ModelAttribute("User") User user,  Model model) {return "signup";}

    @PostMapping
    public String signupUser(@ModelAttribute("User") User user, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Info: Sign Up a New User!");
        String userName = user.getUsername();
        logger.info("Info: User name is {}", userName);

        String signupError = null;
        // 判断用户名是否可用：
        if (!userService.isUserNameAvailable(userName)){
            logger.warn("Error: The username {} already exists!", userName);
            signupError = SIGNUP_ERROR_USER_EXISTS;
        }
        // 可用， 创建用户：
        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            // 插入操作返回的是插入成功的列数，如果返回小于0 说明给数据库插入出了错误。
            if (rowsAdded < 0) {
                logger.warn("Error: There was an error when create user!");
                signupError = SIGNUP_ERROR;
            }
        }

        if (signupError == null) {
            logger.info("Info: New user {} has been created!", userName);
            redirectAttributes.addAttribute("isSuccess",true);
            redirectAttributes.addAttribute("signupMsg",SIGNUP_SUCCESS+userName);
            return "redirect:/login";
        } else{
            // 如果没有注册成功，给model注入signupError 变量，
            redirectAttributes.addAttribute("isFail", true);
            redirectAttributes.addAttribute("signupMsg", signupError);
            // 返回渲染的页面。
            return "redirect:/signup";
        }
    }
}
