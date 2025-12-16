package concepts.screenshot;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FullPageScreenShotTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'fireFoxBrowserSetup' from the 'DriverConfiguration' class
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

    @Test
    public void takeFullPageScreenShot() throws IOException {
        // Open the target URL
        driver.get("https://www.selenium.dev/");

        // Capture the full page screenshot using getFullPageScreenShot method
        BufferedImage fullPageScreenShot = getFullPageScreenShot(driver);

        // Save the screenshot as FullPageScreenShot.png
        ImageIO.write(fullPageScreenShot, "png", new File("./FullPageScreenShot.png"));
    }

    private static BufferedImage getFullPageScreenShot(WebDriver driver) throws IOException {
        // Get JavascriptExecutor for advanced scripting
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        // Get the total width of the page
        int totalWidth = ((Long) Objects.requireNonNull(javascriptExecutor.executeScript("return document.body.scrollWidth"))).intValue();

        // Get the total height of the page
        int totalHeight = ((Long) Objects.requireNonNull(javascriptExecutor.executeScript("return document.body.scrollHeight"))).intValue();

        // Get the viewport width (the width of the visible part of the page)
        int viewPortWidth = ((Long) Objects.requireNonNull(javascriptExecutor.executeScript("return window.innerWidth"))).intValue();

        // Get the viewport height (the height of the visible part of the page)
        int viewPortHeight = ((Long) Objects.requireNonNull(javascriptExecutor.executeScript("return window.innerHeight"))).intValue();

        // Create a BufferedImage to hold the stitched screenshot
        BufferedImage stitchedImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);

        // Create a Graphics2D object to draw the screenshots onto the BufferedImage
        Graphics2D graphics2D = stitchedImage.createGraphics();

        // Loop through the page, taking screenshots of each viewport-sized section and stitching them together
        for (int y = 0; y < totalHeight; y += viewPortHeight) {
            for (int x = 0; x < totalWidth; x += viewPortWidth) {
                // Scroll to the next section
                javascriptExecutor.executeScript("window.scrollTo(" + x + "," + y + ")");

                // Wait for the scroll to complete and the page to render
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Take a screenshot of the current viewport
                File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage bufferedImage = ImageIO.read(screenShot);

                // Draw the captured viewport screenshot onto the stitched image at the corresponding position
                graphics2D.drawImage(bufferedImage, x, y, null);
            }
        }

        // Clean up resources by disposing of the Graphics2D object
        graphics2D.dispose();

        // Return the final stitched image representing the entire webpage
        return stitchedImage;
    }

}
