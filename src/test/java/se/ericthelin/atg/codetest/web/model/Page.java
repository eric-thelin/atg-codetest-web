package se.ericthelin.atg.codetest.web.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

class Page {
	final WebDriver driver;

	Page(WebDriver driver) {
		this.driver = requireNonNull(driver);
	}

	WebDriverWait pause() {
		return new WebDriverWait(driver, Duration.ofSeconds(1));
	}
}
