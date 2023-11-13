package concepts.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ClickElementTest {

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
	public void testClickElement() {
		// Define the expected title
		String expectedTitle = "Dashboard / nopCommerce administration";

		// Navigate to the login page
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Find the login button field using a CSS selector
		WebElement login = driver.findElement(By.xpath("//button[@class='button-1 login-button']"));

		// Click on the login button field
		login.click();

		// Assert that the actual title matches the expected title
		Assert.assertEquals(driver.getTitle(), expectedTitle);
	}

}
