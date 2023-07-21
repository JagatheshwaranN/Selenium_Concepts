package com.qa.selenium.scenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _13_Handle_FetchDataFromPropertyFileForScript {

	private WebDriver driver;
	Properties properties = new Properties();

	@Test
	private void readDataFromPropFileAndUseInAutomationFLow() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement email = driver.findElement(By.id("Email"));
		WebElement password = driver.findElement(By.id("Password"));
		email.clear();
		email.sendKeys(getDataFromPropFile("email"));
		password.clear();
		password.sendKeys(getDataFromPropFile("password"));
		driver.findElement(By.cssSelector(".button-1.login-button")).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		waitForSomeTime();
		driver.close();
	}

	@BeforeMethod
	private void loadPropertyFile() throws IOException {
		File file = new File(
				System.getProperty("user.dir") + "//src//main//resources//supportFiles//testData.properties");
		FileInputStream fileInputStream = new FileInputStream(file);
		properties.load(fileInputStream);
	}

	private String getDataFromPropFile(String key) {
		return properties.getProperty(key).trim();
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
