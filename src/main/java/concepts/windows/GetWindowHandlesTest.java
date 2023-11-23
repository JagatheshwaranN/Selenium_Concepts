package concepts.windows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.Set;

public class GetWindowHandlesTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 5 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

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
	public void testGetWindowHandles() {
        // Define the expected number of windows
        int expectedWindows = 2;

        // Navigate to the target URL
        driver.get("https://the-internet.herokuapp.com/windows");

        // Click on the "Click Here" link to open a new window
        driver.findElement(By.xpath("//a[text()='Click Here']")).click();

        // Create a WebDriverWait object with a timeout value
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        // Wait for the number of windows to be 2
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Retrieve all the window handles currently available to the WebDriver
        Set<String> windowHandles = driver.getWindowHandles();

        // Iterate through each window handle and print them using lambda expression
        windowHandles.forEach(System.out::println);

        // Assert that the number of window handles is equal to 2
        Assert.assertEquals(windowHandles.size(), expectedWindows);
    }

}
