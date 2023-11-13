package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class CustomDropDownSelectTest {

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
	public void testCustomDropDownSelect() {
		// Define the expected option that should be selected in the dropdown
		String expectedSelectedOption = "Google Chrome";

		// URL of the HTML file
		String filePath = "file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/Dropdown.html";

		// Open the webpage
		driver.get(filePath);

		// Find the custom dropdown element
		WebElement dropDown = driver.findElement(By.cssSelector("div.select-selected"));

		// Click to open the dropdown
		dropDown.click();

		// Desired option to select
		String desiredOption = "Google Chrome";

		// Flag to check if the option is found
		boolean optionFound = false;

		// Find all the options in the dropdown
		List<WebElement> dropDownOptions = driver.findElements(By.cssSelector("ul.select-items li"));

		// Loop through the options to find and click the desired option
		for (WebElement option : dropDownOptions) {

			// Check if the text of the current option matches the desired option
			if (option.getText().equalsIgnoreCase(desiredOption)) {

				// Set the flag to true to indicate the option is found
				optionFound = true;

				// Click the found option to select it
				option.click();

				// Get the text of the currently selected option in the dropdown
				String actualSelectedOption = driver.findElement(By.cssSelector("div[class='select-selected']")).getText();

				// Assert that the actual selected option matches the expected selected option
				Assert.assertEquals(actualSelectedOption, expectedSelectedOption);

				// Exit the loop since the desired option is found
				break;
			}
		}

		// If the desired option is not found, throw an exception
		if (!optionFound) {
			throw new NoSuchElementException("The option '" + desiredOption + "' is not in the dropdown list");
		}
	}

}
