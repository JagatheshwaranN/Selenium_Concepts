package concepts.driver.edge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SSLSecurityIssueTestCase {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void acceptSSLSecurityIssueOnEdge() {
        // Define the expected title for comparison
        String expectedTitle = "untrusted-root.badssl.com";

        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate EdgeOptions to configure the EdgeDriver
        EdgeOptions edgeOptions = new EdgeOptions();

        // Allow the acceptance of insecure certificates
        edgeOptions.setAcceptInsecureCerts(true);

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

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
