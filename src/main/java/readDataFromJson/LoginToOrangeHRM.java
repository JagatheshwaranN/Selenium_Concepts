package readDataFromJson;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginToOrangeHRM {

	WebDriver driver;
	JSONParser jsonParser;
	FileReader fileReader;
	Object obj;
	JSONObject userloginObj, users;
	JSONArray userloginArray;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterClass
	public void teardown() {
		driver.close();
	}

	@Test(dataProvider = "LoginData")
	public void login(String data) {
		String users[] = data.split(",");
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys(users[0]);
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(users[1]);
		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
	}

	@DataProvider(name = "LoginData")
	public String[] readJson() throws IOException, ParseException {
		jsonParser = new JSONParser();
		fileReader = new FileReader(System.getProperty("user.dir")+"//src//main//java//testData.json");
		obj = jsonParser.parse(fileReader);
		userloginObj = (JSONObject) obj;
		userloginArray = (JSONArray) userloginObj.get("userlogins");
		String[] userArray = new String[userloginArray.size()];
		for (int i = 0; i < userloginArray.size(); i++) {
			users = (JSONObject) userloginArray.get(i);
			String username = (String) users.get("username");
			String password = (String) users.get("password");
			userArray[i] = username + "," + password;
		}
		return userArray;
	}

}
