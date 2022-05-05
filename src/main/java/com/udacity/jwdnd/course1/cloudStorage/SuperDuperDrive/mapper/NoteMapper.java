package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int deleteNoteById(Integer noteId);

    // Mybaits must return Note[]?
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note[] getNotes(Integer userId);

    // Mybaits must return Note[]?
    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{title}")
    Note[] getNotesTitle(Integer userId, String title);


    // change the note content
    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
    int editNote(Integer noteId, String title, String description);
}
