package scenarios.captcha;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TextCaptchaTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Define the file path for the CAPTCHA screenshot
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\scenarios\\captcha\\captcha_screenshot.png";

    @BeforeMethod
    public void setUp() {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create ChromeOptions instance to configure the browser.
        ChromeOptions chromeOptions = new ChromeOptions();

        // Add an argument to disable browser notifications.
        chromeOptions.addArguments("--disable-notifications");

        // Initialize the WebDriver with the configured ChromeOptions.
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window.
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testTextCaptcha() {
        // Navigate to the IRCTC website
        driver.get("https://www.irctc.co.in/nget/train-search");

        // Set up WebDriverWait with a specified timeout
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        // Wait for a Login link to be visible and click it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='LOGIN']"))).click();

        // Wait for login modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-content']")));

        // Locate the Captcha image element and wait for it to be visible
        WebElement captcha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='captcha-img']")));

        // Capture the Captcha image as a temporary file
        File captchaImgFile = captcha.getScreenshotAs(OutputType.FILE);

        // Declaring a variable to store the extracted captcha content
        String captchaContent;

        try {
            // Copy the temporary file to the designated path
            FileHandler.copy(captchaImgFile, new File(FILE_PATH)); // Copying the captcha image to a specific file path

            // Use Tesseract to perform OCR (Optical Character Recognition) on the CAPTCHA image
            // Initializing Tesseract OCR engine
            ITesseract image = new Tesseract();

            // Extracting text from the captcha image
            captchaContent = image.doOCR(new File(FILE_PATH));

            // Print the extracted CAPTCHA text
            System.out.println(captchaContent);

        } catch (IOException | TesseractException e) {
            // Throws a runtime exception in case of IO or Tesseract exception
            throw new RuntimeException(e);
        }

        // Finding the input field for captcha by its ID using WebDriver
        WebElement captchaInput = driver.findElement(By.id("captcha"));

        // Entering the extracted captcha content into the input field
        captchaInput.sendKeys(captchaContent);

        // Retrieving the entered captcha text from the input field
        String enteredCaptcha = captchaInput.getAttribute("value");

        // Printing the entered captcha text
        System.out.println(enteredCaptcha);

        // Assertion to check if the entered captcha matches the extracted captcha content
        Assert.assertEquals(enteredCaptcha, captchaContent);
    }

    /*
            try {
            // Copy the temporary file to the designated path (optional)
            // FileHandler.copy(captchaImgFile, new File(FILE_PATH));

            // Initialize Tesseract engine
            ITesseract image = new Tesseract();

            // Set language and data path (adjust as needed)
            image.setLanguage("eng");
            image.setDataPath("/path/to/tessdata"); // Replace with your actual path

            // Perform OCR on the Captcha image
            String captchaContent = image.doOCR(captchaImgFile);

            // Print the recognized text
            System.out.println("Extracted Captcha text: " + captchaContent);
        } catch (IOException | TesseractException e) {
            // Handle specific exceptions for better error reporting
            throw new RuntimeException(e);
        }
    */

}
