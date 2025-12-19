package concepts.support;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ThreadGuardTest {

    @Test
    public void openSeleniumSite() {
        // Get the WebDriver instance specific to the current TestNG thread
        WebDriver driver = ThreadGuardDriverManager.getDriver();

        // Navigate to the Selenium official website
        driver.get("https://selenium.dev");
    }

    @Test
    public void openExampleSite() {
        // Get the WebDriver instance specific to the current TestNG thread
        WebDriver driver = ThreadGuardDriverManager.getDriver();

        // Navigate to the Example domain website
        driver.get("https://example.com");
    }

    @AfterMethod
    public void tearDown() {
        // Quit and clean up the WebDriver instance after each test method
        // This ensures no browser or thread-local data leaks into other tests
        ThreadGuardDriverManager.quitDriver();
    }
}
