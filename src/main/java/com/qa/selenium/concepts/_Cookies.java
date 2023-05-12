package com.qa.selenium.concepts;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _Cookies {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = false)
	private static void addCookie() {
		browserSetup();
		driver.get("http://www.example.com/");
		driver.manage().addCookie(new Cookie("Test", "12345"));
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 2, enabled = false)
	private static void getCookie() {
		browserSetup();
		driver.get("http://www.example.com/");
		driver.manage().addCookie(new Cookie("Test", "12345"));
		Cookie cookie = driver.manage().getCookieNamed("Test");
		System.out.println(cookie);
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 3, enabled = true)
	private static void getCookies() {
		browserSetup();
		driver.get("http://www.example.com/");
		driver.manage().addCookie(new Cookie("Test", "12345"));
		driver.manage().addCookie(new Cookie("Auto", "98765"));
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println(cookies);
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
