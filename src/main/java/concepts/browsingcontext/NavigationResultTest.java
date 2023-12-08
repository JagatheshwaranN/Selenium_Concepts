package concepts.browsingcontext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.NavigationResult;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class NavigationResultTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

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
    public void testNavigationResult() {
        // Create a BrowsingContext object with the driver and specify WindowType as WINDOW
        BrowsingContext browsingContext = new BrowsingContext(driver, WindowType.WINDOW);

        // Navigate to the specified URL using the created browsing context and store the navigation to NavigationResult
        NavigationResult result = browsingContext.navigate("https://www.selenium.dev/");

        // Extract and print the navigation ID from the result object
        System.out.println("Navigation Id : " + result.getNavigationId());

        // Extract and print the final URL reached after navigation from the result object
        System.out.println("Navigation Url : " + result.getUrl());

        // Get the unique ID of the created browsing context
        String browsingContextId = browsingContext.getId();

        // Print the browsing context ID for reference
        System.out.println("Browsing Context Id : " + browsingContextId);

        // Close the browsing context
        browsingContext.close();

        // Assert that the browsing context ID is not null
        Assert.assertNotNull(browsingContextId);
    }

}