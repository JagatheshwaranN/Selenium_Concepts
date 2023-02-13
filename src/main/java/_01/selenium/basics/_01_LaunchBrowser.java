package _01.selenium.basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class _01_LaunchBrowser {

	public static WebDriver _driver;

	public static void main(String[] args) {

		launchChromeBrowser();
		launchEdgeBrowser();
		launchFirefoxBrowser();
	}

	public static void launchChromeBrowser() {

		_driver = new ChromeDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void launchEdgeBrowser() {

		_driver = new EdgeDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void launchFirefoxBrowser() {

		_driver = new FirefoxDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static WebDriver get_driver() {
		if (_driver == null) {
			return _driver = new ChromeDriver();
		} else {
			return _driver;
		}
	}

	public static WebDriver get_driver(ChromeOptions options) {
		if (_driver == null) {
			return _driver = new ChromeDriver(options);
		} else {
			return _driver;
		}
	}
}
