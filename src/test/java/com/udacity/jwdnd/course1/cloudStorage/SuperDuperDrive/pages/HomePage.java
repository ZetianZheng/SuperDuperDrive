package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id="btnLogout")
    private WebElement btnLogout;

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

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id="btnSaveNoteChange")
    private WebElement btnSaveNoteChange;

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

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        btnLogout.click();
    }
}
