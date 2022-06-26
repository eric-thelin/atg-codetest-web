package com.zingtongroup.atg.codetest.web;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePage {
	private final WebDriver driver;

	HomePage(WebDriver driver) {
		this.driver = driver;
	}

	void visit() {
		driver.get("https://www.atg.se/");

		assertEquals("ATG - Spel på Sport, Häst och Casino", driver.getTitle());
	}
}
