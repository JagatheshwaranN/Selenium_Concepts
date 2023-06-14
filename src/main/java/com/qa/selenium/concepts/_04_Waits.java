package com.qa.selenium.concepts;

import java.time.Clock;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _04_Waits {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static Wait<WebDriver> wait;

	@Test(priority = 1, enabled = true)
	private static void implicitWait() {
		browserSetup();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.cssSelector("input[id='myText']"));
		input.sendKeys("admin");
		String result = input.getAttribute("value");
		Assert.assertEquals(result, "admin");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void waitUntilType1() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id='myText']")));
		input.sendKeys("admin");
		String result = input.getAttribute("value");
		Assert.assertEquals(result, "admin");
		waitForSomeTime();
		driver.close();
		/**
		 * More Options available under ExpectedCondition Class
		 * https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		 */
	}

	@Test(priority = 3, enabled = true)
	private static void waitUntilType2() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(1500))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".button-1.login-button")));
		loginButton.click();
		var result = driver.findElement(By.xpath("//div[@class='content-header']//h1")).getText();
		Assert.assertEquals(result, "Dashboard");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void waitUntilType3() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(2000),
				Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button-1.login-button")));
		loginButton.click();
		var result = driver.findElement(By.xpath("//div[@class='content-header']//h1")).getText();
		Assert.assertEquals(result, "Dashboard");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void fluentWait() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(1500)).ignoring(NoSuchElementException.class)
				.ignoring(NotFoundException.class);
		WebElement username = wait.until(driver -> driver.findElement(By.cssSelector("input[id='myText']")));
		username.sendKeys("admin");
		var result = username.getAttribute("value");
		Assert.assertEquals(result, "admin");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void fluentwait() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		wait = new FluentWait<WebDriver>(driver, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)
				.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(1500))
				.ignoring(NotFoundException.class);
		WebElement username = wait.until(driver -> driver.findElement(By.cssSelector("input[id='myText']")));
		username.sendKeys("admin");
		var result = username.getAttribute("value");
		Assert.assertEquals(result, "admin");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void pageLoadWait() {
		browserSetup();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement orangeHRMLogo = driver.findElement(By.cssSelector("div[class='orangehrm-login-logo']"));
		Assert.assertTrue(orangeHRMLogo.isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void scriptWait() {
		browserSetup();
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
		((JavascriptExecutor) driver).executeScript("alert('hello world');");
		driver.switchTo().alert().accept();
		((JavascriptExecutor) driver).executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 500);");
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
