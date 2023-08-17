package com.qa.selenium.concepts;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.LogInspector;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.log.LogLevel;
import org.openqa.selenium.bidi.log.StackTrace;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _25_BIDI_Console {

	private WebDriver driver;
	private ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = false)
	private void getConsoleLog() throws InterruptedException, ExecutionException, TimeoutException {
		browserSetup();
		try (LogInspector logInspector = new LogInspector(driver)) {
			CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<ConsoleLogEntry>();
			logInspector.onConsoleEntry(future::complete);
			driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
			driver.findElement(By.xpath("//button[@id='consoleLog']")).click();
			ConsoleLogEntry logEntry = future.get(5, TimeUnit.SECONDS);
			Assert.assertEquals(logEntry.getText(), "Hello, world!");
			Assert.assertEquals(logEntry.getArgs().size(), 1);
			Assert.assertEquals(logEntry.getType(), "console");
			Assert.assertEquals(logEntry.getMethod(), "log");
			Assert.assertNotNull(logEntry.getStackTrace().toString());
			waitForSomeTime();
			driver.close();
		}
	}
	
	@Test(priority = 2, enabled = false)
	private void getJavaScriptLog() throws InterruptedException, ExecutionException, TimeoutException {
		browserSetup();
		try (LogInspector logInspector = new LogInspector(driver)) {
			CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<JavascriptLogEntry>();
			logInspector.onJavaScriptLog(future::complete);
			driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
			driver.findElement(By.xpath("//button[@id='jsException']")).click();
			JavascriptLogEntry logEntry = future.get(5, TimeUnit.SECONDS);
			Assert.assertEquals(logEntry.getText(), "Error: Not working");
			Assert.assertEquals(logEntry.getType(), "javascript");
			Assert.assertEquals(logEntry.getLevel(), LogLevel.ERROR);
			waitForSomeTime();
			driver.close();
		}
	}
	
	@Test(priority = 3, enabled = false)
	private void getJavaScriptError() throws InterruptedException, ExecutionException, TimeoutException {
		browserSetup();
		try (LogInspector logInspector = new LogInspector(driver)) {
			CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<JavascriptLogEntry>();
			logInspector.onJavaScriptException(future::complete);
			driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
			driver.findElement(By.xpath("//button[@id='jsException']")).click();
			JavascriptLogEntry logEntry = future.get(5, TimeUnit.SECONDS);
			Assert.assertEquals(logEntry.getText(), "Error: Not working");
			Assert.assertEquals(logEntry.getType(), "javascript");
			waitForSomeTime();
			driver.close();
		}
	}
	
	
	@Test(priority = 4, enabled = false)
	private void getStackTraceForLog() throws InterruptedException, ExecutionException, TimeoutException {
		browserSetup();
		try (LogInspector logInspector = new LogInspector(driver)) {
			CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<JavascriptLogEntry>();
			logInspector.onJavaScriptException(future::complete);
			driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
			driver.findElement(By.xpath("//button[@id='logWithStacktrace']")).click();
			JavascriptLogEntry logEntry = future.get(5, TimeUnit.SECONDS);
			StackTrace stackTrace = logEntry.getStackTrace();
			Assert.assertNotNull(logEntry.getStackTrace());
			Assert.assertEquals(stackTrace.getCallFrames().size(), 3);
			waitForSomeTime();
			driver.close();
		}
	}
	
	@Test(priority = 5, enabled = true)
	private void getJavaScriptLogswithMultiConsumer() throws InterruptedException, ExecutionException, TimeoutException {
		browserSetup();
		try (LogInspector logInspector = new LogInspector(driver)) {
			CompletableFuture<JavascriptLogEntry> future1 = new CompletableFuture<JavascriptLogEntry>();
			logInspector.onJavaScriptException(future1::complete);
			CompletableFuture<JavascriptLogEntry> future2 = new CompletableFuture<JavascriptLogEntry>();
			logInspector.onJavaScriptException(future2::complete);
			driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
			driver.findElement(By.xpath("//button[@id='logWithStacktrace']")).click();
			JavascriptLogEntry logEntry1 = future1.get(5, TimeUnit.SECONDS);
			Assert.assertEquals(logEntry1.getText(), "Error: Not working");
			Assert.assertEquals(logEntry1.getType(), "javascript");			
			JavascriptLogEntry logEntry2 = future1.get(5, TimeUnit.SECONDS);
			Assert.assertEquals(logEntry2.getText(), "Error: Not working");
			Assert.assertEquals(logEntry2.getType(), "javascript");
			waitForSomeTime();
			driver.close();
		}
	}

	private WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.setCapability("webSocketUrl", true);
		driver = new ChromeDriver(chromeOptions);
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
