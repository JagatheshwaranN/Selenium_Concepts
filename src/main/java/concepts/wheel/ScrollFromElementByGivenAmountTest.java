package concepts.wheel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollFromElementByGivenAmountTest {

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
    public void testScrollFromElementByGivenAmount() {
        // Initializing x-axis scroll amount to 0
        int xaxis = 0;

        // Initializing y-axis scroll amount to 200 pixels
        int yaxis = 200;

        // Open the Selenium website
        driver.get("https://www.selenium.dev/");

        // Locate the Selenium Donation button
        WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));

        // Create a scroll origin object from the Selenium Donation button
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation);

        // Scroll the page from the Selenium Donation button by 200 pixels vertically
        new Actions(driver).scrollFromOrigin(scrollOrigin, xaxis, yaxis).perform();

        // Locate the copyright content element
        WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));

        // Verify that the copyright content element is now visible within the viewport
        Assert.assertTrue(inViewport(copyRightContent));
    }

    public boolean inViewport(WebElement element) {
        String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
        return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
    }

}
