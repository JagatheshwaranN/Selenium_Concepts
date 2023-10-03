package com.qa.selenium.scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;



public class HandlePOMWithCacheLookup {

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
		// Set Chrome driver properties for logging.
		System.setProperty("webdriver.chrome.logfile", "TestExecutionLog.log");
		System.setProperty("webdriver.chrome.verboseLogging", "true");

		// Initialize WebDriver and open the test page.
		driver = DriverConfiguration.browserSetup();

		// Navigate to the website you want to test.
		driver.get("file:///D://Environment_Collection//Eclipse_Env//Workspace//Selenium_Concepts//src//main//resources//supportFiles//DisabledElement.html");

		// Initialize a Page Object instance for HandlePOMWithCacheLookup class using PageFactory.
		HandlePOMWithCacheLookup pageObject = PageFactory.initElements(driver, HandlePOMWithCacheLookup.class);

		// Perform actions with and without cache lookup.
		pageObject.inputWithCache.sendKeys("Selenium");

		// Record the current time before performing an operation without cache.
		long withoutCacheStartTime = System.currentTimeMillis();

		// Loop once (100 iteration) to perform the operation without cache.
		for (int i = 0; i < 100; i++) {
			pageObject.inputWithoutCache.getAttribute("value");
		}

		// Record the current time after completing the operation without cache.
		long withoutCacheEndTime = System.currentTimeMillis();

		// Calculate and print time taken for operations.
		System.out.println("Time taken for the get value operation without cache is "
				+ ((withoutCacheEndTime - withoutCacheStartTime) / 1000));

		// Record the current time before performing an operation with cache.
		long withCacheStartTime = System.currentTimeMillis();

		// Loop once (100 iteration) to perform the operation with cache.
		for (int i = 0; i < 100; i++) {
			pageObject.inputWithCache.getAttribute("value");
		}

		// Record the current time after completing the operation with cache.
		long withCacheEndTime = System.currentTimeMillis();

		// Calculate and print time taken for operations.
		System.out.println("Time taken for the get value operation with cache is "
				+ ((withCacheEndTime - withCacheStartTime) / 1000));

		// Quit the WebDriver after the test.
		driver.quit();
	}

}
