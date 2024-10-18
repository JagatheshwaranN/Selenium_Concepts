package concepts.bidi.old;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.ResourceType;
import org.openqa.selenium.devtools.v129.network.model.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.*;

public class CaptureHttpTrafficTest {

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

    @Test(priority = 1)
    public void testCaptureHttpTraffic() {
        // URLs to be tested
        String seleniumWebSiteURL = "https://www.selenium.dev/";
        String youtubeURL = "https://www.youtube.com/playlist?list=PLRdSclUtJDYXDEsWI0vwBmJxW17NgsaAk";

        // Maximum allowed image size
        int imageSize = 500;

        // Get the DevTools instance from the WebDriver
        DevTools devTools = driver.getDevTools();

        // Create a DevTools session if not already present
        devTools.createSessionIfThereIsNotOne();

        // Enable network tracking in DevTools
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // List to capture network responses in a thread-safe way
        List<Response> captureResponses = Collections.synchronizedList(new ArrayList<>());

        // Add a listener to capture network responses
        devTools.addListener(Network.responseReceived(), response -> captureResponses.add(response.getResponse()));

        // Access the Selenium website
        driver.get("https://www.selenium.dev/");

        // Assertion methods to check captured network traffic
        // Ensure there are no error responses
        assertNoErrorResponse(captureResponses);

        // Ensure request to Selenium's website was made
        assertRequestMade(captureResponses, seleniumWebSiteURL);

        // Ensure request to YouTube playlist was not made
        assertRequestNotMade(captureResponses, youtubeURL);

        // Check if large images were not requested
        assertNotLargeImagesRequested(captureResponses, imageSize);

        // Disable the Network domain in DevTools
        devTools.send(Network.disable());
    }

    // Assertion to check if there are no error responses in the captured network responses
    private void assertNoErrorResponse(List<Response> captureResponses) {
        // Check if any response status code falls within the error range (400-599)
        boolean doWehaveErrorCodes = captureResponses.stream().anyMatch(response ->
                response.getStatus() > 400 && response.getStatus() < 599);

        // Assert that there are no error responses; if any, fail the assertion
        Assert.assertFalse(doWehaveErrorCodes, "Error response detected on the page");
    }

    // Assertion to verify if a specific request URL was made among the captured responses
    private void assertRequestMade(List<Response> captureResponses, String url) {
        // Check if any response URL contains the specified URL
        boolean anyRequestsMade = captureResponses.stream().anyMatch(response ->
                response.getUrl().contains(url));

        // Assert that a request to the specified URL was made; if not, fail the assertion
        Assert.assertTrue(anyRequestsMade, String.format("Request %s not made.", url));
    }

    // Assertion to verify if a specific request URL was not made among the captured responses
    private void assertRequestNotMade(List<Response> captureResponses, String url) {
        // Check if any response URL contains the specified URL
        boolean anyRequestsMade = captureResponses.stream().anyMatch(response ->
                response.getUrl().contains(url));

        // Assert that no request to the specified URL was made; if found, fail the assertion
        Assert.assertFalse(anyRequestsMade, String.format("Request %s made.", url));
    }

    // Assertion to ensure that images larger than a certain content length were not requested
    private void assertNotLargeImagesRequested(List<Response> captureResponses, int contentLength) {
        // Check if any response corresponds to an image and its content length is less than the specified size
        boolean doWehaveLargeImages = captureResponses.stream().anyMatch(response ->
                Objects.equals(response.getMimeType(), ResourceType.IMAGE.toString()) && // Check if the response is an image
                        response.getHeaders() != null && // Ensure headers are present
                        Integer.parseInt(response.getHeaders().get("Content-Length").toString()) < contentLength); // Check content length

        // Assert that no large images (larger than the specified content length) were requested; if found, fail the assertion
        Assert.assertFalse(doWehaveLargeImages, String.format("Images larger than %s size detected.", contentLength));
    }

}