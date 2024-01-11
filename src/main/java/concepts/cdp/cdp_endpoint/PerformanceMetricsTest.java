package concepts.cdp.cdp_endpoint;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.HasCdp;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceMetricsTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test @SuppressWarnings("unchecked")
    public void testPerformanceMetrics() {
        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/frameset.html");

        // Enable the Performance domain in Chrome DevTools Protocol (CDP)
        ((HasCdp) driver).executeCdpCommand("Performance.enable", new HashMap<>());

        // Retrieve performance metrics data using the Performance domain in CDP
        Map<String, Object> performanceData = ((HasCdp) driver).executeCdpCommand("Performance.getMetrics", new HashMap<>());

        // Extract the list of metrics from the performance data
        List<Map<String, Object>> metricList = (List<Map<String, Object>>) performanceData.get("metrics");

        // Iterate through each metric in the list
        for (Map<String, Object> metric : metricList) {
            // Print the name and value of each metric
            System.out.println(metric.get("name") + " = " + metric.get("value"));

            // Perform specific assertions for certain metrics:
            if (metric.get("name").equals("DevToolsCommandDuration")) {

                // Assert that the DevToolsCommandDuration value is non-negative
                Assert.assertTrue((double) metric.get("value") >= 0, "DevToolsCommandDuration value should be positive");
            } else if (metric.get("name").equals("Frames")) {

                // Assert that the Frames value is 12
                Assert.assertEquals((Long) metric.get("value"), 12, "Frames value should be 12");
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
