package concepts.grid.parallel;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class HubWithNodeBrowser2Test {

    public WebDriver driver;

    public DesiredCapabilities capabilities = new DesiredCapabilities();

    @Test
    public void testHubWithNodeBrowser2() {
        String browser = "edge";
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        capabilities.setPlatform(Platform.ANY);
        capabilities.setBrowserName("MicrosoftEdge");
        EdgeOptions options = new EdgeOptions();
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
        driver.get("https://selenium.dev/");
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
