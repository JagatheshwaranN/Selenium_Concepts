package concepts.file.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

public class FileDownloadTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 10 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

	@BeforeMethod
    public void setUp() {
        // Create a HashMap to store custom browser preferences for Google Chrome
        HashMap<String, Object> preferences = new HashMap<>();

        // Disable pop-up dialogs that appear during file downloads
        preferences.put("profile.default_content_settings.popups", 0);

        // Set the default file download location to the project's root directory
        preferences.put("download.default_directory", System.getProperty("user.dir"));

        // Create ChromeOptions to configure Chrome browser behavior
        ChromeOptions chromeOptions = new ChromeOptions();

        // Apply the custom preferences to Chrome using experimental options
        chromeOptions.setExperimentalOption("prefs", preferences);

        // Initialize the ChromeDriver instance with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();
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
	public void testFileDownload() throws InterruptedException {
		// Define the expected File Name
		String expectedFileName = "chromedriver_win32.zip";

		// Navigate to the webpage containing the download link
		driver.get("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");

		// Wait until the download link is visible
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		WebElement downloadLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='chromedriver_win32.zip']")));

		// Click on the download link
        Assert.assertNotNull(downloadLink);
        downloadLink.click();

		// Wait for a specific time (You might need a more reliable wait strategy)
		Thread.sleep(WAIT_TIMEOUT);

		// Retrieve the list of files in the directory
		File folder = new File(System.getProperty("user.dir"));
		File[] files = folder.listFiles();

		// Initialize variables for file handling
		boolean found = false;
		File downloadedFile = null;

		// Ensure files exist before proceeding
		assert files != null;

		// Iterate through the files to find the downloaded file
		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();
				System.out.println("File : " + file.getName());

				// Check if the file name contains the expected name
				if (fileName.contains(expectedFileName)) {
					downloadedFile = new File(fileName);
					found = true;
				}
			}
		}

		// Assert if the downloaded file is found
		Assert.assertTrue(found, "Downloaded document is not found");

		// Optionally, you can delete the file after test completion
        if (downloadedFile.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
	}

	@Test(priority = 2)
	public void testFileDownloadApproach2() throws Exception {
		// Define the expected File Name
		String expectedFileName = "chromedriver_win32.zip";

		// Navigate to the webpage containing the download link
		driver.get("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");

		// Wait until the download link is visible
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		WebElement downloadLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='chromedriver_win32.zip']")));

		// Click on the download link
        Assert.assertNotNull(downloadLink);
        downloadLink.click();

		// Define the downloaded file path
		String downloadedFilePath = System.getProperty("user.dir") + File.separator + expectedFileName;

		// Wait for the download to complete (You may need a more reliable wait strategy)
		Thread.sleep(WAIT_TIMEOUT); // Adjust the wait time as per the expected download time

		// Define the path for the downloaded file
		File downloadedFile = new File(downloadedFilePath);

		// Check if the downloaded file exists
		if (!downloadedFile.exists()) {
			// Throw an exception if the downloaded file is not found
			throw new Exception("Downloaded file not found");
		} else {
			// Assert if the downloaded file exists (optional assertion)
			Assert.assertTrue(downloadedFile.exists(), "Downloaded file is found in the directory");
		}

		// Cleanup: You may delete the file after test completion
		downloadedFile.deleteOnExit();
	}

}
