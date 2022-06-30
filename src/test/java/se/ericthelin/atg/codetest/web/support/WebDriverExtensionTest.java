package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class WebDriverExtensionTest {

	@Mock
	private WebDriver driver;
	@Mock
	private ExtensionContext context;

	@Mock
	private WebDriverFailureListener failureListener;

	private WebDriverExtension subject;

	@BeforeEach
	void setUp() {
		subject = new WebDriverExtension(() -> driver, failureListener);
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
	void notifiesListenerOnFailure() {
		// Given
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.of(new AssertionError()));

		// When
		subject.afterEach(context);

		// Then
		verify(failureListener).processFailure(context, driver);
	}

	@Test
	void skipsNotificationOnSuccess() {
		// Given
		subject.getDriver();
		given(context.getExecutionException()).willReturn(Optional.empty());

		// When
		subject.afterEach(context);

		// Then
		verifyNoInteractions(failureListener);
	}
}
