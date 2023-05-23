package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _17_Files {

	private WebDriver driver;
	private ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private void fileUpload() {
		browserSetup();
		driver.get("https://demo.guru99.com/test/upload/");
		driver.findElement(By.id("uploadfile_0"))
				.sendKeys("D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\Selenium_Print.pdf");
		driver.findElement(By.id("submitbutton")).click();
		WebElement fileUploadMessage = driver.findElement(By.xpath("(//center)[2]"));
		fileUploadMessage.isDisplayed();
		Assert.assertEquals(fileUploadMessage.getText().contains("1 file"), true);
		Assert.assertEquals(fileUploadMessage.getText().contains("has been successfully uploaded"), true);
		waitForSomeTime();
		driver.close();
	}
	
	// Yet to Update
	@Test(priority = 2, enabled = true)
	private void fileDownload() {
		
	}

	private WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
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
