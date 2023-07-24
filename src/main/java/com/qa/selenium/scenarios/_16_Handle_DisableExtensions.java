package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _16_Handle_DisableExtensions {

	private WebDriver driver;
	private ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private void disableExtension() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-extensions");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		waitForSomeTime();
		driver.close();
	}
	

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
