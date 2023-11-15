package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class MouseDragDropByOffsetTest {

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
	public void testMouseDragDropByOffset() {
		// Define the expected value after the drag-and-drop action
		String expectedValue = "dropped";

		// Open the web page with the drag-and-drop demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		/*
			Another way to get the element location
			=======================================
			Rectangle start = driver.findElement(By.id("draggable")).getRect();
			Rectangle finish = driver.findElement(By.id("droppable")).getRect();
		*/
		// Create an Actions object for performing actions on the page
		Actions actions = new Actions(driver);

		// Find the draggable element and retrieve its location
		WebElement draggableElement = driver.findElement(By.id("draggable"));
		Point draggableCoords = draggableElement.getLocation();

		// Find the droppable element and retrieve its location
		WebElement droppableElement = driver.findElement(By.id("droppable"));
		Point droppableCoords = droppableElement.getLocation();

		// Calculate the offset between draggable and droppable elements and perform the drag-and-drop action
		actions.dragAndDropBy(
				draggableElement,
				droppableCoords.getX() - draggableCoords.getX(), // Calculate X-axis offset
				droppableCoords.getY() - draggableCoords.getY()  // Calculate Y-axis offset
		).perform();

		// Retrieve the text from the specified element to verify the drop status
		String actualValue = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();

		// Assert the expected drop status with the actual value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
