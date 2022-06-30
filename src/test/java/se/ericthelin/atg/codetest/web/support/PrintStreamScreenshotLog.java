package se.ericthelin.atg.codetest.web.support;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

class PrintStreamScreenshotLog implements ScreenshotLog {
	private final PrintStream target;

	PrintStreamScreenshotLog(PrintStream target) {
		this.target = target;
	}

	@Override
	public void recordAttemptToSaveScreenshot(File destination) {
		target.printf("Saving screenshot to \"%s\"%n", destination);
	}

	@Override
	public void recordFailureToSaveScreenshot(File destination, IOException cause) {
		target.println("Failed to save screenshot. Cause: " + cause.getMessage());
	}
}
