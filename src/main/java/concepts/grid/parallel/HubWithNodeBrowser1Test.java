package concepts.grid.parallel;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class HubWithNodeBrowser1Test {

    // WebDriver reference used to control the remote browser session
    public WebDriver driver;

    // DesiredCapabilities object to define browser and platform details for Selenium Grid
    public DesiredCapabilities capabilities = new DesiredCapabilities();

    @Test
    public void testHubWithNodeBrowser1() {

        // Browser name to be used for this test execution
        String browser = "chrome";

        // Allow the test to run on any platform registered with the Grid
        capabilities.setPlatform(Platform.ANY);

        // Specify the browser type to be used by the remote node
        capabilities.setBrowserName(browser);

        // ChromeOptions to customize Chrome-specific settings (if needed later)
        ChromeOptions options = new ChromeOptions();

        // Merge DesiredCapabilities into ChromeOptions
        options.merge(capabilities);

        try {
            // Create a RemoteWebDriver instance pointing to the Selenium Grid Hub
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444").toURL(),
                    options
            );
        } catch (MalformedURLException e) {
            // Wrap checked exception into runtime exception for simplicity
            throw new RuntimeException(e);
        }

        // Clean up any existing cookies before starting the test
        driver.manage().deleteAllCookies();

        // Maximize browser window for consistent UI behavior
        driver.manage().window().maximize();

        // Set maximum time to wait for a page to load completely
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        // Set implicit wait for locating elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navigate to the application under test
        driver.get("https://www.google.com/");

        // Print page title along with browser name for verification
        System.out.println(
                "Title of Page " + driver.getTitle() + " from Browser " + browser
        );
    }

    @AfterMethod
    public void tearDown() {

        // Ensure the WebDriver instance exists before attempting to quit
        if (driver != null) {

            // Close the browser session and release resources on the node
            driver.quit();
        }
    }
}