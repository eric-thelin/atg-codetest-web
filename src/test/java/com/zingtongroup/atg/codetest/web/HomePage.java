package com.zingtongroup.atg.codetest.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
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

	HomePage acceptCookies() {
		pause().until(visibilityOfElementLocated(By.id("onetrust-banner-sdk")))
				.findElement(By.id("onetrust-accept-btn-handler"))
				.click();
		pause().until(invisibilityOfElementLocated(By.id("onetrust-banner-sdk")));

		return this;
	}

	void selectV4Game() {
		driver.findElement(By.id("MENU_PLAY_button")).click();
		pause().until(visibilityOfElementLocated(By.id("SideMenu_menu")));
		pause().until(elementToBeClickable(
				By.cssSelector("[data-test-id=horse-left-menu-sub-menu-toggle-allaspel-new]")
		)).click();

		WebElement item = pause().until(visibilityOfElementLocated(
				By.cssSelector("[data-test-id=horse-left-menu-sub-menu-item-v4]")
		));
		new Actions(driver).scrollToElement(item).build().perform();
		pause().until(elementToBeClickable(item)).click();

		new V4Page(driver).awaitReady();
	}

	private WebDriverWait pause() {
		return new WebDriverWait(driver, Duration.ofSeconds(1));
	}
}
