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
        // Navigate to the facebook.com
        driver.get("https://www.facebook.com/");

        // xpath=//tag[contains(@attribute, value)]
        // Selects the input element whose `data-testid` attribute contains the value
        // `royal_email`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@data-testid,'royal_email')]")).isDisplayed());

        // xpath=//tag[@attribute=value or @attribute=value]
        // Selects the input element whose `type` attribute is either `password` or `pass`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' or @name='pass']")).isDisplayed());

        // xpath=//tag[@attribute=value and @attribute=value]
        // Selects the input element whose `type` attribute is `password` and whose `name`
        // attribute is `pass`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' and @name='pass']")).isDisplayed());

        // xpath=//tag[starts-with(@attribute, value)]
        // Selects the input element whose `data-testid` attribute starts with the value `royal`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[starts-with(@data-testid,'royal')]")).isDisplayed());

        // xpath=//tag[text()=value]
        // Selects the `h2` element whose text content is equal to the value `Facebook helps
        // you connect and share with the people in your life.`
        Assert.assertTrue(driver
                .findElement(
                        By.xpath("//h2[text()='Facebook helps you connect and share with the people in your life.']"))
                .isDisplayed());

        // xpath=//tag[@attribute=value]//following::tag
        // Selects the `button` element that is a descendant of the `div` element with the ID
        // `passContainer`.
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='passContainer']//following::button")).isDisplayed());

        // xpath=//tag[@attribute=value]//ancestor::tag
        // Selects the `form` element that is an ancestor of the `button` element with the name
        // `login`.
        Assert.assertTrue(driver.findElement(By.xpath("//button[@name='login']//ancestor::form")).isDisplayed());

        // xpath=//tag[@attribute=value]//child::tag[@attribute=value]
        // Selects the `input` element that is a child of the `form` element with the data-testid
        // `royal_login_form` and whose name attribute is `email`.
        Assert.assertTrue(
                driver.findElement(By.xpath("//form[@data-testid='royal_login_form']//child::input[@name='email']"))
                        .isDisplayed());

        // xpath=//tag[@attribute=value]//preceding::tag[@attribute=value]
        // Selects the `input` element that is a preceding sibling of the `button` element with
        // the name `login` and whose name attribute is `email`.
        Assert.assertTrue(
                driver.findElement(By.xpath("//button[@name='login']//preceding::input[@name='email']")).isDisplayed());

        // xpath=//tag[@attribute=value]//following-sibling::tag
        // Selects the `h2` element that is a following sibling of the `div` element with the
        // class `_8esl`.
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_8esl']//following-sibling::h2")).isDisplayed());

        // xpath=//tag[@attribute=value]//parent::tag
        // Selects the `div` element that is the parent of the `input` element with the name
        // `email`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[@name='email']//parent::div")).isDisplayed());

        // xpath=//tag[@attribute=value]//self::same-tag
        // Selects the `input` element that is the same tag as the `input` element with the
        // type `password`.
        Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password']//self::input")).isDisplayed());

        // xpath=//tag[@attribute=value]//descendant::tag[@attribute=value]
        // Selects the `input` element that is a descendant of the `form` element with the
        // data-testid `royal_login_form` and whose name attribute
        Assert.assertTrue(driver
                .findElement(By.xpath("//form[@data-testid='royal_login_form']//descendant::input[@name='email']"))
                .isDisplayed());
    }

}
