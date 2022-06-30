package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ScreenshotRecorderTest {

	@Mock(extraInterfaces = {WebDriver.class})
	private TakesScreenshot driver;

	@Mock
	private ExtensionContext context;
	@Mock
	private FileSystem fileSystem;
	@Mock
	private ScreenshotLog log;
	private final File screenshotDestination = new File("screenshot.png");

	private ScreenshotRecorder subject;

	@BeforeEach
	void setUp() {
		subject = new ScreenshotRecorder(
				context -> screenshotDestination, log, fileSystem
		);
	}

	@Test
	void recordsScreenshot() {
		// Given
		given(driver.getScreenshotAs(OutputType.BYTES)).willReturn("screenshot".getBytes());

		// When
		subject.processFailure(context, (WebDriver) driver);

		// Then
		verify(log).recordAttemptToSaveScreenshot(
				screenshotDestination.getAbsoluteFile()
		);
		verify(fileSystem).write(
				"screenshot".getBytes(),
				screenshotDestination.getAbsoluteFile()
		);
	}

	@Test
	void skipsRecordingWhenDriverDoesNotSupportScreenshots() {
		// When
		subject.processFailure(context, mock(WebDriver.class));

		// Then
		verifyNoInteractions(log, fileSystem);
	}
}
