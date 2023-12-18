package scenarios.scanner;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;


public class BarCodeTest {

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
    public void testBarCode() {
        // URL to the webpage containing the BAR code image
        URL url;

        // BufferedImage to hold the downloaded image
        BufferedImage bufferedImage;

        // Result object containing the decoded BAR code information
        Result barCodeContent;

        // Navigate to the webpage containing the BAR code
        driver.get("https://barcode.tec-it.com/en");

        // Find the BAR code image element and extract its source URL
        String barCodeUrl = driver.findElement(By.xpath("//img[contains(@alt,'Barcode')]"))
              .getAttribute("src");

        try {
            // Create a URL object from the extracted BAR code image URL
            url = URI.create(barCodeUrl).toURL();

            // Read the image from the URL into a BufferedImage
            bufferedImage = ImageIO.read(url);
        } catch (IOException e) {
            // If an I/O exception occurs during image reading, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Create a LuminanceSource from the BufferedImage
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);

        // Create a BinaryBitmap using HybridBinarizer from the LuminanceSource
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            // Use MultiFormatReader to decode the BAR code content from the BinaryBitmap
            barCodeContent = new MultiFormatReader().decode(binaryBitmap);
        } catch (NotFoundException e) {
            // If the BAR code decoding fails, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Print the decoded BAR code information
        System.out.println("BAR Code Information: " + barCodeContent.getText());
    }

}
