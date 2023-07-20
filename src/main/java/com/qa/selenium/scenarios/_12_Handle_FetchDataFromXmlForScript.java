package com.qa.selenium.scenarios;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class _12_Handle_FetchDataFromXmlForScript {

	private WebDriver driver;

	@Test
	private void getDataFromXMLFile() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(
					new File(System.getProperty("user.dir") + "//src//main//resources//supportFiles//testData.xml"));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression xPathExpression = null;
		try {
			xPathExpression = xPath.compile("//test//userlogins");
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] userData = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			int emailId = currentItem.getChildNodes().getLength();
			System.out.println(emailId);
		}
		// return null;
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
