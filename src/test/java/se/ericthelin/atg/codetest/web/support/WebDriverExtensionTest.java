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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WebDriverExtensionTest {

	@Mock(extraInterfaces = {TakesScreenshot.class})
	private WebDriver driver;
	@Mock
	private ExtensionContext context;
	private WebDriverExtension subject;

	@TempDir
	private File targetDirectory;
	private File sourceScreenshot;
	private File destinationScreenshot;

	@BeforeEach
	void setUp() throws IOException {
		sourceScreenshot = File.createTempFile("source", ".png", targetDirectory);
		destinationScreenshot = new File(targetDirectory, "destination.png");
		subject = new WebDriverExtension(
				() -> driver, context -> destinationScreenshot
		);
	}

	@Test
	void quitsDriverAfterEachTest() {
		// Given
		subject.getDriver();

		// When
		subject.afterEach(context);

		// Then
		verify(driver).quit();
	}

	@Test
	void avoidsAttemptToQuitDriverWhenNotInitialized() {
		// When
		subject.afterEach(context);

		// Then
		verify(driver, never()).quit();
	}

	@Test
	void recordsScreenshotOnTestFailure() {
		// Given
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.of(new AssertionError()));
		given(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)).willReturn(sourceScreenshot);

		// When
		subject.afterEach(context);

		// Then
		assertTrue(destinationScreenshot.exists());
	}

	@Test
	void skipsRecordingOfScreenshotOnSuccess() {
		// Given
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.empty());

		// When
		subject.afterEach(context);

		// Then
		assertFalse(destinationScreenshot.exists());
	}

	@Test
	void skipsRecordingOfScreenshotWhenUnsupportedByDriver() {
		// Given
		subject = new WebDriverExtension(
				() -> mock(WebDriver.class), context -> destinationScreenshot
		);
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.of(new AssertionError()));

		// When
		subject.afterEach(context);

		// Then
		assertFalse(destinationScreenshot.exists());
	}
}
