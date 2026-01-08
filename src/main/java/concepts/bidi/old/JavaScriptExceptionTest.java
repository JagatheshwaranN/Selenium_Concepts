package concepts.bidi.old;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JavaScriptExceptionTest {

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
    public void testJavaScriptException() {
        // Get the DevTools instance from the driver
        DevTools devTools = driver.getDevTools();

        // Create a DevTools session if not already present
        devTools.createSessionIfThereIsNotOne();

        // Initialize an empty list to store JavaScript exceptions
        List<JavascriptException> jsExceptions = new ArrayList<>();

        // Create a consumer to add JavaScript exceptions to the list
        Consumer<JavascriptException> addEntry = jsExceptions::add;

        // Add a listener to capture JavaScript exceptions
        devTools.getDomains().events().addJavascriptExceptionListener(addEntry);

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/WebTable.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

        // Locating an element and triggering a JavaScript exception by setting an 'onclick' attribute
        WebElement link = driver.findElement(By.xpath("//div//a[text()='Canada']"));
        driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                link, "onclick", "throw new Error('JavaScriptException Demo!')");

        // Click the element to trigger the exception
        link.click();

        // Iterate through the captured JavaScript exceptions
        for (JavascriptException jse : jsExceptions) {
            // Print the exception message
            System.out.println("JS Exception Message : " + jse.getMessage());

            // Print the system information about the exception
            System.out.println("JS Exception SysInfo : " + jse.getSystemInformation());

            // Print the stack trace of the exception
            jse.getStackTrace();
        }
    }

}