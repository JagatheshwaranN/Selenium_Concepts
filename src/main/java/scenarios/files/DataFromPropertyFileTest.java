package scenarios.files;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class DataFromPropertyFileTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Declare a Properties instance.
	Properties properties;

	// Specifies the path to the properties file containing the test data.
	// The path is constructed dynamically using the 'user.dir' property.
	// 'user.dir' refers to the current working directory where the Java process was launched.
	// The path points to the 'testdata.properties' file in the 'supportFiles' directory.
	private static final String TESTDATA_FILE_PATH = System.getProperty("user.dir")
			+ "//src//main//resources//supportFiles//testData.properties";

	@BeforeClass
	public void init() {
		// Initializes the Properties object
		properties = new Properties();
		try {
			// Loads the property file.
			loadPropertyFile();
		} catch (IOException e) {
			// Throws a runtime exception if there's an error while loading the property file.
			throw new RuntimeException(e);
		}
	}

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
	public void testDataFromPropertyFile() {
		// Navigate to the NopCommerce website by opening the specified URL.
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Locating the email input field by its ID
		WebElement email_element = driver.findElement(By.id("Email"));

		// Locating the password input field by its ID
		WebElement password_element = driver.findElement(By.id("Password"));

		// Clearing any existing content in the email input field
		email_element.clear();

		// Retrieves the email value from the property file and inputs it in the email field.
		email_element.sendKeys(getDataFromPropFile("email"));

		// Clearing any existing content in the password input field
		password_element.clear();

		// Retrieves the password value from the property file and inputs it in the password field.
		password_element.sendKeys(getDataFromPropFile("password"));

		// Click on the login button to proceed with the login attempt.
		driver.findElement(By.cssSelector(".button-1.login-button")).click();

		// Assert the title of the resulting page after login to verify successful login.
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
	}


	private void loadPropertyFile() throws IOException {
		// Create a FileInputStream to read the property file
		FileInputStream fileInputStream = new FileInputStream(TESTDATA_FILE_PATH);

		// Load the properties from the file into the 'properties' object
		properties.load(fileInputStream);

		// Close the FileInputStream to release system resources
		fileInputStream.close();
	}

	private String getDataFromPropFile(String key) {
		// Retrieve the value associated with the specified key from the properties file
		String value = properties.getProperty(key);

		// Remove any leading or trailing whitespaces from the retrieved value
		return value.trim();
	}

}
