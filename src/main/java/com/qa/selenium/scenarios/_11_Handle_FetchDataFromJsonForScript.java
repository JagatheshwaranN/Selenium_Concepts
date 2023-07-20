package com.qa.selenium.scenarios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _11_Handle_FetchDataFromJsonForScript {

	private WebDriver driver;

	@Test(dataProvider = "loginData")
	private void readDataFromJsonAndUseInAutomationFLow(String[] data) {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement email = driver.findElement(By.id("Email"));
		WebElement password = driver.findElement(By.id("Password"));
		email.clear();
		email.sendKeys(data[0].split(",")[0]);
		password.clear();
		password.sendKeys(data[0].split(",")[1]);
		driver.findElement(By.cssSelector(".button-1.login-button")).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		waitForSomeTime();
		driver.close();
	}

	@DataProvider(name = "loginData")
	private String[] getDataFromJSonFile() {
		JSONParser jsonParser = new JSONParser();
		String[] userData = null;
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(
					System.getProperty("user.dir") + "//src//main//resources//supportFiles//testData.json"));
			JSONArray jsonArray = (JSONArray) jsonObject.get("userlogins");
			userData = new String[jsonArray.size()];
			for (int i = 0; i < userData.length; i++) {
				JSONObject userObject = (JSONObject) jsonArray.get(i);
				String emailId = (String) userObject.get("email");
				String password = (String) userObject.get("password");
				userData[i] = emailId + "," + password;
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return userData;
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
