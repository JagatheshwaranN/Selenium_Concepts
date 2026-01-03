package concepts.browsers.firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveExtensionTest {

    // Declare a FirefoxDriver instance to interact with the web browser.
    private FirefoxDriver driver;

    @Test(priority = 1)
    public void testRemoveExtension() {
        // Create a Path object pointing to the extension file
        Path path = Paths.get("src/main/resources/extension/firefox/selenium-example.xpi");

        // Create a FirefoxDriver instance
        driver = new FirefoxDriver();

        // Add the extension to the driver and store it in extensionId
        String extensionId = driver.installExtension(path);

        // Remove the extension
        driver.uninstallExtension(extensionId);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to a test page on Selenium's website
        driver.get("https://www.selenium.dev/selenium/web/blank.html");

        // Assert that no elements with the specified ID are present on the page
        Assert.assertEquals(driver.findElements(By.id("webextensions-selenium-example")).size(), 0);
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
