package se.ericthelin.atg.codetest.web.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

interface WebDriverFailureListener {
	void processFailure(ExtensionContext context, WebDriver driver);
}
