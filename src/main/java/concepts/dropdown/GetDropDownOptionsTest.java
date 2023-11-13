package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class GetDropDownOptionsTest {

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
	public void testGetDropDownOptions() {
		// Define the expected number of options in the dropdown
		int expectedDropDownOptionSize = 29;

		// Navigate to the webpage with the dropdown
		driver.get("https://letcode.in/dropdowns");

		// Get all options from the dropdown
		List<WebElement> dropDownOptions = new Select(driver.findElement(By.cssSelector("#superheros"))).getOptions();

		// Print the text of each option (optional, for visualization purposes)
		dropDownOptions.forEach(e -> System.out.println(e.getText()));

		// Get the actual number of options in the dropdown
		int actualDropDownOptionSize = dropDownOptions.size();

		// Assert that the actual number of options matches the expected number
		Assert.assertEquals(actualDropDownOptionSize, expectedDropDownOptionSize);
	}

}
