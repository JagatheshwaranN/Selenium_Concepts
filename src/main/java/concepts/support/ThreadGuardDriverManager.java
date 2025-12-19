package concepts.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;

public class ThreadGuardDriverManager {

    // ThreadLocal ensures each test thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        // If no WebDriver is associated with the current thread
        if (driver.get() == null) {
            // Create a new ChromeDriver and protect it with ThreadGuard.
            // ThreadGuard throws an error if the driver is accessed from a different thread
            driver.set(ThreadGuard.protect(new ChromeDriver()));
        }
        // Return the WebDriver bound to the current thread
        return driver.get();
    }

    public static void quitDriver() {
        // Quit the WebDriver only if it exists for the current thread
        if (driver.get() != null) {
            // Close the browser session
            driver.get().quit();

            // Remove the WebDriver reference from ThreadLocal
            // This prevents memory leaks in parallel execution
            driver.remove();
        }
    }
}
