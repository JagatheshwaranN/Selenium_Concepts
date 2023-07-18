package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import com.blibli.oss.qa.util.services.NetworkListener;

public class _04_Handle_Network_Calls {

	private WebDriver driver;

	@Test(priority = 1, enabled = true)
	private void captureNetworkLogs() {
		browserSetup();
		NetworkListener networkListener = new NetworkListener(driver, "NetworkRequest.har");
		networkListener.start();	
		driver.get("https://www.selenium.dev/");
		waitForSomeTime();
		driver.close();
		networkListener.createHarFile();
	}
	
	private WebDriver browserSetup() {
		driver = new ChromeDriver();
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
