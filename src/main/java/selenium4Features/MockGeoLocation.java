package selenium4Features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class MockGeoLocation {

	private static WebDriver driver;
	private static Map<String, Object> location;
	private static Map<String, Object> coordinates;

	public static void main(String ar[]) throws InterruptedException {
		geoLocation1();
		geoLocation2();
	}

	public static void geoLocation1() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		location = new HashMap<String, Object>();
		location.put("latitude", 34.052235);
		location.put("longitude", -118.243683);
		location.put("accuracy", 1);
		((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", location);
		driver.get("https://oldnavy.gap.com/stores");
		Thread.sleep(5000);
		driver.close();
	}

	public static void geoLocation2() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		coordinates = Map.of("latitude", 30.3079823, "longitude", -97.893803, "accuracy", 1);
		((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
		driver.get("https://oldnavy.gap.com/stores");
		List<WebElement> addresses = driver.findElements(By.className("address"));
		Assert.assertTrue(addresses.size() > 0, "No addresses found");
		Assert.assertTrue(addresses.stream().allMatch(a -> a.getText().contains(", TX ")),
				"Some addresses listed are not in Texas");
		Thread.sleep(5000);
		driver.close();
	}
}
