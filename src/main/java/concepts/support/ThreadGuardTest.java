package concepts.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;

public class ThreadGuardTest {

    // Declare a WebDriver instance protected by ThreadGuard for thread safety
    private final WebDriver protectedDriver = ThreadGuard.protect(new ChromeDriver());

    // Static block to set system properties before the class is initialized
    static {
        // Set the HTTP client factory to use the JDK's built-in HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jagatheshwaran N\\.cache\\selenium\\chromedriver\\win64\\120.0.6099.109\\chromedriver.exe");
    }

    // Main method to start the program
    public static void main(String[] args) {
        // Create an instance of ThreadGuardTest and run the thread
        new ThreadGuardTest().runThread();
    }

    // Runnable task to navigate the protected driver to a URL
    Runnable runnable = () -> protectedDriver.get("https://selenium.dev");

    // Thread to execute the runnable task
    Thread thread = new Thread(runnable);

    // Method to start the thread
    public void runThread() {
        thread.start();
    }

}

