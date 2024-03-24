package scenarios.svg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class ComplexSVGTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
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
    public void testComplexSVG() {
        // Navigate to the webpage containing the complex SVG element
        driver.get("https://emicalculator.net/");

        // Find all the rectangle elements within the complex SVG chart using XPath
        List<WebElement> barChart = driver.findElements(By.xpath("(//*[name()='svg' and @class='highcharts-root'])[2]//*[local-name()='g' and contains(@class,'highcharts-column-series highcharts-tracker')]//*[local-name()='rect']"));

        // XPath for the tooltip element associated with the SVG chart
        String barChartToolTip = "(//*[name()='svg' and @class='highcharts-root'])[2]//*[local-name()='g' and @class='highcharts-label highcharts-tooltip highcharts-color-undefined']//*[local-name()='text']";

        // Create Actions object to perform mouse hover actions
        Actions actions = new Actions(driver);

        // Iterate over each rectangle element in the chart
        for (WebElement element : barChart) {
            // Perform mouse hover action on the current rectangle element
            actions.moveToElement(element).perform();

            // Extract text from the tooltip element using XPath and print it
            String text = driver.findElement(By.xpath(barChartToolTip)).getText();
            System.out.println(text);
        }
    }

}
