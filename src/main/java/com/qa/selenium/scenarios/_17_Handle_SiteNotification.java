package com.qa.selenium.scenarios;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _17_Handle_SiteNotification {

	private WebDriver driver;
	private ChromeOptions chromeOptions;
	
	@Test(priority = 1, enabled = true)
	private void disableSiteNotification() {
		chromeOptions = new ChromeOptions();
		Map<String, Object> preference = new HashMap<String, Object>();
		preference.put("profile.default_content_setting_values.notifications", 2);
		chromeOptions.setExperimentalOption("prefs", preference);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
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
