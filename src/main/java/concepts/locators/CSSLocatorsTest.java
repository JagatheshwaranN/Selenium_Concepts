package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class CSSLocatorsTest {

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
        // Navigate to the facebook.com
        driver.get("https://www.facebook.com/");

        // css=tag#id
        // Selects the input element with the ID `email`.
        Assert.assertTrue(driver.findElement(By.cssSelector("input#email")).isDisplayed());

        // css=tag.class[attribute=value]
        // Selects the input element with the class `inputtext` and the attribute `name`
        // set to `email`.
        Assert.assertTrue(driver.findElement(By.cssSelector("input.inputtext[name=email]")).isDisplayed());

        // css=tag[attribute=value]
        // Selects the button element with the attribute `name` set to `login`.
        Assert.assertTrue(driver.findElement(By.cssSelector("button[name='login']")).isDisplayed());

        // css=tag.class
        // Selects the img element with the class `fb_logo` and `_8ilh` and `img`.
        Assert.assertTrue(driver.findElement(By.cssSelector("img.fb_logo._8ilh.img")).isDisplayed());

        // css=tag[attribute^=value]
        // Selects the input element with the attribute `type` that starts with `pass`.
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type^='pass']")).isDisplayed());

        // css=tag[attribute$=value]
        // Selects the input element with the attribute `type` that ends with `word`.
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type$='word']")).isDisplayed());

        // css=tag[attribute*=value]
        // Selects the input element with the attribute `type` that contains `swo`.
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type*='swo']")).isDisplayed());

        // css=parentTag > childTag[attribute=value]
        // Selects the button element that is a child of the `div` element and has the attribute
        // `name `set to `login`.
        Assert.assertTrue(driver.findElement(By.cssSelector("div > button[name='login']")).isDisplayed());

        // css=parentTag > childTag > subChildTag:nth-of-type(index)
        // Selects the second `li`element that is a child of the `ul`element that is a child
        // of the `div`element with the ID `pageFooter`.
        Assert.assertTrue(driver.findElement(By.cssSelector("div[id='pageFooter'] > ul > li:nth-of-type(2)")).isDisplayed());
    }

}


