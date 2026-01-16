package scenarios.load_website;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirefoxWebSiteLoadFastWithoutImagesTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Initialize FirefoxOptions
		FirefoxOptions firefoxOptions = new FirefoxOptions();

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

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

	@Test(priority = 1)
	public void testWebSiteLoadFastWithoutImagesOnFirefox(){
		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());
	}

}
