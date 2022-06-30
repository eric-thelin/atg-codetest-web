package se.ericthelin.atg.codetest.web;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

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
		if (extensionContext.getExecutionException().isPresent()) {
			handleFailure(extensionContext);
		}
		driver.quit();
	}

	private void handleFailure(ExtensionContext context) {
		if (driver instanceof TakesScreenshot source) {
			recordScreenshot(source, new File(context.getUniqueId() + ".failure.png"));
		}
	}

	private void recordScreenshot(TakesScreenshot source, File destination) {
		System.err.printf("Saving screenshot to \"%s\"%n", destination.getAbsolutePath());
		if (!destination.getParentFile().mkdirs()) {
			System.err.println("Could not create screenshot directory");
		}
		if (!source.getScreenshotAs(OutputType.FILE).renameTo(destination)) {
			System.err.println("Could not save screenshot");
		}
	}
}
