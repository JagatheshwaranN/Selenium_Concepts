package concepts.frames;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class NestedFrameTest {

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
    public void testNestedFrame() {
        // Store the expected value in a variable.
        String expectedValue = "abc@gmail.com";

        // Navigate to a web page with two frames.
        driver.get("https://letcode.in/frame");

        // Check if the first frame exists before switching to it.
        if (!driver.findElements(By.id("firstFr")).isEmpty()) {

            // Switch to the first frame.
            driver.switchTo().frame("firstFr");
        } else {
            System.out.println("The frame with the ID \"firstFr\" does not exist.");
        }

        // Check if the second frame exists before switching to it.
        if (!driver.findElements(By.xpath("//iframe[@src='innerframe']")).isEmpty()) {

            // Find the WebElement for the second frame.
            WebElement childFr = driver.findElement(By.xpath("//iframe[@src='innerframe']"));

            // Switch to the second frame.
            driver.switchTo().frame(childFr);
        } else {
            System.out.println("The frame with the src attribute set to \"innerframe\" does not exist.");
        }

        // Enter text into the input element.
        driver.findElement(By.name("email")).sendKeys("abc@gmail.com");

        // Get the value of the input element.
        String valueFromInput = driver.findElement(By.tagName("input")).getAttribute("value");

        // Assert that the text was sent to the input element successfully.
        Assert.assertEquals(valueFromInput, expectedValue);

        // Switch back to the parent frame.
        driver.switchTo().parentFrame();

        // Switch back to the default frame.
        driver.switchTo().defaultContent();

        // Assert that the element with the XPATH is displayed.
        Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(@class,'title') and text()='Frame']")).isDisplayed());
    }

}
