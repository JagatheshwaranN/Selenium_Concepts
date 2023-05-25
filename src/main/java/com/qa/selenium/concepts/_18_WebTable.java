package com.qa.selenium.concepts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.Test;

import junit.framework.Assert;

public class _18_WebTable {

	private WebDriver driver;

	@Test(priority = 1, enabled = true)
	private void getRowDataFromTable() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\WebTable.html");
		WebElement table = driver.findElement(By.id("data-table"));
		List<WebElement> totalRows = table.findElements(By.tagName("tr"));
		List<WebElement> totalCells = table.findElements(By.tagName("td"));
		System.out.println("Number of table rows  => " + totalRows.size());
		System.out.println("Number of table cells => " + totalCells.size());
		for (WebElement row : totalRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (cell.getText().equalsIgnoreCase("UK")) {
					System.out.println("The Table Row Data is ==> " + row.getText());
					Assert.assertEquals(row.getText().contains("UK"), true);
				}
			}
		}
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
