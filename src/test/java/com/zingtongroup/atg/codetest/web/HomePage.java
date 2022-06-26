package com.zingtongroup.atg.codetest.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

class HomePage {
	private final WebDriver driver;

	HomePage(WebDriver driver) {
		this.driver = driver;
	}

	void visit() {
		driver.get("https://www.atg.se/");

		new WebDriverWait(driver, Duration.ofSeconds(1)).until(
				titleIs("ATG - Spel på Sport, Häst och Casino")
		);
	}
}
