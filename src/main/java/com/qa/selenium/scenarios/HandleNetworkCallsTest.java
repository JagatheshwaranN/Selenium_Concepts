package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.blibli.oss.qa.util.services.NetworkListener;

public class HandleNetworkCallsTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Initialize WebDriver and maximize the browser window.
		driver = DriverConfiguration.browserSetup();
	}

	@Test(priority = 1)
	public void testCaptureNetworkLogs() {
		// Start capturing network logs using a NetworkListener.
		NetworkListener networkListener = startNetworkListener();

		// Navigate to the website you want to test.
		driver.get("https://www.selenium.dev/");

		// Wait for the page to load completely.
		waitForPageLoad();

		// Create the HAR (HTTP Archive) file after capturing network activity.
		networkListener.createHarFile();
	}

	@AfterMethod
	public void tearDown() {
		// Quit the WebDriver after each test.
		if (driver != null) {
			driver.quit();
		}
	}

	// Start a network listener for capturing network activity.
	private NetworkListener startNetworkListener() {
		// Initialize a NetworkListener with the WebDriver and the name for the HAR file.
		NetworkListener networkListener = new NetworkListener(driver, "NetworkRequest.har");

		// Start capturing network activity.
		networkListener.start();

		// Return the network listener for further use or reference.
		return networkListener;
	}

	// Wait for the page to load completely.
	private void waitForPageLoad() {
		// Create a WebDriverWait instance with a timeout of 10 seconds.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Use an ExpectedCondition to wait until the document's ready state is 'complete.'
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
	}

}
