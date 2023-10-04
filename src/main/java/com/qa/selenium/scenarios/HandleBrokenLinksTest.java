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

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

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

    @Test
    public void findBrokenLinks() throws URISyntaxException {
        // Navigate to the website "https://bstackdemo.com/"
        driver.get("https://bstackdemo.com/");

        // Find all the anchor (<a>) elements on the web page and store them in a list named 'links'
        List<WebElement> links = driver.findElements(By.tagName("a"));

        // Iterate through each 'link' WebElement in the 'links' list
        for (WebElement link : links) {
            // Get the value of the "href" attribute for the current 'link' WebElement
            String href = link.getAttribute("href");

            // Call a 'verifyLink' method to verify the validity of the 'href' attribute value
            // (The implementation of 'verifyLink' is assumed to be elsewhere in the code)
            verifyLink(href);
        }
    }

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

            // Check if the 'responseCode' is not equal to 200
            if (responseCode != 200) {
                // If the response code is not 200, it indicates a broken link
                System.out.println("Broken Link Http Request Status => " + responseCode);
                System.out.println("Broken Link Http Request Body ===> " + connection.getResponseMessage());
            } else {
                // If the response code is 200, it indicates an active link
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
