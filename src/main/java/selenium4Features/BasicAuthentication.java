package selenium4Features;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v87.network.Network;
import org.openqa.selenium.devtools.v87.network.model.Headers;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicAuthentication {

	private static String username = "admin";
	private static String password = "admin";

	public static void main(String ar[]) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		Map<String, Object> header = new HashMap<>();
		String basicAuthentication = "Basic "
				+ new String(new Base64().encode(String.format("%s:%s", username, password).getBytes()));
		header.put("Authorization", basicAuthentication);
		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		driver.close();
	}
}
