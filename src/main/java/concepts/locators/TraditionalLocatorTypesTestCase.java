package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class TraditionalLocatorTypesTestCase {

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
    public void traditionalLocators() {
        driver.get("https://www.selenium.dev/selenium/web/inputs.html");
        // 1. Name
        Assert.assertTrue(driver.findElement(By.name("no_type")).isDisplayed());
        // 2. XPath
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='checkbox']")).isDisplayed());

        driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
        // 3. Id
        Assert.assertTrue(driver.findElement(By.id("click")).isDisplayed());
        // 4. LinkText
        Assert.assertTrue(driver.findElement(By.linkText("Click for Results Page")).isDisplayed());
        // 5. PartialLinkText
        Assert.assertTrue(driver.findElement(By.partialLinkText("Click for Result")).isDisplayed());
        // 6. Class
        Assert.assertTrue(driver.findElement(By.className("ui-droppable")).isDisplayed());
        // 7. CSS
        Assert.assertTrue(driver.findElement(By.cssSelector(".ui-widget-header.ui-droppable")).isDisplayed());
        // 8. Tag
        Assert.assertTrue(driver.findElement(By.tagName("a")).isDisplayed());
    }

}
