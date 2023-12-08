package concepts.browsingcontext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContextInfo;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class BrowsingContextTreeTest {

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
    public void testBrowsingContextTree() {
        // Get the current window handle id (context id)
        String contextId = driver.getWindowHandle();

        // Print the window handle id (context id) for reference
        System.out.println("Window Handle Id : " + contextId);

        // Create a new BrowsingContext instance using the current WebDriver instance and the window handle id (context id)
        BrowsingContext browsingContext = new BrowsingContext(driver, contextId);

        // Navigate to the target URL using the created browsing context
        browsingContext.navigate("https://www.selenium.dev/documentation/webdriver/bidirectional/webdriver_bidi/browsing_context/");

        // Get the unique ID of the created browsing context
        String browsingContextId = browsingContext.getId();

        // Print the browsing context ID for reference
        System.out.println("Browsing Context Id : " + browsingContextId);

        // Get a list of all browsing contexts (including the current one) within the browsing context tree
        List<BrowsingContextInfo> contextInfo = browsingContext.getTree();

        // Close the browsing context
        browsingContext.close();

        // Assert that the browsing context ID is not null
        Assert.assertNotNull(browsingContextId);

        // Assert that the list of browsing contexts is not empty (meaning the current context is not isolated)
        Assert.assertTrue(contextInfo.size() > 0);
    }

}