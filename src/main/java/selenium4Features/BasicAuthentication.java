package selenium4Features;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

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

		driver = new ChromeDriver();
		devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
//		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//		header = new HashMap<>();
//		basicAuthentication = "Basic "
//				+ new String(new Base64().encode(String.format("%s:%s", username, password).getBytes()));
//		header.put("Authorization", basicAuthentication);
//		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		driver.close();

	}
}
