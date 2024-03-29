package scenarios.typeahead_suggestion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class TypeAheadSuggestionType1Test {

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
	public void testTypeAheadSuggestionType1() {
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

}
