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
import java.util.concurrent.atomic.AtomicReference;

public class DatePickerTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds.
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

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
    public void testDatePicker() throws InterruptedException {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get("https://www.goibibo.com/");
        driver.findElement(By.xpath("//span[contains(@class,'logSprite icClose')]")).click();
        driver.findElement(By.xpath("//span[text()='Departure']/following-sibling::p[contains(@class,'fswWidgetTitle')]")).click();
        List<WebElement> monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
        String day = "3";
        String month;
        String year;

        month = monthYearDetailList.get(0).getText().split(" ")[0].trim();
        year = monthYearDetailList.get(0).getText().split(" ")[1].trim();
        System.out.println(month);
        System.out.println(year);

        while (!(month.equalsIgnoreCase("May") && year.equalsIgnoreCase("2024"))) {
            driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
            //monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
            monthYearDetailList = driver.findElements(By.xpath("//div[@class='DayPicker-Month']//div[@class='DayPicker-Caption']//div"));
            month = monthYearDetailList.get(0).getText().split(" ")[0].trim();
            year = monthYearDetailList.get(0).getText().split(" ")[1].trim();
        }

        try {
            driver.findElement(By.xpath("//div[@class='DayPicker-Month']//div[contains(text(),'" + month + "')]//parent::div//following-sibling::div[@class='DayPicker-Body']//div[contains(@class,'DayPicker-Day')]//p[text()='" + day + "']")).click();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread.sleep(3000);
    }

}
