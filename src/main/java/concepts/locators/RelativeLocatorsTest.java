package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import scenarios.DriverConfiguration;

import java.time.Duration;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class RelativeLocatorsTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Declare a WebDriverWait instance to be used for explicit waits
	private WebDriverWait wait;

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
	public void getBelowElement() {
		// Navigate to the specified URL
		driver.get("https://automationbookstore.dev/");

		// Wait for the page to fully load before interacting with elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

		// Find the element below the element with id "pid1" using RelativeLocator
		String bookId = driver.findElement(with(By.tagName("li")).below(By.id("pid1"))).getAttribute("id");

		// Assert that the retrieved element's ID matches the expected value "pid5"
		Assert.assertEquals(bookId, "pid5");
	}

	@Test(priority = 2)
	public void getAboveElement() {
		// Navigate to the specified URL
		driver.get("https://automationbookstore.dev/");

		// Wait for the page to fully load before interacting with elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

		// Find the element above the element with id "pid8" using RelativeLocator
		String bookId = driver.findElement(with(By.tagName("li")).above(By.id("pid8"))).getAttribute("id");

		// Assert that the retrieved element's ID matches the expected value "pid4"
		Assert.assertEquals(bookId, "pid4");
	}


	@Test(priority = 3)
	public void getToLeftOfElement() {
		// Navigate to the specified URL
		driver.get("https://automationbookstore.dev/");

		// Wait for the page to fully load before interacting with elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

		// Find the element to the left of the element with id "pid2" using RelativeLocator
		String bookId = driver.findElement(with(By.tagName("li")).toLeftOf(By.id("pid2"))).getAttribute("id");

		// Assert that the retrieved element's ID matches the expected value "pid1"
		Assert.assertEquals(bookId, "pid1");
	}

	@Test(priority = 4)
	public void getToRightOfElement() {
		// Navigate to the specified URL
		driver.get("https://automationbookstore.dev/");

		// Wait for the page to fully load before interacting with elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

		// Find the element to the right of the element with id "pid5" using RelativeLocator
		String bookId = driver.findElement(with(By.tagName("li")).toRightOf(By.id("pid5"))).getAttribute("id");

		// Assert that the retrieved element's ID matches the expected value "pid6"
		Assert.assertEquals(bookId, "pid6");
	}

	@Test(priority = 5)
	public void getNearElement() {
		// Navigate to the specified URL
		driver.get("https://www.google.com/");

		// Wait for the page to fully load before interacting with elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

		// Find the input element near the element with the name "btnI" using RelativeLocator
		String buttonValue = driver.findElement(with(By.tagName("input")).near(By.name("btnI"))).getAttribute("value");

		// Assert that the retrieved element's value matches the expected value "Google Search"
		Assert.assertEquals(buttonValue, "Google Search");
	}

}
