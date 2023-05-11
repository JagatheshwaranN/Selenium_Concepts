package com.qa.selenium.concepts;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LocatorTypes extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static ChromeOptions _chromeOptions;

	public static void main(String ar[]) {

		traditionalLocators();
	}

	public static WebDriver browserSetup() {
		_chromeOptions = new ChromeOptions();
		_chromeOptions.addArguments("--remote-allow-origins=*");
		_driver = get_driver(_chromeOptions);
		return _driver;
	}

	public static void traditionalLocators() {

		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/inputs.html");
		// 1. Name
		Assert.assertTrue(_driver.findElement(By.name("no_type")).isDisplayed());
		// 2. XPath
		Assert.assertTrue(_driver.findElement(By.xpath("//input[@type='checkbox']")).isDisplayed());

		_driver.navigate().to("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		// 3. Id
		Assert.assertTrue(_driver.findElement(By.id("click")).isDisplayed());
		// 4. LinkText
		Assert.assertTrue(_driver.findElement(By.linkText("Click for Results Page")).isDisplayed());
		// 5. PartialLinkText
		Assert.assertTrue(_driver.findElement(By.partialLinkText("Click for Result")).isDisplayed());
		// 6. Class
		Assert.assertTrue(_driver.findElement(By.className("ui-droppable")).isDisplayed());
		// 7. CSS
		Assert.assertTrue(_driver.findElement(By.cssSelector(".ui-widget-header.ui-droppable")).isDisplayed());
		// 8. Tag
		Assert.assertTrue(_driver.findElement(By.tagName("a")).isDisplayed());
		_driver.quit();
	}
}
