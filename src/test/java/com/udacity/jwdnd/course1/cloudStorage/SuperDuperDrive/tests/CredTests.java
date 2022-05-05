package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Credential;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.HomePage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.ResultPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CredTests extends CloudStorageTest {
    private final String url = "test.com";
    private final String username = "tester";
    private final String password = "123";

    /**
     * Test that creates and delete a credential.
     */
    @Test
    void testCreatedThenDelete() {
        HomePage homePage = signUpAndLogin();
        createCredential(url, username,password, homePage);
        homePage.navToCredentialsTab();

        assertFalse(homePage.noCredentials(driver));
        deleteCredential(homePage);
        assertTrue(homePage.noNotes(driver));
    }

    /**
     * Test that creates a credential and view it
     */
    @Test
    void testCreateAndView() {
        // sign up and login, then create a new credential
        HomePage homePage = signUpAndLogin();
        createCredential(url, username, password, homePage);

        // navigate to credential tab and verify the first credential, and also the password has been encrypted
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        assertEquals(url, credential.getUrl());
        assertEquals(username, credential.getUserName());
        assertNotEquals(password, credential.getPassword());

        // test delete credential
        deleteCredential(homePage);
        assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }

    @Test
    void testModify() {
        // sign up and login the home page
        HomePage homePage = signUpAndLogin();
        createCredential(url, username, password, homePage);

        // navigate to note tab and test edit the note
        homePage.navToCredentialsTab();
        homePage = new HomePage(driver);
        homePage.editCredential();

        // modify note
        homePage.modifyCredUrl("new url");
        homePage.modifyCredUserName("new username");
        homePage.modifyCredPw("new password");
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        // verify the edited note
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();

        assertEquals("new url", credential.getUrl());
        assertEquals("new username", credential.getUserName());
    }
    /**
     * helper functions
     */
    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
    }

    public void deleteCredential(HomePage homePage) {
        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }
}
