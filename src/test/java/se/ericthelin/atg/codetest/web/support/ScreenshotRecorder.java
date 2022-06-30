package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

class ScreenshotRecorder implements WebDriverFailureListener {

	private final Function<ExtensionContext, File> screenshotDestination;

	ScreenshotRecorder(Function<ExtensionContext, File> screenshotDestination) {
		this.screenshotDestination = screenshotDestination;
	}

	@Override
	public void processFailure(ExtensionContext context, WebDriver driver) {
		if (driver instanceof TakesScreenshot source) {
			recordScreenshot(source, screenshotDestination.apply(context).getAbsoluteFile());
		}
	}

	private void recordScreenshot(TakesScreenshot source, File destination) {
		System.err.printf("Saving screenshot to \"%s\"%n", destination);
		if (!destination.getParentFile().exists() && !destination.getParentFile().mkdirs()) {
			System.err.println("Could not create screenshot directory");
		}
		try {
			Files.write(destination.toPath(), source.getScreenshotAs(OutputType.BYTES));
		} catch (IOException e) {
			System.err.println("Could not save screenshot");
		}
	}
}
