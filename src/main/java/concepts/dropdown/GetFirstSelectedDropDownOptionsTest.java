package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetFirstSelectedDropDownOptionsTest {

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
	public void testGetFirstSelectedDropDownOptionsTest() {
		// Navigate to the dropdown example page
		driver.get("https://letcode.in/dropdowns");

		// Create a Select object for the dropdown
		Select selectObject = new Select(driver.findElement(By.cssSelector("#superheros")));

		// Select an option by its attribute value ("aq" in this case)
		selectObject.selectByValue("aq");

		// Get the currently selected option from the dropdown
		WebElement dropDownOption = selectObject.getFirstSelectedOption();

		// Print the text of the selected option to the console
		System.out.println(dropDownOption.getText());
	}

}
