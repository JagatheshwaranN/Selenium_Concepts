package concepts.driver.chrome;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import junit.framework.Assert;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class HTMLUnitDriverChromeVersionTestCase {

    // Declare an HtmlUnitDriver instance to interact with the web browser.
    private HtmlUnitDriver driver;

    @Test(priority = 1)
    public void htmlUnitDriverLaunch() {
        // Create an instance of HtmlUnitDriver (Headless browser)
        driver = new HtmlUnitDriver();

        // Navigate to the specified URL (https://www.google.com/)
        driver.get("https://www.google.com/");

        // Assert that the title of the current web page is "Google"
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @Test(priority = 2)
    public void htmlUnitDriverChromeVersionLaunch() {
        // Create an instance of HtmlUnitDriver with a specified BrowserVersion (CHROME)
        driver = new HtmlUnitDriver(BrowserVersion.CHROME);

        // Navigate to the specified URL (https://www.google.com/)
        driver.get("https://www.google.com/");

        // Assert that the title of the current web page is "Google"
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.close();
        }
    }

}
