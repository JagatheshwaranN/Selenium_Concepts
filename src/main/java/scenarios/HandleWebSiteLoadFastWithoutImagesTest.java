package scenarios;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class HandleWebSiteLoadFastWithoutImagesTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Declaration of the ChromeOptions object for configuring Chrome driver options
	public ChromeOptions chromeOptions;

	// Declaration of the FirefoxOptions object for configuring Firefox driver options
	public FirefoxOptions firefoxOptions;

	// Declaration of the EdgeOptions object for configuring Edge driver options
	public EdgeOptions edgeOptions;

	@Test(priority = 1)
	public void loadWebsiteFastWithoutImagesOnChrome(){
		// Call the method to set up Chrome browser
		chromeSetup();

		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());

		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	@Test(priority = 2)
	public void loadWebsiteFastWithoutImagesOnFirefox(){
		// Call the method to set up Firefox browser
		firefoxSetup();

		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());

		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	@Test(priority = 3)
	public void loadWebsiteFastWithoutImagesOnEdge(){
		// Call the method to set up Edge browser
		edgeSetup();

		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());

		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	public void chromeSetup() {
		// Set the system property for the HTTP factory
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		// Initialize ChromeOptions
		chromeOptions = new ChromeOptions();

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

	public void firefoxSetup() {
		// Initialize FirefoxOptions
		firefoxOptions = new FirefoxOptions();

		// Create a new Firefox profile
		FirefoxProfile firefoxProfile = new FirefoxProfile();

		// Set the preference for image permissions
		firefoxProfile.setPreference("permissions.default.image", 2);

		// Set the FirefoxOptions with the created profile
		firefoxOptions.setProfile(firefoxProfile);

		// Create a new FirefoxDriver with the defined options
		driver = new FirefoxDriver(firefoxOptions);

		// Maximize the browser window
		driver.manage().window().maximize();
	}

	public void edgeSetup() {
		// Initialize EdgeOptions
		edgeOptions = new EdgeOptions();

		// Create a map for the image configuration
		Map<String, Object> imageConfig = new HashMap<>();
		imageConfig.put("images", 2);

		// Create a map for the edge preferences
		Map<String, Object> edgePreferences = new HashMap<>();
		edgePreferences.put("profile.default_content_setting_values", imageConfig);

		// Set the experimental options for Edge with the defined preferences
		edgeOptions.setExperimentalOption("prefs", edgePreferences);

		// Create a new EdgeDriver with the defined options
		driver = new EdgeDriver(edgeOptions);

		// Maximize the browser window
		driver.manage().window().maximize();
	}

}
