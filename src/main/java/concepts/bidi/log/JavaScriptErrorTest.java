package concepts.bidi.log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.concurrent.*;

public class JavaScriptErrorTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 5 seconds
    private static final long WAIT_TIMEOUT = 5;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'bidiBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.bidiBrowserSetup();
    }

    @Test(priority = 1)
    public void testJavaScriptError() {
        // Expected log message
        String expectedLog = "Hello, world!";

        // List to store console log entries
        CopyOnWriteArrayList<ConsoleLogEntry> logs = new CopyOnWriteArrayList<>();

        // Try-with-resources to ensure LogInspector is closed properly
        try (LogInspector logInspector = new LogInspector(driver)) {
            // Register a callback to collect console log entries
            logInspector.onConsoleEntry(logs::add);
        }

        // Navigate to the test page
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        // Trigger an action that generates a console log (assuming a button with id "consoleLog")
        driver.findElement(By.id("consoleLog")).click();

        // Wait for console log entries to be populated (max wait time: 5 seconds)
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT)).until(driverObject -> !logs.isEmpty());

        // Assert that the first log entry text matches the expected log
        Assert.assertEquals(logs.get(0).getText(), expectedLog);
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