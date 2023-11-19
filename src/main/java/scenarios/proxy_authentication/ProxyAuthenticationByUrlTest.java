package scenarios.proxy_authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ProxyAuthenticationByUrlTest {

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
    public void testProxyAuthenticationByUrl() {
        // Navigate to a URL that includes basic authentication credentials
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        // Locate an <h3> element with the text "Basic Auth" using XPath and retrieve its text content
        String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();

        // Assert that the retrieved text matches the expected value ("Basic Auth").
        Assert.assertEquals(result, "Basic Auth");
    }

}
