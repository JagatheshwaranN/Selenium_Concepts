package concepts.cdp.bidi_api;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResponseRecordTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testResponseRecord() {
        // Set expected Content-Type header
        String expectedContentType = "[application/json]";

        // Create a list to store captured Content-Type headers
        CopyOnWriteArrayList<String> contentType = new CopyOnWriteArrayList<>();

        // Use a try-with-resources block to manage the NetworkInterceptor
        try (NetworkInterceptor ignored = new NetworkInterceptor(driver,
                (Filter) next -> request -> {
                    // Intercept the network request and execute it
                    HttpResponse response = next.execute(request);
                    // Extract and store the Content-Type header from the response
                    contentType.add(response.getHeaders("Content-Type").toString());
                    return response;  // Return the response
                })) {

            // Navigate to the target URL
            driver.get("https://api.restful-api.dev/objects/7");

            // Wait for at least one response to be captured
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(driverObject -> contentType.size() > 1);
        }

        // Assert that the captured Content-Type matches the expected value
        Assert.assertEquals(contentType.getFirst(), expectedContentType);
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