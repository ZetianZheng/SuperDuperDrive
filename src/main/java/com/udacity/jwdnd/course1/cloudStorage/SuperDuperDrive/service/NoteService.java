package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    // include note mapper and user mapper
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
    public int addNote(String title, String description, String username) {
        // get the userId
        User user = userMapper.getUserByName(username);
        int userId = user.getUserId();
        // insert note
        Note note = new Note(null, title, description, userId);
        return noteMapper.addNote(note);
    }

    /**
     * check if this noteId belongs to the user with userId
     * @param noteId
     * @param userId
     * @return
     */
    public boolean isNoteBelongToUser(Integer noteId, Integer userId) {
        Note note = noteMapper.getNote(noteId);

        return note.getUserId().equals(userId);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNoteById(noteId);
    }

    public int editNote(Integer noteId, String title, String description){
        return noteMapper.editNote(noteId, title, description);
    }

    public Note[] getAllNotes(@NonNull Integer userId){
        return noteMapper.getNotes(userId);
    }
}
