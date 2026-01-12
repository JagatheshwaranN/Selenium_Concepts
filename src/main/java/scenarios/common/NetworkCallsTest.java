package scenarios.common;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import scenarios.DriverConfiguration;

public class NetworkCallsTest {

    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = (ChromeDriver) DriverConfiguration.browserSetup();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));

        devTools.addListener(Network.responseReceived(), response -> {
            System.out.println("URL    : " + response.getResponse().getUrl());
            System.out.println("Status : " + response.getResponse().getStatus());
        });
    }

    @Test
    public void testNetworkCalls() {
        driver.get("https://www.selenium.dev/");
        waitForPageLoad();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.jsReturnsValue(
                        "return document.readyState === 'complete'"
                ));
    }
}
