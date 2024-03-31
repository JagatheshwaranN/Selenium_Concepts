package concepts.shadow_dom;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class NestedShadowDOMUsingGetShadowRootTest {

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
    public void testNestedShadowDOMUsingGetShadowRoot() {
        // Navigate to the webpage containing Shadow DOM elements
        driver.get("http://watir.com/examples/shadow_dom.html");

        // Find the shadow host element using its CSS selector
        WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));

        // Get the shadow root of the shadow host element
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        // Find the element within the shadow root that hosts another shadow DOM
        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#nested_shadow_host"));

        // Get the shadow root of the element within the first shadow root
        SearchContext shadowSubRoot = shadowContent.getShadowRoot();

        // Find the specific element within the nested shadow root and retrieve its text
        String text = shadowSubRoot.findElement(By.cssSelector("#nested_shadow_content > div")).getText();

        // Print the text content retrieved from the nested shadow DOM
        System.out.println(text);
    }

}
