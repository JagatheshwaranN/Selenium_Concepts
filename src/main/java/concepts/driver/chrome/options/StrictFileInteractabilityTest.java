package concepts.driver.chrome.options;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class StrictFileInteractabilityTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Declare a ChromeOptions instance to interact with the web browser.
		ChromeOptions chromeOptions = new ChromeOptions();
		/*
			Another way to achieve the functionality
			========================================
			chromeOptions.setCapability("strictFileInteractability", true);
		*/
		// Enable strict file interactability mode for enhanced security (Enforce stricter
		// security measures for file interactions)
		chromeOptions.setStrictFileInteractability(true);

		// Initialize the WebDriver with ChromeOptions
		driver = new ChromeDriver(chromeOptions);
	}

	@Test(priority = 1)
	public void testStrictFileInteractability() {
        // Define the expected file name
        String expectedFileName = "testData.json";

        // Navigate to the file upload page
        driver.get("https://the-internet.herokuapp.com/upload");

        // Create a File object pointing to the file you want to upload
        File uploadFile = new File("src/main/resources/supportFiles/testData.json");

        // Locate the file input element (type='file') on the webpage
        WebElement uploadElement = driver.findElement(By.cssSelector("input[type=file]"));

        // Upload the file by sending the absolute file path to the input element
        uploadElement.sendKeys(uploadFile.getAbsolutePath());

        // Click on the upload/submit button to complete the file upload
        driver.findElement(By.id("file-submit")).click();

        // Locate the element that displays the uploaded file name
        WebElement fileName = driver.findElement(By.id("uploaded-files"));

        // Assert that the uploaded file name matches the expected name
        Assert.assertEquals(fileName.getText(), expectedFileName);
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
