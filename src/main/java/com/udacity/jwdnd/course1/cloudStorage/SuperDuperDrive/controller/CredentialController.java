package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.*;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.CredentialService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    private final CredentialService credentialService;
    private final Util util;

    @Autowired
    public CredentialController(CredentialService credentialService, Util util) {
        this.credentialService = credentialService;
        this.util = util;
    }

    @GetMapping
    public String homeCredentialView(
            Authentication authentication,
            @ModelAttribute("newFileForm") FileForm newFileForm,
            @ModelAttribute("newNoteForm") NoteForm newNoteForm,
            @ModelAttribute("newCredForm") CredentialForm newCredentialForm,
            Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "home";
    }

    /**
     * upload a new credential or edit a existing credential.
     * decide by whether the credential id is empty or not
     *
     * @param authentication
     * @param newFileForm
     * @param newNoteForm
     * @param newCredentialForm
     * @param model
     * @return
     */
    @PostMapping("/add-cred")
    public String addNewCred(Authentication authentication,
                             @ModelAttribute("newFileForm") FileForm newFileForm,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             @ModelAttribute("newCredForm") CredentialForm newCredentialForm,
                             Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        // get credId from user submit, if this credential exists, update it, if not, create a new one
        String credIdStr = newCredentialForm.getCredentialId();

        if (credIdStr.isEmpty()) { // create the new credential:
            System.out.println("success create cred");
            credentialService.addCredential(newCredentialForm, userId);
        } else {
            int credId = Integer.parseInt(credIdStr);
            credentialService.editCredential(credId, newCredentialForm);
        }

        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    /**
     * TODO: push decrypt password into credentialForm
     * @param authentication
     * @param credId
     * @param model
     * @return
     */
    @GetMapping("/get-cred/{credId}")
    public Credential getCredential(Authentication authentication,
                                    @PathVariable Integer credId,
                                    Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        if (credentialService.isCredBelongToUser(credId, userId)) {
            return credentialService.getCredentialById(credId);
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "Error, this credential not belong to you!");
            return null;
        }
    }

    @GetMapping("/delete-cred/{credId}")
    public String deleteNote(Authentication authentication,
                             @PathVariable Integer credId,
                             @ModelAttribute("newFileForm") FileForm newFileForm,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             @ModelAttribute("newCredForm") CredentialForm newCredentialForm,
                             Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        if (credentialService.isCredBelongToUser(credId, userId)) {
            System.out.println("delete success!");
            credentialService.deleteCredential(credId);
            model.addAttribute("result", "success");
        } else {
            System.out.println("delete error!");
            model.addAttribute("result", "error");
            model.addAttribute("message", "Error, this credential not belong to you!");
        }

        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "result";
    }


}
