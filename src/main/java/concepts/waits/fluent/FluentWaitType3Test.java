package concepts.waits.fluent;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

public class FluentWaitType3Test {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Define a constant duration for the maximum sleep time, set to 0.5 seconds
    private static final Duration WAIT_SLEEP = Duration.ofMillis(500);

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
    public void testFluentWaitType3() {
        // Define the expected value
        String expectedValue = "Dashboard";

        // Navigate to the login page
        driver.get("https://admin-demo.nopcommerce.com/login");

        // Set up a FluentWait for the login button
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(WAIT_TIMEOUT)
                .pollingEvery(WAIT_SLEEP)
                .ignoreAll(Arrays.asList(NoSuchElementException.class, NotFoundException.class));

		/*
			Another way of implementation
			=============================
			Function<WebDriver, Boolean> function = new Function<>() {
				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By.cssSelector(".button-1.login-button"));
					return element.isDisplayed();
				}
			};
		*/

        // Define a Function to handle waiting for the login button
        Function<WebDriver, WebElement> function = driver -> {
            try {
                // Attempt to find the login button
                WebElement element = driver.findElement(By.cssSelector(".button-1.login-button"));
                return element.isDisplayed() ? element : null;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                // Catch exceptions if the element is not found or stale
                return null;
            }
        };

        // Wait until the login button is displayed
        WebElement loginButton = wait.until(function);

        // Click on the login button
        loginButton.click();

        // Get the text of the header after clicking
        String actualValue = driver.findElement(By.xpath("//div[@class='content-header']//h1")).getText();

        // Assert if the actual header text matches the expected value
        Assert.assertEquals(actualValue, expectedValue);
    }

}
