package concepts.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class AddCookieTest {

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
    public void addCookie() {
        // Navigate to the example website.
        driver.get("http://www.example.com/");

        // Add a cookie to the browser with the name "Test" and the value "12345".
        driver.manage().addCookie(new Cookie("Test", "12345"));

        // Retrieve the cookie with the name "Test".
        Cookie testCookie = driver.manage().getCookieNamed("Test");

        // Assert that the cookie was not null, meaning that it was added successfully.
        Assert.assertNotNull(testCookie, "The cookie was not added successfully.");
    }

}
