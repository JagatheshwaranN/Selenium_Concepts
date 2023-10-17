package com.qa.selenium.scenarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HandleDataFromXmlFileTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Specifies the path to the XML file containing the test data.
    // The path is constructed dynamically using the 'user.dir' property.
    // 'user.dir' refers to the current working directory where the Java process was launched.
    // The path points to the 'testData.json' file in the 'supportFiles' directory.
    private static final String XML_FILE_PATH = System.getProperty("user.dir")
            + "/src/main/resources/supportFiles/testData.xml";

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(dataProvider = "loginData")
    public void readDataFromXMLAndUseInAutomationFLow(String email, String password) {
        // Navigate to the NopCommerce website by opening the specified URL.
        driver.get("https://admin-demo.nopcommerce.com/login");

        // Locating the email input field by its ID
        WebElement email_element = driver.findElement(By.id("Email"));

        // Locating the password input field by its ID
        WebElement password_element = driver.findElement(By.id("Password"));

        // Clearing any existing content in the email input field
        email_element.clear();

        // Entering the email value obtained from the 'data' map into the email input field
        email_element.sendKeys(email);

        // Clearing any existing content in the password input field
        password_element.clear();

        // Entering the password value obtained from the 'data' map into the password input field
        password_element.sendKeys(password);

        // Click on the login button to proceed with the login attempt.
        driver.findElement(By.cssSelector(".button-1.login-button")).click();

        // Assert the title of the resulting page after login to verify successful login.
        Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
    }

    @DataProvider(name = "loginData")
    private Object[][] getDataFromXmlFile() {
        // Creating an instance of DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // Initialize a DocumentBuilder instance.
        DocumentBuilder documentBuilder;

        // Initialize a Document instance.
        Document document = null;

        try {
            // Create a new DocumentBuilder using the DocumentBuilderFactory.
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Parsing the XML file using the DocumentBuilder
            document = documentBuilder.parse(new File(XML_FILE_PATH));

        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Handle any exceptions that occur during XML data processing.
            handleJsonDataException(e);
        }

        // Ensure that the document is not null before proceeding.
        assert document != null;

        // Normalizing the XML structure
        document.getDocumentElement().normalize();
		/*
			System.out.println("Root Element : " + document.getDocumentElement().getNodeName());
		*/
        // Obtaining the list of elements with the tag name "userlogin"
        NodeList nodeList = document.getElementsByTagName("userlogin");

        // Initializing a 2D array to store the user data retrieved from the XML file
        Object[][] userData = new Object[nodeList.getLength()][2];

        // Iterating through the list of user data nodes
        for (int i = 0; i < nodeList.getLength(); i++) {
            // Getting the current node from the NodeList based on the current index 'i'
            Node node = nodeList.item(i);

            // System.out.println("Node Name : " + node.getNodeName());

            // Checking if the node type is an element node
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Casting the current node to an Element type
                Element element = (Element) node;

                // Fetching the email value from the 'email' tag inside the current node
                String email = element.getElementsByTagName("email").item(0).getTextContent();

                // Fetching the password value from the 'password' tag inside the current node
                String password = element.getElementsByTagName("password").item(0).getTextContent();

                // Storing the email and password data into the 2D array
                userData[i][0] = email;
                userData[i][1] = password;
            }
        }
        // Returning the 2D array containing the extracted user data from the XML
        return userData;
    }

    private void handleJsonDataException(Exception e) {
        // Handle various types of exceptions that can occur during XML data processing.
        if (e instanceof FileNotFoundException) {
            System.err.println("The XML file was not found.");
        } else if (e instanceof IOException) {
            System.err.println("An I/O error occurred while reading the XML file.");
        } else if (e instanceof ParseException) {
            System.err.println("There was an error parsing the XML data.");
        } else {
            // Print the stack trace if the exception type is unknown.
            e.printStackTrace();
        }
    }

}
