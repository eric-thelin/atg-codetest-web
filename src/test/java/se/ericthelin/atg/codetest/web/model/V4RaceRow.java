package se.ericthelin.atg.codetest.web.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class V4RaceRow {
	private final WebElement root;

	V4RaceRow(WebElement root) {
		this.root = requireNonNull(root);
	}

	public void markNumberOfHorses(int numberOfHorsesToMark) {
		List<WebElement> starts = streamStarts()
				.limit(numberOfHorsesToMark)
				.toList();

		if (starts.size() < numberOfHorsesToMark) {
			throw new IllegalArgumentException(String.format(
					"Not enough starts found. Expected: %s, Actual: %s",
					numberOfHorsesToMark,
					starts.size()
			));
		}

		starts.forEach(WebElement::click);
	}

	public void markAllHorses() {
		streamStarts().forEach(WebElement::click);
	}

	private Stream<WebElement> streamStarts() {
		return root.findElements(By.cssSelector(
				"[data-test-id=start-button-with-tooltip] button[data-test-scratched=false]"
		)).stream().filter(WebElement::isEnabled);
	}
}
