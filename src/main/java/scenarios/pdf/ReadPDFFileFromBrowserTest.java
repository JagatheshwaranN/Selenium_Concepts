package scenarios.pdf;

import scenarios.DriverConfiguration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;

public class ReadPDFFileFromBrowserTest {

    // Declaration of a private instance variable of type WebDriver
    private WebDriver driver;

    // Declaration of a static final Duration constant
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    @BeforeMethod
    public void setUp() {
        // WebDriver setup using DriverConfiguration class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check for the existence of the WebDriver instance
        if (driver != null) {
            // Close the browser session
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testReadPDFFileFromBrowser() {
        // Navigate the WebDriver to a specific URL
        driver.get("https://www.inkit.com/blog/pdf-the-best-digital-document-management");

        // Find the 'Agree' button element
        WebElement agreeButton = driver.findElement(By.xpath("//a[text()='Accept']"));

        // Find the 'trillions of PDFs' element
        WebElement trillionsOfPDF = driver.findElement(By.xpath("//a[text()='trillions of PDFs']"));

        // Apply a wait condition
        new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.visibilityOf(agreeButton));

        // Click the 'Agree' button
        agreeButton.click();

        // Scroll to the 'trillions of PDFs' element
        new Actions(driver).scrollToElement(trillionsOfPDF).perform();

        // Click the 'trillions of PDFs' element
        trillionsOfPDF.click();

        // Get the current URL of the page
        String pdfFileURL = driver.getCurrentUrl();

        try {
            // Create a URL object
            URL url = URI.create(pdfFileURL).toURL();

            // Open a URL connection
            URLConnection urlConnection = url.openConnection();

            // Add a request property
            urlConnection.addRequestProperty("User-Agent", "Chrome");

            // Get the input stream from the URL connection
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered input stream
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Load the PDF document
            PDDocument pdDocument = PDDocument.load(bufferedInputStream);

            // Get the number of pages in the PDF
            int pdfFilePages = pdDocument.getNumberOfPages();

            // Validate the number of pages
            validatePDFPages(pdfFilePages);

            // Extract the text from the PDF
            String pdfFileContent = extractTextFromPDF(pdDocument);

            // Print the PDF content
            System.out.println(pdfFileContent);

            // Validate the content of the PDF
            validatePDFContent(pdfFileContent);

            // Close the PDF document
            pdDocument.close();
        } catch (IOException ex) {

            // Handle IO exceptions
            handleIOException(ex);
        }
    }

    private void validatePDFPages(int pdfFilePages) {
        // Assertion for the number of pages
        Assert.assertEquals(pdfFilePages, 43, "PDF file should have 43 pages");
    }

    private String extractTextFromPDF(PDDocument pdDocument) throws IOException {
        // Create a PDFTextStripper object
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        // Return the extracted text
        return pdfTextStripper.getText(pdDocument);
    }

    private void validatePDFContent(String pdfFileContent) {
        // Assertion for the presence of a specific text
        Assert.assertTrue(pdfFileContent.contains("PDF Days Europe 2018"), "PDF should contain 'PDF Days Europe 2018'");

        // Assertion for the presence of a specific text
        Assert.assertTrue(pdfFileContent.contains("Thank you! Any questions?"), "PDF should contain 'Thank you! Any questions?'");
    }

    private void handleIOException(IOException ex) {
        // Print the error message
        System.err.println("An error occurred while processing the PDF file: " + ex.getMessage());
    }

}


