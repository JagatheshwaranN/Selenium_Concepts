package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class SelectByIndexTest {

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
	public void testSelectByIndex() {
		// Define the expected success message
		String expectedSuccessMessage = "You have selected Mango";

		// Navigate to the dropdown example page
		driver.get("https://letcode.in/dropdowns");

		// Select an option from the dropdown by index (index 2 in this case)
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByIndex(2);

		// Get the actual success message displayed on the page
		String actualSuccessMessage = driver.findElement(By.cssSelector("div[class='notification is-success']")).getText();

		// Assert that the actual success message matches the expected success message
		Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage);
	}

}
