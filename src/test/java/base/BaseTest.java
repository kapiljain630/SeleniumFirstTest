package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Connect to the Chrome browser already running on port 9222
        options.setExperimentalOption(
            "debuggerAddress",
            "127.0.0.1:9222"
        );

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(d ->
            ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState")
                .equals("complete")
        );

        System.out.println("PASS: Connected to the existing Chrome browser.");
    }

    @AfterClass
    public void tearDown() {
        // Keep the manually opened Chrome browser running.
        // Do not call driver.quit() here.
    }
}
