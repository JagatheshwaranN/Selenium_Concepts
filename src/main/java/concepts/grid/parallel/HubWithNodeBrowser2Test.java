package concepts.grid.parallel;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class HubWithNodeBrowser2Test {

    // WebDriver reference to control the remote Edge browser session
    public WebDriver driver;

    // DesiredCapabilities used to define browser and platform details for Selenium Grid
    public DesiredCapabilities capabilities = new DesiredCapabilities();

    @Test
    public void testHubWithNodeBrowser2() {

        // Logical browser name used for logging purposes
        String browser = "edge";

        // Allow execution on any platform registered with the Selenium Grid
        capabilities.setPlatform(Platform.ANY);

        // Specify Microsoft Edge as the target browser for the remote node
        capabilities.setBrowserName("MicrosoftEdge");

        // EdgeOptions object for Edge-specific configurations
        EdgeOptions options = new EdgeOptions();

        // Merge DesiredCapabilities into EdgeOptions
        options.merge(capabilities);

        try {
            // Create a RemoteWebDriver instance pointing to the Selenium Grid Hub
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444").toURL(),
                    options
            );
        } catch (MalformedURLException e) {
            // Convert checked exception into unchecked exception
            throw new RuntimeException(e);
        }

        // Delete all cookies to ensure a clean browser session
        driver.manage().deleteAllCookies();

        // Maximize browser window for consistent layout and visibility
        driver.manage().window().maximize();

        // Set maximum time to wait for a page to fully load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        // Set implicit wait for locating web elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navigate to the Selenium official website
        driver.get("https://selenium.dev/");

        // Log page title along with browser name for verification
        System.out.println(
                "Title of Page " + driver.getTitle() + " from Browser " + browser
        );
    }

    @AfterMethod
    public void tearDown() {

        // Verify that WebDriver instance exists before attempting to quit
        if (driver != null) {

            // Close the browser session and release Grid node resources
            driver.quit();
        }
    }
}