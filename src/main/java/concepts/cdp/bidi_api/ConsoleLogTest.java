package concepts.cdp.bidi_api;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.openqa.selenium.devtools.events.CdpEventTypes.consoleEvent;

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
        // Set expected log messages
        String expectedLog1 = "Hello, world!";
        String expectedLog2 = "I am console error";

        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        // Create a thread-safe list to store console log messages
        CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();

        // Register a listener for console log events
        driver.onLogEvent(consoleEvent(element -> messages.add(element.getMessages().getFirst())));

        // Trigger console logs by clicking buttons
        driver.findElement(By.id("consoleLog")).click();
        driver.findElement(By.id("consoleError")).click();

        // Wait for at least two messages to be captured
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driverObject -> messages.size() > 1);

        // Assert that the expected log messages are present in the collected messages
        Assert.assertTrue(messages.contains(expectedLog1));
        Assert.assertTrue(messages.contains(expectedLog2));
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