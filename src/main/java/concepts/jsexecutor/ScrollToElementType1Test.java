package concepts.jsexecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollToElementType1Test {

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
	public void testScrollToElementType1() {
		// Navigate to the Selenium website
		driver.get("https://www.selenium.dev/");

		// Find the desired element using XPath
		WebElement element = driver.findElement(By.xpath("//h2[@class='selenium text-center']"));

		// Create a JavaScriptExecutor instance to execute JavaScript code
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Use JavaScriptExecutor to scroll to the coordinates of the element
		jsExecutor.executeScript(
				"window.scrollTo(arguments[0],arguments[1]);",
				element.getLocation().x,  // X-coordinate of the element
				element.getLocation().y   // Y-coordinate of the element
		);

		// Assert whether the element is in the viewport after scrolling
		Assert.assertTrue(inViewport(element));
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
