package com.qa.selenium.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class HandleSyncUpIssueUsingCustomApproachTest {

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
    public void handleSyncUpIssueUsingCustomApproach() {
        // Load the web page from a local file
        driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/SiteLoadDelay.html");

        // Click on the button with the specified onclick attribute
        driver.findElement(By.xpath("//button[@onclick='load()']")).click();

        // Handle synchronization issue for the specified element
        handleElementSyncUp(By.xpath("//a[@href='/market/login.jsp']")).click();

        // Assert the title of the current web page
        Assert.assertEquals(driver.getTitle(), "Login - Video Courses, eBooks, Prime Packs | Tutorialspoint");

        // Wait for 3 seconds before closing the browser
        sleepInSeconds(3);
    }

    // A method to handle synchronization issues by waiting for an element to be present in the DOM
    private WebElement handleElementSyncUp(By locator) {
        // Declare a Web Element
        WebElement element = null;

        // Try finding the element up to 10 times
        for (int i = 0; i < 10; i++) {
            try {
                // Attempt to find the element
                element = driver.findElement(locator);

                // Break the loop if the element is found
                break;
            } catch (NoSuchElementException ex) {
                // Print the message while waiting for the element to appear
                System.out.println("Waiting for element to show up on the DOM");

                // Sleep for 1 second before the next attempt
                sleepInSeconds(1);
            }
        }
        // Return the web element after it is found
        return element;
    }


    private void sleepInSeconds(int seconds) {
        try {
            // The Thread.sleep method is used to pause the execution of the
            // program for the specified number of seconds.

            // Here, the value is multiplied by 1000 to convert it into milliseconds,
            // which is the unit of time used by Thread.sleep.
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ex) {
            // If an InterruptedException occurs during the sleep process, the
            // stack trace is printed for debugging purposes.
            ex.printStackTrace();
        }
    }

}
