package scenarios.website_load;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class EdgeWebSiteLoadFastWithoutImagesTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set the system property for the HTTP factory
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		// Initialize EdgeOptions
		EdgeOptions edgeOptions = new EdgeOptions();

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


	@Test(priority = 1)
	public void testWebSiteLoadFastWithoutImagesOnEdge(){
		// Open the Amazon website
		driver.get("https://www.amazon.in/");

		// Use WebDriverWait to wait for the 'Orders' element to be present on the page
		WebElement orders = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-orders")));

		// Verify if the 'Orders' element is displayed on the page
		Assert.assertTrue(orders.isDisplayed());
	}

}
