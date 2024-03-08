package concepts.keyboard;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ReturnKeyTest {

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
	public void testReturnKey() {

		// Navigate to the Google homepage
		driver.get("https://www.google.com/");

		// Find the WebElement representing the search bar by its name attribute ("q")
		WebElement searchBar = driver.findElement(By.name("q"));

		// Type the search query "Selenium" into the search bar
		searchBar.sendKeys("Selenium");

		// Simulate pressing the Enter key to perform the search
		searchBar.sendKeys(Keys.RETURN);
	}

}
