package concepts.jsexecutor;

import concepts.jsexecutor.util.ViewPortUtil;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollPageUpByPixelTest {

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
	public void testScrollPageUpByPixel() {
		// Declare a variable to store the pixel value for scrolling
		int pixel = 1000;

		// Open the Selenium website
		driver.get("https://www.selenium.dev/");

		// Create a JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Scroll down the page by 1000 pixels
		jsExecutor.executeScript("window.scrollBy(0, " + pixel + ")");

		// Scroll up the page by 1000 pixels, effectively returning to the top position
		jsExecutor.executeScript("window.scrollBy(0, -" + pixel + ")");

		// Locate the seleniumLogo element, which is the navbar brand element
		WebElement seleniumLogo = driver.findElement(By.xpath("//a[@class='navbar-brand']"));

		// Verify that the seleniumLogo element is visible within the viewport
		Assert.assertTrue(ViewPortUtil.inViewport(seleniumLogo, driver));
	}

}
