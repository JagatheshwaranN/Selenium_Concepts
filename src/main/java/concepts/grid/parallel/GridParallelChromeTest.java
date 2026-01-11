package concepts.grid.parallel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.Duration;

// Selenium 4 Style without Desired Capabilities
public class GridParallelChromeTest {

    // ThreadLocal ensures each parallel test gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Getter for the current thread’s WebDriver
    private WebDriver getDriver() {
        return driver.get();
    }

    @Test
    public void openGoogleOnChrome() {

        // ChromeOptions replaces DesiredCapabilities in Selenium 4
        ChromeOptions options = new ChromeOptions();

        // Optional: add browser-specific arguments
        options.addArguments("--start-maximized");

        try {
            // Create RemoteWebDriver pointing to Selenium Grid Hub
            driver.set(new RemoteWebDriver(
                    URI.create("http://localhost:4444").toURL(),
                    options
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RemoteWebDriver", e);
        }

        // Configure timeouts
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navigate to application
        getDriver().get("https://www.google.com");

        // Simple verification output
        System.out.println(
                "Thread ID: " + Thread.currentThread().getName() +
                        " | Title: " + getDriver().getTitle()
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        // Quit browser for the current thread
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Prevent memory leaks
        }
    }
}
