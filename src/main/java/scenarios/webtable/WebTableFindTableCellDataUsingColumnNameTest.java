package scenarios.webtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class WebTableFindTableCellDataUsingColumnNameTest {

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

    @Test(priority = 1)
    public void testWebTableFindTableCellDataUsingColumnName() {
        // Navigate to the web page with the web table
        driver.get("https://demoqa.com/webtables");

        // Find the search box and enter the search term "Kierra"
        driver.findElement(By.id("searchBox")).sendKeys("Kierra");

        // Get and print the data for the "First Name" column
        String firstName = getTableDataByColumnName("First Name");
        System.out.println("First Name: " + firstName);

        // Get and print the data for the "Last Name" column
        String lastName = getTableDataByColumnName("Last Name");
        System.out.println("Last Name: " + lastName);
    }

    // A method to retrieve table cell data based on column name
    private String getTableDataByColumnName(String columnName) {
        // Constructing a dynamic XPath to locate the cell data in the web table
        String dynamicXPath = "(//div[@class='rt-table']//div[@class='rt-tbody']//div[@class='rt-tr-group'])[1]//div[@class='rt-td'][count(//div[@class='rt-table']//div[contains(@class,'rt-thead')]//div[contains(@class,'rt-th rt-resizable-header')]//div[text()='" + columnName + "']//preceding::div[contains(@class,'rt-th rt-resizable-header')])+1]";

        // Find the element using the constructed XPath and retrieve its text
        return driver.findElement(By.xpath(dynamicXPath)).getText();
    }

}
