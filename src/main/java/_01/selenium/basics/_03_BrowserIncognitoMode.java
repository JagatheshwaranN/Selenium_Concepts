package _01.selenium.basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class _03_BrowserIncognitoMode extends _01_LaunchBrowser {

	public static WebDriver _driver;

	public static void main(String[] args) {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		_driver = get_driver(options);
		_driver.get("https://www.google.com/");
		_driver.quit();
	}
}
