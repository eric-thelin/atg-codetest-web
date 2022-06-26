package com.zingtongroup.atg.codetest.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumWebTest {

	private WebDriver driver;

	@BeforeEach
	void createDriver() {
		driver = new RemoteWebDriver(new ChromeOptions());
	}

	@AfterEach
	void quitDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	void visitSite() {
		// When
		driver.get("https://www.atg.se/");

		// Then
		assertEquals("ATG - Spel på Sport, Häst och Casino", driver.getTitle());
	}
}
