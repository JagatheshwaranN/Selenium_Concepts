package com.qa.selenium.scenarios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

public class _06_Handle_BrokenImages {

	private WebDriver driver;

	@Test(priority = 1, enabled = true)
	private void findBrokenImages() throws URISyntaxException {
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

	private void verifyImageLink(String link) throws URISyntaxException {

		try {
			URI url = new URI(link);
			HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
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

	@Test(priority = 2, enabled = true)
	private void findBrokenImageType2() throws URISyntaxException {
		browserSetup();
		driver.get("https://demoqa.com/broken");
		WebElement image = driver.findElement(By.xpath("//img[@src='/images/Toolsqa_1.jpg']"));
		if(image.getAttribute("naturalWidth").equals("0")) {
			System.out.println("Image display Broken");
		} else {
			System.out.println("Image display Ok");
		}
		waitForSomeTime();
		driver.close();
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
