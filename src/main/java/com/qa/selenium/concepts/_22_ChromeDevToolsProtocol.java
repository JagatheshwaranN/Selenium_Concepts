package com.qa.selenium.concepts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.emulation.Emulation;
import org.openqa.selenium.devtools.v114.performance.Performance;
import org.openqa.selenium.devtools.v114.performance.model.Metric;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _22_ChromeDevToolsProtocol {

	private ChromeDriver driver;
	private static Map<String, Object> location;
	private static Map<String, Object> coordinates;


	@Test(priority = 1, enabled = true)
	private void getPerformanceMetricsUsingCDP() {
		browserSetup();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Performance.enable(Optional.empty()));
		List<Metric> metricList = devTools.send(Performance.getMetrics());
		driver.get("https://selenium.dev/");
		for (Metric metric : metricList) {
			System.out.println(metric.getName() + " = " + metric.getValue());
		}
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private void overrideDeviceModUsingCDP() {
		browserSetup();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Emulation.setDeviceMetricsOverride(400, 900, 70, true, Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty()));
		driver.get("https://google.com/");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 3, enabled = true)
	private void emulateGeoLocationUsingCDP() {
		browserSetup();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Emulation.setGeolocationOverride(Optional.of(36.778259), Optional.of(-119.417931), Optional.of(1)));
		driver.get("https://my-location.org/");
		waitForSomeTime();
		waitForSomeTime();
		driver.close();	
	}
	
	@Test(priority = 4, enabled = true)
	private void emulateGeoLocationUsingCDPCommandApproach1() {
		browserSetup();
		location = new HashMap<String, Object>();
		location.put("latitude", 34.052235);
		location.put("longitude", -118.243683);
		location.put("accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", location);
		driver.get("https://oldnavy.gap.com/stores");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 5, enabled = true)
	private void emulateGeoLocationUsingCDPCommandApproach2() {
		browserSetup();
		coordinates = Map.of("latitude", 30.3079823, "longitude", -97.893803, "accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
		driver.get("https://oldnavy.gap.com/stores");
		List<WebElement> addresses = driver.findElements(By.className("address"));
		Assert.assertTrue(addresses.size() > 0, "No addresses found");
		Assert.assertTrue(addresses.stream().allMatch(a -> a.getText().contains(", TX ")),
				"Some addresses listed are not in Texas");
		waitForSomeTime();
		driver.close();
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
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
