package concepts.launch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class LaunchBrowserTestCase {

	@Test(priority = 1)
	public void launchBrowsers() {

        // Create a map to store browser names and their corresponding WebDriver classes
        Map<String, Class<? extends WebDriver>> browsers = new HashMap<>();
        browsers.put("Chrome", ChromeDriver.class);
        browsers.put("Firefox", FirefoxDriver.class);
        browsers.put("Edge", EdgeDriver.class);

        // Loop through the browsers and perform necessary actions
        for (Map.Entry<String, Class<? extends WebDriver>> entry : browsers.entrySet()) {

			// Get the browser name from the current map entry
			String browserName = entry.getKey();

			// Get the WebDriver class from the current map entry
			Class<? extends WebDriver> driverClass = entry.getValue();

			// Initialize the WebDriver instance as null to handle exceptions
			WebDriver driver = null;

			try {
                // Set the HTTP factory system property
                System.setProperty("webdriver.http.factory", "jdk-http-client");

                // Instantiate the WebDriver instance for the specified browser
                driver = driverClass.getDeclaredConstructor().newInstance();

                // Maximize the browser window
                driver.manage().window().maximize();

                // Open the Google homepage in the current browser
                driver.get("https://www.google.com/");

                // Get the title of the current webpage
                String title = driver.getTitle();

                // Assert that the title of the webpage is equal to "Google"
                assert title.equals("Google");

            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                // Throw a runtime exception if any instantiation error occurs
                throw new RuntimeException(e);
            } catch (WebDriverException wde) {
                // Print the error message if there's an issue with the WebDriver
                System.out.println("Error with " + browserName + " : " + wde);
            } finally {
                // Close the current browser, even if an exception occurs
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }

}
