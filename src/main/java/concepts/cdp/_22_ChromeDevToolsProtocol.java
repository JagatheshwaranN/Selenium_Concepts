package concepts.cdp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.emulation.Emulation;
import org.openqa.selenium.devtools.v119.log.Log;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.ConnectionType;
import org.openqa.selenium.devtools.v119.network.model.Headers;
import org.openqa.selenium.devtools.v119.performance.Performance;
import org.openqa.selenium.devtools.v119.performance.model.Metric;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @resource https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests!
 * 
 * @author Jagatheshwaran N
 */
public class _22_ChromeDevToolsProtocol {

	private ChromeDriver driver;
	private DevTools devTools;
	private static Map<String, Object> location;
	private static Map<String, Object> coordinates;

	@Test(priority = 1, enabled = true)
	private void getPerformanceMetricsUsingCDP() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Performance.enable(Optional.empty()));

		// Validate all metrics
		List<Metric> metricList = devTools.send(Performance.getMetrics());
		driver.get("https://selenium.dev/");
		for (Metric metric : metricList) {
			System.out.println(metric.getName() + " = " + metric.getValue());
		}

		// Validate subset of metrics
		List<String> metricsName = metricList.stream().map(m -> m.getName()).collect(Collectors.toUnmodifiableList());
		devTools.send(Performance.disable());
		List<String> metricsToCheck = List.of("Timestamp", "Documents", "Frames", "JSEventListeners", "LayoutObjects",
				"MediaKeySessions", "Nodes", "Resources", "DomContentLoaded", "NavigationStart");
		metricsToCheck.forEach(
				metric -> System.out.println(metric + " : " + metricList.get(metricsName.indexOf(metric)).getValue()));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private void overrideDeviceModUsingCDP() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Emulation.setDeviceMetricsOverride(400, 900, 70, true, Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty()));
		driver.get("https://google.com/");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private void emulateGeoLocationUsingCDP() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(
				Emulation.setGeolocationOverride(Optional.of(36.778259), Optional.of(-119.417931), Optional.of(1)));
		devTools.send(Emulation.setTimezoneOverride("US/Central"));
		devTools.send(Emulation.setLocaleOverride(Optional.of("en_us")));
		driver.get("https://my-location.org/");
		waitForSomeTime();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private void emulateGeoLocationUsingCDPCommandApproach1() {
		browserSetup();
		location = new HashMap<String, Object>();
		location.put("latitude", 34.052235);
		location.put("longitude", -118.243683);
		location.put("accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", location);
		driver.get("https://oldnavy.gap.com/stores");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private void emulateGeoLocationUsingCDPCommandApproach2() {
		browserSetup();
		coordinates = Map.of("latitude", 30.3079823, "longitude", -97.893803, "accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
		driver.get("https://oldnavy.gap.com/stores");
		List<WebElement> addresses = driver.findElements(By.className("address"));
		Assert.assertTrue(addresses.size() > 0, "No addresses found");
		Assert.assertTrue(addresses.stream().allMatch(a -> a.getText().contains(", TX ")),
				"Some addresses listed are not in Texas");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private void emulateNetworkConnection() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(false, 200, 2048, 4096, Optional.of(ConnectionType.CELLULAR3G)));
		driver.get("https://google.com/");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private void getHttpRequests() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), request -> {
			System.out.println("Request URI 	 : " + request.getRequest().getUrl());
			System.out.println("Request Method 	 : " + request.getRequest().getMethod());
			System.out.println("Request Redirect : " + request.getRedirectResponse().isPresent());
		});
		driver.get("https://google.com/");
		devTools.send(Network.disable());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private void getConsoleLogs() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Log.enable());
		devTools.addListener(Log.entryAdded(), log -> {
			System.out.println("Log              : " + log.getText());
			System.out.println("Level            : " + log.getLevel());
			System.out.println("Category         : " + log.getCategory().isPresent());
			System.out.println("NetworkRequestId : " + log.getNetworkRequestId().isPresent());
			System.out.println("Source           : " + log.getSource());
			System.out.println("StackTrace       : " + log.getStackTrace().isPresent());
		});
		driver.get("https://google.com/");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private void setBasicAuthentication() {
		// String username = "admin";
		// String password = "admin";
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		Map<String, Object> header = new HashMap<>();
		// Base64 Conversion is not accepted in Header value.
		// String baseAuth = "Basic "
		// + new String(new Base64().encode(String.format("%s:%s", username,
		// password).getBytes()));
		header.put("Authorization", "Basic YWRtaW46YWRtaW4=");
		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		String result = driver.findElement(By.xpath("//div[@class='example']//p")).getText();
		Assert.assertEquals(result, "Congratulations! You must have the proper credentials.");
		devTools.send(Network.disable());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 10, enabled = true)
	private void javaScriptNotification() {
		browserSetup();
		devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.getDomains().javascript().pin("notification",
				"""
						window.onload = () => {
							if(!window.jQuery) {
							var jquery = document.createElement('script');
							jquery.type = 'text/javascript';
							jquery.src = 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js';
							document.getElementByTagName('head')[0].appendChild(jquery);
							} else {
								$ = window.jQuery;
							}
							$.getScript('https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.8/jquery.jgrowl.min.js')
							$('head').append('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.8/jquery.jgrowl.min.css" type="text/css" />');
						}

						function highlight(element) {
							let defaultBG = element.style.backgroundColor;
							let defaultOutline = element.style.outline;
							element.style.backgroundColor = '#FAF8B1';
							element.style.outline = '#09FA39 solid 3px';

							setTimeout(function() {
								element.style.backgroundColor = defaultBG;
								element.style.outline = defaultOutline;
								}, 1000);
						}

						""");
		driver.get("https://todomvc.com/examples/backbone/");
		enterTask("Clean the House", driver);
		enterTask("Clean the Car", driver);
		waitForSomeTime();
		driver.close();
	}

	private void enterTask(String task, WebDriver driver) {
		WebElement taskUpdateBar = driver.findElement(By.xpath("//input[@class='new-todo']"));
		((JavascriptExecutor) driver).executeScript("highlight(arguments[0])", taskUpdateBar);
		taskUpdateBar.sendKeys(task);
		new Actions(driver).click(taskUpdateBar).sendKeys(Keys.ENTER).perform();
		generateGrowlMessage(task, driver);
	}

	private void generateGrowlMessage(String message, WebDriver driver) {
		((JavascriptExecutor) driver).executeScript(String.format("$.jGrowl('%s', { header: 'Important!'});", message));
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
