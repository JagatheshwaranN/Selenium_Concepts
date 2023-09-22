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

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Initialize the WebDriver and open the desired URL before each test.
        driver = DriverConfiguration.browserSetup();
        driver.get("https://jqueryui.com/tooltip/");
    }

    @Test(priority = 1)
    public void testHandleTooltip() {
        // Find the iframe containing the tooltip element.
        WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(frameElement);

        // Move to the element to trigger the tooltip.
        new Actions(driver).moveToElement(driver.findElement(By.id("age"))).perform();

        // Get the tooltip text and assert it.
        String toolTip = driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText();
        Assert.assertEquals(toolTip, "We ask for your age only for statistical purposes.");

    }

    @AfterMethod
    public void tearDown() {
        // Quit the WebDriver after each test.
        if (driver != null) {
            driver.quit();
        }
    }

}
