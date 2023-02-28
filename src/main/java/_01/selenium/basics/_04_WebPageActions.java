package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class _04_WebPageActions extends _01_LaunchBrowser {

	public static WebDriver _driver;

	public static void main(String[] args) {

		try {
			typeInAnElement();
			clickOnAnElement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void typeInAnElement() throws InterruptedException {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
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
}
