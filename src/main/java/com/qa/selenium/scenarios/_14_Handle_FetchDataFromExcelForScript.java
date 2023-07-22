package com.qa.selenium.scenarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _14_Handle_FetchDataFromExcelForScript {

	private WebDriver driver;

	@Test(dataProvider = "loginData")
	private void readDataFromExcelAndUseInAutomationFLow(String[] data) {
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
	private String[] getDataFromExcel() {
		String filePath = System.getProperty("user.dir") + "//src//main//resources//supportFiles//testData.xlsx";
		List<String> list = new ArrayList<String>();
		String[] userData = null;
		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowsCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			userData = new String[rowsCount];
			for (int i = 1; i <= rowsCount; i++) {
				int cellsCount = sheet.getRow(i).getLastCellNum();
				for (int j = 0; j < cellsCount; j++) {
					list.add(sheet.getRow(i).getCell(j).getStringCellValue());
				}
			}
			userData[0] = list.get(0) + "," + list.get(1);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
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
