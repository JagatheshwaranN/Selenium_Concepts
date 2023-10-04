package com.qa.selenium.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class HandleTooltipTest {

    // Declare a WebDriver instance to interact with the web browser.
    public WebDriver driver;

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
    public void testHandleTooltip() {
        // Instruct the WebDriver instance (already configured) to navigate to the URL "https://jqueryui.com/tooltip/"
        driver.get("https://jqueryui.com/tooltip/");

        // Locate the iframe element with the specified XPath and store it in 'frameElement'
        WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));

        // Switch the WebDriver's context to the identified iframe
        driver.switchTo().frame(frameElement);

        // Create an Actions object 'actions' and associate it with the WebDriver instance 'driver'
        Actions actions = new Actions(driver);

        // Use 'actions' to perform a mouse hover action over the web element with the ID "age"
        actions.moveToElement(driver.findElement(By.id("age"))).perform();

        // Find the tooltip element using an XPath expression and store its text content in 'toolTip'
        String toolTip = driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText();

        // Assert that the retrieved tooltip text matches the expected value
        Assert.assertEquals(toolTip, "We ask for your age only for statistical purposes.");
    }

}
