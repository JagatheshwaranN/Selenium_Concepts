package scenarios.datepicker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.List;

public class DatePickerType1Test {

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
        // Call the method to select the date "5th April 2024" from the date picker
        selectDateFromDatePicker("5", "April", "2024");
    }

    // Helper method to select the Date from the Date Picker
    private void selectDateFromDatePicker(String day, String month, String year) {
        // Set a timeout of 10 seconds for page loading
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // Go to the Goibibo website
        driver.get("https://www.goibibo.com/");

        // Close the login popup (assuming it exists)
        driver.findElement(By.xpath("//span[contains(@class,'logSprite icClose')]")).click();

        // Click on the "Departure" date field
        driver.findElement(By.xpath("//span[text()='Departure']/following-sibling::p[contains(@class,'fswWidgetTitle')]")).click();

        // Find all elements containing the current month and year details
        List<WebElement> monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));

        // Extract the current month and year from the first element
        String monthYearText = monthYearDetailList.get(0).getText();

        // Print the extracted month and year (for debugging purposes)
        System.out.println(getMonthYear(monthYearText)[0]);
        System.out.println(getMonthYear(monthYearText)[1]);

        // Loop until the desired month and year is reached
        while (!(getMonthYear(monthYearText)[0].equalsIgnoreCase(month) && getMonthYear(monthYearText)[1].equalsIgnoreCase(year))) {

            // Find the "Next Month" button if it exists
            List<WebElement> monthNavigator = driver.findElements(By.xpath("//span[@aria-label='Next Month' and @class='DayPicker-NavButton DayPicker-NavButton--next']"));

            if (!monthNavigator.isEmpty()) {
                // Click "Next Month" button to navigate
                monthNavigator.get(0).click();

                // Update the monthYearDetailList after navigation
                monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
                monthYearText = monthYearDetailList.get(0).getText();

            } else {
                // If "Next Month" doesn't exist, navigate to the next year using the second element
                // (assuming a year view is available)
                monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
                monthYearText = monthYearDetailList.get(1).getText();
            }

            // Print the extracted month and year after navigation (for debugging purposes)
            System.out.println(getMonthYear(monthYearText)[0]);
            System.out.println(getMonthYear(monthYearText)[1]);
        }

        try {
            // Click on the desired date within the extracted month and year
            driver.findElement(By.xpath("//div[@class='DayPicker-Month']//div[contains(text(),'" + getMonthYear(monthYearText)[0].trim() + "')]//parent::div//following-sibling::div[@class='DayPicker-Body']//div[contains(@class,'DayPicker-Day')]//p[text()='" + day + "']")).click();
        } catch (Exception ex) {
            // Print any exceptions that occur during the click action
            ex.printStackTrace();
        }
    }

    // Helper method to split the month and year text
    private String[] getMonthYear(String monthYearText) {
        return monthYearText.split(" ");
    }

}
