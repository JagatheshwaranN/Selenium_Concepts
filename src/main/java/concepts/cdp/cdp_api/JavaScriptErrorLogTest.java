package concepts.cdp.cdp_api;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.runtime.Runtime;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class JavaScriptErrorLogTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testJavaScriptErrorLog() {
        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        // Access the DevTools interface
        DevTools devTools = driver.getDevTools();

        // Ensure a DevTools session is active
        devTools.createSessionIfThereIsNotOne();

        // Enable Runtime domain to access javascript exception
        devTools.send(Runtime.enable());

        // Create a thread-safe list to store javascript exception
        CopyOnWriteArrayList<JavascriptException> errors = new CopyOnWriteArrayList<>();

        // Add a listener to capture javascript exception
        devTools.getDomains().events().addJavascriptExceptionListener(errors::add);

        // Trigger the javascript exception within the page
        driver.findElement(By.id("jsException")).click();

        // Wait for the javascript exception to be captured (up to 10 seconds)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driverObject -> !errors.isEmpty());

        // Assert that the captured errors contain the expected message
        Assert.assertTrue(errors.get(0).getMessage().contains("Error: Not working"));

        // Disable Runtime domain after test execution
        devTools.send(Runtime.disable());
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