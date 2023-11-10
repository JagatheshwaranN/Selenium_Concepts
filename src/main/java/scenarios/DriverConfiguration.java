package scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        // Initialize an EdgeDriver instance for browser automation.
        WebDriver driver = new EdgeDriver();

        // Maximize the browser window for a better view.
        driver.manage().window().maximize();

        // Return the initialized WebDriver instance.
        return driver;
    }

    public static WebDriver fireFoxBrowserSetup() {
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

}
