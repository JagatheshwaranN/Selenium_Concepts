package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class HandleTooltipTest {

    private WebDriver driver;
    private static final Duration WAIT_DURATION = Duration.ofSeconds(3);

    @BeforeMethod
    public void setUp() {
        // Initialize the WebDriver and open the desired URL before each test.
        driver = browserSetup();
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

        // Wait for some time (not recommended, but added as per the original code).
        waitForSomeTime();
    }

    @AfterMethod
    public void tearDown() {
        // Quit the WebDriver after each test.
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver browserSetup() {
        // Initialize and configure the WebDriver (ChromeDriver in this case).
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private void waitForSomeTime() {
        // Wait for a specified duration (not recommended in most cases).
        try {
            Thread.sleep(WAIT_DURATION.toMillis());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
