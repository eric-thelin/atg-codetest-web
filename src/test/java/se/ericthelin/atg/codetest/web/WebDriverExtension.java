package se.ericthelin.atg.codetest.web;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

class WebDriverExtension implements AfterEachCallback {

	private WebDriver driver;

	WebDriver getDriver() {
		if (driver == null) {
			driver = new RemoteWebDriver(new ChromeOptions());
		}
		return driver;
	}

	@Override
	public void afterEach(ExtensionContext extensionContext) {
		if (driver == null) {
			return;
		}
		driver.quit();
	}
}
