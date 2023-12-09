package concepts.browsingcontext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class CloseBrowsingContextWindowsTest {

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
    public void testCloseBrowsingContextWindows() {
        // Create the first BrowsingContext instance using the current WebDriver instance and a new window type
        BrowsingContext browsingContext1 = new BrowsingContext(driver, WindowType.WINDOW);

        // Create the second BrowsingContext instance using the current WebDriver instance and a new window type
        BrowsingContext browsingContext2 = new BrowsingContext(driver, WindowType.WINDOW);

        // Navigate the first browsing context to a target URL
        browsingContext1.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);

        // Print the unique ID of the first browsing context for reference
        System.out.println("Browsing Context Id1 : " + browsingContext1.getId());

        // Navigate the second browsing context to a target URL
        browsingContext2.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);

        // Print the unique ID of the second browsing context for reference
        System.out.println("Browsing Context Id2 : " + browsingContext2.getId());

        // Close the second browsing context
        browsingContext2.close();

        // Close the first browsing context
        browsingContext1.close();
    }

}