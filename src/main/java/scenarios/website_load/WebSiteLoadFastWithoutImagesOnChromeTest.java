package scenarios.website_load;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebSiteLoadFastWithoutImagesOnChromeTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set the system property for the HTTP factory
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		// Initialize ChromeOptions
		ChromeOptions chromeOptions = new ChromeOptions();

		// Set image configuration settings
		Map<String, Object> imageConfig = new HashMap<>();
		imageConfig.put("images", 2);

		// Set Chrome preferences with the image configuration
		Map<String, Object> chromePreferences = new HashMap<>();
		chromePreferences.put("profile.default_content_setting_values", imageConfig);

		// Set the experimental option with the preferences
		chromeOptions.setExperimentalOption("prefs", chromePreferences);

		// Create a new ChromeDriver with the defined options
		driver = new ChromeDriver(chromeOptions);

		// Maximize the browser window
		driver.manage().window().maximize();
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
	public void testWebSiteLoadFastWithoutImagesOnChrome(){
		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());
	}

}
