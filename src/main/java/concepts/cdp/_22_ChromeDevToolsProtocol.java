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
