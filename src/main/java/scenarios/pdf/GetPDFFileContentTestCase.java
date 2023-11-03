package scenarios.pdf;

import scenarios.DriverConfiguration;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class GetPDFFileContentTestCase {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // URL of the PDF document to be accessed
    public final static String PDF_URL = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf";

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
    public void getPDFFileContent() {
        // Navigates the driver to the specified PDF URL
        driver.get(PDF_URL);
        try {
            // Create a PDFTextStripper instance to extract text from the PDF document
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // Get the text content of the PDF document using the PDFTextStripper
            String pdfFileContent = pdfTextStripper.getText(HandlePDFTest.getPDFDocument(PDF_URL));

            // Print the extracted PDF content to the console
            System.out.println(pdfFileContent);

            // Assert that the extracted content contains the specified text "PDF BOOKMARK SAMPLE"
            Assert.assertTrue(pdfFileContent.contains("PDF BOOKMARK SAMPLE"));
        } catch (IOException ex) {
            // If an IOException occurs, print the stack trace to identify the issue
            ex.printStackTrace();
        }
    }

}
