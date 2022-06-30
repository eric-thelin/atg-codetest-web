package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintStreamScreenshotLogTest {

	@Test
	void printsMessage() {
		// Given
		ByteArrayOutputStream destination = new ByteArrayOutputStream();
		PrintStreamScreenshotLog subject = new PrintStreamScreenshotLog(new PrintStream(destination));

		// When
		subject.recordAttemptToSaveScreenshot(new File("foo.png"));

		// Then
		assertEquals("Saving screenshot to \"foo.png\"\n", destination.toString(StandardCharsets.UTF_8));
	}
}
