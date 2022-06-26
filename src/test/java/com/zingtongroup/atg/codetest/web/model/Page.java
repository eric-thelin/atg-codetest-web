package com.zingtongroup.atg.codetest.web.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class Page {
	final WebDriver driver;

	Page(WebDriver driver) {
		this.driver = driver;
	}

	WebDriverWait pause() {
		return new WebDriverWait(driver, Duration.ofSeconds(1));
	}
}
