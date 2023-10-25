package com.qa.selenium.scenarios.login_bypass;

import com.qa.selenium.scenarios.DriverConfiguration;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.Date;

public class HandleLoginByPassUsingCookiesTest {

    public WebDriver driver;

    @Test(priority = 1)
    public void loginByPassUsingCookies() {
        driver = DriverConfiguration.browserSetup();
        driver.manage().window().maximize();
        driver.get("https://admin-demo.nopcommerce.com/");
        setCookies(previousSessionDetail());
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
        waitForSomeTime();
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
        driver.quit();
    }

    private JSONObject previousSessionDetail(){
        JSONObject previousSession = new JSONObject();
        previousSession.put("path", "/");
        previousSession.put("domain", "admin-demo.nopcommerce.com");
        previousSession.put("name", ".Nop.Authentication");
        previousSession.put("isHttpOnly", true);
        previousSession.put("isSecure", true);
        previousSession.put("expiry", "Tue Jul 09 16:27:34 IST 2024");
        previousSession.put("value",
                "CfDJ8GzHBbiRzehKoRKEgRkEcjxx2Ot0gXFOzrK0e1vQBaoPLpszfPtRBu5QgBmrNQkve2JDHkWrjt8kl_EklzA4kyQnnQJpOSmBO8Ma-5vRS9oDo7KZswTaDgDFeSPVYKFkbt219HONhp8kRsS0bMOcHTj8b92Lke8AoU5fMnYnLULaxwE8YFAWQ04NTZZgw84t4XwUx8-rwIgioaZGJVP7UhsDp99jhpkqMayNHnO6MCebZqXTyk9uTNUM1YaJW-vhRhLgAW2tikJGenhNQiIz0xMY0KDsRGXY9hAGnEz-GQCf8KBUD9vDiMVpCfTPShYHWfe_HvsKIJ42WTVUSnMgi3RV3bHl93ZjUYr7XCjb7jLHj73axqoKxdXKBha0Z3gUP4lTC6rzUx-jtUFTDZl3qdec0cgL4dwsr7PUda02Qn8Go__uYt_JGIeCubqJkthQotHzciqhQRBu9-cBx6x725eWNnVPIvR21ij5ZH0jnyZZTFU0W2IyUMBtAlh6fvHz8dXz1QOSKJxNntrzbzNs_L-rmdq8QGtE-vCC7eTrVbbYf2QxsiXahJlv3810ZkdHQQ");
        return previousSession;
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
