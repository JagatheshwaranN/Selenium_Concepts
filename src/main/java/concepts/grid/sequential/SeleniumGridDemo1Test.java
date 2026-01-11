package concepts.grid.sequential;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;

public class SeleniumGridDemo1Test {

    public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<>();
    public DesiredCapabilities capabilities = new DesiredCapabilities();

    public WebDriver getDriver() {
        return driverLocal.get();
    }

    public void setDriver(WebDriver driver) {
        driverLocal.set(driver);
    }

    @Test
    public void setup() throws MalformedURLException, InterruptedException {
        launchBrowser("chrome");
    }

    public void launchBrowser(String browser) throws InterruptedException {

        try {
            if (browser.equals("chrome")) {
                capabilities.setPlatform(Platform.WINDOWS);
                capabilities.setBrowserName("chrome");
                WebDriver driver1 = new RemoteWebDriver(URI.create("http://localhost:4444").toURL(), capabilities);
                driver1.get("https://www.google.com/");
                System.out.println("Title of Page " + driver1.getTitle() + " from Browser ");
                Thread.sleep(2000);
                driver1.close();
            } else {
                throw new IllegalArgumentException("Browser Not Valid");
            }
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL for Remote WebDriver: " + ex.getMessage());
            throw new RuntimeException("Invalid URL for Remote WebDriver.", ex);
        }
    }

}
