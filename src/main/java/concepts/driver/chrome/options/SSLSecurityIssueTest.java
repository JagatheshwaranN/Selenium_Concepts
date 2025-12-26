package concepts.driver.chrome.options;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SSLSecurityIssueTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void acceptSSLSecurityIssueOnChrome() {
        // Define the expected title for comparison
        String expectedTitle = "untrusted-root.badssl.com";

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Allow the acceptance of insecure certificates
        chromeOptions.setAcceptInsecureCerts(true);

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // Navigate to the untrusted website
        driver.get("https://untrusted-root.badssl.com/");

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Actual title does not match expected title.");
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
