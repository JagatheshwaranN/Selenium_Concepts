package concepts.grid.sequential;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class HubAndSingleNodeTest {

    public WebDriver driver;

    public DesiredCapabilities capabilities = new DesiredCapabilities();

    @Test
    public void testHubAndSingleNode() {
        String browser = "chrome";
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        capabilities.setPlatform(Platform.ANY);
        capabilities.setBrowserName(browser);
        ChromeOptions options = new ChromeOptions();
        options.merge(capabilities);
        try {
            driver = new RemoteWebDriver(URI.create("http://192.168.1.5:4444").toURL(), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.google.com/");
        System.out.println("Title of Page " + driver.getTitle() + " from Browser " + browser);
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
