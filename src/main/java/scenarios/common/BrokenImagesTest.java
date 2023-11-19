package scenarios.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class BrokenImagesTest {

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
	public void testBrokenImagesType1() {
		// Instruct the WebDriver instance (already configured) to navigate to the URL "https://demoqa.com/broken"
		driver.get("https://demoqa.com/broken");

		// Find all the 'img' elements on the web page and store them in a list named 'images'
		List<WebElement> images = driver.findElements(By.tagName("img"));

		// Iterate through a list of WebElements named 'images'
		for (WebElement image : images) {
			try {
				// Use JavaScriptExecutor to check if an image is displayed
				boolean imageDisplay = (Boolean) ((JavascriptExecutor) driver).executeScript(
						// Execute a JavaScript snippet to determine if the 'naturalWidth' property of the image is defined and greater than 0
						"return (typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0);",
						image
				);
				// Check the result of the JavaScript execution
				if (imageDisplay) {
					// If the image is displayed (naturalWidth > 0), print "Image display Ok"
					System.out.println("Image display Ok");
				} else {
					// If the image is not displayed (naturalWidth <= 0), print "Image display Broken"
					System.out.println("Image display Broken");
				}
			} catch (Exception ex) {
				// Catch and print any exceptions that may occur during the process
				ex.printStackTrace();
			}
		}
	}

	@Test(priority = 2)
	public void testBrokenImagesType2() {
		// Instruct the WebDriver instance (already configured) to navigate to the URL "https://demoqa.com/broken"
		driver.get("https://demoqa.com/broken");

		// Locate the 'img' element with the 'src' attribute set to '/images/Toolsqa_1.jpg' using XPath
		WebElement image = driver.findElement(By.xpath("//img[@src='/images/Toolsqa_1.jpg']"));

		// Check if the 'naturalWidth' attribute of the 'image' WebElement is equal to "0"
		if (image.getAttribute("naturalWidth").equals("0")) {
			// If 'naturalWidth' is "0," it indicates that the image is not displayed, so print "Image display Broken"
			System.out.println("Image display Broken");
		} else {
			// If 'naturalWidth' is not "0," it indicates that the image is displayed, so print "Image display Ok"
			System.out.println("Image display Ok");
		}
	}

}
