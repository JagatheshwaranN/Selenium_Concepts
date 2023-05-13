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

public class _04_WebPageActions {

	public static WebDriver driver;
	public static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	public static void clearAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://admin-demo.nopcommerce.com/login");
		Thread.sleep(8000);
		driver.findElement(By.cssSelector("input[name='Email']")).clear();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	public static void clickOnAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		driver.findElement(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button"))
				.click();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	public static void typeInAnElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	public static void selectDropDownSingleOptionByVisibleText() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByVisibleText("Apple");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	public static void selectDropDownSingleOptionByValue() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByValue("1");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	public static void selectDropDownSingleOptionByIndex() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByIndex(2);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	public static void selectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		selectObj.selectByValue("am");
		selectObj.selectByValue("aq");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	public static void deSelectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
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

	@Test(priority = 9, enabled = true)
	public static void dropDownOptions() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		List<WebElement> dropDownOptions = new Select(driver.findElement(By.cssSelector("#superheros"))).getOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 10, enabled = true)
	public static void dropDownSelectedOption() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		Select selectObject = new Select(driver.findElement(By.cssSelector("#superheros")));
		selectObject.selectByValue("aq");
		List<WebElement> dropDownOptions = selectObject.getAllSelectedOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 11, enabled = true)
	public static void selectDisabledOption() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/disabledSelect.html");
		Thread.sleep(8000);
		Select selectObject = new Select(driver.findElement(By.name("single_disabled")));
		Assert.assertThrows(UnsupportedOperationException.class, () -> {
			selectObject.selectByValue("disabled");
		});
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
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
