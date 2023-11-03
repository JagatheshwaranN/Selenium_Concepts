package scenarios.pdf;

import scenarios.DriverConfiguration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class GetPDFFileMetaDataTestCase {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declaration of a static final Duration constant
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

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
    public void getPDFFileMetaData() {
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

            // Print the PDF file version
            System.out.println("PDF File Version          ==> " + pdDocument.getVersion());
            // Print the print option availability
            System.out.println("PDF File Print Option     ==> " + pdDocument.getCurrentAccessPermission().canPrint());
            // Print the read-only status
            System.out.println("PDF File ReadOnly         ==> " + pdDocument.getCurrentAccessPermission().isReadOnly());
            // Print the owner permission status
            System.out.println(
                    "PDF File Owner Permission ==> " + pdDocument.getCurrentAccessPermission().isOwnerPermission());
            // Print the author of the PDF
            System.out.println("PDF File Author           ==> " + pdDocument.getDocumentInformation().getAuthor());
            // Print the subject of the PDF
            System.out.println("PDF File Subject          ==> " + pdDocument.getDocumentInformation().getSubject());
            // Print the title of the PDF
            System.out.println("PDF File Title            ==> " + pdDocument.getDocumentInformation().getTitle());
            // Print the creator of the PDF
            System.out.println("PDF File Creator          ==> " + pdDocument.getDocumentInformation().getCreator());
            // Print the creation date of the PDF
            System.out.println(
                    "PDF File Creator Date     ==> " + pdDocument.getDocumentInformation().getCreationDate());
            // Print the encryption status of the PDF
            System.out.println("PDF File Encrypted        ==> " + pdDocument.isEncrypted());
            // Print the document ID of the PDF
            System.out.println("PDF File Document Id      ==> " + pdDocument.getDocumentId());
            
            // Close the PDF document
            pdDocument.close();
        } catch (IOException ex) {

            // Handle IO exceptions
            handleIOException(ex);
        }
    }

    private void handleIOException(IOException ex) {
        // Print the error message
        System.err.println("An error occurred while processing the PDF file: " + ex.getMessage());
    }

}
