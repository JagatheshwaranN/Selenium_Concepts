package com.qa.selenium.concepts;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _05_LocatorTypes extends _01_LaunchBrowser {

	public static WebDriver driver;
	public static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void traditionalLocators() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");
		// 1. Name
		Assert.assertTrue(driver.findElement(By.name("no_type")).isDisplayed());
		// 2. XPath
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='checkbox']")).isDisplayed());

		driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		// 3. Id
		Assert.assertTrue(driver.findElement(By.id("click")).isDisplayed());
		// 4. LinkText
		Assert.assertTrue(driver.findElement(By.linkText("Click for Results Page")).isDisplayed());
		// 5. PartialLinkText
		Assert.assertTrue(driver.findElement(By.partialLinkText("Click for Result")).isDisplayed());
		// 6. Class
		Assert.assertTrue(driver.findElement(By.className("ui-droppable")).isDisplayed());
		// 7. CSS
		Assert.assertTrue(driver.findElement(By.cssSelector(".ui-widget-header.ui-droppable")).isDisplayed());
		// 8. Tag
		Assert.assertTrue(driver.findElement(By.tagName("a")).isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
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
