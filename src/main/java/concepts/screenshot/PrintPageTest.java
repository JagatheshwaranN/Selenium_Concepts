package concepts.screenshot;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.print.PrintOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.io.IOException;

public class PrintPageTest {

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
	public void testPrintPage() {
		// To set the page ranges to print
		// printOptions.setPageRanges("1", "2");
		try {
			// Navigate to the specified URL using the WebDriver instance
			driver.get("http://www.example.com");

			// Create an instance of PrintOptions to configure printing settings
			PrintOptions printOptions = new PrintOptions();

			// Use the PrintsPage interface to print the current page as a PDF
			Pdf pdf = ((PrintsPage) driver).print(printOptions);

			// Extract the content of the PDF as a Base64-encoded string
			String content = pdf.getContent();

			// Convert the Base64-encoded content to bytes and write it to a PDF file
			// Ensure that the destination path is appropriate for your project structure
			Files.write(OutputType.BYTES.convertFromBase64Png(content), new File("./Selenium_Print.pdf"));

		} catch (IOException e) {
			// Handle the exception appropriately, log it, or throw a custom exception.
			e.printStackTrace();
		}
	}

}
