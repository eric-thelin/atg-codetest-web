package com.zingtongroup.atg.codetest.web.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class V4RaceRow {
	private final WebElement root;

	V4RaceRow(WebDriver driver, WebElement root) {
		this.root = root;
	}

	public void markNumberOfHorses(int numberOfHorsesToMark) {
		List<WebElement> starts = root.findElements(By.cssSelector(
				"[data-test-id=start-button-with-tooltip] button[data-test-scratched=false]"
		)).stream().limit(numberOfHorsesToMark).toList();

		if (starts.size() < numberOfHorsesToMark) {
			throw new IllegalArgumentException(String.format(
					"Not enough starts found. Expected: %s, Actual: %s",
					numberOfHorsesToMark,
					starts.size()
			));
		}

		starts.forEach(WebElement::click);
	}
}
