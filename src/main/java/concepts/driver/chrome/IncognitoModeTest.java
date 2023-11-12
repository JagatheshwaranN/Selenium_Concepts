package concepts.driver.chrome;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class IncognitoModeTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void openChromeIncognito() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Add the argument to launch the Chrome browser in incognito mode
        chromeOptions.addArguments("incognito");

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

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
