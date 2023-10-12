package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TypeAheadSuggestionTest  {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Create a WebDriverWait instance to enable explicit waits during the test.
	private WebDriverWait wait;

	// Define a constant string for the input text used in the test.
	private static final String INPUT_TEXT = "sca";

	// Define a constant string for the suggestion you want to select during the test.
	private static final String SUGGESTION_TO_SELECT = "Scala";

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.browserSetup();

		// Create a WebDriverWait instance with a 10-second timeout to be used for explicit waits.
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
	public void selectValueFromTypeAheadSuggestionApproach1() {
		// Navigate to the URL of the jQuery UI Autocomplete example.
		driver.get("http://jqueryui.com/autocomplete/");

		// Switch to the iframe containing the autocomplete input field.
		driver.switchTo().frame(driver.findElement(By.className("demo-frame")));

		// Locate the input field element for typing.
		WebElement searchBar = driver.findElement(By.id("tags"));

		// Type each character from the INPUT_TEXT into the search bar.
		for (char c : INPUT_TEXT.toCharArray()) {
			searchBar.sendKeys(String.valueOf(c));
		}

		// Find the type-ahead suggestion dropdown.
		WebElement typeAheadSuggestion = driver.findElement(By.id("ui-id-1"));

		// Wait for the suggestion dropdown to become visible.
		wait.until(ExpectedConditions.visibilityOf(typeAheadSuggestion));

		// Select the desired suggestion from the dropdown.
		selectSuggestion(typeAheadSuggestion);
	}

	@Test(priority = 2)
	public void selectValueFromTypeAheadSuggestionApproach2() {
		// Navigate to the URL of the jQuery UI Autocomplete example.
		driver.get("http://jqueryui.com/autocomplete/");

		// Switch to the iframe containing the autocomplete input field.
		driver.switchTo().frame(driver.findElement(By.className("demo-frame")));

		// Locate the input field element for typing.
		WebElement searchBar = driver.findElement(By.id("tags"));

		// Loop through each character in the INPUT_TEXT string.
		for (int i = 0; i < INPUT_TEXT.length(); i++) {
			// Extract the character at the current index and convert it to a string.
			searchBar.sendKeys(String.valueOf(INPUT_TEXT.charAt(i)));

			// Add a delay or wait (e.g., for user interface responsiveness).
			waitForSeconds();
		}

		// Find the type-ahead suggestion dropdown.
		WebElement typeAheadSuggestion = driver.findElement(By.id("ui-id-1"));

		// Wait for the suggestion dropdown to become visible.
		wait.until(ExpectedConditions.visibilityOf(typeAheadSuggestion));

		// Select the desired suggestion from the dropdown.
		selectSuggestion(typeAheadSuggestion);
	}

	@Test(priority = 3)
	public void selectValueFromTypeAheadSuggestionApproach3() {
		// Navigate to the URL of the jQuery UI Autocomplete example.
		driver.get("http://jqueryui.com/autocomplete/");

		// Switch to the iframe containing the autocomplete input field.
		driver.switchTo().frame(driver.findElement(By.className("demo-frame")));

		// Locate the input field element for typing.
		WebElement searchBar = driver.findElement(By.id("tags"));

		// Loop through each character in the INPUT_TEXT string.
		for (int i = 0; i < INPUT_TEXT.length(); i++) {
			// Extract the character at the current index.
			char c = INPUT_TEXT.charAt(i);

			// Create a StringBuilder and append the character to it.
			searchBar.sendKeys(new StringBuilder().append(c));

			// Add a delay or wait (e.g., for user interface responsiveness).
			waitForSeconds();
		}

		// Find the type-ahead suggestion dropdown.
		WebElement typeAheadSuggestion = driver.findElement(By.id("ui-id-1"));

		// Wait for the suggestion dropdown to become visible.
		wait.until(ExpectedConditions.visibilityOf(typeAheadSuggestion));

		// Select the desired suggestion from the dropdown.
		selectSuggestion(typeAheadSuggestion);
	}

	private void selectSuggestion(WebElement typeAheadSuggestion) {
		// Loop through each suggestion element in the typeAheadSuggestion list.
		for (WebElement suggestion : typeAheadSuggestion.findElements(By.tagName("li"))) {

			// Check if the text of the suggestion matches the expected SUGGESTION_TO_SELECT.
			if (suggestion.getText().equals(SUGGESTION_TO_SELECT)) {

				// If a match is found, click on the suggestion and exit the loop.
				suggestion.click();

				// Break the loop
				break;
			}
		}
	}

	private void waitForSeconds() {
		try {
			// Sleep for 1000 milliseconds (1 second).
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
