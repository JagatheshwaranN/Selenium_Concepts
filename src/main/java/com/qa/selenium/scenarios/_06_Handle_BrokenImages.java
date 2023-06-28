package com.qa.selenium.scenarios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class _06_Handle_BrokenImages {

	private WebDriver driver;

	@Test
	private void findBrokenImages() {
		browserSetup();
		driver.get("https://demoqa.com/broken");
		List<WebElement> images = driver.findElements(By.tagName("img"));
		for (WebElement image : images) {
			String href = image.getAttribute("src");
			verifyImageLink(href);
			try {
				boolean imageDisplay = (Boolean) ((JavascriptExecutor) driver).executeScript(
						"return (typeof arguments[0].naturalWidth !=\"undefined\" && arguments[0].naturalWidth > 0);",
						image);
				if (imageDisplay) {
					System.out.println("Image display Ok");
				} else {
					System.out.println("Image display Broken");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		waitForSomeTime();
		driver.close();
	}

	private void verifyImageLink(String link) {

		try {
			URL url = new URL(link);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			if (connection.getResponseCode() != 200) {
				System.out.println(link);
				System.out.println("Broken Image Http Request Status => " + connection.getResponseCode());
				System.out.println("Broken Image Http Request Body ===> " + connection.getResponseMessage());
			} else {
				System.out.println(link);
				System.out.println("Active Image Http Request Status => " + connection.getResponseCode());
				System.out.println("Active Image Http Request Body ===> " + connection.getResponseMessage());
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
