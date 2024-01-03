package concepts.browsers.firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AddExtensionTest {

    // Declare a FirefoxDriver instance to interact with the web browser.
    private FirefoxDriver driver;

    @Test(priority = 1)
    public void testAddExtension() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a Path object pointing to the extension file
        Path path = Paths.get("src/main/resources/extension/selenium-example.xpi");

        // Create a FirefoxDriver instance
        driver = new FirefoxDriver();

        // Add the extension to the driver
        driver.installExtension(path);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to a test page on Selenium's website
        driver.get("https://www.selenium.dev/selenium/web/blank.html");

        // Find an element on the page by its ID
        WebElement element = driver.findElement(By.id("webextensions-selenium-example"));

        // Assert that the element's text matches an expected value
        Assert.assertEquals(element.getText(), "Content injected by webextensions-selenium-example");
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
