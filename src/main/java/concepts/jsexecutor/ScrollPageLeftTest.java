package concepts.jsexecutor;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollPageLeftTest {

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
	public void testScrollPageRight() {
		// Load a local HTML file with a horizontal scroll
		driver.get("D:\\Environment_Collection\\Intellij_Env\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\HorizontalScroll.html");

		// Create a JavaScript Executor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Scroll the window to the extreme right of the page
		jsExecutor.executeScript("window.scrollTo(document.body.scrollWidth, 0)");

		/*
			Another way to achieve page left scrolling
			==========================================
			// window.scrollTo({ left: document.body.scrollWidth, behavior: 'auto' });
		*/

		// Scroll the window to the extreme left of the page
		jsExecutor.executeScript("window.scrollTo(-document.body.scrollWidth, 0)");

		// Locate the desired element, "Item 1", within the horizontal scrolling content
		WebElement itemSection = driver.findElement(By.xpath("//div[@class='scroll-item'][text()='Item 1']"));

		// Verify that the element is visible within the viewport after scrolling
		Assert.assertTrue(inViewport(itemSection));
	}

	public boolean inViewport(WebElement element) {
		// Define a JavaScript script to check if the element is within the viewport
		String script = """
        // Calculate the cumulative offset positions of the element and its ancestors
        for (var e = arguments[0], f = e.offsetTop, t = e.offsetLeft, o = e.offsetWidth, n = e.offsetHeight;
            e.offsetParent;) {
            f += (e = e.offsetParent).offsetTop;
            t += e.offsetLeft;
        }

        // Check if the element's top and left positions are within the viewport's boundaries
        return f < window.pageYOffset + window.innerHeight &&
            t < window.pageXOffset + window.innerWidth &&
            f + n > window.pageYOffset &&
            t + o > window.pageXOffset;
    """;

		// Execute the JavaScript script and return the result (whether the element is in viewport)
		return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
	}

}
