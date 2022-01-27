package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.FileForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.FileService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.UserService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util.Util;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class homeController {
    // TODO: why final?
    private final UserService userService;
    private final FileService fileService;
    private final Util util;

    public homeController(UserService userService, FileService fileService, Util util) {
        this.userService = userService;
        this.fileService = fileService;
        this.util = util;
    }

    /**
     * getmapping to render the home page, create Model by ModelAttribute
     * @param authentication
     * @param newFileForm
     * @param model
     * @return
     */
    @GetMapping
    public String homeView(
            Authentication authentication,
            @ModelAttribute("newFileForm") FileForm newFileForm,
            Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        model.addAttribute("files", this.fileService.getALlFiles(userId));
        return "home";
    }
}
