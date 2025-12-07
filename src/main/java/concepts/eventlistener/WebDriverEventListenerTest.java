package concepts.eventlistener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class WebDriverEventListenerTest extends WebDriverEventListener {

	// Declaring a static WebDriver variable accessible across the class
	private static WebDriver driver;

	// Defining a constant Duration for timeout, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

    @Test
	public static void eventLogTest() {
		// Define the expected title for verification
		String expectedTitle = "Logged In Successfully | Practice Test Automation";

		try {
			// Initializing ChromeDriver instance
			driver = new ChromeDriver();

            // Maximize the browser window
            driver.manage().window().maximize();

			// Creating a WebDriverEventListenerTest instance to listen to WebDriver events
			WebDriverListener listener = new WebDriverEventListenerTest();

			// Decorating the driver with the EventFiringDecorator to enable event listening
			driver = new EventFiringDecorator<>(listener).decorate(driver);

			// Navigating to the login page
			driver.get("https://practicetestautomation.com/practice-test-login/");

			// Logging in
			login();

			// Get the actual title of the current page
			String actualTitle = driver.getTitle();

			// Assert that the actual title matches the expected title
			Assert.assertEquals(actualTitle, expectedTitle);

		} catch (Exception e) {
			// Print stack trace in case of exceptions
			e.getStackTrace();
		} finally {
			// Closing WebDriver to end the session
			if (driver != null) {
				driver.quit();
			}
		}
	}

	private static void login() {
		// Find the username, password fields, and login button
		WebElement emailField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.id("submit"));

		// Clear the username and password fields, then enter credentials and click login
		emailField.clear();
		emailField.sendKeys("student");
		passwordField.clear();
		passwordField.sendKeys("Password123");
		loginButton.click();

		// Set up WebDriverWait for waiting up to WAIT_TIMEOUT for the title to contain "Dashboard"
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		wait.until(ExpectedConditions.titleContains("Logged In Successfully"));
	}

}