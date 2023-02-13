package _01.selenium.basics;

import org.openqa.selenium.WebDriver;

public class _02_MaximizeBrowser extends _01_LaunchBrowser {

	public static WebDriver _driver = get_driver();

	public static void main(String[] args) {

		_driver.manage().window().maximize();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}
}
