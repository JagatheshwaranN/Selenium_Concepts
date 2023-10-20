package com.qa.selenium.scenarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HandleDataFromExcelFileTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Specifies the path to the Excel file containing the test data.
	// The path is constructed dynamically using the 'user.dir' property.
	// 'user.dir' refers to the current working directory where the Java process was launched.
	// The path points to the 'testData.xlsx' file in the 'supportFiles' directory.
	private static final String EXCEL_FILE_PATH = System.getProperty("user.dir")
			+ "/src/main/resources/supportFiles/testData.xlsx";

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

	@Test(dataProvider = "loginData")
	public void readDataFromExcelAndUseInAutomationFLow(String email, String password) {
		// Navigate to the NopCommerce website by opening the specified URL.
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Locating the email input field by its ID
		WebElement email_element = driver.findElement(By.id("Email"));

		// Locating the password input field by its ID
		WebElement password_element = driver.findElement(By.id("Password"));

		// Clearing any existing content in the email input field
		email_element.clear();

		// Entering the email value obtained from the 'data' map into the email input field
		email_element.sendKeys(email);

		// Clearing any existing content in the password input field
		password_element.clear();

		// Entering the password value obtained from the 'data' map into the password input field
		password_element.sendKeys(password);

		// Click on the login button to proceed with the login attempt.
		driver.findElement(By.cssSelector(".button-1.login-button")).click();

		// Assert the title of the resulting page after login to verify successful login.
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
	}

	@DataProvider(name = "loginData")
	private Object[][] getDataFromExcel() {
		// Initialize a list to store the data retrieved from the Excel file
		List<String[]> data = new ArrayList<>();

		// Create a try-with-resources block to ensure the resources are properly closed
		try (FileInputStream fileInputStream = new FileInputStream(EXCEL_FILE_PATH);
			 XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			// Access the first sheet in the Excel workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each row in the sheet
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				// Access the current row
				XSSFRow row = sheet.getRow(i);

				// Checks the row is not null
				if(row != null) {
					// Retrieve the email and password from the first and second columns respectively
					String email = row.getCell(0).getStringCellValue();
					String password = row.getCell(1).getStringCellValue();

					// Add the email and password as a string array to the list
					data.add(new String[]{email, password});
				}
			}
		} catch (IOException ex) {
			// Print the stack trace if an IOException occurs
			ex.printStackTrace();
		}
		// Convert the list of string arrays to a 2D Object array and return it
		return data.toArray(new Object[0][]);
	}

}
