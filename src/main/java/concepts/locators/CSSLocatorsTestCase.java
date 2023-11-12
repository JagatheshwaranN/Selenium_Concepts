package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class CSSLocatorsTestCase {

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

    @Test(priority = 1)
    public void cssLocatorTypes() {
        driver.get("https://www.facebook.com/");

        // css=tag#id
        Assert.assertTrue(driver.findElement(By.cssSelector("input#email")).isDisplayed());

        // css=tag.class[attribute=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("input.inputtext[name=email]")).isDisplayed());

        // css=tag[attribute=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("button[name='login']")).isDisplayed());

        // css=tag.class
        Assert.assertTrue(driver.findElement(By.cssSelector("img.fb_logo._8ilh.img")).isDisplayed());

        // css=tag[attribute^=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type^='pass']")).isDisplayed());

        // css=tag[attribute$=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type$='word']")).isDisplayed());

        // css=tag[attribute*=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type*='swo']")).isDisplayed());

        // css=parentTag > childTag[attribute=value]
        Assert.assertTrue(driver.findElement(By.cssSelector("div > button[name='login']")).isDisplayed());

        // css=parentTag > childTag > subChildTag:nth-of-type(index)
        Assert.assertTrue(driver.findElement(By.cssSelector("div[id='pageFooter'] > ul > li:nth-of-type(2)")).isDisplayed());
    }

}
