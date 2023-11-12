package scenarios.webtable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class HandleWebTableType1Test {

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
    public void getRowDataFromTable() {
        // Navigate to the specified URL using the WebDriver instance
        driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/WebTable.html");

        // Find the HTML table element by its ID
        WebElement table = driver.findElement(By.id("data-table"));

        // Find all the table rows and table cells in the HTML table
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        List<WebElement> tableCells = table.findElements(By.tagName("td"));

        // Print the number of table rows and table cells
        System.out.println("Number of table rows  => " + tableRows.size());
        System.out.println("Number of table cells => " + tableCells.size());

        // Iterate through each table row
        for (WebElement row : tableRows) {

            // Check if the row is not null
            if (row != null) {

                // Find all the cells within the current row
                List<WebElement> rowCells = row.findElements(By.tagName("td"));

                // Iterate through each cell in the current row
                for (WebElement cell : rowCells) {

                    // Check if the cell is not null and contains the text "UK"
                    if (cell != null && cell.getText().equalsIgnoreCase("UK")) {

                        // Print the table row data if the cell contains "UK"
                        System.out.println("The Table Row Data is ==> " + row.getText());

                        // Assert that the row data contains "UK"
                        Assert.assertTrue(row.getText().contains("UK"), "Row data does not contain 'UK'");
                    }
                }
            }
        }
    }

}
