package com.zingtongroup.atg.codetest.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

class HomePage {
	private final WebDriver driver;

	HomePage(WebDriver driver) {
		this.driver = driver;
	}

	HomePage visit() {
		driver.get("https://www.atg.se/");

		pause().until(titleIs("ATG - Spel på Sport, Häst och Casino"));

		return this;
	}

	void acceptCookies() {
		pause().until(visibilityOfElementLocated(By.id("onetrust-banner-sdk")))
				.findElement(By.id("onetrust-accept-btn-handler"))
				.click();
		pause().until(invisibilityOfElementLocated(By.id("onetrust-banner-sdk")));
	}

	private WebDriverWait pause() {
		return new WebDriverWait(driver, Duration.ofSeconds(1));
	}
}
