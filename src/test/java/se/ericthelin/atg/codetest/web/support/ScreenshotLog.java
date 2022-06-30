package se.ericthelin.atg.codetest.web.support;

import java.io.File;
import java.io.IOException;

public interface ScreenshotLog {
	void recordAttemptToSaveScreenshot(File destination);

	void recordFailureToSaveScreenshot(File destination, IOException cause);
}
