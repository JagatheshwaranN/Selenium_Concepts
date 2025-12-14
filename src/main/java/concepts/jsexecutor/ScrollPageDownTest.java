package concepts.jsexecutor;

import com.google.common.util.concurrent.Uninterruptibles;
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

import java.util.concurrent.TimeUnit;

public class ScrollPageDownTest {

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
	public void testScrollPageDown() {
		// Open the Selenium website
		driver.get("https://www.selenium.dev/");

		// Create a JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Scroll the page to the bottom using window.scrollTo() method
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		// Locate the seleniumCopyRight element using xpath
		WebElement seleniumCopyRight = driver.findElement(By.xpath("//small[@class='text-white']"));

        // Uninterruptible sleep for 3 seconds
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);


        // Verify that the seleniumCopyRight element is visible within the viewport
		Assert.assertTrue(ViewPortUtil.inViewport(seleniumCopyRight, driver));
	}

}
