package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "resultSuccess")
    private WebElement resultSuccess;

    public void clickOk() {
        resultSuccess.click();
    }
}
