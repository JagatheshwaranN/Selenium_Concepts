package concepts.bidi.console;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.LogInspector;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.log.LogLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JavaScriptErrorLogTest {

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
    public void testJavaScriptErrorLog() throws InterruptedException, ExecutionException, TimeoutException {
        // Define the expected log entry text
        String expectedValue = "Error: Not working";

        // Creates a LogInspector object to monitor JavaScript logs
        try (LogInspector logInspector = new LogInspector(driver)) {

            // Create a CompletableFuture object to wait and handle asynchronous JavaScript log entries
            CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<>();

            // Register a callback function to be invoked whenever a JavaScript exception entry is added
            logInspector.onJavaScriptException(future::complete);

            // Navigate to the target URL
            driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

            // Locate and click the button with the ID 'jsException', that triggers a JavaScript log entry
            driver.findElement(By.xpath("//button[@id='jsException']")).click();

            // Get the JavaScript log entry asynchronously within a timeout period
            JavascriptLogEntry logEntry = future.get(WAIT_TIMEOUT, TimeUnit.SECONDS);

            // Extract the actual log entry text
            String actualValue = logEntry.getText();

            // Verify the log entry type is "javascript"
            Assert.assertEquals(logEntry.getType(), "javascript");

            // Verify the log entry level is "ERROR"
            Assert.assertEquals(logEntry.getLevel(), LogLevel.ERROR);

            // Verify that the actual log entry text matches the expected value
            Assert.assertEquals(actualValue, expectedValue);
        }
    }

}