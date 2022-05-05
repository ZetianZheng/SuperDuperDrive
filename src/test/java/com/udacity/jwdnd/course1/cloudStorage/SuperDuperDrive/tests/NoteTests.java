package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.HomePage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.ResultPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTests extends CloudStorageTest{
    private final String noteTitle = "note here";
    private final String noteDescription = "some description";

    /**
     * Test that creates and delete a note.
     */
    @Test
    void testCreatedThenDelete() {
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        assertFalse(homePage.noNotes(driver));
        deleteNote(homePage);
        assertTrue(homePage.noNotes(driver));
    }

    /**
     * Test that creates a note and view it
     */
    @Test
    void testCreateAndView() {
        // sign up and login, then create a new note
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);

        // navigate to note tab and verify the first note
        homePage.navToNotesTab();
        Note note = homePage.getFirstNote();
        assertEquals(noteTitle, note.getNoteTitle());
        assertEquals(noteDescription, note.getNoteDescription());

        // test delete note
        deleteNote(homePage);
        assertTrue(homePage.noNotes(driver));
        homePage.logout();
    }

    /**
     * Test change a note
     */
    @Test
    void testModify() {
        // sign up and login the home page
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);

        // navigate to note tab and test edit the note
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        homePage.editNote();

        // modify note
        homePage.modifyNoteTitle("new title");
        homePage.modifyNoteDescription("new description");
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        // verify the edited note
        homePage.navToNotesTab();
        Note note = homePage.getFirstNote();

        assertEquals("new title", note.getNoteTitle());
        assertEquals("new description", note.getNoteDescription());
    }

    public void createNote(String noteTitle, String noteDescription, HomePage homePage) {
        homePage.navToNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
    }

    public void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

}
