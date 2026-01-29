package scenarios.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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

import java.io.BufferedInputStream;
import java.io.File;
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
        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/pdf/openPDF.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

        // Find the 'samplePDF' button element
        WebElement samplePDFButton = driver.findElement(By.id("pdf"));

        // Apply a wait condition
        new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.visibilityOf(samplePDFButton));

        // Click the 'samplePDF' button
        samplePDFButton.click();

        // Get the current URL of the page
        String pdfFileURL = driver.getCurrentUrl();

        try {
            // Create a URL object
            Assert.assertNotNull(pdfFileURL);
            URL url = URI.create(pdfFileURL).toURL();

            // Open a URL connection
            URLConnection urlConnection = url.openConnection();

            // Add a request property
            urlConnection.addRequestProperty("User-Agent", "Chrome");

            // Get the input stream from the URL connection
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered input stream
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] pdfBytes;

            try (InputStream is = urlConnection.getInputStream()) {
                pdfBytes = is.readAllBytes();   // Java 9+
            }

            // Load the PDF document
            PDDocument pdDocument = Loader.loadPDF(pdfBytes);

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
        Assert.assertEquals(pdfFilePages, 1, "PDF file should have 1 page");
    }

    private String extractTextFromPDF(PDDocument pdDocument) throws IOException {
        // Create a PDFTextStripper object
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        // Return the extracted text
        return pdfTextStripper.getText(pdDocument);
    }

    private void validatePDFContent(String pdfFileContent) {
        // Assertion for the presence of a specific text
        Assert.assertTrue(pdfFileContent.contains("Lorem Ipsum"), "PDF should contain 'Lorem Ipsum'");
    }

    private void handleIOException(IOException ex) {
        // Print the error message
        System.err.println("An error occurred while processing the PDF file: " + ex.getMessage());
    }

}


