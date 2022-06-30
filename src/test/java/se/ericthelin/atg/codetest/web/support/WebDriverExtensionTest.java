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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

	@BeforeEach
	void setUp() {
		subject = new WebDriverExtension(() -> driver);
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
	void recordsScreenshotOnTestFailure(@TempDir File targetDirectory) throws IOException {
		// Given
		subject.getDriver();
		File original = File.createTempFile("original", ".png", targetDirectory);
		given(context.getExecutionException()).willReturn(Optional.of(new AssertionError()));
		given(context.getUniqueId()).willReturn("foo");
		given(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)).willReturn(original);

		// When
		subject.afterEach(context);

		// Then
		assertTrue(new File("foo.failure.png").exists());
	}

	@Test
	void skipsRecordingOfScreenshotOnSuccess() {
		// Given
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.empty());

		// When
		subject.afterEach(context);

		// Then
		verify(((TakesScreenshot) driver), never()).getScreenshotAs(any(OutputType.class));
	}

	@Test
	void skipsRecordingOfScreenshotWhenUnsupportedByDriver() {
		// Given
		subject = new WebDriverExtension(() -> mock(WebDriver.class));
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.of(new AssertionError()));

		// When
		subject.afterEach(context);

		// Then
		verify(((TakesScreenshot) driver), never()).getScreenshotAs(any(OutputType.class));
	}
}
