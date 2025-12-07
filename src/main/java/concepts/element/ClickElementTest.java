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

        // Define the expected message
        String expectedMessage = "focused";

        // Navigate to the Selenium mouse interaction test page
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        // Locate the clickable element using its ID
        WebElement login = driver.findElement(By.id("clickable"));

        // Perform a click action on the located element
        login.click();

        // Locate the element that displays the click status message
        WebElement actualMessage = driver.findElement(By.id("click-status"));

        // Assert that the displayed message matches the expected message
        Assert.assertEquals(actualMessage.getText(), expectedMessage);
    }

}
