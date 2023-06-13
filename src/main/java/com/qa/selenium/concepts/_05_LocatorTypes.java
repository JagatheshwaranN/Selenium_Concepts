package com.qa.selenium.concepts;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _05_LocatorTypes {

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

	@Test(priority = 2, enabled = true)
	private static void cssLocatorTypes() {
		browserSetup();
		driver.get("https://www.facebook.com/");
		// css=tag#id
		Assert.assertTrue(driver.findElement(By.cssSelector("input#email")).isDisplayed());
		// css=tag.class[attribute=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("input.inputtext[name=email]")).isDisplayed());
		// css=tag[attribute=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("button[name='login']")).isDisplayed());
		// css=tag.class
		Assert.assertTrue(driver.findElement(By.cssSelector("img.fb_logo._8ilh.img")).isDisplayed());
		// css=tag[attribute^=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("input[type^='pass']")).isDisplayed());
		// css=tag[attribute$=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("input[type$='word']")).isDisplayed());
		// css=tag[attribute*=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("input[type*='swo']")).isDisplayed());
		// css=parentTag > childTag[attribute=value]
		Assert.assertTrue(driver.findElement(By.cssSelector("div > button[name='login']")).isDisplayed());
		// css=parentTag > childTag > subChildTag:nth-of-type(index)
		Assert.assertTrue(driver.findElement(By.cssSelector("div[id='pageFooter'] > ul > li:nth-of-type(2)")).isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void xpathLocatorTypes() {
		browserSetup();
		driver.get("https://www.facebook.com/");
		// xpath=//tag[contains(@attribute, value)]
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@data-testid,'royal_email')]")).isDisplayed());
		// xpath=//tag[@attribute=value or @attribute=value]
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' or @name='pass']")).isDisplayed());
		// xpath=//tag[@attribute=value and @attribute=value]
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password' and @name='pass']")).isDisplayed());
		// xpath=//tag[starts-with(@attribute, value)]
		Assert.assertTrue(driver.findElement(By.xpath("//input[starts-with(@data-testid,'royal')]")).isDisplayed());
		// xpath=//tag[text()=value]
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//h2[text()='Facebook helps you connect and share with the people in your life.']"))
				.isDisplayed());
		// xpath=//tag[@attribute=value]//following::tag
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='passContainer']//following::button")).isDisplayed());
		// xapth=//tag[@attribute=value]//ancestor::tag
		Assert.assertTrue(driver.findElement(By.xpath("//button[@name='login']//ancestor::form")).isDisplayed());
		// xpath=//tag[@attribute=value]//child::tag[@attribute=value]
		Assert.assertTrue(
				driver.findElement(By.xpath("//form[@data-testid='royal_login_form']//child::input[@name='email']"))
						.isDisplayed());
		// xpath=//tag[@attribute=value]//preceding::tag[@attribute=value]
		Assert.assertTrue(
				driver.findElement(By.xpath("//button[@name='login']//preceding::input[@name='email']")).isDisplayed());
		// xpath=//tag[@attribute=value]//following-sibling::tag
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_8esl']//following-sibling::h2")).isDisplayed());
		// xpath=//tag[@attribute=value]//parent::tag
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='email']//parent::div")).isDisplayed());
		// xpath=//tag[@attribute=value]//self::same-tag
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='password']//self::input")).isDisplayed());
		// xpath=//tag[@attribute=value]//descendant::tag[@attribute=value]
		Assert.assertTrue(driver
				.findElement(By.xpath("//form[@data-testid='royal_login_form']//descendant::input[@name='email']"))
				.isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
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
