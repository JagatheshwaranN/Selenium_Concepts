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

import java.util.ArrayList;
import java.util.List;

public class DeselectMultipleOptionTest {

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
	public void testDeselectMultipleOption() {
		// Navigate to the Letcode Dropdowns page
		driver.get("https://letcode.in/dropdowns");

		// Find the dropdown element by its CSS selector
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));

		// Check if the dropdown supports multiple selections
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);

		// Perform actions if the dropdown supports multiple selections
		if (isMultiSelect) {

			// Select options by index, value, and visible text
			selectObj.selectByIndex(0);
			selectObj.selectByValue("aq");
			selectObj.selectByVisibleText("The Avengers");

			// Get all currently selected options in the dropdown
			List<WebElement> selectedOptions = selectObj.getAllSelectedOptions();

			// Create a list to store text from selected options
			List<String> selectedOptionsText = new ArrayList<>();

			// Iterate through each selected option WebElement
			for (WebElement element : selectedOptions) {

				// Extract the visible text of each selected option and add it to the list
				selectedOptionsText.add(element.getText());
			}

			// Assert that specific options are selected
			Assert.assertTrue(selectedOptionsText.contains("Ant-Man"));
			Assert.assertTrue(selectedOptionsText.contains("Aquaman"));
			Assert.assertTrue(selectedOptionsText.contains("The Avengers"));

			// Deselect options by index, value, and visible text
			selectObj.deselectByIndex(0);
			selectObj.deselectByValue("aq");
			selectObj.deselectByVisibleText("The Avengers");

			// Retrieve all selected options and their text after deselecting some options
			selectedOptions = selectObj.getAllSelectedOptions();

			// Create a new list to store the text of selected options after deselection
			selectedOptionsText = new ArrayList<>();

			// Loop through each WebElement representing the selected options
			for (WebElement element : selectedOptions) {

				// Extract the visible text of each selected option and add it to the list
				selectedOptionsText.add(element.getText());
			}

			// Assert that specific options are deselected
			Assert.assertFalse(selectedOptionsText.contains("Ant-Man"));
			Assert.assertFalse(selectedOptionsText.contains("Aquaman"));
			Assert.assertFalse(selectedOptionsText.contains("The Avengers"));
		} else {
			// Print a message if the dropdown doesn't support multiple selections
			System.out.println("This dropdown does not support multiple selections.");
		}
	}

}
