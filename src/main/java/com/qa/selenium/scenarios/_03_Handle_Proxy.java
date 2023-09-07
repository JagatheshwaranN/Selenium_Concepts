package com.qa.selenium.scenarios;

import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.Headers;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _03_Handle_Proxy {

	private WebDriver driver;
	private FirefoxOptions firefoxOptions;
	private Map<String, Object> header;
	private String basicAuthentication;
	private DevTools devTools;
	private String username = "admin";
	private String password = "admin";

	@Test(priority = 1, enabled = true)
	private void handleProxyAuthenticationOnChrome() {
		chromeBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private void handleProxyAuthenticationOnEdge() {
		edgeBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private void handleProxyAuthenticationOnFirefox() {
		fireFoxBrowserSetup();
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private void handleProxyUsingChromeDevTool() {
		chromeBrowserSetup();
		devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		header = new HashMap<>();
		basicAuthentication = "Basic "
				+ new String(Base64.getEncoder().encode(String.format("%s:%s", username, password).getBytes()));
		header.put("Authorization", basicAuthentication);
		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
		Assert.assertEquals(result, "Basic Auth");
		waitForSomeTime();
		driver.close();
	}

	private WebDriver chromeBrowserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver edgeBrowserSetup() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver fireFoxBrowserSetup() {
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile profileInstance = profile.getProfile("testAutomation");
		firefoxOptions = new FirefoxOptions();
		firefoxOptions.setProfile(profileInstance);
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
