package concepts.jsexecutor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class GetElementsTest {

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

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void testGetElements() {
		// Define the expected number of images on the page
		int expectedValue = 4;

		// Navigate to the target website
		driver.get("https://demoqa.com/broken");

		// Create a JavascriptExecutor object
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		/*
			Another way to get the WebElement
			=================================
			List<WebElement> images = (List<WebElement>) jsExecutor.executeScript("return document.querySelectorAll('img');");
		*/

		// Execute JavaScript to retrieve all `img` elements by tag name
		List<WebElement> images = (List<WebElement>) jsExecutor.executeScript("return document.getElementsByTagName('img');");

		// Get the actual number of retrieved images
		int actualValue = images.size();

		// Verify that the actual number of images matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void testGetElementsType2() {
		// Define the expected number of valid images
		int expectedValue = 4;

		// Navigate to the target website
		driver.get("https://demoqa.com/broken");

		// Create a JavascriptExecutor object
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to retrieve all `img` elements using querySelectorAll
		List<WebElement> images = (List<WebElement>) jsExecutor.executeScript("return document.querySelectorAll('img');");

		// Initialize a counter-variable to track valid images
		int actualValue = 0;

		// Loop through each retrieved image element
		for (WebElement image : images) {

			// Extract the image source URL
			String imageUrl = image.getAttribute("src");

			// Check if the URL is valid and not broken
			if (isValidImageUrl(imageUrl)) {

				// Increment the counter for valid images
				actualValue++;
			}
		}

		// Verify that the actual number of valid images matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

	private boolean isValidImageUrl(String imgUrl) {
		try {
			// Parse the link into a URI.
			URI url = new URI(imgUrl);

			// Create an HTTP connection by opening a connection to the URL.
			HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();

			// Set the connection timeout to 5000 milliseconds (5 seconds).
			connection.setConnectTimeout(5000);

			// Connect to the URL to initiate the HTTP request.
			connection.connect();

			// Check the HTTP response code to determine if the link is broken or active.
			int responseCode = connection.getResponseCode();

			// Print the link
			System.out.println(imgUrl);

			// Check if the 'responseCode' is not equal to 200
			if (responseCode != 200) {

				// If the response code is not 200, it indicates a broken link
				System.out.println("Broken Link Http Request Status => " + responseCode);
				System.out.println("Broken Link Http Request Body ===> " + connection.getResponseMessage());
			} else {

				// If the response code is 200, it indicates an active link
				System.out.println("Active Link Http Request Status => " + responseCode);
				System.out.println("Active Link Http Request Body ===> " + connection.getResponseMessage());
			}
			return true;
		} catch (IOException | URISyntaxException ex) {

			// Handle MalformedURLException by printing the stack trace.
			ex.printStackTrace();
			return false;
		}
	}

}
