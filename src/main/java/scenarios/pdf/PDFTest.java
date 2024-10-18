package scenarios.pdf;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.util.NotImplemented;

@NotImplemented
public class PDFTest {

    /**
     * Retrieves a PDDocument from the provided PDF file URL
     *
     * @param pdfFileURL The URL string pointing to the PDF file
     * @return A PDDocument loaded from the specified URL
     */
    public static PDDocument getPDFDocument(String pdfFileURL) {
        // Initialize the PDDocument as null
        PDDocument document = null;
        try {
            // Create a URL from the provided PDF file URL string
            URL url = URI.create(pdfFileURL).toURL();

            // Open an input stream from the URL
            InputStream inputStream = url.openStream();

            // Wrap the input stream in a buffered input stream for efficiency
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Load the PDF document from the buffered input stream
           // document = PDDocument.load(bufferedInputStream);
        } catch (IOException ex) {
            // If an I/O exception occurs during the loading of the document, print the stack trace
            ex.printStackTrace();
        }
        // Return the loaded PDF document
        return document;
    }

    /**
     * Retrieves images from the provided PDDocument
     *
     * @param document The input PDDocument from which to extract images
     * @return A list of BufferedImages extracted from the PDF document
     */
    public static List<BufferedImage> getImagesFromPDFDocument(PDDocument document) {
        // Initialize an empty list to store BufferedImages
        List<BufferedImage> images = new ArrayList<>();

        // Iterate through each page in the PDF document
        for (PDPage page : document.getPages()) {
            // Retrieve images from the resources of the current page and add them to the list
            images.addAll(getImagesFromResource(page.getResources()));
        }
        // Return the list of BufferedImages extracted from the PDF document
        return images;
    }

    /**
     * Retrieves images from the given PDResources
     *
     * @param resources The input PDResources from which to extract images
     * @return A list of BufferedImages extracted from the resources
     */
    public static List<BufferedImage> getImagesFromResource(PDResources resources) {
        // Initialize an empty list to store BufferedImages
        List<BufferedImage> images = new ArrayList<>();

        // Iterate through each COSName (PDF XObject name) in the resources
        for (COSName cosName : resources.getXObjectNames()) {
            PDXObject pdxObject = null;
            try {
                // Get the XObject associated with the current COSName
                pdxObject = resources.getXObject(cosName);
            } catch (IOException ex) {
                // Handle any IO exceptions that may occur during the process
                ex.printStackTrace();
            }

            // Check if the XObject is a PDFormXObject, then recursively extract images from its resources
            if (pdxObject instanceof PDFormXObject) {
                images.addAll(getImagesFromResource(((PDFormXObject) pdxObject).getResources()));
            }
            // Check if the XObject is a PDImageXObject, then extract the image and add it to the list
            else if (pdxObject instanceof PDImageXObject) {
                try {
                    images.add(((PDImageXObject) pdxObject).getImage());
                } catch (IOException ex) {
                    // Handle any IO exceptions that may occur during the process
                    ex.printStackTrace();
                }
            }
        }
        // Return the list of BufferedImages extracted from the resources
        return images;
    }

    /**
     * Extracts images from a given PDF document using PDFBox library
     *
     * @param document The input PDDocument from which to extract images
     */
    public static void pdfBoxExtractImages(PDDocument document) {
        // Get the pages from the document
        PDPageTree pdPageTree = document.getPages();

        // Iterate through each page in the document
        for (PDPage pdPage : pdPageTree) {
            // Get the resources associated with the current page
            PDResources pdResources = pdPage.getResources();

            // Iterate through each COSName (PDF XObject name) in the resources
            for (COSName cosName : pdResources.getXObjectNames()) {
                try {
                    // Get the XObject associated with the current COSName
                    PDXObject pdxObject = pdResources.getXObject(cosName);

                    // Check if the XObject is a PDImageXObject, then write the image to a file
                    if (pdxObject instanceof PDImageXObject) {
                        // Create a new file with a unique name in the specified directory
                        File file = new File(System.getProperty("user.dir") + "//src//main//resources//supportFiles//pdfImages//" + System.nanoTime() + ".png");

                        // Write the image to the file in PNG format
                        ImageIO.write(((PDImageXObject) pdxObject).getImage(), "png", file);
                    }
                } catch (IOException ex) {
                    // Handle any IO exceptions that may occur during the process
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Compares two images pixel by pixel and calculates the difference
     *
     * @param file1 The first image file
     * @param file2 The second image file
     */
    public static void compareImages(File file1, File file2) {
        try {
            // Read the two images from the provided files
            BufferedImage image1 = ImageIO.read(file1);
            BufferedImage image2 = ImageIO.read(file2);

            // Check if the dimensions of the two images are the same
            if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
                // Print an error message if the dimensions are different
                System.out.println("Error: Image1 and Image2 dimensions are mismatch");
            } else {
                // Calculate the difference between the two images
                long imagesDifference = calculateImageDifference(image1, image2);

                // Calculate the total number of pixels in the images
                double totalImagePixels = image1.getWidth() * image1.getHeight() * 3;

                // Calculate the average difference in pixel values between the two images
                double averageImageDifferencePixels = imagesDifference / totalImagePixels;

                // Calculate the difference as a percentage of the maximum pixel value (255)
                double imageDifferenceInpercentage = (averageImageDifferencePixels / 255) * 100;

                // Print the calculated image difference percentage
                System.out.println("Image Difference In Percentage: " + String.format("%.2f", imageDifferenceInpercentage));
            }
        } catch (IOException ex) {
            // Handle any IO exceptions that may occur
            ex.printStackTrace();
        }
    }

    /**
     * Calculates the difference between corresponding pixels of two images
     *
     * @param image1 The first BufferedImage
     * @param image2 The second BufferedImage
     * @return The total difference between the two images
     */
    private static long calculateImageDifference(BufferedImage image1, BufferedImage image2) {
        // Initialize the variable to store the total difference
        long imagesDifference = 0;

        // Get the width and height of the images
        int width = image1.getWidth();
        int height = image1.getHeight();

        // Iterate through each pixel in the images
        for (int yAxis = 0; yAxis < height; yAxis++) {
            for (int xAxis = 0; xAxis < width; xAxis++) {
                // Get the RGB values for each pixel in both images
                int image1RGBValue = image1.getRGB(xAxis, yAxis);
                int image2RGBValue = image2.getRGB(xAxis, yAxis);
                // Extract the individual red, green, and blue values from the RGB values
                int image1RedValue = (image1RGBValue >> 16) & 0xff;
                int image1GreenValue = (image1RGBValue >> 8) & 0xff;
                int image1BlueValue = (image1RGBValue) & 0xff;
                int image2RedValue = (image2RGBValue >> 16) & 0xff;
                int image2GreenValue = (image2RGBValue >> 8) & 0xff;
                int image2BlueValue = (image2RGBValue) & 0xff;
                // Calculate the absolute difference between corresponding pixel values of the two images
                imagesDifference += Math.abs(image1RedValue - image2RedValue);
                imagesDifference += Math.abs(image1GreenValue - image2GreenValue);
                imagesDifference += Math.abs(image1BlueValue - image2BlueValue);
            }
        }
        // Return the total difference between the two images
        return imagesDifference;
    }

}