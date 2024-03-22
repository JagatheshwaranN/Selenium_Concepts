package concepts.file.chrome;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

public class WaitForFileDownloadTest {

    // Declare a private WebDriver instance variable named 'driver' to handle browser interactions
    private WebDriver driver;

    // Declare a private static final Duration variable named 'WAIT_TIMEOUT' and setting it to 1 minute
    private static final Duration WAIT_TIMEOUT = Duration.ofMinutes(1);

    // Declare a private static final Duration variable named 'POLL_TIMEOUT' and setting it to 10 seconds
    private static final Duration POLL_TIMEOUT = Duration.ofSeconds(10);

    @BeforeMethod
    public void setup() {
        // Set the system property to use the JDK HTTP client for WebDriver
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Set preferences for Chrome WebDriver
        HashMap<String, Object> preferences = new HashMap<>();

        // Disable pop-up windows by setting the default content settings for popups to 0
        preferences.put("profile.default_content_settings.popups", 0);

        // Set the default download directory to the current user directory
        preferences.put("download.default_directory", System.getProperty("user.dir"));

        // Create ChromeOptions instance to customize ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        // Set experimental options, such as preferences
        chromeOptions.setExperimentalOption("prefs", preferences);

        // Initialize ChromeDriver with ChromeOptions
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window
        driver.manage().window().maximize();
    }

    @AfterMethod
    // Method to clean up resources after testing
    public void tearDown() {
        // Check if the driver instance is not null
        if (driver != null) {
            // Quit the WebDriver session if it's not null
            driver.quit();
        }
    }

    @Test
    public void testWaitForFileDownload() {
        // Open the URL where the file is located for download
        driver.get("https://get.jenkins.io/windows-stable/2.440.2/jenkins.msi");

        // Define the path where the downloaded file is expected to be saved
        File file = new File(System.getProperty("user.dir"), "jenkins.msi");

        // Set up a FluentWait instance to wait for the file download to complete
        FluentWait<File> wait = new FluentWait<>(file)
                .withTimeout(WAIT_TIMEOUT) // Set the maximum time to wait for the file to be downloaded
                .pollingEvery(POLL_TIMEOUT) // Set the polling interval to check if the file is downloaded
                .ignoring(Exception.class) // Ignore any exceptions that might occur during the waiting process
                .withMessage("File is not downloaded"); // Specify the error message if the file is not downloaded within the timeout

        try {
            // Wait until the file exists and is readable, indicating that it's downloaded completely
            boolean isFileDownloaded = wait.until(fileObj -> fileObj.exists() && fileObj.canRead());

            // If the file is downloaded completely, print a success message
            if (isFileDownloaded) {
                System.out.println("File is completely downloaded");
            }
        } catch (TimeoutException e) {
            // If the file is not downloaded within the specified timeout, print a failure message
            System.out.println("File is not completely downloaded");
        }

        // Delete the file on exit to clean up the environment
        file.deleteOnExit();
    }

}
