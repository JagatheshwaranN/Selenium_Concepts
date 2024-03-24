package scenarios.slider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class SliderByDragAndDropTest {

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

    // Test 1: Slide the loan amount slider from minimum to maximum
    @Test
    public void testSliderMethod1() {
        // Open the EMI calculator webpage
        driver.get("https://emicalculator.net/");

        // Locate the slider elements
        WebElement sliderTotalLength = driver.findElement(By.xpath("//div[@id='loanamountslider']"));
        WebElement sliderMinLength = driver.findElement(By.xpath("(//div[@class='ui-slider-range ui-corner-all ui-widget-header ui-slider-range-min'])[1]"));
        WebElement slider = driver.findElement(By.xpath("//div[@id='loanamountslider']//span[contains(@class,'ui-slider')]"));

        // Get the width of the slider at minimum and maximum positions
        int startXAxis = sliderMinLength.getSize().getWidth();
        int endXAxis = sliderTotalLength.getSize().getWidth();

        // Create an Actions object for performing slider interactions
        Actions actions = new Actions(driver);

        // Define a small increment value for each drag (controls movement granularity)
        int increment = 2;

        // Loop through positions from startXAxis (with increment) to endXAxis
        for (int i = startXAxis + increment; i <= endXAxis; i += increment) {
            // Move the slider by 'increment' pixels to the right using dragAndDropBy
            actions.dragAndDropBy(slider, increment, 0).perform();

            // Wait for the loan amount element to update after the drag
            new WebDriverWait(driver, Duration.ofMillis(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("loanamount")));

            // Get the current loan amount text displayed on the webpage
            String loanAmount = driver.findElement(By.id("loanamount")).getAttribute("value");

            // Parse the loan amount string to an integer (removing commas)
            int currentLoanAmount = Integer.parseInt(loanAmount.replaceAll(",", ""));

            // Print the current loan amount
            System.out.println(loanAmount);

            // Break the loop if the loan amount reaches or exceeds the maximum value (20000000 in this example)
            if (currentLoanAmount >= 20000000) {
                break;
            }
        }
    }

    // Test 2: Move slider to the leftmost position first, then slide right incrementally
    @Test
    public void testSliderMethod2() {
        // Open the EMI calculator webpage
        driver.get("https://emicalculator.net/");

        // Locate the slider elements
        WebElement sliderTotalLength = driver.findElement(By.xpath("//div[@id='loanamountslider']"));
        WebElement sliderMinLength = driver.findElement(By.xpath("(//div[@class='ui-slider-range ui-corner-all ui-widget-header ui-slider-range-min'])[1]"));
        WebElement slider = driver.findElement(By.xpath("//div[@id='loanamountslider']//span[contains(@class,'ui-slider')]"));

        // Get the width of the slider at the minimum position
        int startXAxis = sliderMinLength.getSize().getWidth();
        int endXAxis = sliderTotalLength.getSize().getWidth();

        // Create an Actions object for performing slider interactions
        Actions actions = new Actions(driver);

        // Move the slider all the way to the left in one go (assuming this is possible)
        actions.dragAndDropBy(slider, -startXAxis, 0).perform();

        // Define a small increment value for each drag (controls movement granularity)
        int increment = 2;

        // Loop through positions from startXAxis (with increment) to endXAxis
        for (int i = startXAxis + increment; i <= endXAxis; i += increment) {
            // Move the slider by 'increment' pixels to the right using dragAndDropBy
            actions.dragAndDropBy(slider, increment, 0).perform();

            // Wait for the loan amount element to update after the drag
            new WebDriverWait(driver, Duration.ofMillis(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("loanamount")));

            // Get the current loan amount text displayed on the webpage
            String loanAmount = driver.findElement(By.id("loanamount")).getAttribute("value");

            // Parse the loan amount string to an integer (removing commas)
            int currentLoanAmount = Integer.parseInt(loanAmount.replaceAll(",", ""));

            // Print the current loan amount
            System.out.println(loanAmount);

            // Break the loop if the loan amount reaches or exceeds the maximum value (20000000 in this example)
            if (currentLoanAmount >= 20000000) {
                break;
            }
        }
    }

}