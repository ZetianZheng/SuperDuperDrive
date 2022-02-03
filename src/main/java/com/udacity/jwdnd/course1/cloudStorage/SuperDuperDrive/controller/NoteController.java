package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.*;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.NoteService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util.Util;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final Util util;

    public NoteController(NoteService noteService, Util util) {
        this.noteService = noteService;
        this.util = util;
    }

    /**
     * if have GET request from "/note", render home page and only note list for user
     * TODO: Is there any method not to write 3 modelAttribute?
     * @param authentication
     * @param newFileForm
     * @param newNoteForm
     * @param model
     * @return
     */
    @GetMapping
    public String homeNoteView(
            Authentication authentication,
            @ModelAttribute("newFileForm") FileForm newFileForm,
            @ModelAttribute("newNoteForm") NoteForm newNoteForm,
            @ModelAttribute("newCredForm") CredentialForm newCredentialForm,
            Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "home";
    }

    /**
     * TODO: do we need to check if the note exists?
     * @param authentication
     * @param newFileForm
     * @param newNoteForm
     * @param model
     * @return
     */
    @PostMapping("add-note")
    public String addNewNote(Authentication authentication,
                             @ModelAttribute("newFileForm") FileForm newFileForm,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             @ModelAttribute("newCredForm") CredentialForm newCredentialForm,
                             Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();
        String userName = user.getUsername();

        // get note object from user submit:
        // get noteIdStr from user submit, if this note exists, update it, if not, create a new one
        String noteIdStr = newNoteForm.getNoteId();
        String title = newNoteForm.getTitle();
        String description = newNoteForm.getDescription();

        // create or edit note:
        if (noteIdStr.isEmpty()) {
            noteService.addNote(title, description, userName);
        } else {
            int noteId = Integer.parseInt(noteIdStr);
            noteService.editNote(noteId, title, description);
        }

        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("result", "success");

        return "home";
    }

    /**
     * TODO: security problem, what if a user request the noteId for another user? add a method: isNoteBelongToUser(NoteId, UserId)
     * @param authentication
     * @param noteId
     * @return
     */
    @GetMapping("/get-note/{noteId}")
    public Note getNote(Authentication authentication,
                        @PathVariable Integer noteId,
                        Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        if (noteService.isNoteBelongToUser(noteId, userId)) {
            return noteService.getNote(noteId);
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "Error, this note not belong to you!");
            return null;
        }
    }

    /**
     * TODO: security problem, what if a user delete the noteId for another user? add a method: isNoteBelongToUser(NoteId, UserId)
     * @param authentication
     * @param noteId
     * @param newNoteForm
     * @param model
     */
    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(Authentication authentication,
                             @PathVariable Integer noteId,
                             @ModelAttribute("newFileForm") FileForm newFileForm,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        if (noteService.isNoteBelongToUser(noteId, userId)) {
            System.out.println("delete success!");
            noteService.deleteNote(noteId);
            model.addAttribute("result", "success");
        } else {
            System.out.println("delete error!");
            model.addAttribute("result", "error");
            model.addAttribute("message", "Error, this note not belong to you!");
        }

        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "home";
    }
}
