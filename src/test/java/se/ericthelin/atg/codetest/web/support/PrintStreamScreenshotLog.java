package se.ericthelin.atg.codetest.web.support;

import java.io.File;
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
}
