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
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	private File destinationScreenshot;

	private ScreenshotRecorder subject;

	@BeforeEach
	void setUp() {
		destinationScreenshot = new File(targetDirectory, "screenshot.png");
		subject = new ScreenshotRecorder(context -> destinationScreenshot);
	}

	@Test
	void recordsScreenshot() throws IOException {
		// Given
		given(driver.getScreenshotAs(OutputType.BYTES)).willReturn("screenshot".getBytes());

		// When
		subject.processFailure(context, (WebDriver) driver);

		// Then
		assertTrue(destinationScreenshot.exists());
		assertEquals("screenshot", contentsOf(destinationScreenshot));
	}

	@Test
	void skipsRecordingWhenDriverDoesNotSupportScreenshots() {
		// When
		subject.processFailure(context, mock(WebDriver.class));

		// Then
		assertFalse(destinationScreenshot.exists());
	}

	private String contentsOf(File source) throws IOException {
		return Files.readString(source.toPath());
	}
}
