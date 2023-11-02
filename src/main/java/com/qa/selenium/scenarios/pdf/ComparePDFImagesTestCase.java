package com.qa.selenium.scenarios.pdf;

import com.qa.selenium.scenarios.DriverConfiguration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class ComparePDFImagesTestCase {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    private static final String FILE_URL = "file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/devops.pdf";

    private static final String HDFC_COLOR_FILE = System.getProperty("user.dir") + "//src//main//resources//supportFiles//images//hdfc_color.png";

    private static final String HDFC_BLACK_FILE = System.getProperty("user.dir") + "//src//main//resources//supportFiles//images//hdfc_black.png";

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
    public void verifyTwoImages() {

        PDDocument docx = HandlePDFTest.getPDFDocument(FILE_URL);
        int imagesCount = HandlePDFTest.getImagesFromPDFDocument(docx).size();
        System.out.println("Total Images Count : " + imagesCount);
        HandlePDFTest.pdfBoxExtractImages(docx);
        File file1 = new File(HDFC_COLOR_FILE);
        File file2 = new File(HDFC_COLOR_FILE);
        File file3 = new File(HDFC_BLACK_FILE);
        HandlePDFTest.compareImages(file1, file2);
        HandlePDFTest.compareImages(file2, file3);
    }

}
