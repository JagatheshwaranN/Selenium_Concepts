package concepts.browsers.edge.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

public class LogConsoleOutputTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare an EdgeDriverService object to manage the EdgeDriver process
    EdgeDriverService edgeDriverService;

    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        logLocation = FileUtil.getTempFile("logsCaptureToConsole", ".log");

        try {
            System.setOut(new PrintStream(logLocation));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        edgeDriverService = new EdgeDriverService.Builder().withLogOutput(System.out).build();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogConsoleOutput() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");

        String fileContent;

        try {
            fileContent = new String(Files.readAllBytes(logLocation.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(fileContent);
        Assert.assertTrue(fileContent.contains("Starting Microsoft Edge WebDriver"));
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();

            // Close the service after WebDriver usage
            edgeDriverService.stop();
        }
    }

}
