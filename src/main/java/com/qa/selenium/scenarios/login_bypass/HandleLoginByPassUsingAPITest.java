package com.qa.selenium.scenarios.login_bypass;

import java.util.Date;
import java.util.Map;

import com.qa.selenium.scenarios.DriverConfiguration;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HandleLoginByPassUsingAPITest {

	public WebDriver driver;

	/**
	 * This approach will work ONLY when the API Post call is having status as 200
	 * OK. It will not work when the status is 302 and redirect exists.
	 * <p>
	 * Note: This approach is not working as of now.
	 */
	@Test(priority = 1)
	public void loginByPassUsingAPI() {
		driver = DriverConfiguration.browserSetup();
		String domain = "www.99.co";
		driver.get("https://www.99.co");
		String URI = "https://www.99.co/api/v1/web/accounts/login";
		Map<String, String> cookies = postCallGetCookies(payload(), URI);
		byPassLoginUsingCookies(cookies, domain);
		driver.findElement(By.xpath("//a[@data-cy='navProfile']")).isDisplayed();
		driver.quit();
	}

	private JSONObject payload(){
		JSONObject payLoad = new JSONObject();
		payLoad.put("email", "woref59486@msback.com");
		payLoad.put("password", "Welcome@123");
		return payLoad;
	}

	private void byPassLoginUsingCookies(Map<String, String> cookies, String domain) {
		System.out.println("========== Deletion of all existing cookies ===========");
		driver.manage().deleteAllCookies();
		cookies.keySet().forEach(cookie -> {
			String value = cookies.get(cookie);
			Cookie cookieData = new Cookie.Builder(cookie, value).path("/").domain(domain)
					.expiresOn(new Date(new Date().getTime() + 3600 * 1000)).isSecure(false).isHttpOnly(false).build();
			driver.manage().addCookie(cookieData);
		});
		System.out.println("Cookies : " + cookies);
		System.out.println("Cookies added successfully");
		driver.navigate().refresh();
		waitForSomeTime();
	}

	private Map<String, String> postCallGetCookies(JSONObject payLoad, String Uri) {
		return RestAssured.given().baseUri(Uri).accept(ContentType.JSON).body(payLoad.toString()).when().post()
				.getCookies();
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
