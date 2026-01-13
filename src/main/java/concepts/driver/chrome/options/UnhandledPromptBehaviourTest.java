package concepts.driver.chrome.options;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

/**
 * The CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR capability indeed specifies the default
 * behavior for handling unhandled prompts, but its effectiveness can vary depending on
 * the WebDriver implementation and browser.
 * <p>
 * Upon review, it's essential to clarify that the CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR
 * capability might not always handle the prompts automatically as expected in all scenarios,
 * despite setting the capability to a specific behavior like accept, dismiss, or others.
 * <p>
 * In some cases, WebDriver's automatic handling might not suffice due to browser or WebDriver
 * implementation differences. Therefore, explicit handling of the prompts using alert.accept()
 * or alert.dismiss() might be necessary to ensure consistent behavior across different
 * environments and browsers.
 */
public class UnhandledPromptBehaviourTest {

    public static void main(String[] args) {
        // Set the WebDriver HTTP factory to "jdk-http-client" for improved HTTP request handling.
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Declare a ChromeOptions instance to interact with the web browser.
        ChromeOptions chromeOptions = new ChromeOptions();

        // Behavior to test: "accept", "dismiss", "dismiss and notify", "accept and notify", "ignore"
        String[] behaviors = {"accept", "dismiss", "dismiss and notify", "accept and notify", "ignore"};

        for (String behavior : behaviors) {
            // Set unhandledPromptBehavior capability for each behavior
            chromeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, behavior);

            // Initialize the WebDriver with ChromeOptions
            WebDriver driver = new ChromeDriver(chromeOptions);

            // Open the HTML page with the prompt
            driver.get("file:///D:/Environment_Collection/Intellij_Env/Selenium_Concepts/src/main/resources/supportFiles/Prompt.html");

            // Click the button to trigger the prompt
            driver.findElement(By.tagName("button")).click();

            try {
                // Handle the prompt
                Alert alert = driver.switchTo().alert();

                // Simulate a delay to observe the behavior
                Thread.sleep(2000);

                // Handling the prompt based on the specified behavior
                if (behavior.equals("accept") || behavior.equals("accept and notify")) {

                    // If behavior is set to 'accept' or 'accept and notify', accept the prompt
                    alert.accept();
                } else if (behavior.equals("dismiss") || behavior.equals("dismiss and notify")) {

                    // If behavior is set to 'dismiss' or 'dismiss and notify', dismiss the prompt
                    alert.dismiss();
                }

                // Simulate another delay to observe the behavior
                Thread.sleep(2000);

                // Close the browser
                driver.quit();

            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

}
