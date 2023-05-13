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
	
	@Test(priority = 3, enabled = false)
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
	
	@Test(priority = 4, enabled = false)
	private static void deleteCookie() {
		Set<Cookie> cookies;
		browserSetup();
		driver.get("http://www.example.com/");
		driver.manage().addCookie(new Cookie("Test", "12345"));
		Cookie cookie = new Cookie("Auto", "98765");
		driver.manage().addCookie(cookie);
		cookies = driver.manage().getCookies();
		System.out.println(cookies);
		driver.manage().deleteCookieNamed("Test");
		cookies = driver.manage().getCookies();
		System.out.println(cookies);
		driver.manage().deleteCookie(cookie);
		waitForSomeTime();
		driver.close();
	}
	
	
	@Test(priority = 5, enabled = false)
	private static void deleteAllCookies() {
		browserSetup();
		driver.get("http://www.example.com/");
		driver.manage().addCookie(new Cookie("Test", "12345"));
		Cookie cookie = new Cookie("Auto", "98765");
		driver.manage().addCookie(cookie);
		driver.manage().deleteAllCookies();
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println(cookies);
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 6, enabled = true)
	private static void cookieSameSiteTest() {
		browserSetup();
		driver.get("http://www.example.com/");
		Cookie cookie1 = new Cookie.Builder("Test","12345").sameSite("Strict").build();
		Cookie cookie2 = new Cookie.Builder("Auto","98765").sameSite("Lax").build();
		driver.manage().addCookie(cookie1);
		driver.manage().addCookie(cookie2);
		System.out.println(cookie1.getSameSite());
		System.out.println(cookie2.getSameSite());
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
