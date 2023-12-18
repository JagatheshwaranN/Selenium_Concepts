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


public class QRCodeTest {

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
    public void testQRCode() {
        // URL to the webpage containing the QR code image
        URL url;

        // BufferedImage to hold the downloaded image
        BufferedImage bufferedImage;

        // Result object containing the decoded QR code information
        Result qrCodeContent;

        // Navigate to the webpage containing the QR code
        driver.get("https://qrcode.meetheed.com/qrcode_examples.php");

        // Find the QR code image element and extract its source URL
        String qrCodeUrl = driver.findElement(By.xpath("//img[@alt='QR Contact Example']"))
                .getAttribute("src");

        try {
            // Create a URL object from the extracted QR code image URL
            url = URI.create(qrCodeUrl).toURL();

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
            // Use MultiFormatReader to decode the QR code content from the BinaryBitmap
            qrCodeContent = new MultiFormatReader().decode(binaryBitmap);
        } catch (NotFoundException e) {
            // If the QR code decoding fails, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Print the decoded QR code information
        System.out.println("QR Code Information: " + qrCodeContent.getText());
    }

}
