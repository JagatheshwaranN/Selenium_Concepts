package concepts.bidi.log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.log.StackTrace;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JavaScriptStackTraceLogTest {

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
    public void testJavaScriptStackTraceLog() throws InterruptedException, ExecutionException, TimeoutException {
        // Define the expected number of frames in the JavaScript stack trace
        int expectedValue = 3;

        // Creates a LogInspector object to monitor JavaScript logs
        try (LogInspector logInspector = new LogInspector(driver)) {

            // Create a CompletableFuture object to wait and handle asynchronous JavaScript log entries
            CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<>();

            // Register a callback for when a JavaScript exception log entry is found
            logInspector.onJavaScriptException(future::complete);

            // Navigate to the target URL
            driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

            // Locate and click the button with the ID 'logWithStacktrace', that triggers a JavaScript log entry with a stack trace
            driver.findElement(By.xpath("//button[@id='logWithStacktrace']")).click();

            // Get the JavaScript exception log entry asynchronously within a timeout period
            JavascriptLogEntry logEntry = future.get(WAIT_TIMEOUT, TimeUnit.SECONDS);

            // Retrieve the stack trace from the log entry
            StackTrace stackTrace = logEntry.getStackTrace();

            // Get the actual number of frames in the stack trace
            int actualValue = stackTrace.getCallFrames().size();

            // Verify that the stack trace is not null
            Assert.assertNotNull(stackTrace);

            // Assert that the captured actual stack trace frame size matches the expected value
            Assert.assertEquals(actualValue, expectedValue);
        }
    }

}