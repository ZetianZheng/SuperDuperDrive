package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.tests;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.pages.SignupPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



/**
 * <p>1. 先把服务器跑起来 ， 并用 SpringBootTest.WebEnvironment.RANDOM_PORT 放在随机端口，因为实测的时候会运行在多个port上：</p>
 * <p>2. 利用localServerPort annotation获得当前端口</p>
 * <p>3. before all 设置webDriverManager，afterAll退出</p>
 * <p>extends CloudStorageTest which have all before, after, drive and port.</p>
 */

class LoginTests extends CloudStorageTest{
    /**
     * Write a test that verifies that an unauthorized user can only access the login and signup pages.
     * <title>Login</title> equals driver.getTitle()
     */
    @Test
    void testUnAuthLogin() {
        driver.get("http://localhost:" + port + "/login");
        assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + port + "/home");
        assertEquals("Login", driver.getTitle());
    }

    /**
     * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out,
     * and verifies that the home page is no longer accessible.
     */
    @Test
    void testSignupAndLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signUpNow("zane", "John", "123", "123");
        assertEquals("Login", driver.getTitle());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("123", "123");
        assertEquals("Home", driver.getTitle());
    }

    @Test
    void testLoginFLow_userNotExists() {
        driver.get("http://localhost:" + port + "/login");
        assertEquals("Login", driver.getTitle());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Zane", "123456");

        assertEquals("Login", driver.getTitle());
    }
}
