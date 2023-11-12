package concepts.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class XPathLocatorsTestCase {

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
    public void xpathLocatorTypes() {
        driver.get("https://www.facebook.com/");

        // xpath=//tag[contains(@attribute, value)]
        Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@data-testid,'royal_email')]")).isDisplayed());

        // xpath=//tag[@attribute=value or @attribute=value]
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' or @name='pass']")).isDisplayed());

        // xpath=//tag[@attribute=value and @attribute=value]
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' and @name='pass']")).isDisplayed());

        // xpath=//tag[starts-with(@attribute, value)]
        Assert.assertTrue(driver.findElement(By.xpath("//input[starts-with(@data-testid,'royal')]")).isDisplayed());

        // xpath=//tag[text()=value]
        Assert.assertTrue(driver
                .findElement(
                        By.xpath("//h2[text()='Facebook helps you connect and share with the people in your life.']"))
                .isDisplayed());

        // xpath=//tag[@attribute=value]//following::tag
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='passContainer']//following::button")).isDisplayed());

        // xpath=//tag[@attribute=value]//ancestor::tag
        Assert.assertTrue(driver.findElement(By.xpath("//button[@name='login']//ancestor::form")).isDisplayed());

        // xpath=//tag[@attribute=value]//child::tag[@attribute=value]
        Assert.assertTrue(
                driver.findElement(By.xpath("//form[@data-testid='royal_login_form']//child::input[@name='email']"))
                        .isDisplayed());

        // xpath=//tag[@attribute=value]//preceding::tag[@attribute=value]
        Assert.assertTrue(
                driver.findElement(By.xpath("//button[@name='login']//preceding::input[@name='email']")).isDisplayed());

        // xpath=//tag[@attribute=value]//following-sibling::tag
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_8esl']//following-sibling::h2")).isDisplayed());

        // xpath=//tag[@attribute=value]//parent::tag
        Assert.assertTrue(driver.findElement(By.xpath("//input[@name='email']//parent::div")).isDisplayed());

        // xpath=//tag[@attribute=value]//self::same-tag
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password']//self::input")).isDisplayed());

        // xpath=//tag[@attribute=value]//descendant::tag[@attribute=value]
        Assert.assertTrue(driver
                .findElement(By.xpath("//form[@data-testid='royal_login_form']//descendant::input[@name='email']"))
                .isDisplayed());
    }

}
