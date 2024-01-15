package concepts.bidi.log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.LogInspector;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JavaScriptErrorLogWithMultiConsumerTest {

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
    public void testJavaScriptErrorLogWithMultiConsumer() throws InterruptedException, ExecutionException, TimeoutException {
        // Define expected values for text and type
        String expectedText = "Error: Not working";
        String expectedType = "javascript";

        // Create LogInspector to monitor JavaScript logs
        try (LogInspector logInspector = new LogInspector(driver)) {

            // Create two CompletableFutures objects to wait and handle asynchronous JavaScript log entries
            CompletableFuture<JavascriptLogEntry> future1 = new CompletableFuture<>();
            CompletableFuture<JavascriptLogEntry> future2 = new CompletableFuture<>();

            // Register a callback function to be invoked whenever a JavaScript log entry is added
            logInspector.onJavaScriptException(future1::complete);

            // Register a callback function to be invoked whenever a JavaScript log entry is added
            logInspector.onJavaScriptException(future2::complete); // Register second listener

            // Navigate to the target URL
            driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

            // Locate and click the button with the ID 'logWithStacktrace', that triggers a JavaScript log entry with a stack trace
            driver.findElement(By.xpath("//button[@id='logWithStacktrace']")).click();

            // Get the JavaScript exception log entry asynchronously from first future within a timeout period
            JavascriptLogEntry logEntry1 = future1.get(WAIT_TIMEOUT, TimeUnit.SECONDS);
            String actualText = logEntry1.getText();
            String actualType = logEntry1.getType();

            // Assert that retrieved values match expected values
            Assert.assertEquals(actualText, expectedText);
            Assert.assertEquals(actualType, expectedType);

            // Get the JavaScript exception log entry asynchronously from second future within a timeout period
            JavascriptLogEntry logEntry2 = future2.get(WAIT_TIMEOUT, TimeUnit.SECONDS);
            actualText = logEntry2.getText();
            actualType = logEntry2.getType();

            // Assert that retrieved values match expected values
            Assert.assertEquals(actualText, expectedText);
            Assert.assertEquals(actualType, expectedType);
        }
    }

}