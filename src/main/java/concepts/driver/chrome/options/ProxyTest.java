package concepts.driver.chrome.options;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ProxyTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Declare a ChromeOptions instance to interact with the web browser.
        ChromeOptions chromeOptions = new ChromeOptions();

        // Create a Proxy object
        Proxy proxy = new Proxy();

        // Set the HTTP proxy (replace with your actual proxy address and port)
        proxy.setHttpProxy("127.0.0.1:8888");

        // Set the proxy capability in ChromeOptions
        chromeOptions.setCapability("proxy", proxy);

        // Initialize the WebDriver with ChromeOptions
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testProxy() {
        // Navigate to the Google Home page.
        driver.get("https://www.selenium.dev/");

        // Assert that the page title is "Selenium".
        Assert.assertEquals(driver.getTitle(), "Selenium");
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
