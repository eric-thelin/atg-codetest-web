package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintStreamScreenshotLogTest {

	private final ByteArrayOutputStream destination = new ByteArrayOutputStream();
	private final PrintStreamScreenshotLog subject = new PrintStreamScreenshotLog(new PrintStream(destination));

	@Test
	void printsAttempt() {
		// When
		subject.recordAttemptToSaveScreenshot(new File("foo.png"));

		// Then
		assertEquals("Saving screenshot to \"foo.png\"\n", destination.toString(StandardCharsets.UTF_8));
	}

	@Test
	void printsFailure() {
		// When
		subject.recordFailureToSaveScreenshot(new File("foo.png"), new IOException());

		// Then
		assertEquals("Failed to save screenshot\n", destination.toString(StandardCharsets.UTF_8));
	}
}
