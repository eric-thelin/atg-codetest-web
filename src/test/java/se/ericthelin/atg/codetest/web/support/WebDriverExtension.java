package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.function.Function;
import java.util.function.Supplier;

public class WebDriverExtension implements AfterEachCallback {

	private final Supplier<WebDriver> driverSource;
	private final Function<ExtensionContext, File> screenshotDestination;
	private WebDriver driver;

	public WebDriverExtension() {
		this(() -> new RemoteWebDriver(new ChromeOptions()),
				context -> new File("target/selenium/" + context.getUniqueId() + ".failure.png")
		);
	}

	WebDriverExtension(
			Supplier<WebDriver> driverSource,
			Function<ExtensionContext, File> screenshotDestination
	) {
		this.driverSource = driverSource;
		this.screenshotDestination = screenshotDestination;
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
			handleFailure(context);
		}
		driver.quit();
	}

	private void handleFailure(ExtensionContext context) {
		if (driver instanceof TakesScreenshot source) {
			recordScreenshot(source, screenshotDestination.apply(context).getAbsoluteFile());
		}
	}

	private void recordScreenshot(TakesScreenshot source, File destination) {
		System.err.printf("Saving screenshot to \"%s\"%n", destination);
		if (!destination.getParentFile().exists() && !destination.getParentFile().mkdirs()) {
			System.err.println("Could not create screenshot directory");
		}
		if (!source.getScreenshotAs(OutputType.FILE).renameTo(destination)) {
			System.err.println("Could not save screenshot");
		}
	}
}
