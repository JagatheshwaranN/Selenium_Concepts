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

		// 1. Name
		// Selects the input element with the name `no_type`.
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");
		Assert.assertTrue(driver.findElement(By.name("no_type")).isDisplayed());

		// 2. XPath
		// Selects the input element whose type attribute is `checkbox`.
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='checkbox']")).isDisplayed());

		// 3. Id
		// Select the element with the ID `click`.
		driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		Assert.assertTrue(driver.findElement(By.id("click")).isDisplayed());

		// 4. LinkText
		// Selects the anchor element whose link text is `Click for Results Page`.
		driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		Assert.assertTrue(driver.findElement(By.linkText("Click for Results Page")).isDisplayed());

		// 5. PartialLinkText
		// Selects the anchor element whose link text contains the text `Click for Result`.
		driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		Assert.assertTrue(driver.findElement(By.partialLinkText("Click for Result")).isDisplayed());

		// 6. Class
		// Selects the element with the class `ui-droppable`.
		driver.get("https://www.selenium.dev/selenium/web/droppable.html");
		Assert.assertTrue(driver.findElement(By.className("ui-droppable")).isDisplayed());

		// 7. CSS
		// Selects the element with the class `ui-widget-header` and the class `ui-droppable`.
		driver.get("https://www.selenium.dev/selenium/web/droppable.html");
		Assert.assertTrue(driver.findElement(By.cssSelector(".ui-widget-header.ui-droppable")).isDisplayed());

		// 8. Tag
		// Selects the first anchor element on the page.
		Assert.assertTrue(driver.findElement(By.tagName("a")).isDisplayed());
	}

}
