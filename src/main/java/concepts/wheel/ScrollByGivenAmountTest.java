package concepts.wheel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollByGivenAmountTest {

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
    public void testScrollByGivenAmount() {
        // Initializing x-axis scroll amount to 0
        int xAxis = 0;

        // Open the Selenium website
        driver.get("https://www.selenium.dev/");

        // Locate the "Learn More" button
        WebElement seleniumLearnMoreButton = driver
                .findElement(By.xpath("//a[contains(@class,'selenium-button selenium-white-cyan')]"));

        // Get the y-coordinate (vertical position) of the "Learn More" button
        int yAxis = seleniumLearnMoreButton.getRect().y;

        // Scroll the page vertically by the specified amount (button's y-coordinate)
        new Actions(driver).scrollByAmount(xAxis, yAxis).perform();

        // Verify that the "Learn More" button is now visible within the viewport
        Assert.assertTrue(inViewport(seleniumLearnMoreButton));
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
