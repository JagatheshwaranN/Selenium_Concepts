package concepts.file.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class MultipleFileUploadTest {

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

	@Test(priority = 1)
	public void testMultipleFileUploadTest() {
		// Navigate to the webpage for file upload
		driver.get("https://davidwalsh.name/demo/multiple-file-upload.php");

		// Define the file paths for the files to be uploaded
		String filePath1 = "D:\\Environment_Collection\\Intellij_Env\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\demo.png";
		String filePath2 = "D:\\Environment_Collection\\Intellij_Env\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\devops.pdf";

		// Locate the file input element on the webpage
		WebElement uploadElement = driver.findElement(By.id("filesToUpload"));

		// Combine the file paths with a newline character to simulate selecting multiple files
		String combinedPath = filePath1 + "\n" + filePath2;

		// Use the sendKeys() method to input the combined file paths into the file input element
		uploadElement.sendKeys(combinedPath);
	}

}
