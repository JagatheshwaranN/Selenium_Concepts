package concepts.browsers.chrome;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.webextension.ExtensionPath;
import org.openqa.selenium.bidi.webextension.InstallExtensionParameters;
import org.openqa.selenium.bidi.webextension.WebExtension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class AddExtensionTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1, enabled = false,
            description = "Chrome blocks automation-installed extensions on Windows due to security policies"
    )
    public void testAddExtension() {
        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.enableBiDi();
        chromeOptions.addArguments("--remote-debugging-pipe");
        chromeOptions.addArguments("--enable-unsafe-extension-debugging");

        // Create a Path object pointing to the extension file
        Path path = Paths.get("src/main/resources/extension/chrome/selenium-example");

        // Convert the Path to a URI and creating a File object from it
        File extensionFilePath = new File(path.toUri());

        // Initialize ChromeDriver with ChromeOptions
        driver = new ChromeDriver(chromeOptions);

        WebExtension extension = new WebExtension(driver);
        ExtensionPath extensionPath = new ExtensionPath(path.toString());
        InstallExtensionParameters parameters = new InstallExtensionParameters(extensionPath);
        extension.install(parameters);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to a test page on Selenium's website
        driver.get("https://www.selenium.dev/selenium/web/blank.html");

        // Find an element on the page by its ID
        WebElement element = driver.findElement(By.id("webextensions-selenium-example"));

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

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
