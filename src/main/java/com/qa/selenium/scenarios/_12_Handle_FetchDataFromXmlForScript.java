package com.qa.selenium.scenarios;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import junit.framework.Assert;

public class _12_Handle_FetchDataFromXmlForScript {

	private WebDriver driver;

	@Test(dataProvider = "loginData")
	private void readDataFromXMLAndUseInAutomationFLow(String[] data) {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement email = driver.findElement(By.id("Email"));
		WebElement password = driver.findElement(By.id("Password"));
		email.clear();
		email.sendKeys(data[0].split(",")[0]);
		password.clear();
		password.sendKeys(data[0].split(",")[1]);
		driver.findElement(By.cssSelector(".button-1.login-button")).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		waitForSomeTime();
		driver.close();
	}

	@DataProvider(name = "loginData")
	private String[] getDataFromXMLFile() throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder
				.parse(new File(System.getProperty("user.dir") + "//src//main//resources//supportFiles//testData.xml"));
		document.getDocumentElement().normalize();
		// System.out.println("Root Element : " +
		// document.getDocumentElement().getNodeName());
		NodeList nodeList = document.getElementsByTagName("userlogin");
		String[] userData = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			// System.out.println("Node Name : " + node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String emailId = element.getElementsByTagName("email").item(i).getTextContent();
				String password = element.getElementsByTagName("password").item(i).getTextContent();
				userData[0] = emailId + "," + password;
			}
		}
		return userData;
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
