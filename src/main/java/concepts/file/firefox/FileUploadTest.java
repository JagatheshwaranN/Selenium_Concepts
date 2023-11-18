package concepts.file.firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class FileUploadTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'firefoxBrowserSetup' from the 'DriverConfiguration' class
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

	@Test(priority = 1)
	public void testFileUpload() {
		// Load the webpage for file upload
		driver.get("https://demo.guru99.com/test/upload/");

		// File path for upload using forward slashes or properly escaped backward slashes
		String filePath = "D:\\Environment_Collection\\Intellij_Env\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\demo.png";

		// Provide a file path for upload
		WebElement uploadElement = driver.findElement(By.id("uploadfile_0"));
		uploadElement.sendKeys(filePath);

		// Click on the submit button
		driver.findElement(By.id("submitbutton")).click();

		// Explicitly wait for the file upload success message to be displayed
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		WebElement fileUploadMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//center)[2]")));

		// Verify if the file upload a success message is displayed
		Assert.assertTrue(fileUploadMessage.isDisplayed(), "File upload success message is displayed.");
	}

}
