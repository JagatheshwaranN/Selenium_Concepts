package concepts.browsers.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddExtensionTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void testAddExtension() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Create a Path object pointing to the extension file
        Path path = Paths.get("src/main/resources/extension/webextensions-selenium-example.crx");

        // Convert the Path to a URI and creating a File object from it
        File extensionFilePath = new File(path.toUri());

        // Add the extension file to ChromeOptions
        chromeOptions.addExtensions(extensionFilePath);

        // Initialize ChromeDriver with ChromeOptions
        driver = new ChromeDriver(chromeOptions);

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
