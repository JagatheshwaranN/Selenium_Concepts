package com.qa.selenium.scenarios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class HandleBrokenLinksTest {

    // Declare a WebDriver instance.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver and maximize the browser window before each test.
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void findBrokenLinks() throws URISyntaxException {
        // Open the web page containing links to check.
        driver.get("https://bstackdemo.com/");

        // Find all anchor elements on the page.
        List<WebElement> links = driver.findElements(By.tagName("a"));

        // Loop through each link and verify if it is broken.
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            verifyLink(href);
        }
    }

    @AfterMethod
    public void tearDown() {
        // Close the WebDriver after each test.
        driver.quit();
    }

    // Verify if a link is broken or active.
    private void verifyLink(String link) throws URISyntaxException {
        try {
            // Parse the link into a URI.
            URI url = new URI(link);

            // Create an HTTP connection by opening a connection to the URL.
            HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();

            // Set the connection timeout to 5000 milliseconds (5 seconds).
            connection.setConnectTimeout(5000);

            // Connect to the URL to initiate the HTTP request.
            connection.connect();

            // Check the HTTP response code to determine if the link is broken or active.
            int responseCode = connection.getResponseCode();

            // Print the link
            System.out.println(link);

            // Verify the HTTP response code is equal to 200 or not.
            if (responseCode != 200) {
                System.out.println("Broken Link Http Request Status => " + responseCode);
                System.out.println("Broken Link Http Request Body ===> " + connection.getResponseMessage());
            } else {
                System.out.println("Active Link Http Request Status => " + responseCode);
                System.out.println("Active Link Http Request Body ===> " + connection.getResponseMessage());
            }
        } catch (MalformedURLException ex) {
            // Handle MalformedURLException by printing the stack trace.
            ex.printStackTrace();
        } catch (IOException ex) {
            // Handle IOException by printing the stack trace.
            ex.printStackTrace();
        }
    }

}
