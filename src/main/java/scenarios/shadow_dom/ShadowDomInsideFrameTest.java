package scenarios.shadow_dom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ShadowDomInsideFrameTest {


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
    public void testShadowDom() throws InterruptedException {
        driver.get("https://selectorshub.com/shadow-dom-in-iframe/");
        WebElement frameElement = driver.findElement(By.id("pact"));
        driver.switchTo().frame(frameElement);
        JavascriptExecutor javascriptExecutor =  (JavascriptExecutor)driver;
        WebElement input = (WebElement) javascriptExecutor.executeScript("return document.querySelector('#snacktime').shadowRoot.querySelector('#tea')");
        javascriptExecutor.executeScript("arguments[0].setAttribute('value', 'I love Tea')", input);
        Thread.sleep(3000);
    }

}
