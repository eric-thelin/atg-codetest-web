package com.zingtongroup.atg.codetest.web.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

class V4Page {
	private final WebDriver driver;

	V4Page(WebDriver driver) {
		this.driver = driver;
	}

	void awaitReady() {
		new WebDriverWait(driver, Duration.ofSeconds(1)).until(titleContains("V4"));
	}
}
