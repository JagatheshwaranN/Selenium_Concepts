package scenarios.webtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class HandleWebTableType2Test {

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
    public void getTableRowAndCellNumber() {
        // Navigate to the specified URL using the WebDriver instance
        driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/WebTable.html");

        // Find the HTML table element by its ID
        WebElement table = driver.findElement(By.id("data-table"));

        // Find all the table rows in the HTML table
        List<WebElement> totalRows = table.findElements(By.tagName("tr"));

        // Subtracting 1 to exclude the header row from the count
        int rows = totalRows.size() - 1;
        System.out.println("Number of table rows  => " + rows);

        // Iterate through each row (excluding the header)
        for (int row = 0; row < rows; row++) {

            // Find all the cells within the current row
            List<WebElement> rowCells = totalRows.get(row).findElements(By.tagName("td"));

            // Get the number of cells in the current row
            int numberOfCells = rowCells.size();

            // Iterate through each cell in the current row
            for (int cell = 0; cell < numberOfCells; cell++) {

                // Get the text content of the current cell
                String cellData = rowCells.get(cell).getText();

                // Check if the cell contains the text "UK"
                if (cellData.contains("UK")) {

                    // Print the information about the cell
                    System.out.println("The Table Cell which has " + cellData +
                            " whose Row Number is " + row + " and Column Number is " + cell);

                    // Assert that the cell data contains "UK"
                    Assert.assertTrue(cellData.contains("UK"), "Cell data does not contain 'UK'");
                }
            }
        }
    }

}
