package scenarios;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.blibli.oss.qa.util.services.NetworkListener;

public class HandleNetworkCallsTest {

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
	public void testCaptureNetworkLogs() {
		// Start a network listener using the 'startNetworkListener()' method or function
		NetworkListener networkListener = startNetworkListener();

		// Instruct the WebDriver instance (already configured) to navigate to the URL "https://www.selenium.dev/"
		driver.get("https://www.selenium.dev/");

		// Wait for the page to load completely.
		waitForPageLoad();

		// Create the HAR (HTTP Archive) file after capturing network activity.
		networkListener.createHarFile();
	}

	private NetworkListener startNetworkListener() {
		// Create a new instance of a 'NetworkListener' with the WebDriver instance 'driver' and a file name "NetworkRequest.har"
		NetworkListener networkListener = new NetworkListener(driver, "NetworkRequest.har");

		// Start capturing network activity.
		networkListener.start();

		// Return the network listener for further use or reference.
		return networkListener;
	}

	private void waitForPageLoad() {
		// Create a WebDriverWait instance with a timeout of 10 seconds.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Use an ExpectedCondition with JavaScript expression to wait until the document's ready state is 'complete.'
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
	}

}
