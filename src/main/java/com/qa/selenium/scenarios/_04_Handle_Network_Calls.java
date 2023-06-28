package com.qa.selenium.scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
