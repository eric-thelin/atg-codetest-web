package se.ericthelin.atg.codetest.web.support;

import java.io.File;

public interface ScreenshotLog {
	void recordAttemptToSaveScreenshot(File destination);
}
