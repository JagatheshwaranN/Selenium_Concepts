package com.qa.selenium.scenarios.pdf;

import com.qa.selenium.scenarios.DriverConfiguration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class GetPDFFilePageCountTestCase {

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
    public void getPDFFilePageCount() {
        // Navigates the driver to the specified PDF URL
        driver.get(PDF_URL);

        // Retrieves the number of pages in the PDF document
        int pages = HandlePDFTest.getPDFDocument(PDF_URL).getNumberOfPages();

        // Verifies if the number of pages in the PDF document matches the expected value
        Assert.assertEquals(pages, 4);
    }

}
