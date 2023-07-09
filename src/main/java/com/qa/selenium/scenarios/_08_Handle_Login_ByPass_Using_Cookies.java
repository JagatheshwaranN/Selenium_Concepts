package com.qa.selenium.scenarios;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.Test;

public class _08_Handle_Login_ByPass_Using_Cookies {

	private WebDriver driver;
	private WebStorage webStorage;

	@Test(priority = 1, enabled = false)
	private void loginOnceToCaptureSessionDetails() throws IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		webStorage = (WebStorage) new Augmenter().augment(driver);

		driver.get("https://admin-demo.nopcommerce.com/");
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("admin@yourstore.com");
		driver.findElement(By.id("Password")).clear();
		driver.findElement(By.id("Password")).sendKeys("admin");
		driver.findElement(By.xpath("//button[contains(@class,'login-button')]")).click();
		waitForSomeTime();
		driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
		storeSessionFile("nopcommerce", "admin@yourstore.com");
	}

	@Test(priority = 2, enabled = false)
	private void loginByPassUsingSessionData() throws IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		webStorage = (WebStorage) new Augmenter().augment(driver);
		driver.get("https://admin-demo.nopcommerce.com/");
		usePreviousLoggedInSessionDetails("nopcommerce");
		driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
		waitForSomeTime();
		driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
		driver.quit();
	}
	
	@Test(priority = 3, enabled = true)
	private void loginByPassUsingCookies() throws IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		webStorage = (WebStorage) new Augmenter().augment(driver);
		driver.get("https://admin-demo.nopcommerce.com/");
		
		JSONObject previousSession = new JSONObject();
		previousSession.put("path","/");
		previousSession.put("domain","admin-demo.nopcommerce.com");
		previousSession.put("name",".Nop.Authentication");
		previousSession.put("isHttpOnly",true);
		previousSession.put("isSecure",true);
		previousSession.put("expiry","Tue Jul 09 16:27:34 IST 2024");
		previousSession.put("value","CfDJ8JTcaVVzbwZIo9QJm7k7-zxjR3n_M7wf4Q0x5gOiGir9zLMsuEtj54FCwpxGUim9CpTgD8yXYoSL8oxVQlGLuGtIeZ0MsmzF9OTpFAnx9uCgo8Ch7tBOI0TuV6r0cH0-N4tcwkoQHiPfpmzYTKQMbEUWTO9FzjqvdGDEBRPoRyKzPpW1WLWyMu6MZYl79lOjYjbXqhCIozXivDWzPPWDlM_C4OfF9QCw_9R0XCrl3-B-jCqg5roOimQAu8NrWwdhHngH1dKFGK5zeO1Rbl6XhRggTb0X2OgddyrV9Vp1J1CDeCzzh2tLLkOkgh00sLALHqw22y99Nbam3jFnBldXuA7bIyDu9M8jnsAqYFO_IekXc5MkdCkWxtVnwW6ETw9cE-JjrJzNUIZgDolOStxtjAvCyqefmRRxSnaSaCdTk227noqTX4Z8R41KOuZGhsNnc9boJzSko4pyhyvlb3MdtpaA5t5TK-s2lcaso4ORT4frtVqcrJUssFwhUy_BOfOMAEoXvuy6qbXYAb-Kpup9LOmv_wqgmCpUC2gtO0c2ZG5K48W9RZypmvnIuo3h2CdzvQ");
		setCookies(previousSession);
		driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
		waitForSomeTime();
		driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
		driver.quit();
	}

	private JSONArray getCookiesData() {

		JSONArray cookies = new JSONArray();
		driver.manage().getCookies().stream().forEach(cookie -> {
			JSONObject cookieJsonObject = new JSONObject();
			cookieJsonObject.put("name", cookie.getName());
			cookieJsonObject.put("value", cookie.getValue());
			cookieJsonObject.put("path", cookie.getPath());
			cookieJsonObject.put("domain", cookie.getDomain());
			cookieJsonObject.put("expiry", cookie.getExpiry());
			cookieJsonObject.put("isSecure", cookie.isSecure());
			cookieJsonObject.put("isHttpOnly", cookie.isHttpOnly());
			cookies.put(cookieJsonObject);
		});

		return cookies;
	}

	private JSONObject getLocalStorageData() {
		LocalStorage localStorage = webStorage.getLocalStorage();
		JSONObject localStorageJsonObject = new JSONObject();
		localStorageJsonObject.keySet().stream()
				.forEach(locStore -> localStorageJsonObject.put(locStore, localStorage.getItem(locStore)));
		return localStorageJsonObject;
	}

	private JSONObject getSessionStorageData() {
		SessionStorage sessionStorage = webStorage.getSessionStorage();
		JSONObject sessionStorageJsonObject = new JSONObject();
		sessionStorageJsonObject.keySet().stream()
				.forEach(locStore -> sessionStorageJsonObject.put(locStore, sessionStorage.getItem(locStore)));
		return sessionStorageJsonObject;
	}

	private JSONObject getSessionData() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("session_storage", getSessionStorageData());
		jsonObject.put("local_storage", getLocalStorageData());
		jsonObject.put("cookies", getCookiesData());
		return jsonObject;
	}

	public void storeSessionFile(String fileName, String userName) throws IOException {
		if (Files.exists(Paths.get(System.getProperty("user.dir") + "/" + fileName + ".json"))) {
			Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/" + fileName + ".json"));
		}

		JSONObject sessionObject = new JSONObject();
		sessionObject.put("username", userName);
		sessionObject.put("createdAt", LocalDateTime.now());
		sessionObject.put("session_data", getSessionData());
		System.out.println("JSON Object - Session Data : " + sessionObject);
		writeJSONObjectToFile(sessionObject, "./" + fileName + ".json");
	}

	public void applyCookiesToCurrentSession(JSONObject jsonObject) {
		JSONArray cookiesArray = jsonObject.getJSONArray("cookies");
		for (int i = 0; i < cookiesArray.length(); i++) {
			JSONObject cookies = cookiesArray.getJSONObject(i);
			Cookie cookie = new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
					.path(cookies.get("path").toString()).domain(cookies.get("domain").toString())
					.expiresOn(!cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
					.isSecure((Boolean) cookies.get("isSecure")).isHttpOnly((Boolean) cookies.get("isHttpOnly"))
					.build();
			driver.manage().addCookie(cookie);
		}
	}

	private void applyLocalStorage(JSONObject sessionData) {
		JSONObject localStorageObject = sessionData.getJSONObject("local_storage");
		localStorageObject.keySet().stream().forEach(localSotrage -> {
			webStorage.getLocalStorage().setItem(localSotrage, localStorageObject.get(localSotrage).toString());
		});
	}

	private void applySessionStorage(JSONObject sessionData) {
		JSONObject sessionStorageObject = sessionData.getJSONObject("session_storage");
		sessionStorageObject.keySet().stream().forEach(sessionSotrage -> {
			webStorage.getSessionStorage().setItem(sessionSotrage, sessionStorageObject.get(sessionSotrage).toString());
		});
	}

	private void usePreviousLoggedInSessionDetails(String fileName) {
		driver.manage().getCookies().clear();
		JSONObject jsonObject = null;
		jsonObject = parseJSONFile(System.getProperty("user.dir") + "/" + fileName + ".json");
		JSONObject sessionData = jsonObject.getJSONObject("session_data");
		applyCookiesToCurrentSession(sessionData);
		applyLocalStorage(sessionData);
		applySessionStorage(sessionData);
		waitForSomeTime();
		driver.navigate().refresh();

	}

	private void setCookies(JSONObject cookies) {
		System.out.println("========== Deletion of all existing cookies ===========");
		driver.manage().deleteAllCookies();

		Cookie cookie = new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
				.path(cookies.get("path").toString()).domain(cookies.get("domain").toString())
				.expiresOn(!cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
				.isSecure((Boolean) cookies.get("isSecure")).isHttpOnly((Boolean) cookies.get("isHttpOnly")).build();
		driver.manage().addCookie(cookie);
		System.out.println("Cookies added successfully");
		driver.navigate().refresh();
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
		System.out.println("Cookies added successfully");
		driver.navigate().refresh();
		waitForSomeTime();
	}

	private void writeJSONObjectToFile(JSONObject sessionObject, String filePath) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(sessionObject.toString());
		fileWriter.close();
		System.out.println("JSON object successfully written into the file");
	}

	private JSONObject parseJSONFile(String fileName) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		assert content != null;
		return new JSONObject(content);
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
