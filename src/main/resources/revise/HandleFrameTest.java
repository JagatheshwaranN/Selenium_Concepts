package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HandleFrameTest {

    static WebDriver driver;

    public static void main(String[] args) {
        switchToFrame();
        switchToDefaultContent();
        nestedFrame();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void switchToFrame() {
        browserLaunch();
        driver.get("https://letcode.in/frame");
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("firstFr")));
        driver.findElement(By.name("fname")).sendKeys("John");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

    private static void switchToDefaultContent() {
        browserLaunch();
        driver.get("https://letcode.in/frame");
        driver.switchTo().frame(0);
        driver.findElement(By.name("fname")).sendKeys("John");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("(//div[@class='hero-body'])[1]")).isDisplayed();
        driver.close();
    }

    private static void nestedFrame() {
        browserLaunch();
        driver.get("https://letcode.in/frame");
        driver.switchTo().frame(0);
        driver.findElement(By.name("fname")).sendKeys("John");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        WebElement frame = driver.findElement(By.xpath("//iframe[@src='innerFrame']"));
        driver.switchTo().frame(frame);
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.switchTo().parentFrame();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.findElement(By.name("lname")).sendKeys("Smith");
        driver.switchTo().defaultContent();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("(//div[@class='hero-body'])[1]")).isDisplayed();
        driver.close();
    }

}
