package scenarios.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ComparePDFImagesTest {

    // Define the file path for the Devops pdf
    private static final String FILE_PATH = "src/main/resources/supportFiles/pdf/devops.pdf";

    // Define the file path for the HDFC color image file
    private static final String HDFC_COLOR_FILE = "src/main/resources/supportFiles/images/hdfc_color.png";

    // Define the file path for the HDFC black image file
    private static final String HDFC_BLACK_FILE = "src/main/resources/supportFiles/images/hdfc_black.png";

    @Test(priority = 1)
    public void testExtractPDFImages() throws IOException {
        // Get the PDDocument object by providing the FILE_PATH
        PDDocument docx = Loader.loadPDF(new File(FILE_PATH));

        // Obtain the count of images from the PDF document
        int imagesCount = PDFTest.getImagesFromPDFDocument(docx).size();

        // Print the total count of images
        System.out.println("Total Images Count : " + imagesCount);

        // Extract images from the PDF document using PDFBox
        PDFTest.pdfBoxExtractImages(docx);
    }

    @Test(priority = 2)
    public void testCompareImages() throws IOException {
        // Create File objects for the specified image files
        File file1 = new File(HDFC_COLOR_FILE);
        File file2 = new File(HDFC_COLOR_FILE);
        File file3 = new File(HDFC_BLACK_FILE);

        // Compare the first two image files
        PDFTest.compareImages(file1, file2);

        // Compare the second and third image files
        PDFTest.compareImages(file2, file3);
    }

}
