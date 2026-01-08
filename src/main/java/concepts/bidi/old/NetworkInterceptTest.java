package concepts.bidi.old;

import com.google.common.net.MediaType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.ByteArrayInputStream;

public class NetworkInterceptTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @SuppressWarnings("deprecation")
    @Test(priority = 1)
    public void testNetworkIntercept() {
        // Define the expected value
        String expectedValue = "Hello World";
        try (
                // Create a NetworkInterceptor instance using try-with-resources to manage its lifecycle
                NetworkInterceptor ignored = new NetworkInterceptor(driver,
                        // Configure the interceptor to match all requests
                        Route.matching(req -> true)
                                .to(() -> req ->
                                        // Create a mock HTTP response for intercepted requests
                                        new HttpResponse()
                                                .setStatus(200) // Set the response status code to 200 (OK)
                                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString()) // Add a content type header
                                                .setContent(() ->
                                                        // Provide the response content as a ByteArrayInputStream with "Hello World"
                                                        new ByteArrayInputStream(expectedValue.getBytes()))
                                )
                )
        ) {
            // Navigate to a website (https://example.com/) that triggers network requests
            driver.get("https://example.com/");

            // Get the page source after the interception
            String source = driver.getPageSource();

            // Assert that the intercepted response contains the expected value in its source
            Assert.assertNotNull(source);
            Assert.assertTrue(source.contains(expectedValue));
        }
    }

}