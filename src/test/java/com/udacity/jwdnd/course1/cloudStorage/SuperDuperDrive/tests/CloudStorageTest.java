package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.HomePage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CloudStorageTest {
    @LocalServerPort
    protected Integer port;

    protected WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        signUp();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());
    }

    protected HomePage signUp () {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signUpNow("zane", "John", "123", "123");
        return new HomePage(driver);
    }

    protected HomePage login() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("123", "123");
        return new HomePage(driver);
    }

}
