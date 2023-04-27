package _01.selenium.basics;

import org.openqa.selenium.WebDriver;

public class _02_BrowserActions extends _01_LaunchBrowser {

	public static WebDriver _driver = get_driver();

	public static void main(String[] args) {

		try {
			maximizeBrowser();
			reloadBrowser();
			movePageBackward();
			movePageForward();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public static void maximizeBrowser() throws InterruptedException {

		_driver.manage().window().maximize();
		_driver.get("https://www.google.com/");
		Thread.sleep(5000);
	}

	public static void reloadBrowser() throws InterruptedException {

		maximizeBrowser();
		_driver.navigate().refresh();
		Thread.sleep(5000);
	}

	public static void movePageBackward() throws InterruptedException {

		maximizeBrowser();
		_driver.navigate().to("https://www.selenium.dev/");
		_driver.navigate().back();
		System.out.println("Current WebPage URL ==> " + _driver.getCurrentUrl());
		Thread.sleep(5000);
	}

	public static void movePageForward() throws InterruptedException {

		movePageBackward();
		_driver.navigate().forward();
		System.out.println("Current WebPage URL ==> " + _driver.getCurrentUrl());
		Thread.sleep(5000);
		_driver.quit();
	}

}
