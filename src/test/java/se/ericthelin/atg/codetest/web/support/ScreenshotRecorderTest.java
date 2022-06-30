package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ScreenshotRecorderTest {

	@Mock(extraInterfaces = {WebDriver.class})
	private TakesScreenshot driver;

	@Mock
	private ExtensionContext context;

	@TempDir
	private File targetDirectory;
	private File sourceScreenshot;
	private File destinationScreenshot;

	private ScreenshotRecorder subject;

	@BeforeEach
	void setUp() throws IOException {
		sourceScreenshot = File.createTempFile("source", ".png", targetDirectory);
		destinationScreenshot = new File(targetDirectory, "destination.png");
		subject = new ScreenshotRecorder(context -> destinationScreenshot);
	}

	@Test
	void recordsScreenshot() {
		// Given
		given(driver.getScreenshotAs(OutputType.FILE)).willReturn(sourceScreenshot);

		// When
		subject.processFailure(context, (WebDriver) driver);

		// Then
		assertTrue(destinationScreenshot.exists());
	}

	@Test
	void skipsRecordingWhenDriverDoesNotSupportScreenshots() {
		// When
		subject.processFailure(context, mock(WebDriver.class));

		// Then
		assertFalse(destinationScreenshot.exists());
	}
}
