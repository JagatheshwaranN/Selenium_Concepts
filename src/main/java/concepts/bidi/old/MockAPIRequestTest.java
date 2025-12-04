package concepts.bidi.old;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.fetch.Fetch;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

public class MockAPIRequestTest {

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
    public void testMockAPIRequest() {
        // Define the expected table rows
        int expectedTableRows = 5;

        // Get the DevTools instance from the WebDriver
        DevTools devTools = driver.getDevTools();

        // Create a new DevTools session if one doesn't exist
        devTools.createSessionIfThereIsNotOne();

        // Enable the Fetch domain to intercept network requests
        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        // Add a listener to intercept requests and modify specific URLs
        devTools.addListener(Fetch.requestPaused(), request -> {

            // Check if the request URL contains a specific pattern
            if (request.getRequest().getUrl().contains("https://demos.telerik.com/kendo-ui/service/Northwind.svc/Orders")) {

                // Modify the URL by replacing a query parameter from 'top=20' to 'top=5'
                String mockURL = request.getRequest().getUrl().replace("top=20", "top=5");

                // Continue the intercepted request with the modified URL
                devTools.send(Fetch.continueRequest(
                        request.getRequestId(), // Request ID
                        Optional.of(mockURL),   // New URL as an Optional
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty()        // Optional parameters for request modification
                ));
            } else {
                // Continue the intercepted request without modification
                devTools.send(Fetch.continueRequest(
                        request.getRequestId(), // Request ID
                        Optional.of(request.getRequest().getUrl()), // Original URL as an Optional
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty(),       // Optional parameters for request modification
                        Optional.empty()        // Optional parameters for request modification
                ));
            }
        });

        // Navigating to a website that triggers network requests
        driver.get("https://demos.telerik.com/aspnet-mvc/grid/odata");

        try {
            // Wait for some time to allow the browser to load the table properly
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // If an InterruptedException occurs during the sleep, throw a RuntimeException
            throw new RuntimeException(e);
        }

        // Disable Fetch domain after the network intercept completes
        devTools.send(Fetch.disable());

        // Extract the table rows count from the table element with the specified XPath selector and store it actual table rows
        int actualTableRows = driver.findElements(By.xpath("//tbody[@class='k-table-tbody']//tr")).size();

        // Verify that the actual table rows content matches the expected table rows
        Assert.assertEquals(actualTableRows, expectedTableRows);
    }

}