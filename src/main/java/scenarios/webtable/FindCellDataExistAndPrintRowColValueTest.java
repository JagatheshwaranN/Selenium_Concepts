package scenarios.webtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class FindCellDataExistAndPrintRowColValueTest {

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
    public void testFindCellDataExistAndPrintRowColValue() {
        // Navigate to the webpage with the demo web table
        driver.get("https://www.techlistic.com/2017/02/automate-demo-web-table-with-selenium.html");

        // Call the method to find cell data and return its row and column value for the specified value
        System.out.println(findCellDataAndReturnRowColValue("Germany"));
    }

    // Method to find cell data in the web table and return its row and column value
    private boolean findCellDataAndReturnRowColValue(String value) {
        // Initialize a flag to indicate whether the cell data is found
        boolean flag = false;

        // Find the total number of rows in the table
        int rows = driver.findElements(By.xpath("//table[@id='customers']//tr")).size();

        // Find the total number of columns in the table
        int columns = driver.findElements(By.xpath("//table[@id='customers']//th")).size();

        // Iterate through each row of the table
        for (int i = 1; i < rows; i++) {
            // Iterate through each column of the table
            for (int j = 1; j <= columns; j++) {
                // Find the text of the cell at the current row and column
                String cellData = driver.findElement(By.xpath("//table[@id='customers']//tr[" + (i + 1) + "]//td[" + j + "]")).getText();

                // Check if the cell data matches the specified value
                if (cellData.equalsIgnoreCase(value)) {
                    // If found, print the row and column value of the cell
                    System.out.println("Row : " + i + " , " + "Column : " + j);
                    // Set the flag to indicate that the cell data is found
                    flag = true;
                    // Break out of the inner loop since the cell data is found
                    break;
                }
            }
            // If the flag is set to true, break out of the outer loop as well
            if (flag) {
                break;
            }
        }
        // Return the flag indicating whether the cell data is found
        return flag;
    }

}
