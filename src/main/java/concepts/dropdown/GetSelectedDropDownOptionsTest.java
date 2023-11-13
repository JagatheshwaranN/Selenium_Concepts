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

public class GetSelectedDropDownOptionsTest {

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
	public void testGetSelectedDropDownOptionsTest() {
		// Define the expected number of options in the dropdown
		int expectedSelectedOptionCount = 1;

		// Navigate to the dropdown example page
		driver.get("https://letcode.in/dropdowns");

		// Create a Select object for the dropdown
		Select selectObject = new Select(driver.findElement(By.cssSelector("#superheros")));

		// Select an option by its value
		selectObject.selectByValue("aq");

		// Get all selected options from the dropdown
		List<WebElement> dropDownOptions = selectObject.getAllSelectedOptions();

		// Print the text of each selected option (optional, for visualization purposes)
		dropDownOptions.forEach(e -> System.out.println(e.getText()));

		// Get the actual number of options in the dropdown
		int actualSelectedOptionCount = dropDownOptions.size();

		// Assert that the actual number of options matches the expected number
		Assert.assertEquals(actualSelectedOptionCount, expectedSelectedOptionCount);
	}

}
