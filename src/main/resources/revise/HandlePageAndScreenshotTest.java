package revise;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.print.PrintOptions;

import java.io.File;
import java.io.IOException;


public class HandlePageAndScreenshotTest {

    protected static WebDriver driver;

    public static void main(String[] args) throws IOException {
        pageActionsTest();
        screenShotTest();
        printPage();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void pageActionsTest() {
        browserLaunch();
        driver.get("https://selenium.dev/");
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getPageSource());
    }

    private static void screenShotTest() throws IOException {
        browserLaunch();
        driver.get("https://selenium.dev/");
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("./viewport.png"));
        File elementSrc = driver.findElement(By.id("Layer_1")).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(elementSrc, new File("./element.png"));
        driver.close();
    }

    private static void printPage() throws IOException {
        browserLaunch();
        driver.get("https://selenium.dev/");
        PrintOptions printOptions = new PrintOptions();
        Pdf pdf = ((PrintsPage) driver).print(printOptions);
        String content = pdf.getContent();
        Files.write(OutputType.BYTES.convertFromBase64Png(content), new File("./print.pdf"));
        driver.close();
    }
}
