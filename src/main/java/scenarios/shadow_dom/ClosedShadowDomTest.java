package scenarios.shadow_dom;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ClosedShadowDomTest {


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
    public void testClosedShadowDom() {
        // Navigate to the URL where the shadow DOM elements are present
        driver.get("https://selectorshub.com/xpath-practice-page/");

        // Find the input element with id "userPass"
        WebElement input = driver.findElement(By.id("userPass"));

        // Click on the input element to focus it
        input.click();

        // Initialize Actions class for performing keyboard actions
        Actions actions = new Actions(driver);

        // Simulate pressing the TAB key using Actions class to move to the next element
        actions.sendKeys(Keys.TAB).perform();

        // Enter the text "admin@123" into the input element using Actions class
        actions.sendKeys("admin@123").perform();
    }

}
