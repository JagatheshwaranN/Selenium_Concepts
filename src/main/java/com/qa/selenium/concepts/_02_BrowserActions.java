package com.qa.selenium.concepts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _02_BrowserActions extends _01_LaunchBrowser {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	public static void reloadBrowser() throws InterruptedException {
		browserSetup();
		driver.get("https://github.com/");
		driver.navigate().refresh();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	public static void movePageBackward() throws InterruptedException {
		browserSetup();
		reloadBrowser();
		driver.navigate().to("https://www.selenium.dev/");
		driver.navigate().back();
		System.out.println("Current WebPage URL ==> " + driver.getCurrentUrl());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	public static void movePageForward() throws InterruptedException {
		browserSetup();
		movePageBackward();
		driver.navigate().forward();
		System.out.println("Current WebPage URL ==> " + driver.getCurrentUrl());
		waitForSomeTime();
		driver.quit();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = get_driver(chromeOptions);
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
