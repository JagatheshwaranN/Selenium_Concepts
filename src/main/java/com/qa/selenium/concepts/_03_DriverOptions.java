package com.qa.selenium.concepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class _03_DriverOptions extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static ChromeOptions options = new ChromeOptions();
	public static FirefoxOptions ffOptions = new FirefoxOptions();
	public static EdgeOptions edgeOptions = new EdgeOptions();

	public static void main(String[] args) {

		openMaximizedBrowser();
		openChromeIncognito();
		acceptSSLSecurityIssue();
		pageLoadStrategy();
		waitTimeout();
		unHandledPrompt();
		browserDetails();
		headlessChromeBrowserLaunch();
		browserDetach();
		headlessFirefoxBrowserLaunch();
		headlessIEBrowserLaunch();
	}

	public static void openMaximizedBrowser() {
		options.addArguments("start-maximized");
		_driver = get_driver(options);
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/chrome/");
		_driver.quit();
	}

	public static void openChromeIncognito() {
		options.addArguments("incognito");
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void acceptSSLSecurityIssue() {
		options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://untrusted-root.badssl.com/");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_driver.quit();
	}

	public static void pageLoadStrategy() {
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://unsplash.com/t/people");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_driver.quit();
	}

	public static void waitTimeout() {
		options.setPageLoadTimeout(Duration.ofSeconds(10));
		options.setImplicitWaitTimeout(Duration.ofSeconds(10));
		options.setScriptTimeout(Duration.ofSeconds(10));
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/SiteLoadDelay.html");
		_driver.findElement(By.xpath("//button[@onclick='load()']")).click();
		_driver.findElement(By.cssSelector(".light-mode-item.navbar-brand-item")).isDisplayed();
		_driver.quit();
	}

	// Not Complete
	public static void unHandledPrompt() {
		// options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
		options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_prompt");
		// _driver.quit();
	}

	public static void browserDetails() {
		_driver = get_driver(options);
		System.out.println("Browser Name >> " + options.getBrowserName());
		// Not Working
		// System.out.println("Browser Version >> " + options.getBrowserVersion());
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/documentation/webdriver/drivers/options/");
		_driver.quit();
	}

	// Not sure about this usecase.
	public static void browserDetach() {
//		options.setExperimentalOption("detach", true);
//		options.setCapability("detach", true);
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/chrome/");
		System.out.println(_driver.getTitle());
		_driver.close();
	}

	public static void headlessChromeBrowserLaunch() {
		options.addArguments("--headless=new");
		_driver = get_driver(options);
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/chrome/");
		System.out.println(_driver.getTitle());
		_driver.quit();
	}

	public static void headlessFirefoxBrowserLaunch() {
		ffOptions.addArguments("-headless");
		_driver = get_ffDriver(ffOptions);
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/firefox/");
		System.out.println(_driver.getTitle());
		_driver.quit();
	}

	public static void headlessIEBrowserLaunch() {
		edgeOptions.addArguments("--headless=new");
		_driver = get_edgeDriver(edgeOptions);
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/edge/");
		System.out.println(_driver.getTitle());
		_driver.quit();
	}

}
