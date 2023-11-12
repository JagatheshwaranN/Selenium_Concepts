package concepts.driver.firefox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class PrivateModeTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void openFirefoxInPrivate() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate FirefoxOptions to configure the FirefoxDriver
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Add the argument to launch the Firefox browser in private mode
        firefoxOptions.addArguments("--private");

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxOptions);

        // Navigate to the specified URL
        driver.get("https://www.google.com/");

        // Compare the actual title with the expected title and assert their equality
        Assert.assertEquals(driver.getTitle(), "Google");
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
