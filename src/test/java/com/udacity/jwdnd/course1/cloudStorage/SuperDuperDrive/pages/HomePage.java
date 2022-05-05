package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Credential;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id="btnLogout")
    private WebElement btnLogout;

    // web elements about note:
    @FindBy(id="btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id="btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id="btnDeleteNote")
    private WebElement btnDeleteNote;

    @FindBy(id="tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id="tableNoteDescription")
    private WebElement tableNoteDescription;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id="btnSaveNoteChange")
    private WebElement btnSaveNoteChange;

    // web elements about credential:
    @FindBy(id="btnNewCredential")
    private WebElement btnNewCredential;

    @FindBy(id="btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id="btnDeleteCredential")
    private WebElement btnDeleteCredential;

    @FindBy(id="tableCredURL")
    private WebElement tableCredURL;

    @FindBy(id="tableCredUserName")
    private WebElement tableCredUserName;

    @FindBy(id="tableCredPassword")
    private WebElement tableCredPassword;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id="credential-id")
    private WebElement credentialId;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id="btnSaveCredChange")
    private WebElement btnSaveCredChange;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        btnLogout.click();
    }

    /**
     * actions about notes
     */
    public void editNote() {
        wait.until(ExpectedConditions.elementToBeClickable(btnEditNote)).click();
    }

    public void deleteNote() {
        wait.until(ExpectedConditions.elementToBeClickable(btnDeleteNote)).click();
    }

    public void saveNoteChanges() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveNoteChange)).click();
    }

    public void addNewNote() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddNewNote)).click();
    }

    public void setNoteTitle(String title) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(title);
    }

    public void modifyNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(newNoteTitle);
    }

    public void setNoteDescription(String description) {
        noteDescription.clear();
        noteDescription.sendKeys(description);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.id("tableNoteTitle"), driver) && !isElementPresent(By.id("tableNoteDescription"), driver);
    }

    public void modifyNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(newNoteDescription);
    }

    public void navToNotesTab() {
        navNotesTab.click();
    }

    /**
     * action about credentials
     */
    public void editCredential() {
        wait.until(ExpectedConditions.elementToBeClickable(btnEditCredential)).click();
    }

    public void deleteCredential() {
        wait.until(ExpectedConditions.elementToBeClickable(btnDeleteCredential)).click();
    }


    public void addNewCredential() {
        wait.until(ExpectedConditions.elementToBeClickable(btnNewCredential)).click();
    }

    public void setCredentialUrl(String url) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(url);
    }

    public void setCredentialUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys(username);
    }

    public void setCredentialPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys(password);
    }


    public void navToCredentialsTab() {
        navCredentialsTab.click();
    }


    public void saveCredentialChanges() {
        btnSaveCredChange.click();
    }

    public void modifyCredUrl(String url) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(url);
    }

    public void modifyCredUserName(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys(username);
    }

    public void modifyCredPw(String pw) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys(pw);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("tableCredURL"), driver) &&
                !isElementPresent(By.id("tableCredUserName"), driver) &&
                !isElementPresent(By.id("tableCredPassword"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();

        return new Note(title, description);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tableCredURL)).getText();
        String username = tableCredUserName.getText();
        String password = tableCredPassword.getText();

        return new Credential(url, username, password);
    }


}
