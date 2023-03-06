package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class _04_WebPageActions extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static Select _selectObject;

	public static void main(String[] args) {

		try {
			clearAnAnElement();
			clickOnAnElement();
			selectDropDownByIndex();
			selectDropDownByVisibleText();
			selectDropDownByValue();	
			typeInAnElement();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearAnAnElement() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://admin-demo.nopcommerce.com/login");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='Email']")).clear();
		Thread.sleep(3000);
		_driver.quit();
	}

	public static void clickOnAnElement() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button"))
				.click();
		_driver.quit();
	}
	
	public static void selectDropDownByVisibleText() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByVisibleText("Apple");
		Thread.sleep(2000);
		_driver.quit();
	}
	
	public static void selectDropDownByValue() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByValue("1");
		Thread.sleep(2000);
		_driver.quit();
	}
	
	public static void selectDropDownByIndex() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByIndex(2);
		Thread.sleep(2000);
		_driver.quit();
	}
	

	public static void typeInAnElement() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
		_driver.quit();
	}

	//https://github.com/SeleniumHQ/seleniumhq.github.io/blob/trunk//examples/java/src/test/java/dev/selenium/support/SelectListTest.java#L66
	
}
