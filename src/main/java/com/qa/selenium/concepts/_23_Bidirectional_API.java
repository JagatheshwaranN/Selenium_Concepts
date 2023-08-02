package com.qa.selenium.concepts;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _23_Bidirectional_API {

	private WebDriver driver;
	private ChromeDriver chromeDriver;

	@Test(priority = 1, enabled = false)
	private void registerBasicAuthentication() {
		browserSetup();
		Predicate<URI> uriPredicate = uriObject -> uriObject.getHost().contains("herokuapp.com");
		((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("admin", "admin"));
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//div[@class='example']//p")).getText();
		Assert.assertEquals(result, "Congratulations! You must have the proper credentials.");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 2, enabled = true)
	private void getDomMutation() throws InterruptedException {
		chromeBrowserSetup();
		AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
		CountDownLatch latch = new CountDownLatch(1);
		((HasLogEvents) chromeDriver).onLogEvent(mutation -> {
			seen.set((DomMutationEvent) mutation);
			latch.countDown();
		});
		chromeDriver.get("https://www.google.com");
		WebElement span = chromeDriver.findElement(By.xpath("//span[]"));
		((JavascriptExecutor) chromeDriver).executeScript("arguments[0].setAttribute('cheese', 'gouda');", span);
		//Assert.assertEquals(latch.await(10, TimeUnit.SECONDS),true );
		Assert.assertEquals(seen.get().getAttributeName(),"cheese");
		Assert.assertEquals(seen.get().getCurrentValue(), "gouda");
		waitForSomeTime();
		driver.close();
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	private WebDriver chromeBrowserSetup() {
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().window().maximize();
		return chromeDriver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
