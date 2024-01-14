package concepts.cdp.bidi_api;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class PinTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPinScript() {
        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/xhtmlTest.html");

        // Find the element with ID "id1"
        WebElement element = driver.findElement(By.id("id1"));

        // Cast the driver to a JavascriptExecutor for script execution
        JavascriptExecutor javascriptExecutor = driver;

        // Pin a JavaScript snippet that returns its arguments
        ScriptKey scriptKey = javascriptExecutor.pin("return arguments;");

        // Execute the pinned script with arguments 1, true, and the element
        List<Object> arguments = (List<Object>) javascriptExecutor.executeScript(scriptKey, 1, true, element);

        // Assert that the returned arguments match the expected list
        Assert.assertEquals(List.of(1L, true, element), arguments);
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
