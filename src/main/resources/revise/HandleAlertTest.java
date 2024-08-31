package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HandleAlertTest {

    public static void main(String[] args) {
       handleAlertHappyPath();
       handleAlertNegativeScenario1();
       handleAlertNegativeScenario2();
       handleAlertNegativeScenario3();
    }

    public static void handleAlertHappyPath() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
        driver.close();
    }

    public static void handleAlertNegativeScenario1() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/inputs");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
        driver.close();
    }

    public static void handleAlertNegativeScenario2() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/inputs");
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
        driver.close();
    }

    public static void handleAlertNegativeScenario3() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        driver.findElement(By.id("result")).isDisplayed();
    }

}
