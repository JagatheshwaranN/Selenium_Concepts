package concepts.file.firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.io.IOException;

public class FileDownloadUsingWidgetTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'fireFoxBrowserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.fireFoxBrowserSetup();
	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	// https://eternallybored.org/misc/wget/
	@Test(priority = 1)
	public void testFileDownloadUsingWget() {
		// Define the expected File Name
		String expectedFileName = "msgr11us.exe";

		// Navigate to the Yahoo page
		driver.get("https://demo.guru99.com/test/yahoo.html");

		// Locate the download button
		WebElement downloadButton = driver.findElement(By.id("messenger-download"));

		// Extract the download file URL
		String downloadFileSource = downloadButton.getAttribute("href");

		// Construct the wget command
		String wgetCommand = "cmd /c C:\\Wget\\wget.exe -P " + System.getProperty("user.dir") + " --no-check-certificate " + downloadFileSource;

		try {
			// Create a process builder for wget
			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", wgetCommand);

			// Start the process and wait for it to complete
			Process process = processBuilder.start();
			int exitValue = process.waitFor();

			// Check the exit value to ensure successful download
			if (exitValue == 0) {
				System.out.println("File downloaded successfully");
			} else {
				System.out.println("Error downloading file: Exit value " + exitValue);
			}

			// Define the downloaded file path
			String downloadedFilePath = System.getProperty("user.dir") + File.separator + expectedFileName;

			// Define the path for the downloaded file
			File downloadedFile = new File(downloadedFilePath);

			// Check if the downloaded file exists
			if (!downloadedFile.exists()) {
				// Throw an exception if the downloaded file is not found
				throw new Exception("Downloaded file not found");
			} else {
				// Assert if the downloaded file exists (optional assertion)
				Assert.assertTrue(downloadedFile.exists(), "Downloaded file is found in the directory");

				// Cleanup: You may delete the file after test completion or keep it for further use
				if (!downloadedFile.delete()) {
					System.out.println("Error deleting file: " + downloadedFilePath);
				}
				System.out.println("File Deleted");
			}
		} catch (IOException | InterruptedException e) {
			// Handle IOException or InterruptedException by printing the stack trace
			e.printStackTrace();

		} catch (Exception e) {
			// If any other unexpected Exception occurs:
			// Rethrow it as a RuntimeException for clarity and to propagate it further
			throw new RuntimeException(e);
		}
	}

}
