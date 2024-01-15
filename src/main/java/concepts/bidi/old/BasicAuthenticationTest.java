package concepts.bidi.old;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.net.URI;
import java.util.function.Predicate;

public class BasicAuthenticationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
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
    public void testBasicAuthentication() {
        // Define the expected success message for authentication
        String expectedMessage = "Congratulations! You must have the proper credentials.";

        // Create a predicate to check if the URI's host contains "herokuapp.com"
        Predicate<URI> uriPredicate = uriObject -> uriObject.getHost().contains("herokuapp.com");

        // Registering authentication credentials (username and password) with the driver
        // Assuming 'driver' implements the 'HasAuthentication' interface
        // (Only basic authentication for "herokuapp.com" will be performed)
        ((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("admin", "admin"));

        // Navigate to the target URL
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        // Extract the page content from the element with the specified XPath selector and store it actual message
        String actualMessage = driver.findElement(By.xpath("//div[@class='example']//p")).getText();

        // Verify that the actual message content matches the expected message
        Assert.assertEquals(actualMessage, expectedMessage);
    }

}