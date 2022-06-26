package com.zingtongroup.atg.codetest.web.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class V4Page extends Page {

	V4Page(WebDriver driver) {
		super(driver);
	}

	V4Page awaitReady() {
		pause().until(titleContains("V4"));

		return this;
	}

	public V4Page createNewCoupon() {
		driver.findElement(By.cssSelector("[data-test-id=new-coupon]")).click();

		WebElement modal = pause().until(visibilityOfElementLocated(By.cssSelector("[data-test-id=modal]")));
		pause().until(elementToBeClickable(By.xpath("//button[text()='Tom kupong']"))).click();
		pause().until(invisibilityOf(modal));

		return this;
	}

	public V4RaceRow getFirstRace() {
		return new V4RaceRow(
				driver,
				driver.findElement(By.cssSelector("[data-test-id=coupon-race-1]"))
		);
	}
}
