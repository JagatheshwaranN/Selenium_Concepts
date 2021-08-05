package selenium4Features;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockGeoLocation {

	public static void main(String ar[]) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		Map<String, Object> location = new HashMap<String, Object>();
		location.put("latitude", 34.052235);
		location.put("longitude", -118.243683);
		location.put("accuracy",1);
		((ChromeDriver)driver).executeCdpCommand("Emulation.setGeolocationOverride", location);
		driver.get("https://oldnavy.gap.com/stores");
		driver.close();
	}
}
