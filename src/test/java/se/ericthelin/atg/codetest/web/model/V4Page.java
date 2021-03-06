package se.ericthelin.atg.codetest.web.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
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

	public void playCoupon() {
		pause().until(invisibilityOfElementLocated(
				By.cssSelector("[data-test-id=auth-modal]")
		));
		driver.findElement(
				By.cssSelector("[data-test-id=play-game-coupon]")
		).click();
		pause().until(visibilityOfElementLocated(
				By.cssSelector("[data-test-id=auth-modal]")
		));
	}

	public V4RaceRow getRace(int raceNumber) {
		return new V4RaceRow(
				driver.findElement(By.cssSelector(String.format(
						"[data-test-id=coupon-race-%d]", raceNumber
				))));
	}
}
