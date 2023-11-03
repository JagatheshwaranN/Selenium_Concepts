package scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;



public class HandlePOMWithCacheLookupTest {

	// Declare a Static WebDriver instance to interact with the web browser.
	static WebDriver driver;

	// WebElement without CacheLookup - Will find the element every time it's accessed.
	@FindBy(how = How.ID, using = "myText")
	public WebElement inputWithoutCache;

	// WebElement with CacheLookup - Will find the element once and cache it for subsequent access.
	@FindBy(how = How.ID, using = "myText")
	@CacheLookup
	public WebElement inputWithCache;

	public static void main(String[] args) {
		// Set the system property to specify the log file for Chrome WebDriver
		System.setProperty("webdriver.chrome.logfile", "TestExecutionLog.log");

		// Set the system property to enable verbose logging for Chrome WebDriver
		System.setProperty("webdriver.chrome.verboseLogging", "true");

		// Set up the WebDriver instance by calling the 'browserSetup' method or function
		driver = DriverConfiguration.browserSetup();

		// Navigate to the website you want to test.
		driver.get("file:///D://Environment_Collection//Eclipse_Env//Workspace//Selenium_Concepts//src//main//resources//supportFiles//DisabledElement.html");

		// Initialize a Page Object instance for HandlePOMWithCacheLookupTest class using PageFactory.
		HandlePOMWithCacheLookupTest pageObject = PageFactory.initElements(driver, HandlePOMWithCacheLookupTest.class);

		// Perform actions with and without cache lookup.
		pageObject.inputWithCache.sendKeys("Selenium");

		// Record the current time before performing an operation without cache.
		long withoutCacheStartTime = System.currentTimeMillis();

		// Repeat a certain operation 100 times in a loop
		for (int i = 0; i < 100; i++) {
			// Get the "value" attribute of the 'inputWithoutCache' element within the 'pageObject'
			pageObject.inputWithoutCache.getAttribute("value");
		}

		// Record the current time after completing the operation without cache.
		long withoutCacheEndTime = System.currentTimeMillis();

		// Calculate and print the time taken for a specific operation without cache
		System.out.println("Time taken for the get value operation without cache is "
				+ ((withoutCacheEndTime - withoutCacheStartTime) / 1000));

		// Record the current time before performing an operation with cache.
		long withCacheStartTime = System.currentTimeMillis();

		// Repeat a certain operation 100 times in a loop
		for (int i = 0; i < 100; i++) {
			// Get the "value" attribute of the 'inputWithoutCache' element within the 'pageObject'
			pageObject.inputWithCache.getAttribute("value");
		}

		// Record the current time after completing the operation with cache.
		long withCacheEndTime = System.currentTimeMillis();

		// Calculate and print the time taken for a specific operation with cache
		System.out.println("Time taken for the get value operation with cache is " +
				((withCacheEndTime - withCacheStartTime) / 1000));

		// Close and quit the WebDriver instance, ending the browser session
		driver.quit();
	}

}
