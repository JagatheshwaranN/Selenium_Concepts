package scenarios.svg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SimpleSVGTest {

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

    @Test
    public void testSimpleSVG() {
        // Navigate to the local HTML file containing the SVG element
        driver.get("file:///D:/Environment_Collection/Intellij_Env/Selenium_Concepts/src/main/resources/supportFiles/SVG.html");

        // Find the SVG text element using XPath
        String text = driver.findElement(By.xpath("//*[name()='svg']//*[local-name()='text']")).getText();

        // Print the text content of the SVG element
        System.out.println(text);
    }


}
