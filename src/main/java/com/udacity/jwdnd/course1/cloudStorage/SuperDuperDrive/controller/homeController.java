package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.FileForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.FileService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.UserService;
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

    public homeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
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
        User user = getUserByAuth(authentication);
        Integer userId = user.getUserId();

        model.addAttribute("files", this.fileService.getALlFiles(userId));
        return "home";
    }

    /**
     * add new file to the database.
     * @param authentication use authentication by Spring security to get the user
     * @param newFileForm the form object which user submitted
     * @param model use to render thymeleaf page
     * @return
     * TODO 使用透明窗口display "message" to client
     */
    @PostMapping
    public String addNewFile(Authentication authentication, @ModelAttribute("newFileForm") FileForm newFileForm, Model model) {
        // get username from authentication and then got User POJO from User service:
        User user = getUserByAuth(authentication);
        Integer userId = user.getUserId();
        String username = user.getUsername();
        // get file object:
        MultipartFile multipartFile = newFileForm.getFile();
        String filename = multipartFile.getOriginalFilename();
        // whether the duplicate name file exists:
        Boolean isNoDuplicate = fileService.noDuplicateFileName(filename, userId);

        if(isNoDuplicate) {
            try {
                fileService.addFile(multipartFile, username);
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "duplicated file found! please change the file name or delete old version.");
        }

        model.addAttribute("files", fileService.getALlFiles(userId));
        return "home";
    }

    /**
     * The @ResponseBody annotation:
     *      tells a controller that the object returned is automatically serialized
     *      into JSON and passed back into the HttpResponse object.
     * @param filename
     * @return
     * reference: [Spring's RequestBody and ResponseBody Annotations | Baeldung](https://www.baeldung.com/spring-request-response-body)
     */
    @GetMapping(
            value = "/get-file/{filename}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    public byte[] getFile(@PathVariable String filename) {
        return fileService.getFile(filename).getFileData();
    }

    /**
     * TODO: here is GetMapping, but why?
     *
     * TODO what if different user have same file name?
     *      two parameters: fileService.deleteFile(filename, userId);
     * delete file by the filename which got by path variable.
     * before delete, the newFileForm object be created to render home.
     * @param authentication
     * @param filename
     * @param model
     * @return
     */
    @GetMapping("/delete-file/{filename}")
    public String deleteFile(Authentication authentication,
                             @PathVariable String filename,
                             @ModelAttribute("newFileForm") FileForm newFileForm,
                             Model model) {
        User user = getUserByAuth(authentication);
        Integer userId = user.getUserId();

        // delete the file:
        fileService.deleteFile(filename, userId);
        // model:
        model.addAttribute("files", fileService.getALlFiles(userId));
        model.addAttribute("result", "success");

        return "home";
    }

    public User getUserByAuth(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUser(username);
    }
}
