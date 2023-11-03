package scenarios.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.annotations.Test;

import java.io.File;

public class ComparePDFImagesTestCase {

    // Define the file path for the Devops pdf
    private static final String FILE_PATH = "file:///D:/Environment_Collection/Intellij_Env/Selenium_Concepts/src/main/resources/supportFiles/devops.pdf";

    // Define the file path for the HDFC color image file
    private static final String HDFC_COLOR_FILE = System.getProperty("user.dir") + "//src//main//resources//supportFiles//images//hdfc_color.png";

    // Define the file path for the HDFC black image file
    private static final String HDFC_BLACK_FILE = System.getProperty("user.dir") + "//src//main//resources//supportFiles//images//hdfc_black.png";

    @Test(priority = 1)
    public void verifyTwoImages() {
        // Get the PDDocument object by providing the FILE_PATH
        PDDocument docx = HandlePDFTest.getPDFDocument(FILE_PATH);

        // Obtain the count of images from the PDF document
        int imagesCount = HandlePDFTest.getImagesFromPDFDocument(docx).size();

        // Print the total count of images
        System.out.println("Total Images Count : " + imagesCount);

        // Extract images from the PDF document using PDFBox
        HandlePDFTest.pdfBoxExtractImages(docx);

        // Create File objects for the specified image files
        File file1 = new File(HDFC_COLOR_FILE);
        File file2 = new File(HDFC_COLOR_FILE);
        File file3 = new File(HDFC_BLACK_FILE);

        // Compare the first two image files
        HandlePDFTest.compareImages(file1, file2);

        // Compare the second and third image files
        HandlePDFTest.compareImages(file2, file3);
    }

}
