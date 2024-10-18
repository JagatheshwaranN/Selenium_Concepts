package concepts.cdp.cdp_api;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.runtime.Runtime;
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
public class ConsoleLogTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testConsoleLog() {
        // Define the expected console log message
        String expectedLog = "Hello, world!";

        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        // Access the DevTools interface
        DevTools devTools = driver.getDevTools();

        // Ensure a DevTools session is active
        devTools.createSessionIfThereIsNotOne();

        // Enable Runtime domain to access console logs
        devTools.send(Runtime.enable());

        // Create a thread-safe list to store captured logs
        CopyOnWriteArrayList<String> logs = new CopyOnWriteArrayList<>();

        // Add a listener to capture console logs
        devTools.addListener(Runtime.consoleAPICalled(), event -> {
            // Extract the log message from the event
            String logMessage = (String) event.getArgs().get(0).getValue().orElse("");
            logs.add(logMessage);
        });

        // Trigger the console log within the page
        driver.findElement(By.id("consoleLog")).click();

        // Wait for the console log to be captured (up to 10 seconds)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driverObject -> !logs.isEmpty());

        // Assert that the captured log matches the expected message
        Assert.assertEquals(logs.get(0), expectedLog);

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