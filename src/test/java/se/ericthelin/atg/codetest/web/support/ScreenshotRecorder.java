package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.function.Function;

class ScreenshotRecorder implements WebDriverFailureListener {

	private final Function<ExtensionContext, File> screenshotDestination;
	private final FileSystem fileSystem;

	ScreenshotRecorder(Function<ExtensionContext, File> screenshotDestination) {
		this(screenshotDestination, new RealFileSystem());
	}

	ScreenshotRecorder(
			Function<ExtensionContext, File> screenshotDestination,
			FileSystem fileSystem
	) {
		this.screenshotDestination = screenshotDestination;
		this.fileSystem = fileSystem;
	}

	@Override
	public void processFailure(ExtensionContext context, WebDriver driver) {
		if (driver instanceof TakesScreenshot source) {
			recordScreenshot(source, screenshotDestination.apply(context).getAbsoluteFile());
		}
	}

	private void recordScreenshot(TakesScreenshot source, File destination) {
		System.err.printf("Saving screenshot to \"%s\"%n", destination);
		fileSystem.write(source.getScreenshotAs(OutputType.BYTES), destination);
	}
}
