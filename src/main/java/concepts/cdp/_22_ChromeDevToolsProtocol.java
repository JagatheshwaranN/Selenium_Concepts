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
