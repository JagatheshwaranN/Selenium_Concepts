package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class HandleKeyboardTest {

    protected static WebDriver driver;
    public static void main(String[] args) {
        keyboardActions();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void keyboardActions() {
        browserLaunch();
        driver.get("https://admin-demo.nopcommerce.com/login?ReturnUrl=%2Fadmin%2F");
        WebElement id = driver.findElement(By.id("Email"));
        id.sendKeys(Keys.chord(Keys.CONTROL +"a"));
        id.sendKeys(Keys.DELETE);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        new Actions(driver).keyDown(id, Keys.SHIFT)
                .sendKeys(id, "test").keyUp(id, Keys.SHIFT)
                .perform();
    }
}
