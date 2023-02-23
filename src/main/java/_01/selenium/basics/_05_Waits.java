package _01.selenium.basics;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class _05_Waits extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static WebDriverWait _wait;

	public static void main(String[] args) {
//		implicitWait();
//		waitUntilType1();
		waitUntilType2();
	}

	public static void implicitWait() {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement username = new WebDriverWait(_driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
	}

	public static void waitUntilType1() {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement username = new WebDriverWait(_driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
		/**
		 * More Options available under ExpectedCondition Class
		 * https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		 */
	}

	public static void waitUntilType2() {
		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement loginButton = new WebDriverWait(_driver, Duration.ofSeconds(5), Duration.ofMillis(1500))
				.until(ExpectedConditions.elementToBeClickable(
						By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button")));
		loginButton.click();
		_driver.quit();
	}

}
