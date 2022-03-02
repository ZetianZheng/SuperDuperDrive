package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.HomePage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * 1. 先把服务器跑起来 ， 并用 SpringBootTest.WebEnvironment.RANDOM_PORT 放在随机端口，因为实测的时候会运行在多个port上：
 * 2. 利用localServerPort annotation获得当前端口
 * 3. before all 设置webDriverManager，afterAll退出
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * 在每一次test之前，都生成一个新的login page 以用测试，避免干扰
     */
    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait (driver, 1000);
    }

    /**
     * Write a test that verifies that an unauthorized user can only access the login and signup pages.
     * <title>Login</title> equals driver.getTitle()
     */
    @Test
    public void testUnAuthLogin() {
        driver.get("http://localhost:" + port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out,
     * and verifies that the home page is no longer accessible.
     */
    @Test
    public void testSignupFlow(){
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.logout();
    }

    @Test
    public void testLoginFLow() {
        driver.get("http://localhost:" + port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName("Zane");
        loginPage.setInputPassword("123456");
        loginPage.login();

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.id("error-msg"));
        });
    }
}
