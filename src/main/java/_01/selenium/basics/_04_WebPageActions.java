package _01.selenium.basics;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class _04_WebPageActions extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static ChromeOptions _chromeOptions;
	public static Select _selectObject;

	public static void main(String[] args) {

		try {
			clearAnAnElement();
			clickOnAnElement();
			dropDownOptions();
			dropDownSelectedOption();
			deSelectDropDownMultipleOptions();
			selectDisabledOption();
			selectDropDownSingleOptionByIndex();
			selectDropDownSingleOptionByVisibleText();
			selectDropDownSingleOptionByValue();
			selectDropDownMultipleOptions();
			typeInAnElement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver browserSetup() {
		_chromeOptions = new ChromeOptions();
		_chromeOptions.addArguments("--remote-allow-origins=*");
		_driver = get_driver(_chromeOptions);
		return _driver;
	}

	public static void clearAnAnElement() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://admin-demo.nopcommerce.com/login");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='Email']")).clear();
		Thread.sleep(3000);
		_driver.quit();
	}

	public static void clickOnAnElement() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button"))
				.click();
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByVisibleText() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByVisibleText("Apple");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByValue() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByValue("1");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByIndex() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByIndex(2);
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownMultipleOptions() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = _selectObject.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		_selectObject.selectByValue("am");
		_selectObject.selectByValue("aq");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void deSelectDropDownMultipleOptions() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = _selectObject.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		_selectObject.selectByIndex(0);
		_selectObject.selectByValue("aq");
		_selectObject.selectByVisibleText("The Avengers");
		Thread.sleep(2000);
		_selectObject.deselectByIndex(0);
		_selectObject.deselectByValue("aq");
		_selectObject.deselectByVisibleText("The Avengers");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void dropDownOptions() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		List<WebElement> _dropDownOptions = _selectObject.getOptions();
		_dropDownOptions.forEach(e -> System.out.println(e.getText()));
		_driver.quit();
	}

	public static void dropDownSelectedOption() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		_selectObject.selectByValue("aq");
		List<WebElement> _dropDownOptions = _selectObject.getAllSelectedOptions();
		_dropDownOptions.forEach(e -> System.out.println(e.getText()));
		_driver.quit();
	}

	public static void selectDisabledOption() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/disabledSelect.html");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.name("single_disabled")));
		Assert.assertThrows(UnsupportedOperationException.class, () -> {
			_selectObject.selectByValue("disabled");
		});
		_driver.quit();
	}

	public static void typeInAnElement() throws InterruptedException {
		_driver = browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
		_driver.quit();
	}

}
