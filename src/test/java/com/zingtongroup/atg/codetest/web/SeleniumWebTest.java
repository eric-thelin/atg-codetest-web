package com.zingtongroup.atg.codetest.web;

import com.zingtongroup.atg.codetest.web.model.HomePage;
import com.zingtongroup.atg.codetest.web.model.V4Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

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
	void playV4() {
		V4Page v4Page = new HomePage(driver)
				.visit()
				.acceptCookies()
				.selectV4Game()
				.createNewCoupon();

		v4Page.getRace(1).markNumberOfHorses(4);
		v4Page.getRace(2).markNumberOfHorses(1);
		v4Page.getRace(3).markNumberOfHorses(2);
		v4Page.getRace(4).markAllHorses();

		v4Page.playCoupon();
	}
}
