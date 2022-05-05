package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.exceptions.InvalidUploadException;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.exceptions.RepositoryFailException;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.*;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.NoteService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;

import static com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.constants.Messages.*;

@Controller
@RequestMapping("/home")
public class NoteController {
    private final NoteService noteService;
    private final Util util;
    Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(NoteService noteService, Util util) {
        this.noteService = noteService;
        this.util = util;
    }

    /**
     * try to add a new note
     * @param authentication
     * @param newNoteForm
     * @param model
     * @return
     */
    @PostMapping("/add-note")
    public String addNewNote(Authentication authentication,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             Model model) {
        String noteErr = null;
        Integer noteId = null;
        Integer userId = null;

        try {
            User user = util.getUserByAuth(authentication);
            userId = user.getUserId();
            String userName = user.getUsername();
            Integer rowNum;

            // get note object from user submit:
            // get noteIdStr from user submit, if this note exists, update it, if not, create a new one
            String noteIdStr = newNoteForm.getNoteId();
            String title = newNoteForm.getTitle();
            String description = newNoteForm.getDescription();

            if (noteIdStr.isEmpty()) {
                logger.info("[Notes][add note] Creat Note: title: {}, description: {}.", title, description);
                rowNum = noteService.addNote(title, description, userName);
            } else {
                logger.info("[Notes][add note] Edit Note: Id: {}, title: {}, description: {}.", noteIdStr, title, description);
                noteId = Integer.parseInt(noteIdStr);
                rowNum = noteService.editNote(noteId, title, description);
            }

            if (rowNum < 0) {
                throw new RepositoryFailException();
            }
        } catch(RepositoryFailException e) {
            noteErr = NOTE_UPLOAD_FAIL;
        } catch (Exception e) {
            logger.warn("[Error][Notes][add note] some unexpected error occurred!");
            noteErr = NOTE_OTHER_ERR;
        }

        if (noteErr == null && userId != null) {
            model.addAttribute(NOTES, this.noteService.getAllNotes(userId));
            model.addAttribute(RESULT, SUCCESS);
        } else {
            model.addAttribute(RESULT, ERROR);
            assert noteErr != null;
            model.addAttribute(MESSAGE, String.format(noteErr, noteId));
        }

        return RESULT;
    }

    /**
     * Get note by noteId
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
            model.addAttribute(RESULT, ERROR);
            model.addAttribute(MESSAGE, NOTE_BELONGS_ERROR);
            return null;
        }
    }

    /**
     * Delete notes
     * @param authentication
     * @param noteId
     * @param newNoteForm
     * @param model
     */
    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(Authentication authentication,
                             @PathVariable Integer noteId,
                             @ModelAttribute("newNoteForm") NoteForm newNoteForm,
                             Model model) {
        User user = util.getUserByAuth(authentication);
        Integer userId = user.getUserId();

        if (noteService.isNoteBelongToUser(noteId, userId)) {
            logger.info("[Notes][delete] Delete success!");
            noteService.deleteNote(noteId);
            model.addAttribute(RESULT, "success");
        } else {
            logger.info("[Notes][delete] Delete fail!");
            model.addAttribute(RESULT, ERROR);
            model.addAttribute(MESSAGE, NOTE_BELONGS_ERROR);
        }

        model.addAttribute(NOTES, this.noteService.getAllNotes(userId));
        return RESULT;
    }
}
