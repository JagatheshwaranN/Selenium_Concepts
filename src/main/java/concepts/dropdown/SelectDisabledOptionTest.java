package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;


public class SelectDisabledOptionTest {

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
	public void testSelectDisabledOption() {
		// Define the expected error message
		String expectedMessage = "You may not select a disabled option";

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/DisabledSelect.html";

		// Navigate to the specified URL
		driver.get(new File(filePath).toURI().toString());

		// Locate the select dropdown element
		Select selectObject = new Select(driver.findElement(By.name("single_disabled")));

		try {
			// Attempt to select a disabled option
			selectObject.selectByValue("disabled");

			// Fail the test if the selection didn't throw an exception
			Assert.fail("Expected UnsupportedOperationException to be thrown");
		} catch (UnsupportedOperationException e) {
			// Catch the expected exception and verify its error message
			String actualMessage = e.getMessage();

			// Verify that the actual error message matches the expected error message
			Assert.assertEquals(actualMessage, expectedMessage);
		}
	}

}
