package concepts.file.edge;

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

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;

public class FileUploadUsingToolKitTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'edgeBrowserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.edgeBrowserSetup();
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
    public void testFileUploadUsingToolKit() {
        // Navigate to the page where file upload functionality exists
        driver.get("https://www.plupload.com/examples/");

        // Create a File object pointing to the file you want to upload
        File uploadFile = new File("src/main/resources/supportFiles/selenium.png");

        // Locate and click the upload button or element
        WebElement uploadElement = driver.findElement(By.xpath("//span[text()='Add Files']"));
        uploadElement.click();

        try {
            // Introduce a delay to handle any asynchronous behavior after the click
            Thread.sleep(WAIT_TIMEOUT);
        } catch (InterruptedException e) {
            // If interrupted, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Perform the file upload action using the defined file path
        handleFileUpload(uploadFile.getAbsolutePath());

        // Wait for the uploaded file element to be visible on the page
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        WebElement uploadedFile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".plupload_file.ui-state-default.plupload_delete")));

        // Assert if the uploaded file element is displayed, indicating a successful upload
        Assert.assertNotNull(uploadedFile);
        Assert.assertTrue(uploadedFile.isDisplayed(), "File upload success message is displayed.");
    }

    private void handleFileUpload(String filePath) {
        try {
            // Creating a new Robot instance
            Robot robot = new Robot();

            // Creating a string selection from the file path
            StringSelection stringSelection = new StringSelection(filePath);

            // Setting clipboard content to the string selection
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

            // Simulating key press for a control key
            robot.keyPress(KeyEvent.VK_CONTROL);

            // Simulating key press for a V key
            robot.keyPress(KeyEvent.VK_V);

            // Simulating key release for a V key
            robot.keyRelease(KeyEvent.VK_V);

            // Simulating key release for a control key
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Simulating key press for an enter key
            robot.keyPress(KeyEvent.VK_ENTER);

            // Simulating key release for an enter key
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            // Printing the stack trace in case of an AWTException
            e.getStackTrace();
        }
    }

}
