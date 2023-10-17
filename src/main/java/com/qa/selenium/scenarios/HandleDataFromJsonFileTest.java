package com.qa.selenium.scenarios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class HandleDataFromJsonFileTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Specifies the path to the JSON file containing the test data.
	// The path is constructed dynamically using the 'user.dir' property.
	// 'user.dir' refers to the current working directory where the Java process was launched.
	// The path points to the 'testData.json' file in the 'supportFiles' directory.
	private static final String JSON_FILE_PATH = System.getProperty("user.dir")
			+ "//src//main//resources//supportFiles//testData.json";

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
	public void readDataFromJsonAndUseInAutomationFLow(Map<String, String> data) {
		// Navigate to the NopCommerce website by opening the specified URL.
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Locating the email input field by its ID
		WebElement email = driver.findElement(By.id("Email"));

		// Locating the password input field by its ID
		WebElement password = driver.findElement(By.id("Password"));

		// Clearing any existing content in the email input field
		email.clear();

		// Entering the email value obtained from the 'data' map into the email input field
		email.sendKeys(data.get("email"));

		// Clearing any existing content in the password input field
		password.clear();

		// Entering the password value obtained from the 'data' map into the password input field
		password.sendKeys(data.get("password"));

		// Click on the login button to proceed with the login attempt.
		driver.findElement(By.cssSelector(".button-1.login-button")).click();

		// Assert the title of the resulting page after login to verify successful login.
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
	}

	@DataProvider(name = "loginData")
	private Object[][] getDataFromJsonFile() {
		// Create a map to hold user data.
		Map<String, String> userMap = new HashMap<>();

		// Initialize a JSONParser instance.
		JSONParser jsonParser = new JSONParser();

		// Initialize a 2D array to hold user data.
		Object[][] userData;

		// Initialize a JSONArray instance.
		JSONArray jsonArray = null;

		try {
			// Parse the JSON file and retrieve the JSON object.
			JSONObject jsonObject = parseJsonFile(jsonParser);

			// Extract the JSON array containing user logins.
			jsonArray = (JSONArray) jsonObject.get("userlogins");

		} catch (IOException | ParseException ex) {
			// Handle any exceptions that occur during JSON data processing.
			handleJsonDataException(ex);
		}

		// Ensure that the jsonArray is not null before initializing the userData array.
		assert jsonArray != null;

		// Initialize the 2D array based on the size of the JSON array.
		userData = new Object[jsonArray.size()][1];

		// Iterate through the JSON array to retrieve user data.
		for (int i = 0; i < userData.length; i++) {

			// Retrieve the current user object from the JSON array.
			JSONObject userObject = (JSONObject) jsonArray.get(i);

			// Populate the map with the user email and password.
			userMap.put("email", (String) userObject.get("email"));
			userMap.put("password", (String) userObject.get("password"));

			// Store the user map in the 2D array.
			userData[i][0] = userMap;
		}
		// Return the 2D array containing user data.
		return userData;
	}

	private JSONObject parseJsonFile(JSONParser jsonParser) throws IOException, ParseException {
		// Parse the JSON file and return the JSON object.
		return (JSONObject) jsonParser.parse(new FileReader(JSON_FILE_PATH));
	}

	private void handleJsonDataException(Exception e) {
		// Handle various types of exceptions that can occur during JSON data processing.
		if (e instanceof FileNotFoundException) {
			System.err.println("The JSON file was not found.");
		} else if (e instanceof IOException) {
			System.err.println("An I/O error occurred while reading the JSON file.");
		} else if (e instanceof ParseException) {
			System.err.println("There was an error parsing the JSON data.");
		} else {
			// Print the stack trace if the exception type is unknown.
			e.printStackTrace();
		}
	}

}
