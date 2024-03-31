package concepts.shadow_dom;

import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetShadowRootTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test
    public void testGetShadowRoot() {
        // Navigate to the webpage
        driver.get("https://www.alodokter.com/");

        // Find the shadow host element using its CSS selector
        WebElement topNavBarShadowHost = driver.findElement(By.cssSelector("#top-navbar-view"));

        // Get the shadow root of the shadow host element
        SearchContext shadowRoot = topNavBarShadowHost.getShadowRoot();

        // Find the element within the shadow root using its CSS selector and send keys "Fever"
        shadowRoot.findElement(By.cssSelector("#searchinput")).sendKeys("Fever");
    }

}
