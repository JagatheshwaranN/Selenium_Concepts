package scenarios.datepicker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class DatePickerSingleDateDisplayTest {

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
    /**
     * This method selects a specific date from a date picker on a web page.
     *
     * @param day   The day to be selected.
     * @param month The month of the desired date.
     * @param year  The year of the desired date.
     */
    private void selectDateFromDatePicker(String day, String month, String year) {
        // Set a timeout of 10 seconds for page loading
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // Navigate to the webpage containing the date picker
        driver.get("https://seleniumpractise.blogspot.com/2016/08/how-to-handle-calendar-in-selenium.html");

        // Click on the date picker to open it
        WebElement datepicker = driver.findElement(By.id("datepicker"));
        datepicker.click();

        // Get the month and year details displayed in the date picker
        WebElement monthYearDetail = driver.findElement(By.className("ui-datepicker-title"));
        String monthYearText = monthYearDetail.getText();

        // Print the extracted month and year (for debugging purposes)
        System.out.println(getMonthYear(monthYearText)[0]);
        System.out.println(getMonthYear(monthYearText)[1]);

        // Loop until the desired month and year are reached
        while (!(getMonthYear(monthYearText)[0].equalsIgnoreCase(month) && getMonthYear(monthYearText)[1].equalsIgnoreCase(year))) {
            // Find and click the "Next Month" button
            WebElement monthNavigator = driver.findElement(By.xpath("//span[contains(@class,'ui-icon ui-icon-circle-triangle-e')]"));
            monthNavigator.click();

            // Refresh the month and year details after navigation
            monthYearDetail = driver.findElement(By.className("ui-datepicker-title"));
            monthYearText = monthYearDetail.getText();

            // Print the extracted month and year after navigation (for debugging purposes)
            System.out.println(getMonthYear(monthYearText)[0]);
            System.out.println(getMonthYear(monthYearText)[1]);
        }

        try {
            // Click on the specified day within the selected month and year
            driver.findElement(By.xpath("//td[@data-handler='selectDay']//a[text()='" + day + "']")).click();
        } catch (Exception ex) {
            // Print any exceptions that occur during the click action
            ex.printStackTrace();
        }

    }

    /**
     * Helper method to split the month and year text.
     *
     * @param monthYearText The text containing month and year details.
     * @return An array containing the extracted month and year.
     */
    private String[] getMonthYear(String monthYearText) {
        return monthYearText.split(" ");
    }

}
