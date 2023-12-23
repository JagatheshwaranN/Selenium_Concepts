package concepts.driver.chrome.options;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class DetachBrowserTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void browserDetails() {
        /*
            The driver.close() method will close the browser window even if the detach option
            is set to true.
            This is a bug in the Selenium WebDriver implementation. The detach option is intended
            to prevent the browser process from being terminated when the Selenium script finishes
            executing. However, the driver.close() method currently closes the browser window
            regardless of the detach option setting.

            https://github.com/SeleniumHQ/selenium/issues/10658
        */

        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Sets the experimental option "detach" to true. This will prevent the browser process
        // from being terminated when the Selenium script finishes executing.
        chromeOptions.setExperimentalOption("detach", true);

        // Sets the capability "detach" to true. This is the same as setting the experimental
        // option above, but it is the preferred way to set the detach option in newer versions
        // of Selenium.
        chromeOptions.setCapability("detach", true);

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();

        // Starts the Chrome browser in detached mode.
        driver.get("https://www.google.com/");

        // Asserts that the title of the browser window is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.close();
        }
    }

}
