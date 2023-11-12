package concepts.driver.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TimeOutTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void waitTimeout() {
        // Define the expected title for comparison
        String expectedTitle = "Online Tutorials, Courses, and eBooks Library | Tutorialspoint";

        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Set the page load timeout to 30 seconds to define the maximum amount of time to wait for a page to load
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(30));

        // Set the implicit wait timeout to 20 seconds to define the maximum amount of time to wait for an element to be found
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(20));

        // Set the script timeout to 10 seconds to define the maximum amount of time to wait for an asynchronous script to finish execution
        chromeOptions.setScriptTimeout(Duration.ofSeconds(10));

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // Navigate to the specified file URL
        driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/SiteLoadDelay.html");

        // Find and click the button using its XPath
        driver.findElement(By.xpath("//button[@onclick='load()']")).click();

        // Check if the element with the class "logo-desktop" is displayed
        driver.findElement(By.cssSelector(".logo-desktop")).isDisplayed();

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(expectedTitle, driver.getTitle(), "Actual title does not match expected title.");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
