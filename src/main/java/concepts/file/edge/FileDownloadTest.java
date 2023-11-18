package concepts.file.edge;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
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

	// Define a constant duration for the maximum wait time, set to 15 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		HashMap<String, Object> preferences = new HashMap<>();
		preferences.put("profile.default_content_settings.popups", 0);
		preferences.put("download.default_directory", System.getProperty("user.dir"));
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setExperimentalOption("prefs", preferences);
		driver = new EdgeDriver(edgeOptions);
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
		downloadedFile.deleteOnExit();
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

		// Cleanup: You may delete the file after test completion or keep it for further use
		downloadedFile.deleteOnExit();
	}

}
