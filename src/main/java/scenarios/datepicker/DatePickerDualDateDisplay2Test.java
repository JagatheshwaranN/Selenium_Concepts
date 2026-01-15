package scenarios.datepicker;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatePickerDualDateDisplay2Test {

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
    public void testDatePicker() {
        LocalDate dateToSelect = LocalDate.now().plusDays(60);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String[] dateToSelectArray = formatter.format(dateToSelect).split(" ");

        // Call the method to select the date from the date picker
        selectDateFromDatePicker(dateToSelectArray[0], dateToSelectArray[1]+" "+dateToSelectArray[2]);
    }

    // Helper method to select the Date from the Date Picker
    private void selectDateFromDatePicker(String day, String monthYear) {
        // Set page load timeout to 10 seconds
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // Open the Goibibo website
        driver.get("https://www.goibibo.com/");

        // Close any pop-ups if present
        driver.findElement(By.xpath("//span[contains(@class,'logSprite icClose')]")).click();

        // Pause execution for 5 seconds
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        // Click on the departure date field
        driver.findElement(By.xpath("//span[text()='Departure']")).click();

        // Pause execution for 3 seconds
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        // Locate month and year details for navigation
        List<WebElement> monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));

        // Loop until the desired month and year is found
        while (!(monthYearDetailList.get(0).getText().equalsIgnoreCase(monthYear)) && !(monthYearDetailList.get(1).getText().equalsIgnoreCase(monthYear))) {
            // Click on the next month button to navigate forward
            WebElement monthNavigator = driver.findElement(By.xpath("//span[@aria-label='Next Month' and @class='DayPicker-NavButton DayPicker-NavButton--next']"));
            monthNavigator.click();

            // Refresh the month and year details list
            monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
        }

        // Select the specified day from the calendar
        try {
            driver.findElement(By.xpath("//div[@class='DayPicker-Month']//div[contains(text(),'" + monthYear.split(" ")[0].trim() + "')]//parent::div//following-sibling::div[@class='DayPicker-Body']//div[contains(@class,'DayPicker-Day')]//p[text()='" + day + "']")).click();
        } catch (Exception ex) {
            // Print any exceptions that occur during the click action
            ex.getStackTrace();
        }
    }

}
