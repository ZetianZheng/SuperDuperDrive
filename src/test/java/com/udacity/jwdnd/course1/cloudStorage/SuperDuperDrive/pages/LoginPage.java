package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 1. findBy annotation 通过find by来找到元素。返回 webElement
 * 2. 构造函数： 使用pageFactory在构造函数中来初始化各类FindBy，以此替代driver.findElement.
 * 3. support function
 */
public class LoginPage {
    // 1. findBy
    @FindBy(id="error-msg")
    private WebElement errorMsg;

    @FindBy(id="logout-msg")
    private WebElement logoutMsg;

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="login-button")
    private WebElement loginButton;

    // 2. 构造函数：
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // 3. support functions
    public void setUserName(String userName) {
        inputUsername.sendKeys(userName);
    }

    public void setInputPassword(String passWord) {
        inputPassword.sendKeys(passWord);
    }

    public void login() {
        loginButton.click();
    }
}
