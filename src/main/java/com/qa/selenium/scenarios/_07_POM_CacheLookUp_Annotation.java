package com.qa.selenium.scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class _07_POM_CacheLookUp_Annotation {
	
	static WebDriver driver;

	@FindBy(how = How.ID, using = "myText")
	public WebElement inputWithoutCache;

	@FindBy(how = How.ID, using = "myText")
	@CacheLookup
	public WebElement inputWithCache;

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.logfile", "TestExecutionLog.log");
		System.setProperty("webdriver.chrome.verboseLogging", "true");
		
		browserSetup();
		driver.get(
				"file:///D://Environment_Collection//Eclipse_Env//Workspace//Selenium_Concepts//src//main//resources//supportFiles//DisabledElement.html");
		_07_POM_CacheLookUp_Annotation pageObject = PageFactory.initElements(driver,
				_07_POM_CacheLookUp_Annotation.class);

		pageObject.inputWithCache.sendKeys("Selenium");

		long withoutCacheStartTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			pageObject.inputWithoutCache.getAttribute("value");
		}
		long withoutCacheEndTime = System.currentTimeMillis();
		System.out.println("Time taken for the get value operation without cache is "
				+ ((withoutCacheEndTime - withoutCacheStartTime) / 1000));

		long withCacheStartTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			pageObject.inputWithCache.getAttribute("value");
		}
		long withCacheEndTime = System.currentTimeMillis();
		System.out.println("Time taken for the get value operation with cache is "
				+ ((withCacheEndTime - withCacheStartTime) / 1000));
		
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
