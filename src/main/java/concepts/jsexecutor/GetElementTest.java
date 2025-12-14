package concepts.jsexecutor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class GetElementTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.browserSetup();
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
	public void testGetElement() {
		// Define the expected title after search
		String expectedTitle = "Javascript - Google Search";

		// Navigate to the Google homepage
		driver.get("https://www.google.com/");

		// Create a JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		/*
			Another way to get the WebElement
			=================================
			WebElement searchBar = (WebElement) jsExecutor.executeScript(
				"return document.querySelector(\"[name='q']\")");
		 */

		// Find the search bar element by ID using JavaScript
		WebElement searchBar = (WebElement) jsExecutor.executeScript("return document.getElementById('APjFqb');");

		// Define a WebDriverWait object with a timeout
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Wait until the search bar is clickable
        Assert.assertNotNull(searchBar);
        wait.until(ExpectedConditions.elementToBeClickable(searchBar));

		// Send "Javascript" to the search bar
		searchBar.sendKeys("Javascript");

		// Submit the search using a keyboard ENTER key
		new Actions(driver).sendKeys(Keys.ENTER).perform();

		// Get the actual page title
		String actualTitle = driver.getTitle();

		// Verify that the actual title matches the expected title
		Assert.assertEquals(actualTitle, expectedTitle);
	}

}
