package scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

public class DriverConfiguration {

    public static WebDriver browserSetup() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Initialize a ChromeDriver instance for browser automation.
        WebDriver driver = new ChromeDriver();

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

    public static WebDriver edgeBrowserSetup() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Initialize an EdgeDriver instance for browser automation.
        WebDriver driver = new EdgeDriver();

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

    public static WebDriver fireFoxBrowserSetup() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a ProfilesIni instance to manage Firefox profiles.
        ProfilesIni profile = new ProfilesIni();

        // Get the specific Firefox profile named "testAutomation."
        FirefoxProfile profileInstance = profile.getProfile("testAutomation");

        // Create FirefoxOptions to customize the Firefox browser behavior.
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Set the Firefox profile to the FirefoxOptions.
        firefoxOptions.setProfile(profileInstance);

        // Initialize a FirefoxDriver instance with the customized options.
        WebDriver driver = new FirefoxDriver(firefoxOptions);

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

    public static ChromeDriver cdpBrowserSetup() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Initialize a ChromeDriver instance for browser automation.
        ChromeDriver driver = new ChromeDriver();

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

    public static WebDriver bidiBrowserSetup() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create an instance of ChromeOptions to customize ChromeDriver settings
        ChromeOptions chromeOptions = new ChromeOptions();

        // Set the desired capability for ChromeOptions, but 'webSocketUrl' isn't a standard capability
        chromeOptions.setCapability("webSocketUrl", true);

        // Initialize the ChromeDriver with the customized ChromeOptions
        WebDriver driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

}
