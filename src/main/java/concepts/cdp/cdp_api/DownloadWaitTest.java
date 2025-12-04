package concepts.cdp.cdp_api;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.browser.Browser;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadWaitTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testDownloadWait() {
        // Navigate to the download page
        driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");

        // Access the DevTools interface
        DevTools devTools = driver.getDevTools();

        // Ensure a DevTools session is active
        devTools.createSessionIfThereIsNotOne();

        // Configure download behavior to allow and name downloads
        devTools.send(Browser.setDownloadBehavior(
                Browser.SetDownloadBehaviorBehavior.ALLOWANDNAME,
                Optional.empty(),  // Optional path for downloads
                Optional.of(""),   // Optional suggested filename
                Optional.of(true)  // True to prompt for download location
        ));

        // Flag to track download completion
        AtomicBoolean downloadComplete = new AtomicBoolean(false);

        // Add a listener for download progress events
        devTools.addListener(Browser.downloadProgress(), download_event -> {
            // Set downloadComplete flag to true when download is completed
            downloadComplete.set(Objects.equals(download_event.getState().toString(), "completed"));
        });

        // Wait for the download link to be present (up to 10 seconds)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-2")));

        // Initiate the download by clicking the link
        driver.findElement(By.id("file-2")).click();

        // Wait for the download to complete (using a custom wait condition)
        try {
            wait.until(driverObject -> downloadComplete.get());
        } catch (Exception e) {
            // Fail the test if an unexpected exception occurs during download
            Assert.fail("An unexpected exception was thrown: " + e.getMessage());
        }
    }

    /*
        Custom Approach
        ===============
        assertDoesNotThrow(()-> wait.until(driverObject -> downloadComplete));
        public static void assertDoesNotThrow(Runnable code) {
            try {
                code.run();
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception thrown: " + e.getMessage(), e);
            }
        }
    */

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
