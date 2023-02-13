package selenium4Features;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.network.Network;
import org.openqa.selenium.devtools.v91.network.model.Headers;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class BasicAuthentication {

	private static WebDriver driver;
	private static Map<String, Object> header;
	private static String basicAuthentication;
	private static DevTools devTools;
	private static String username = "admin";
	private static String password = "admin";

	public static void main(String ar[]) {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
//		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//		header = new HashMap<>();
		basicAuthentication = "Basic "
				+ new String(new Base64().encode(String.format("%s:%s", username, password).getBytes()));
		header.put("Authorization", basicAuthentication);
		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		driver.close();
		
	}
}
