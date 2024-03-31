package concepts.shadow_dom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class OpenShadowDomTest {


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
    public void testOpenShadowDom() {
        // Navigate to the URL where the shadow DOM elements are present
        driver.get("https://www.alodokter.com/");

        // Initialize JavaScriptExecutor to execute JavaScript code
        JavascriptExecutor javascriptExecutor =  (JavascriptExecutor)driver;

        // Use JavaScript to find the shadow DOM element with ID "searchinput" inside the shadow DOM with ID "top-navbar-view"
        WebElement input = (WebElement) javascriptExecutor.executeScript("return document.querySelector('#top-navbar-view').shadowRoot.querySelector('#searchinput')");

        // Use JavaScript to set the value of the input element to "Fever"
        javascriptExecutor.executeScript("arguments[0].setAttribute('value', 'Fever')", input);
    }

}
