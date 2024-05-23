package concepts.screenshot;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import scenarios.DriverConfiguration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FullPageScreenShotUsingAshotTest {

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
    public void takeFullPageScreenShotUsingAshot() throws IOException {
        // Open the target URL
        driver.get("https://www.selenium.dev/");

        // Create an instance of AShot for taking screenshots
        AShot aShot = new AShot();

        // Configure AShot to capture the full page using viewportPasting strategy
        aShot.shootingStrategy(ShootingStrategies.viewportPasting(1000));

        // Capture the screenshot of the current webpage using the configured AShot instance
        Screenshot screenshot = aShot.takeScreenshot(driver);

        // Save the screenshot as FullPageScreenShot.png
        ImageIO.write(screenshot.getImage(), "png", new File("./FullPageScreenShot.png"));
    }

}
