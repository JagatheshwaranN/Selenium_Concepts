package com.qa.selenium.concepts;

import java.time.Duration;

import org.htmlunit.BrowserVersion;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _02_DriverOptions {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static FirefoxOptions firefoxOptions;
	private static EdgeOptions edgeOptions;

	@Test(priority = 1, enabled = true)
	private static void openMaximizedBrowser() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		driver = new ChromeDriver(chromeOptions);
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void openChromeIncognito() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("incognito");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void acceptSSLSecurityIssue() {
		chromeOptions = new ChromeOptions();
		chromeOptions.setAcceptInsecureCerts(true);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://untrusted-root.badssl.com/");
		Assert.assertEquals(driver.getTitle(), "untrusted-root.badssl.com");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void pageLoadStrategy() {
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://unsplash.com/t/people");
		Assert.assertEquals(driver.getTitle(), "People | Unsplash");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void waitTimeout() {
		chromeOptions.setPageLoadTimeout(Duration.ofSeconds(10));
		chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));
		chromeOptions.setScriptTimeout(Duration.ofSeconds(10));
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/SiteLoadDelay.html");
		driver.findElement(By.xpath("//button[@onclick='load()']")).click();
		driver.findElement(By.cssSelector(".light-mode-item.navbar-brand-item")).isDisplayed();
		Assert.assertEquals(driver.getTitle(), "Online Courses and eBooks Library");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	// Not Complete
	private static void unHandledPrompt() {
		// options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
		chromeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_prompt");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void browserDetails() {
		browserSetup();
		System.out.println("Browser Name >> " + chromeOptions.getBrowserName());
		// Not Working
		// System.out.println("Browser Version >> " +
		// chromeOptions.getBrowserVersion());
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	// Not sure about this usecase.
	private static void browserDetach() {
//		options.setExperimentalOption("detach", true);
//		options.setCapability("detach", true);
		browserSetup();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private static void headlessChromeBrowserLaunch() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless=new");
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}

	@Test(priority = 10, enabled = true)
	private static void headlessFirefoxBrowserLaunch() {
		firefoxOptions = new FirefoxOptions();
		firefoxOptions.addArguments("-headless");
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}

	@Test(priority = 11, enabled = true)
	private static void headlessIEBrowserLaunch() {
		edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("--headless=new");
		driver = new EdgeDriver(edgeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}
	
	@Test(priority = 12, enabled = true)
	private static void htmlUnitDriverLaunch() {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}
	
	@Test(priority = 13, enabled = true)
	private static void htmlUnitDriverChromeVersionLaunch() {
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
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
