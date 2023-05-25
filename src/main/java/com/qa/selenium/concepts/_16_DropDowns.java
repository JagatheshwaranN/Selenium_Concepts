package com.qa.selenium.concepts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _16_DropDowns {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void selectDropDownSingleOptionByVisibleText() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		WebElement dropDown = driver.findElement(By.cssSelector("#fruits"));
		new Select(dropDown).selectByVisibleText("Apple");
		Assert.assertEquals(dropDown.getText().contains("Apple"), true);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void selectDropDownSingleOptionByValue() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByValue("1");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void selectDropDownSingleOptionByIndex() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByIndex(2);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void selectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		selectObj.selectByValue("am");
		selectObj.selectByValue("aq");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void deSelectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		selectObj.selectByIndex(0);
		selectObj.selectByValue("aq");
		selectObj.selectByVisibleText("The Avengers");
		waitForSomeTime();
		selectObj.deselectByIndex(0);
		selectObj.deselectByValue("aq");
		selectObj.deselectByVisibleText("The Avengers");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void dropDownOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		List<WebElement> dropDownOptions = new Select(driver.findElement(By.cssSelector("#superheros"))).getOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void dropDownSelectedOption() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		waitForSomeTime();
		Select selectObject = new Select(driver.findElement(By.cssSelector("#superheros")));
		selectObject.selectByValue("aq");
		List<WebElement> dropDownOptions = selectObject.getAllSelectedOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void selectDisabledOption() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/disabledSelect.html");
		waitForSomeTime();
		Select selectObject = new Select(driver.findElement(By.name("single_disabled")));
		Assert.assertThrows(UnsupportedOperationException.class, () -> {
			selectObject.selectByValue("disabled");
		});
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private static void chooseOptionFromDropdownWithoutSelectClass() {
		browserSetup();
		driver.get("D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\Dropdown.html");
		WebElement dropDown = driver.findElement(By.xpath("//div[@class='select-selected']"));
		dropDown.click();
		List<WebElement> dropDownOptions = driver.findElements(By.xpath("//ul[@class='select-items']//li"));
		boolean flag = false;
		for (WebElement option : dropDownOptions) {
			if (option.getText().equalsIgnoreCase("Google Chrome")) {
				flag = true;
				option.click();
				break;
			}
		}
		if (flag == false)
			System.out.println("The option is not in the dropdown list");
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
