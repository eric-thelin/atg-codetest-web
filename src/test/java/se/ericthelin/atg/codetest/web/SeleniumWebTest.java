package se.ericthelin.atg.codetest.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import se.ericthelin.atg.codetest.web.model.HomePage;
import se.ericthelin.atg.codetest.web.model.V4Page;

public class SeleniumWebTest {

	@RegisterExtension
	WebDriverExtension selenium = new WebDriverExtension();

	@Test
	void playV4() {
		V4Page v4Page = new HomePage(selenium.getDriver())
				.visit()
				.acceptCookies()
				.selectV4Game()
				.createNewCoupon();

		v4Page.getRace(1).markNumberOfHorses(4);
		v4Page.getRace(2).markNumberOfHorses(1);
		v4Page.getRace(3).markNumberOfHorses(2);
		v4Page.getRace(4).markAllHorses();

		v4Page.playCoupon();
	}
}
