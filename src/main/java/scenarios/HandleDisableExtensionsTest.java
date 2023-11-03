package scenarios;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;



public class HandleDisableExtensionsTest {

	// Declare a WebDriver instance to interact with the web browser.
	public WebDriver driver;

	// Declare a ChromeOptions instance to configure Chrome browser settings.
	public ChromeOptions chromeOptions;

	@Test(priority = 1)
	public void disableExtension() {
		// Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
		System.setProperty("webdriver.http.factory", "jdk-http-client");

		// Create ChromeOptions instance to configure the browser.
		chromeOptions = new ChromeOptions();

		// Add an argument to disable browser extensions.
		chromeOptions.addArguments("--disable-extensions");

		// Initialize the WebDriver with the configured ChromeOptions.
		driver = new ChromeDriver(chromeOptions);

		// Maximize the browser window.
		driver.manage().window().maximize();

		// Navigate to Google.
		driver.get("https://www.google.com/");

		// Verify that the page title is "Google".
		Assert.assertEquals(driver.getTitle(), "Google");

		// Close the browser.
		driver.quit();
	}

}
