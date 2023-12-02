package concepts.cdp;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.performance.Performance;
import org.openqa.selenium.devtools.v119.performance.model.Metric;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class GetPerformanceMetricsTest {

	// Declare a WebDriver instance to interact with the web browser.
	private ChromeDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.cdpBrowserSetup();
	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void testGetPerformanceMetrics() {
		// Navigate to the Selenium website
		driver.get("https://selenium.dev/");

		// Get the DevTools interface from the WebDriver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session
		devTools.createSession();

		// Enable the Performance domain to gather performance metrics
		devTools.send(Performance.enable(Optional.empty()));

		// Get the list of performance metrics
		List<Metric> metricsList = devTools.send(Performance.getMetrics());

		// Assert that the metrics list is not null and not empty
		assertNotNull(metricsList, "Metrics list should not be null");
		assertFalse(metricsList.isEmpty(), "Metrics list should not be empty");

		// Iterate through the metrics list
		for (Object metric : metricsList) {

			// Check if metric is a LinkedHashMap representing a custom metric
			if (metric instanceof Map) {

				// Cast 'metric' to a LinkedHashMap<String, Object>
				LinkedHashMap<String, Object> metricObj = (LinkedHashMap<String, Object>) metric;

				// Print metric name and value
				System.out.println(metricObj.get("name") + " = " + metricObj.get("value"));

				// Perform assertions based on specific metric names
				if (metricObj.get("name").equals("DevToolsCommandDuration")) {

					// Assert that the value for DevToolsCommandDuration is positive
					Assert.assertTrue((double) metricObj.get("value") >= 0, "DevToolsCommandDuration value should be positive");
				} else if (metricObj.get("name").equals("Frames")) {

					// Assert that the value for Frames is equal to 6
					Assert.assertEquals((Long) metricObj.get("value"), 6, "Frames value should be 6");
				}
			}
		}
	}

}