package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import scenarios.DriverConfiguration;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class RelativeLocatorsTestCase {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

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
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).below(By.id("pid1"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid5");
	}

	@Test(priority = 2)
	public void getAboveElement() {
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).above(By.id("pid8"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid4");
	}

	@Test(priority = 3)
	public void getToLeftOfElement() {
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).toLeftOf(By.id("pid2"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid1");
	}

	@Test(priority = 4)
	public void getToRightOfElement() {
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).toRightOf(By.id("pid5"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid6");
	}

	@Test(priority = 5)
	public void getNearElement() {
		driver.get("https://www.google.com/");
		String buttonValue = driver.findElement(with(By.tagName("input")).near(By.name("btnI"))).getAttribute("value");
		Assert.assertEquals(buttonValue, "Google Search");
	}

}
