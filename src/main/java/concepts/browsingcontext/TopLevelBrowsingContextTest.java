package concepts.browsingcontext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContextInfo;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class TopLevelBrowsingContextTest {

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
    public void testTopLevelBrowsingContext() {
        // Define the expected number of top-level browsing contexts
        int expectedContextInfoSize = 2;

        // Get the window handle id (context id) for the first browsing context
        String contextId = driver.getWindowHandle();

        // Create the first BrowsingContext instance using the current WebDriver instance and the window handle id (context id)
        BrowsingContext browsingContext1 = new BrowsingContext(driver, contextId);

        // Create the second BrowsingContext instance using the current WebDriver instance and a new window type
        BrowsingContext browsingContext2 = new BrowsingContext(driver, WindowType.WINDOW);

        // Navigate the first browsing context to a specific URL
        browsingContext1.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);

        // Print the unique ID of the first browsing context for reference
        System.out.println("Browsing Context Id1 : " + browsingContext1.getId());

        // Navigate the second browsing context to a different URL
        browsingContext2.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);

        // Print the unique ID of the second browsing context for reference
        System.out.println("Browsing Context Id2 : " + browsingContext2.getId());

        // Get a list of BrowsingContextInfo objects for all top-level browsing contexts
        List<BrowsingContextInfo> contextInfo = browsingContext1.getTopLevelContexts();

        // Close both browsing contexts
        browsingContext1.close();
        browsingContext2.close();

        // Get the actual number of top-level browsing contexts found
        int actualContextInfoSize = contextInfo.size();

        // Assert that the actual number of top-level contexts matches the expected count
        Assert.assertEquals(actualContextInfoSize, expectedContextInfoSize);
    }

}