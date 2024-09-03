package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HandleCookieTest {

    static protected WebDriver driver;
    public static void main(String[] args) {
        handleCookie();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void handleCookie() {
        browserLaunch();
        driver.get("https://www.example.com/");
        driver.manage().window().maximize();
        Cookie cookie = new Cookie("Test1", "24680");
        driver.manage().addCookie(cookie);
        Cookie cookie1 = new Cookie.Builder("Test2","13579").build();
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(new Cookie("Test3", "12345"));
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        Set<Cookie> cookieObj = driver.manage().getCookies();
        System.out.println(cookieObj);
        Cookie testCookie = driver.manage().getCookieNamed("Test1");
        System.out.println(testCookie);
        driver.manage().deleteCookie(cookie1);
        driver.manage().deleteCookieNamed("Test1");
        driver.manage().deleteAllCookies();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        Set<Cookie> cookieObj1 = driver.manage().getCookies();
        System.out.println(cookieObj1);
        driver.close();
    }


}
