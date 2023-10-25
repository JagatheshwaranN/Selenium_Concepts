package com.qa.selenium.scenarios.login_bypass;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

public class HandleLoginByPassUsingCookiesTest {

    public WebDriver driver;
    public WebStorage webStorage;

    @Test(priority = 3, enabled = true)
    public void loginByPassUsingCookies() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        webStorage = (WebStorage) new Augmenter().augment(driver);
        driver.get("https://admin-demo.nopcommerce.com/");

        JSONObject previousSession = new JSONObject();
        previousSession.put("path", "/");
        previousSession.put("domain", "admin-demo.nopcommerce.com");
        previousSession.put("name", ".Nop.Authentication");
        previousSession.put("isHttpOnly", true);
        previousSession.put("isSecure", true);
        previousSession.put("expiry", "Tue Jul 09 16:27:34 IST 2024");
        previousSession.put("value",
                "CfDJ8JTcaVVzbwZIo9QJm7k7-zxjR3n_M7wf4Q0x5gOiGir9zLMsuEtj54FCwpxGUim9CpTgD8yXYoSL8oxVQlGLuGtIeZ0MsmzF9OTpFAnx9uCgo8Ch7tBOI0TuV6r0cH0-N4tcwkoQHiPfpmzYTKQMbEUWTO9FzjqvdGDEBRPoRyKzPpW1WLWyMu6MZYl79lOjYjbXqhCIozXivDWzPPWDlM_C4OfF9QCw_9R0XCrl3-B-jCqg5roOimQAu8NrWwdhHngH1dKFGK5zeO1Rbl6XhRggTb0X2OgddyrV9Vp1J1CDeCzzh2tLLkOkgh00sLALHqw22y99Nbam3jFnBldXuA7bIyDu9M8jnsAqYFO_IekXc5MkdCkWxtVnwW6ETw9cE-JjrJzNUIZgDolOStxtjAvCyqefmRRxSnaSaCdTk227noqTX4Z8R41KOuZGhsNnc9boJzSko4pyhyvlb3MdtpaA5t5TK-s2lcaso4ORT4frtVqcrJUssFwhUy_BOfOMAEoXvuy6qbXYAb-Kpup9LOmv_wqgmCpUC2gtO0c2ZG5K48W9RZypmvnIuo3h2CdzvQ");
        setCookies(previousSession);
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
        waitForSomeTime();
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
        driver.quit();
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

    private void waitForSomeTime() {
        try {
            Thread.sleep(3);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }



}
