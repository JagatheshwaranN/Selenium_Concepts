package com.qa.selenium.scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

public class DriverConfiguration {

    static WebDriver browserSetup() {
        // Initialize and configure the WebDriver (ChromeDriver in this case).
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    static WebDriver edgeBrowserSetup() {
        // Initialize and configure the WebDriver (EdgeDriver in this case).
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    static WebDriver fireFoxBrowserSetup() {
        // Initialize and configure the WebDriver (FirefoxDriver in this case).
        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile profileInstance = profile.getProfile("testAutomation");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profileInstance);
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();
        return driver;
    }

}
