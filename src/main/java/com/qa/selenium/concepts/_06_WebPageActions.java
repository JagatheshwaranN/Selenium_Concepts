package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _06_WebPageActions {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void clearAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://admin-demo.nopcommerce.com/login");
		waitForSomeTime();
		driver.findElement(By.cssSelector("input[name='Email']")).clear();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void clickOnAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		waitForSomeTime();
		driver.findElement(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button"))
				.click();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void typeInAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		waitForSomeTime();
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
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
