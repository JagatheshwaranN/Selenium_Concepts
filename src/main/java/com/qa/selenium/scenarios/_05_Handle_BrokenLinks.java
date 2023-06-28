package com.qa.selenium.scenarios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class _05_Handle_BrokenLinks {

	private WebDriver driver;

	@Test
	private void findBrokenLinks() {
		browserSetup();
		driver.get("https://demoqa.com/broken");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement link : links) {
			String href = link.getAttribute("href");
			verifyLink(href);
		}
		waitForSomeTime();
		driver.close();
	}

	private void verifyLink(String link) {

		try {
			URL url = new URL(link);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			if (connection.getResponseCode() != 200) {
				System.out.println(link);
				System.out.println("Broken Link Http Request Status => " + connection.getResponseCode());
				System.out.println("Broken Link Http Request Body ===> " + connection.getResponseMessage());
			} else {
				System.out.println(link);
				System.out.println("Active Link Http Request Status => " + connection.getResponseCode());
				System.out.println("Active Link Http Request Body ===> " + connection.getResponseMessage());
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
