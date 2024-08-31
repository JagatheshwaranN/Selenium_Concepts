package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class HandleNavigationTest {

    static WebDriver driver;

    public static void main(String[] args) {
        navigateOperations();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void navigateOperations() {
        browserLaunch();
        driver.navigate().to("https://www.selenium.dev/");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.get("https://letcode.in/frame");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.navigate().back();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.navigate().forward();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.navigate().refresh();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

}
