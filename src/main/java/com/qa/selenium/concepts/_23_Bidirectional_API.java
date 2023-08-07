package com.qa.selenium.concepts;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.v113.fetch.Fetch;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.BlockedReason;
import org.openqa.selenium.devtools.v114.network.model.ResourceType;
import org.openqa.selenium.devtools.v114.security.Security;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;

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

	@Test(priority = 2, enabled = false)
	private void getDomMutation() throws InterruptedException {
		chromeBrowserSetup();
		List<DomMutationEvent> mutationList = Collections.synchronizedList(new ArrayList<>());
		((HasLogEvents) chromeDriver).onLogEvent(domMutation(mutation -> {
			mutationList.add(mutation);
		}));
		chromeDriver.get("https://www.gps-coordinates.net/");
		WebElement latitude = chromeDriver.findElement(By.id("latitude"));
		WebElement longitude = chromeDriver.findElement(By.id("longitude"));
		latitude.clear();
		longitude.clear();
		latitude.sendKeys("52.520008");
		longitude.sendKeys("13.404954");
		chromeDriver.findElement(By.xpath("(//button[text()='Get Address'])[1]")).click();
		for (var mutation : mutationList) {
			var attributeName = Optional.ofNullable(mutation.getAttributeName()).orElse("NAN");
			var oldValue = Optional.ofNullable(mutation.getOldValue()).orElse("NAN");
			var currentValue = Optional.ofNullable(mutation.getCurrentValue()).orElse("NAN");
			var element = Optional.ofNullable(mutation.getElement().toString()).orElse("NAN");
			System.out.println(String.format("\nAttribute Name: %s\n Old Value: %s\n New Value: %s\n Element: %s\n",
					attributeName, oldValue, currentValue, element));
		}
		waitForSomeTime();
		chromeDriver.close();
	}

	@Test(priority = 3, enabled = false)
	private void handleJSException() {
		chromeBrowserSetup();
		DevTools devTools = chromeDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		List<JavascriptException> jsExceptions = new ArrayList<>();
		Consumer<JavascriptException> addEntry = jsExceptions::add;
		devTools.getDomains().events().addJavascriptExceptionListener(addEntry);
		chromeDriver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/WebTable.html");
		WebElement link = chromeDriver.findElement(By.xpath("//div//a[text()='Canada']"));
		((JavascriptExecutor) chromeDriver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
				link, "onclick", "throw new Error('JavaScriptException Demo!')");
		link.click();
		for (JavascriptException jse : jsExceptions) {
			System.out.println("JS Exception Message : " + jse.getMessage());
			System.out.println("JS Exception SysInfo : " + jse.getSystemInformation());
			jse.printStackTrace();
		}
		waitForSomeTime();
		chromeDriver.close();
	}

	@Test(priority = 4, enabled = false)
	private void handleNetworkIntercept() {
		chromeBrowserSetup();
		try (NetworkInterceptor interceptor = new NetworkInterceptor(chromeDriver,
				Route.matching(req -> true)
						.to(() -> req -> new HttpResponse()
								.setStatus(200)
								.addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
								//.addHeader("Content-Type", "text/html; charset=utf-8")
								//.addHeader("Accept-Encoding", "gzip, deflate")
								.setContent(() -> new ByteArrayInputStream("Hello World".getBytes()))))) {
			chromeDriver.get("https://example.com/");
			String source = chromeDriver.getPageSource();
			Assert.assertTrue(source.contains("Hello World"));
		}
		waitForSomeTime();
		chromeDriver.close();
	}
	
	// Not working
	@Test(priority = 5, enabled = false)
	private void blackHolePatternTest() {
		chromeBrowserSetup();
		var devToolsDriver = (HasDevTools)chromeDriver;
		DevTools devTools = devToolsDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Security.setIgnoreCertificateErrors(true));
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.png","*.css")));
		devTools.addListener(Network.loadingFailed(), loadingFailed -> {
			if(loadingFailed.getType().equals(ResourceType.STYLESHEET)) {
				Assert.assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
			} else if(loadingFailed.getType().equals(ResourceType.IMAGE)) {
				Assert.assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
			}
		});
		devTools.send(Network.setBlockedURLs(List.of("https://demos.bellatrix.solutions/wp-content/uploads/2018/04/640px-Iridium-1_Launch_32312419215.jpg")));
		devTools.addListener(Network.loadingFailed(), loadingFailed ->{
			System.out.println("Blocking reason : "+loadingFailed.getBlockedReason().get());
		});
		chromeDriver.get("https://demos.bellatrix.solutions/wp-content/uploads/2018/04/640px-Iridium-1_Launch_32312419215.jpg");
		waitForSomeTime();
		chromeDriver.close();
	}
	
	@Test(priority = 6, enabled = true)
	private void mockAPIRequestTest() {
		chromeBrowserSetup();
		DevTools devTools = chromeDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			if(request.getRequest().getUrl().contains("https://demos.telerik.com/kendo-ui/service/Northwind.svc/Orders")) {
				String mockURL = request.getRequest().getUrl().replace("top=20", "top=5");
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockURL), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));				
			} else {
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
			}
		});
		chromeDriver.get("https://demos.telerik.com/aspnet-mvc/grid/odata");
		waitForSomeTime();
		chromeDriver.close();
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
