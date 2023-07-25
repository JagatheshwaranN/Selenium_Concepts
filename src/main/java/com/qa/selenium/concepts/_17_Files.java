package com.qa.selenium.concepts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _17_Files {

	private WebDriver driver;
	private ChromeOptions chromeOptions;
	private FirefoxOptions firefoxOptions;
	private FirefoxProfile firefoxProfile;

	@Test(priority = 1, enabled = false)
	private void fileUpload() {
		browserSetup();
		driver.get("https://demo.guru99.com/test/upload/");
		driver.findElement(By.id("uploadfile_0"))
				.sendKeys("D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\Selenium_Print.pdf");
		driver.findElement(By.id("submitbutton")).click();
		WebElement fileUploadMessage = driver.findElement(By.xpath("(//center)[2]"));
		Assert.assertEquals(fileUploadMessage.isDisplayed(), true);
		waitForSomeTime();
		driver.close();
	}

	// https://eternallybored.org/misc/wget/
	@Test(priority = 2, enabled = false)
	private void fileDownloadUsingWget() {
		browserSetup();
		driver.get("https://demo.guru99.com/test/yahoo.html");
		WebElement downloadButton = driver.findElement(By.id("messenger-download"));
		String downloadFileSource = downloadButton.getAttribute("href");
		String wget_command = "cmd /c C:\\Wget\\wget.exe -P D: --no-check-certificate " + downloadFileSource;
		try {
			Process exec = Runtime.getRuntime().exec(wget_command);
			int exitValue = exec.waitFor();
			System.out.println("Exit Value " + exitValue);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = false)
	private void fileDownload() {
		browserSetupForDownload();
		driver.get("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");
		waitForSomeTime();
		WebElement downloadLink = driver.findElement(By.xpath("//a[text()='chromedriver_win32.zip']"));
		downloadLink.click();
		waitForSomeTime();
		File folder = new File(System.getProperty("user.dir"));
		File[] files = folder.listFiles();
		boolean found = false;
		File downloadedfile = null;
		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();
				System.out.println("File : " + file.getName());
				if (fileName.contains("chromedriver_win32.zip")) {
					downloadedfile = new File(fileName);
					found = true;
				}
			}
		}
		Assert.assertTrue(found, "Downloaded document is not found");
		downloadedfile.deleteOnExit();
		driver.close();
	}
	
	
	@Test(priority = 4, enabled = true)
	private void firefoxFileDownload() {
		firefoxBrowserSetupForDownload();
		driver.get("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");
		waitForSomeTime();
		WebElement downloadLink = driver.findElement(By.xpath("//a[text()='chromedriver_win32.zip']"));
		downloadLink.click();
		waitForSomeTime();
		File folder = new File(System.getProperty("user.dir"));
		File[] files = folder.listFiles();
		boolean found = false;
		File downloadedfile = null;
		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();
				System.out.println("File : " + file.getName());
				if (fileName.contains("chromedriver_win32.zip")) {
					downloadedfile = new File(fileName);
					found = true;
				}
			}
		}
		Assert.assertTrue(found, "Downloaded document is not found");
		downloadedfile.deleteOnExit();
		waitForSomeTime();
		driver.close();
	}

	private WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver browserSetupForDownload() {
		HashMap<String, Object> preferences = new HashMap<String, Object>();
		preferences.put("profile.default_content_settings.popups", 0);
		preferences.put("download.default_directory", System.getProperty("user.dir"));
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		chromeOptions.setExperimentalOption("prefs", preferences);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver firefoxBrowserSetupForDownload() {
		firefoxProfile = new FirefoxProfile();
		firefoxOptions = new FirefoxOptions();
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.download.dir",System.getProperty("user.dir"));
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/zip");
		firefoxOptions.setProfile(firefoxProfile);
		driver = new FirefoxDriver(firefoxOptions);
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
