package com.qa.selenium.scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverConfiguration {

    static WebDriver browserSetup() {
        // Initialize and configure the WebDriver (ChromeDriver in this case).
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
