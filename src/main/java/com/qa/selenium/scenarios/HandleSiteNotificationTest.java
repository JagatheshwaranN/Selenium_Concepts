package com.qa.selenium.scenarios;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class HandleSiteNotificationTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set the WebDriver property for HTTP factory to jdk-http-client
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		// Create a ChromeOptions object to configure Chrome WebDriver settings
		ChromeOptions chromeOptions = new ChromeOptions();

		/*
			Alternate Option to disable Site Notification

			// Add the command-line argument to disable notifications
			chromeOptions.addArguments("--disable-notifications");
		*/

		// Create a map to store Chrome preferences
		Map<String, Object> preference = new HashMap<>();

		// Set the preference to disable notifications
		preference.put("profile.default_content_setting_values.notifications", 2);

		// Associate the preferences with the ChromeOptions
		chromeOptions.setExperimentalOption("prefs", preference);

		// Initialize the ChromeDriver with the configured options
		driver = new ChromeDriver(chromeOptions);

		// Maximize the browser window
		driver.manage().window().maximize();

	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	@Test(priority = 1)
	public void disableSiteNotification() {
		// Navigate to the Facebook website by opening the specified URL.
		driver.get("https://www.facebook.com/");

		// Assert that the title of the current web page matches the expected title.
		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
	}

}
