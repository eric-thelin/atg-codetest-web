package com.zingtongroup.atg.codetest.web.model;

import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

class V4Page extends Page {

	V4Page(WebDriver driver) {
		super(driver);
	}

	void awaitReady() {
		pause().until(titleContains("V4"));
	}
}
