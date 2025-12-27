package concepts.driver.edge;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class TimeOutTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void waitTimeout() {
        // Define the expected title for comparison
        String expectedTitle = "Selenium";

        // Instantiate EdgeOptions to configure the EdgeDriver
        EdgeOptions edgeOptions = getEdgeOptions();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/SiteLoadDelay.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

        // Find and click the button using its XPath
        driver.findElement(By.xpath("//button[@onclick='load()']")).click();

        // Check if the element with the class "selenium_webdriver" is displayed
        driver.findElement(By.cssSelector("#selenium_webdriver")).isDisplayed();

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Actual title does not match expected title.");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    private static @NotNull EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();

        // Set the page load timeout to 30 seconds to define the maximum amount of time to wait for a page to load
        edgeOptions.setPageLoadTimeout(Duration.ofSeconds(30));

        // Set the implicit wait timeout to 20 seconds to define the maximum amount of time to wait for an element to be found
        edgeOptions.setImplicitWaitTimeout(Duration.ofSeconds(20));

        // Set the script timeout to 10 seconds to define the maximum amount of time to wait for an asynchronous script to finish execution
        edgeOptions.setScriptTimeout(Duration.ofSeconds(10));
        return edgeOptions;
    }

}
