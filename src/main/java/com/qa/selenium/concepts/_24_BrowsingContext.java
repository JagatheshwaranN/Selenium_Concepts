package com.qa.selenium.concepts;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContextInfo;
import org.openqa.selenium.bidi.browsingcontext.NavigationResult;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _24_BrowsingContext {
	
	private WebDriver driver;
	private ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = false)
	private void createBrowsingContextWindow() {
		//browserSetup();
		BrowsingContext browsingContext = new BrowsingContext(browserSetup(), WindowType.WINDOW);
		browsingContext.navigate("https://www.selenium.dev/documentation/webdriver/bidirectional/bidirectional_w3c/browsing_context/");
		Assert.assertNotNull(browsingContext.getId());
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 2, enabled = false)
	private void createBrowsingContextTab() {
		//browserSetup();
		BrowsingContext browsingContext = new BrowsingContext(browserSetup(), WindowType.TAB);
		browsingContext.navigate("https://www.selenium.dev/documentation/webdriver/bidirectional/bidirectional_w3c/browsing_context/");
		Assert.assertNotNull(browsingContext.getId());
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 3, enabled = false)
	private void createBrowsingContextForGivenHandle() {
		browserSetup();
		String id = driver.getWindowHandle();
		System.out.println(id);
		waitForSomeTime();
		BrowsingContext browsingContext = new BrowsingContext(driver, id);
		browsingContext.navigate("https://www.selenium.dev/");
		Assert.assertNotNull(browsingContext.getId());
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 4, enabled = false)
	private void openWindowWithReferenceContext() {
		browserSetup();
		waitForSomeTime();
		BrowsingContext browsingContext = new BrowsingContext(driver, WindowType.WINDOW, driver.getWindowHandle());
		browsingContext.navigate("https://www.selenium.dev/");
		Assert.assertNotNull(browsingContext.getId());
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 5, enabled = false)
	private void openTapWithReferenceContext() {
		browserSetup();
		waitForSomeTime();
		BrowsingContext browsingContext = new BrowsingContext(driver, WindowType.TAB, driver.getWindowHandle());
		browsingContext.navigate("https://www.selenium.dev/");
		Assert.assertNotNull(browsingContext.getId());
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 6, enabled = false)
	private void getNavigationResult() {
		browserSetup();
		BrowsingContext browsingContext = new BrowsingContext(driver, WindowType.TAB);
		NavigationResult result = browsingContext.navigate("https://www.selenium.dev/");
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		System.out.println("Navigation Id       : "+result.getNavigationId());
		System.out.println("Navigation Url      : "+result.getUrl());
		Assert.assertNotNull(browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 7, enabled = false)
	private void navigateToUrlWithReadiness() {
		browserSetup();
		BrowsingContext browsingContext = new BrowsingContext(driver, WindowType.TAB);
		NavigationResult result = browsingContext.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		System.out.println("Navigation Id       : "+result.getNavigationId());
		System.out.println("Navigation Url      : "+result.getUrl());
		Assert.assertNotNull(browsingContext.getId());
		waitForSomeTime();
		browsingContext.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 8, enabled = false)
	private void getBrowsingContextTree() {
		browserSetup();
		String contextId = driver.getWindowHandle();
		BrowsingContext browsingContext = new BrowsingContext(driver, contextId);
		browsingContext.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		List<BrowsingContextInfo> contextInfo = browsingContext.getTree();
		Assert.assertNotNull(browsingContext.getId());
		Assert.assertEquals(1, contextInfo.size());
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 9, enabled = false)
	private void getBrowsingContextTreeWithChild() {
		browserSetup();
		String contextId = driver.getWindowHandle();
		BrowsingContext browsingContext = new BrowsingContext(driver, contextId);
		browsingContext.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		List<BrowsingContextInfo> contextInfo = browsingContext.getTree();
		Assert.assertNotNull(browsingContext.getId());
		Assert.assertEquals(1, contextInfo.size());
		BrowsingContextInfo information = contextInfo.get(0);
		Assert.assertEquals(1, information.getChildren().size());
		Assert.assertEquals(contextId, information.getId());
		Assert.assertTrue(information.getChildren().get(0).getUrl().contains("formPage.html"));
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 10, enabled = false)
	private void getBrowsingContextTreeWithDepth() {
		browserSetup();
		String contextId = driver.getWindowHandle();
		BrowsingContext browsingContext = new BrowsingContext(driver, contextId);
		browsingContext.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id : "+browsingContext.getId());
		List<BrowsingContextInfo> contextInfo = browsingContext.getTree(0);
		Assert.assertNotNull(browsingContext.getId());
		Assert.assertEquals(1, contextInfo.size());
		BrowsingContextInfo information = contextInfo.get(0);
		Assert.assertNull( information.getChildren());
		Assert.assertEquals(contextId, information.getId());
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 11, enabled = false)
	private void getAllTopLevelBrowsingContexts() {
		browserSetup();
		String contextId = driver.getWindowHandle();
		BrowsingContext browsingContext1 = new BrowsingContext(driver, contextId);
		BrowsingContext browsingContext2 = new BrowsingContext(driver, WindowType.WINDOW);
		browsingContext1.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id1 : "+browsingContext1.getId());
		browsingContext2.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id2 : "+browsingContext2.getId());
		List<BrowsingContextInfo> contextInfo = browsingContext1.getTopLevelContexts();
		Assert.assertEquals(2, contextInfo.size());
		waitForSomeTime();
		browsingContext2.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 12, enabled = false)
	private void closeBrowsingContextWindows() {
		browserSetup();
		BrowsingContext browsingContext1 = new BrowsingContext(driver, WindowType.WINDOW);
		BrowsingContext browsingContext2 = new BrowsingContext(driver, WindowType.WINDOW);
		browsingContext1.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id1 : "+browsingContext1.getId());
		browsingContext2.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id2 : "+browsingContext2.getId());
		waitForSomeTime();
		browsingContext2.close();
		waitForSomeTime();
		browsingContext1.close();
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 13, enabled = true)
	private void closeBrowsingContextTabs() {
		browserSetup();
		BrowsingContext browsingContext1 = new BrowsingContext(driver, WindowType.TAB);
		BrowsingContext browsingContext2 = new BrowsingContext(driver, WindowType.TAB);
		browsingContext1.navigate("https://www.selenium.dev/selenium/web/iframes.html", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id1 : "+browsingContext1.getId());
		browsingContext2.navigate("https://www.selenium.dev/", ReadinessState.COMPLETE);
		System.out.println("Browsing Context Id2 : "+browsingContext2.getId());
		waitForSomeTime();
		browsingContext2.close();
		waitForSomeTime();
		browsingContext1.close();
		waitForSomeTime();
		driver.close();
	}
	
	private WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.setCapability("webSocketUrl", true);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
