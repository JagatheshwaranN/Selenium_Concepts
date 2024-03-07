package concepts.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MouseDragDropWithoutBuiltInOptionTest {

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
	public void testMouseDragDrop() {
		// Define the expected value after the drag-and-drop action
		String expectedValue = "dropped";

		// Open the web page with the drag-and-drop demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Locate the draggable element on the web page using its ID
		WebElement draggableElement = driver.findElement(By.id("draggable"));

		// Locate the droppable element on the web page using its ID
		WebElement droppableElement = driver.findElement(By.id("droppable"));

		// Create an Actions object to perform mouse actions
		Actions actions = new Actions(driver);

		// Perform a click-and-hold action on the draggable element
		actions.clickAndHold(draggableElement)
				// Move the mouse to the location of the droppable element
				.moveToElement(droppableElement)
				// Release the mouse to drop the draggable element onto the droppable element
				.release(droppableElement)
				// Perform the entire sequence of actions
				.perform();

		// Retrieve the text from the element with the xpath "//strong[@id='drop-status']"
		String actualValue = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
