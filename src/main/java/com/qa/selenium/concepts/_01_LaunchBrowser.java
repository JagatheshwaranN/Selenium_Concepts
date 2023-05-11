package com.qa.selenium.concepts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

public class _01_LaunchBrowser {

	public static WebDriver driver;

	@Test(priority = 1, enabled = true)
	public static void launchChromeBrowser() {
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		driver.quit();
	}

	@Test(priority = 2, enabled = true)
	public static void launchEdgeBrowser() {
		driver = new EdgeDriver();
		driver.get("https://www.google.com/");
		driver.quit();
	}

	@Test(priority = 3, enabled = true)
	public static void launchFirefoxBrowser() {
		driver = new FirefoxDriver();
		driver.get("https://www.google.com/");
		driver.quit();
	}

	/**
	 * After Chrome Version 110. The ChromeDriver() will not work as before due to
	 * the security constraint added. To fix this issue we must provide the
	 * ChromeOptions to the driver instance.
	 */
	public static WebDriver get_driver() {
		return driver = new ChromeDriver();
	}

	public static WebDriver get_driver(ChromeOptions options) {
		return driver = new ChromeDriver(options);
	}

	public static WebDriver get_ffDriver(FirefoxOptions options) {
		return driver = new FirefoxDriver(options);
	}

	public static WebDriver get_edgeDriver(EdgeOptions options) {
		return driver = new EdgeDriver(options);
	}
}
