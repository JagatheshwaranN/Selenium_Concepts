package com.qa.selenium.scenarios;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleProxyTest {

    public WebDriver driver1;
    public WebDriver driver2;
    public WebDriver driver3;
    public Map<String, Object> header;
    public String basicAuthentication;
    public DevTools devTools;
    public String username = "admin";
    public String password = "admin";

    @BeforeMethod
    public void setUp() {
        // Initialize the WebDriver and open the desired URL before each test.
        driver1 = DriverConfiguration.browserSetup();
        driver1.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        driver2 = DriverConfiguration.edgeBrowserSetup();
        driver2.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        driver3 = DriverConfiguration.fireFoxBrowserSetup();
        driver3.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
    }

    @Test(priority = 1)
    public void testProxyAuthentication() {
        // Find the web element with the specified XPath and get its text.
        String result = driver1.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();

        // Assert that the retrieved text matches the expected value ("Basic Auth").
        Assert.assertEquals(result, "Basic Auth");
    }


    @AfterMethod
    public void tearDown() {
        // Quit the WebDriver after each test.
        if (driver1 != null) {
            driver1.quit();
        }
        if (driver2 != null) {
            driver2.quit();
        }
        if (driver3 != null) {
            driver3.quit();
        }
    }

    @Test(priority = 2, enabled = false)
    public void testProxyUsingChromeDevTool() {
        // Uncomment the setup method for Chrome browser if needed.
        // chromeBrowserSetup();

        // Initialize the DevTools for Chrome.
        devTools = ((ChromeDriver) driver1).getDevTools();
        devTools.createSession();

        // Enable network interception in Chrome DevTools.
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Prepare HTTP headers for Basic Authentication.
        header = new HashMap<>();
        basicAuthentication = "Basic " + new String(Base64.getEncoder()
                .encode(String.format("%s:%s", username, password).getBytes()));
        header.put("Authorization", basicAuthentication);

        // Set extra HTTP headers with Basic Authentication.
        devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));

        // Find a web element and get its text.
        String result = driver1.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();

        // Assert that the retrieved text matches the expected value ("Basic Auth").
        Assert.assertEquals(result, "Basic Auth");
    }

}
