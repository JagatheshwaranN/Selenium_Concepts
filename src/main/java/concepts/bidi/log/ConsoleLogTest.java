package concepts.bidi.log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.LogInspector;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsoleLogTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds
    private static final long WAIT_TIMEOUT = 10;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'bidiBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.bidiBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testConsoleLog() throws InterruptedException, ExecutionException, TimeoutException {
        //  Define the expected log entry text
        String expectedValue = "Hello, world!";

        try (LogInspector logInspector = new LogInspector(driver)) {
            // Create a CompletableFuture to capture the console log entry
            CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<>();

            // Register a callback function to be invoked whenever a new console log entry is emitted
            logInspector.onConsoleEntry(future::complete);

            // Navigate to the target URL
            driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

            // Locate and click the button with the ID 'consoleLog', triggering the generation of a log entry
            driver.findElement(By.xpath("//button[@id='consoleLog']")).click();

            // Retrieve the captured log entry from the CompletableFuture within a WAIT_TIMEOUT-second timeout
            ConsoleLogEntry logEntry = future.get(WAIT_TIMEOUT, TimeUnit.SECONDS);

            // Extract the actual log entry text
            String actualValue = logEntry.getText();

            // Verify that the log entry has one argument
            Assert.assertEquals(logEntry.getArgs().size(), 1);

            // Verify that the log entry's type is "console"
            Assert.assertEquals(logEntry.getType(), "console");

            // Verify that the log entry's method is "log"
            Assert.assertEquals(logEntry.getMethod(), "log");

            // Verify that the log entry's stack trace is not null
            Assert.assertNotNull(logEntry.getStackTrace().toString());

            // Verify that the actual log entry text matches the expected value
            Assert.assertEquals(actualValue, expectedValue);
        }
    }

}