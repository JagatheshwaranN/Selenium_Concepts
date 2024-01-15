package concepts.bidi.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

public class DomMutationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testDomMutation() {
        try {
            // Create a synchronized list to store mutation events
            List<DomMutationEvent> mutationList = Collections.synchronizedList(new ArrayList<>());

            // Cast the driver to HasLogEvents and register a callback for DOM mutation events
            ((HasLogEvents) driver).onLogEvent(domMutation(mutationList::add));

            // Navigate to the target website
            driver.get("https://www.gps-coordinates.net/");

            // Finding latitude and longitude input elements
            WebElement latitude = driver.findElement(By.id("latitude"));
            WebElement longitude = driver.findElement(By.id("longitude"));

            // Clearing existing values in latitude and longitude fields
            latitude.clear();
            longitude.clear();

            // Entering new values in latitude and longitude fields
            latitude.sendKeys("52.520008");
            longitude.sendKeys("13.404954");

            // Click the "Get Address" button
            driver.findElement(By.xpath("(//button[text()='Get Address'])[1]")).click();

            // Iterate through the captured mutation events
            for (var mutation : mutationList) {
                // Extract optional values from the mutation event
                var attributeName = Optional.ofNullable(mutation.getAttributeName()).orElse("NAN");
                var oldValue = Optional.ofNullable(mutation.getOldValue()).orElse("NAN");
                var currentValue = Optional.ofNullable(mutation.getCurrentValue()).orElse("NAN");
                var element = Optional.ofNullable(mutation.getElement().toString()).orElse("NAN");

                // Print details of each mutation event
                System.out.printf("\nAttribute Name: %s\n Old Value: %s\n New Value: %s\n Element: %s\n%n",
                        attributeName, oldValue, currentValue, element);
            }
        } catch (Exception ex) {
            // Print any exceptions encountered during the test
            ex.printStackTrace();
        }
    }

}