package concepts.cdp.bidi_api;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

public class RequestInterceptTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @SuppressWarnings("WriteOnlyObject")
    @Test
    public void testRequestIntercept() {
        // Expected message after request interception
        String expectedMessage = "two";

        // AtomicBoolean to track whether the interception is completed
        AtomicBoolean completed = new AtomicBoolean(false);

        // Using a try-with-resources to create a NetworkInterceptor
        try (NetworkInterceptor ignored = new NetworkInterceptor(
                driver, (Filter) next -> request -> {
            // Intercept the network request
            if (request.getUri().contains("one.js")) {
                // If request URI contains "one.js" modify the request to fetch "two.js" instead
                request = new HttpRequest(HttpMethod.GET, request.getUri().replace("one.js", "two.js"));
            }

            // Set the completed flag to true
            completed.set(true);

            // Execute the modified or original request
            return next.execute(request);
        })) {

            // Load a URL to trigger a network request
            driver.get("https://www.selenium.dev/selenium/web/devToolsRequestInterceptionTest.html");

            // Click the button on the page, triggering another network request
            driver.findElement(By.tagName("button")).click();
        }

        // Find the element with the id "result" to get the actual message
        String actualMessage = driver.findElement(By.id("result")).getText();

        // Assert that the actual message matches the expected message
        Assert.assertEquals(actualMessage, expectedMessage);
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