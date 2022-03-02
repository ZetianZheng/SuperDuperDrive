package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public
class SuperDuperDriveApplicationTests {

	protected WebDriver webDriver;

	@LocalServerPort
	protected int port;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.webDriver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.webDriver != null) {
			webDriver.quit();
		}
	}
	@Test
	void contextLoads() {
	}

}
