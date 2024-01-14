package concepts.cdp.bidi_api;

import com.google.common.net.MediaType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ResponseTransformTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup'
        // from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testResponseTransform() {
        // Expected content type for the response
        String expectedContentType = "Creamy, Delicious Pasta!";

        // Using a try-with-resources to create a NetworkInterceptor
        try (NetworkInterceptor ignored = new NetworkInterceptor(driver,
                // Define a route matching all requests and returning a custom HttpResponse
                Route.matching(request -> true)
                        .to(() -> request -> new HttpResponse()
                                // Set response status code to 201
                                .setStatus(201)
                                // Add Content-Type header to the response
                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                // Set the response content to the expected content type
                                .setContent(Contents.utf8String("Creamy, Delicious Pasta!"))))) {

            // Load a URL to trigger a network request
            driver.get("https://reqres.in/api/users/2");
        }

        // Find the body element in the loaded page
        WebElement body = driver.findElement(By.tagName("body"));

        // Assert that the text content of the body element matches the expected content type
        Assert.assertEquals(body.getText(), expectedContentType);
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