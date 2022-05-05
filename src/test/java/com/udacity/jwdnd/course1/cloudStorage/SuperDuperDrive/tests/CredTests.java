package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Credential;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.HomePage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.ResultPage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredTests extends CloudStorageTest {
    private final String url = "test.com";
    private final String username = "tester";
    private final String password = "123";

    /**
     * Test that creates and delete a credential.
     */
    @Test
    @Order(1)
    void testCreatedThenDelete() {
        HomePage homePage = login();
        createCredential(url, username,password, homePage);
        homePage.navToCredentialsTab();

        assertFalse(homePage.noCredentials(driver));
        deleteCredential(homePage);
        assertTrue(homePage.noNotes(driver));
    }

    /**
     * Test that creates a credential and view it
     * TODO: clear the database every time,(but how?) then each test can create a new user to test
     */
    @Test
    @Order(2)
    void testCreateAndView() {
        // sign up and login, then create a new credential
        HomePage homePage1 = login();
        createCredential(url, username, password, homePage1);

        // navigate to credential tab and verify the first credential, and also the password has been encrypted
        homePage1.navToCredentialsTab();
        Credential credential = homePage1.getFirstCredential();
        assertEquals(url, credential.getUrl());
        assertEquals(username, credential.getUserName());
        assertNotEquals(password, credential.getPassword());

        // test delete credential
        deleteCredential(homePage1);
        assertTrue(homePage1.noCredentials(driver));
    }

    @Test
    @Order(3)
    void testModify() {
        // sign up and login the home page
        HomePage homePage2 = login();
        createCredential(url, username, password, homePage2);

        // navigate to note tab and test edit the note
        homePage2.navToCredentialsTab();
        homePage2 = new HomePage(driver);
        homePage2.editCredential();

        // modify note
        homePage2.modifyCredUrl("new url");
        homePage2.modifyCredUserName("new username");
        homePage2.modifyCredPw("new password");
        homePage2.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        // verify the edited note
        homePage2.navToCredentialsTab();
        Credential credential = homePage2.getFirstCredential();

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
