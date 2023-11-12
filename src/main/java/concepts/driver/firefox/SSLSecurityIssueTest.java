package concepts.driver.firefox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SSLSecurityIssueTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void acceptSSLSecurityIssueOnFirefox() {
        // Define the expected title for comparison
        String expectedTitle = "untrusted-root.badssl.com";

        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate FirefoxOptions to configure the FirefoxDriver
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Allow the acceptance of insecure certificates
        firefoxOptions.setAcceptInsecureCerts(true);

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxOptions);

        // Navigate to the untrusted website
        driver.get("https://untrusted-root.badssl.com/");

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(expectedTitle, driver.getTitle(), "Actual title does not match expected title.");
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
