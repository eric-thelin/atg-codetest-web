package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.function.Supplier;

public class WebDriverExtension implements AfterEachCallback {

	private final Supplier<WebDriver> driverSource;
	private WebDriver driver;
	private final WebDriverFailureListener failureHandler;

	public WebDriverExtension() {
		this(() -> new RemoteWebDriver(new ChromeOptions()),
				new ScreenshotRecorder(context -> new File("target/selenium/" + context.getUniqueId() + ".failure.png"))
		);
	}

	WebDriverExtension(
			Supplier<WebDriver> driverSource,
			WebDriverFailureListener failureHandler
	) {
		this.driverSource = driverSource;
		this.failureHandler = failureHandler;
	}

	public WebDriver getDriver() {
		if (driver == null) {
			driver = driverSource.get();
		}
		return driver;
	}

	@Override
	public void afterEach(ExtensionContext context) {
		if (driver == null) {
			return;
		}
		if (context.getExecutionException().isPresent()) {
			failureHandler.processFailure(context, driver);
		}
		driver.quit();
	}
}
