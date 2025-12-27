package concepts.browsingcontext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContextInfo;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class BrowsingContextTreeWithDepthTest {

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
    public void testBrowsingContextTreeWithDepth() {
        // Get the current window handle id (context id)
        String contextId = driver.getWindowHandle();

        // Print the window handle id (context id) for reference
        System.out.println("Window Handle Id : " + contextId);

        // Create a new BrowsingContext instance using the current WebDriver instance and the window handle id (context id)
        BrowsingContext browsingContext = new BrowsingContext(driver, contextId);

        // Navigate to the target URL using the created browsing context
        browsingContext.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);

        // Get the unique ID of the created browsing context
        String browsingContextId = browsingContext.getId();

        // Print the browsing context ID for reference
        System.out.println("Browsing Context Id : " + browsingContextId);

        // Get the tree structure starting from the root browsing context (depth 0)
        List<BrowsingContextInfo> contextInfo = browsingContext.getTree(0);

        // Get the first browsing context information object from the list
        BrowsingContextInfo info = contextInfo.getFirst();

        // Close the browsing context
        browsingContext.close();

        // Verify that only one browsing context is returned
        Assert.assertEquals(contextInfo.size(), 1);

        // Ensure the captured browsing context ID is not null
        Assert.assertNotNull(browsingContextId);

        // Verify that the context has no child contexts (i.e., it is a leaf node)
        Assert.assertNull(info.getChildren());

        // Validate that the context ID matches the previously stored context ID
        Assert.assertEquals(contextId, info.getId());
    }

}