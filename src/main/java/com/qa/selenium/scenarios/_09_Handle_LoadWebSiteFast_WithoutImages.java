package com.qa.selenium.scenarios;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

public class _09_Handle_LoadWebSiteFast_WithoutImages {
	
	private WebDriver driver;
	private ChromeOptions chromeOptions;
	private FirefoxOptions firefoxOptions;
	private EdgeOptions edgeOptions;
	
	@Test(priority = 1, enabled = true)
	private void loadWebsiteFastWithoutImagesOnChrome() {
		chromeOptions = new ChromeOptions();
		Map<String, Object> imageConfig = new HashMap<String, Object>();
		imageConfig.put("images", 2);
		Map<String, Object> chromePreferences = new HashMap<String, Object>();
		chromePreferences.put("profile.default_content_setting_values", imageConfig);
		chromeOptions.setExperimentalOption("prefs", chromePreferences);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 2, enabled = true)
	private void loadWebSiteFastWithoutImagesOnFirefox() {
		firefoxOptions = new FirefoxOptions();
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("permissions.default.image", 2);
		firefoxOptions.setProfile(firefoxProfile);
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 3, enabled = true)
	private void loadWebsiteFastWithoutImagesOnEdge() {
		edgeOptions = new EdgeOptions();
		Map<String, Object> imageConfig = new HashMap<String, Object>();
		imageConfig.put("images", 2);
		Map<String, Object> edgePreferences = new HashMap<String, Object>();
		edgePreferences.put("profile.default_content_setting_values", imageConfig);
		edgeOptions.setExperimentalOption("prefs", edgePreferences);
		driver = new EdgeDriver(edgeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		waitForSomeTime();
		driver.close();
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
