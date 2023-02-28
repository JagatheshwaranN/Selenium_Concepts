package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class _06_WebPageElements extends _01_LaunchBrowser {

	public static WebDriver _driver = get_driver();

	public static void main(String[] args) {

		elementDisplay();
		elementEnabled();
		elementSelected();
	}

	public static void gotoTestSite() {

		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/inputs.html");
	}

	public static void elementDisplay() {

		gotoTestSite();
		boolean isElementDisplay = _driver.findElement(By.cssSelector("input[name='no_type']")).isDisplayed();
		System.out.println("Element Display Status ==> " + isElementDisplay);
	}

	public static void elementEnabled() {

		gotoTestSite();
		boolean isElementEnabled = _driver.findElement(By.cssSelector("input[name='no_type']")).isEnabled();
		System.out.println("Element Enable Status ==> " + isElementEnabled);
	}

	public static void elementSelected() {

		gotoTestSite();
		boolean isElementSelected = _driver.findElement(By.cssSelector("input[name='checkbox_input']")).isSelected();
		System.out.println("Element Selected Status ==> " + isElementSelected);
	}
}
