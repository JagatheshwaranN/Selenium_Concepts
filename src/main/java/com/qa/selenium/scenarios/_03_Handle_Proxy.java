package com.qa.selenium.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _03_Handle_Proxy {

	private static WebDriver driver;
	private static FirefoxOptions firefoxOptions;
	
	@Test(priority = 1, enabled = true)
	private static void handleProxyAuthenticationOnChrome() {
		chromeBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 2, enabled = true)
	private static void handleProxyAuthenticationOnEdge() {
		edgeBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 3, enabled = true)
	private static void handleProxyAuthenticationOnFirefox() {
		fireFoxBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}
	
	private static WebDriver chromeBrowserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	private static WebDriver edgeBrowserSetup() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	private static WebDriver fireFoxBrowserSetup() {
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile profileInstance = profile.getProfile("testAutomation");
		firefoxOptions = new FirefoxOptions();
		firefoxOptions.setProfile(profileInstance);
		driver = new FirefoxDriver(firefoxOptions);
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
