
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.TimeoutException;

public class FirstTest {

    public static void main(String[] args) throws InterruptedException {

        // Test data: Homepage tray titles
        String expectedFirstTrayTitle = "Women of Authority";
        String expectedLastTrayTitle = "National Award Winners";
        String expectedFirstContent = "Birangana";
        String expectedLastContent = "Jimmi";

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption(
            "debuggerAddress",
            "127.0.0.1:9222"
        );


        WebDriver driver = new ChromeDriver(options);

        try {
            // Test 1: Open Hoichoi
           // driver.get("https://hoichoi.tv/");
          //  driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(20)
            );

            wait.until(d ->
                ((JavascriptExecutor) d).executeScript(
                    "return document.readyState"
                ).equals("complete")
            );

            System.out.println("PASS: Homepage document loaded.");



            System.out.println("Page Title: " + driver.getTitle());

            // Test 2: Validate page title
            if (driver.getTitle().contains("hoichoi")) {
                System.out.println("PASS: Page title validated.");
            } else {
                throw new AssertionError("Page title validation failed.");
            }

            // Test 3: Validate URL
            if (driver.getCurrentUrl().contains("hoichoi.tv")) {
                System.out.println("PASS: URL validated.");
            } else {
                throw new AssertionError("URL validation failed.");
            }

            // Test 4: Validate Hoichoi logo
            WebElement logo = driver.findElement(
                By.cssSelector("img[alt='logo']")
            );

            if (logo.isDisplayed()) {
                System.out.println("PASS: Hoichoi logo is visible.");
            } else {
                throw new AssertionError("Hoichoi logo is not visible.");
            }

            // Test 5: Validate Subscribe text
            WebElement subscribe = driver.findElement(
                By.xpath("//*[normalize-space()='Subscribe']")
            );

            if (subscribe.isDisplayed()) {
                System.out.println("PASS: Subscribe is visible.");
            } else {
                throw new AssertionError("Subscribe is not visible.");
            }


            // Test 6: Validate navigation drawer is initially collapsed
            WebElement homeText = driver.findElement(
                By.xpath("//a[@href='/' and not(@aria-label) and normalize-space()='Home']")
            );

            int homeWidth = homeText.getSize().getWidth();
            int homeHeight = homeText.getSize().getHeight();

            System.out.println("Home text width: " + homeWidth);
            System.out.println("Home text height: " + homeHeight);

            if (homeWidth == 0 && homeHeight == 0) {
                System.out.println(
                    "PASS: Home text is collapsed initially."
                );
            } else {
                throw new AssertionError(
                    "Expected collapsed Home text, but dimensions were: "
                    + homeWidth + "x" + homeHeight
                );
            }



            // Test 7: Validate navigation icons in collapsed state
            String[] navigationIcons = {
                "Search", "Home", "Movies", "Shows", "Free", "Login"
            };

            for (String iconName : navigationIcons) {

                WebElement icon = driver.findElement(
                    By.cssSelector(
                        "[aria-label='" + iconName + "']"
                    )
                );

                if (icon.isDisplayed()) {
                    System.out.println(
                        "PASS: " + iconName + " icon is displayed."
                    );
                } else {
                    throw new AssertionError(
                        iconName + " icon is not displayed."
                    );
                }
            }
            // Test 8: Verify navigation expands on Search hover
            System.out.println("START: Test 8 - Expand navigation");
// Existing Test 8 code
            WebElement searchIcon = driver.findElement(
                By.cssSelector("a[aria-label='Search']")
            );

            new Actions(driver)
                .moveToElement(searchIcon)
                .pause(Duration.ofSeconds(1))
                .perform();

        

            WebElement homeLabel = driver.findElement(
                By.xpath("//span[normalize-space()='Home']")
            );

            wait.until(d -> {
                String visibility = homeLabel.getCssValue("visibility");
                String opacity = homeLabel.getCssValue("opacity");

                return "visible".equals(visibility)
                    && Double.parseDouble(opacity) > 0;
            });

            System.out.println("PASS: Home label became visible after hover.");


            // Test 9: Validate all navigation labels when expanded
            System.out.println("START: Test 9 - Validate expanded labels");
// Existing Test 9 code
            String[] navigationLabels = {
                "Search", "Home", "Movies", "Shows", "Free", "Login"
            };

            for (String label : navigationLabels) {
                WebElement navLabel = driver.findElement(
                    By.xpath("//span[normalize-space()='" + label + "']")
                );

                wait.until(d -> {
                    String visibility = navLabel.getCssValue("visibility");
                    String opacity = navLabel.getCssValue("opacity");

                    return "visible".equals(visibility)
                        && Double.parseDouble(opacity) > 0;
                });

                System.out.println(
                    "PASS: Navigation label is visible - " + label
                );
            }

            // Test 10: Verify navigation collapses when pointer leaves
            System.out.println("START: Test 10 - Collapse navigation");
// Existing Test 10 code
            new Actions(driver)
                .moveByOffset(300, 400)
                .perform();

            for (String label : navigationLabels) {
                WebElement navLabel = driver.findElement(
                    By.xpath("//span[normalize-space()='" + label + "']")
                );

                wait.until(d ->
                    "hidden".equals(navLabel.getCssValue("visibility"))
                    || Double.parseDouble(
                        navLabel.getCssValue("opacity")
                    ) == 0
                );

                System.out.println(
                    "PASS: Navigation label hidden after collapse - " + label
                );
            }

            // Test 11: Validate navigation icons after collapse
            System.out.println("START: Test 11 - Validate collapsed icons");
// Existing Test 11 code
            String[] navigationIconsAfterCollapse = {
                "Search", "Home", "Movies", "Shows", "Free", "Login"
            };

            for (String iconName : navigationIconsAfterCollapse) {
                WebElement icon = driver.findElement(
                    By.cssSelector("[aria-label='" + iconName + "']")
                );

                if (icon.isDisplayed()) {
                    System.out.println(
                        "PASS: Navigation icon is displayed - " + iconName
                    );
                } else {
                    throw new AssertionError(
                        "FAIL: Navigation icon is not displayed - " + iconName
                    );
                }
            }

            // Test 12: Validate Watch Now link and play icon
            System.out.println("START: Test 12 - Validate Watch Now");
// Existing Test 12 code
            WebElement watchNowLink = driver.findElement(
                By.xpath("//a[.//span[normalize-space()='Watch Now']]")
            );

            if (watchNowLink.isDisplayed()) {
                System.out.println("PASS: Watch Now link is displayed.");
            }

            WebElement playIcon = watchNowLink.findElement(
                By.cssSelector("img[src*='web-watch-play.svg']")
            );

            if (playIcon.isDisplayed()) {
                System.out.println("PASS: Watch Now play icon is displayed.");
            }

            // Test 13: Validate Add to Watchlist plus icon
            System.out.println("START: Test 13 - Validate plus icon");
// Existing Test 13 code
            WebElement plusIcon = driver.findElement(
                By.cssSelector("img[src*='plus-01.svg']")
            );

            if (plusIcon.isDisplayed()) {
                System.out.println(
                    "PASS: Add to Watchlist plus icon is displayed."
                );
            } else {
                throw new AssertionError(
                    "FAIL: Add to Watchlist plus icon is not displayed."
                );
            }


            // Test 14: Scroll down and validate the last content tray
            System.out.println("START: Test 14 - Validate last tray");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            boolean expectedTrayFound = false;

            int maxScrollAttempts = 40;
            int scrollStep = 500;

            // Step 1: Scroll down and wait for new trays to load
            for (int attempt = 1; attempt <= maxScrollAttempts; attempt++) {

                List<WebElement> trays = driver.findElements(
                    By.cssSelector("section[data-component='media-rail']")
                );

                int currentTrayCount = trays.size();
                String currentLastTray = "";

                if (!trays.isEmpty()) {
                    WebElement lastTray = trays.get(trays.size() - 1);

                    currentLastTray = lastTray
                        .findElement(By.cssSelector("h2"))
                        .getText()
                        .trim();
                }

                System.out.println(
                    "Step " + attempt
                    + " | Tray count: " + currentTrayCount
                    + " | Current last tray: " + currentLastTray
                );

                if (currentLastTray.equals(expectedLastTrayTitle)) {
                    expectedTrayFound = true;
                    break;
                }

                final int previousTrayCount = currentTrayCount;

                js.executeScript(
                    "window.scrollBy(0, arguments[0]);",
                    scrollStep
                );

                try {
                    wait.until(d -> d.findElements(
                        By.cssSelector("section[data-component='media-rail']")
                    ).size() > previousTrayCount);
                } catch (TimeoutException e) {
                    System.out.println(
                        "Tray count did not increase within 3 seconds; continuing."
                    );
                }
            }

            // Step 2: Print all detected trays for verification
            System.out.println("\n--- Tray verification ---");

            List<WebElement> allTrays = driver.findElements(
                By.cssSelector("section[data-component='media-rail']")
            );

            for (int i = 0; i < allTrays.size(); i++) {
                WebElement tray = allTrays.get(i);

                String title = tray.findElement(By.cssSelector("h2"))
                    .getText()
                    .trim();

                String testId = tray.getAttribute("data-testid");

                System.out.println(
                    "Tray " + (i + 1)
                    + " | Title: " + title
                    + " | Test ID: " + testId
                );
            }

            System.out.println("Total tray sections: " + allTrays.size());
            System.out.println("--- End tray verification ---");

            // Step 3: Validate the actual last tray
            if (expectedTrayFound && !allTrays.isEmpty()) {

                String actualLastTray = allTrays.get(allTrays.size() - 1)
                    .findElement(By.cssSelector("h2"))
                    .getText()
                    .trim();

                if (actualLastTray.equals(expectedLastTrayTitle)) {
                    System.out.println(
                        "Test 14 PASS: Last tray is " + actualLastTray
                    );
                } else {
                    throw new AssertionError(
                        "Expected last tray: " + expectedLastTrayTitle
                        + ", but found: " + actualLastTray
                    );
                }

            } else {
                String actualLastTray = allTrays.isEmpty()
                    ? "No trays found"
                    : allTrays.get(allTrays.size() - 1)
                        .findElement(By.cssSelector("h2"))
                        .getText()
                        .trim();

                throw new AssertionError(
                    "Expected last tray: " + expectedLastTrayTitle
                    + ", but found: " + actualLastTray
                    + ". Total trays found: " + allTrays.size()
                );
            }



 




            // Test 15: Scroll to top and validate the first content tray
            System.out.println("START: Test 15 - Validate first tray");

           // JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll to the top of the page
            js.executeScript("window.scrollTo(0, 0)");

            Thread.sleep(1500);

            // Find all content trays
            List<WebElement> trays = driver.findElements(
                By.cssSelector("section[data-component='media-rail']")
            );

            if (trays.isEmpty()) {
                throw new AssertionError("No content trays were found.");
            }

            // Get the first content tray
            WebElement firstTray = trays.get(0);

            // Read the first tray title
            //String expectedFirstTrayTitle = "Women of Authority";

            String actualFirstTrayTitle = firstTray
                .findElement(By.cssSelector("h2"))
                .getText()
                .trim();

            System.out.println("First tray found: " + actualFirstTrayTitle);

            // Validate the title
            if (actualFirstTrayTitle.equals(expectedFirstTrayTitle)) {
                System.out.println(
                    "Test 15 PASS: First tray is " + actualFirstTrayTitle
                );
            } else {
                throw new AssertionError(
                    "Expected first tray: " + expectedFirstTrayTitle
                    + ", but found: " + actualFirstTrayTitle
                );
            }

           // Test 16: Validate first and last content items in the first tray
            System.out.println("START: Test 16 - Horizontal scrolling validation");

            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));



            // Step 1: Find the first content tray


            String actualTrayTitle = firstTray
                .findElement(By.cssSelector("h2"))
                .getText()
                .trim();

            if (!actualTrayTitle.equals(expectedFirstTrayTitle)) {
                throw new AssertionError(
                    "Expected tray: " + expectedFirstTrayTitle
                    + ", but found: " + actualTrayTitle
                );
            }

            System.out.println("Tray found: " + actualTrayTitle);

            // Step 2: Find the horizontal row containing the content cards
            WebElement scrollContainer = (WebElement) js.executeScript(
                "return Array.from(arguments[0].querySelectorAll('*'))" +
                ".filter(e => e.classList.contains('flex-nowrap'))" +
                ".find(e => e.scrollWidth > e.clientWidth + 5);",
                firstTray
            );

            if (scrollContainer == null) {
                throw new AssertionError(
                    "Horizontal scrolling container was not found."
                );
            }

            System.out.println(
                "Scroll container class: "
                + scrollContainer.getAttribute("class")
            );

            // Step 3: Get the content cards
            List<WebElement> cards = scrollContainer.findElements(
                By.cssSelector(":scope > a")
            );

            if (cards.isEmpty()) {
                throw new AssertionError(
                    "No content cards found in the first tray."
                );
            }

            System.out.println("Total content cards: " + cards.size());

            // Step 4: Scroll to the beginning
            js.executeScript(
                "arguments[0].scrollLeft = 0;",
                scrollContainer
            );

            wait.until(d -> {
                Object position = js.executeScript(
                    "return arguments[0].scrollLeft;",
                    scrollContainer
                );
                return ((Number) position).doubleValue() <= 1;
            });

            // Step 5: Validate the first item
            String actualFirstItem = cards.get(0)
                .findElement(By.cssSelector("[data-testid='media-card-title']"))
                .getText()
                .trim();

            System.out.println("First content item: " + actualFirstItem);

            if (!actualFirstItem.equals(expectedFirstContent)) {
                throw new AssertionError(
                    "Expected first item: " + expectedFirstContent
                    + ", but found: " + actualFirstItem
                );
            }

            System.out.println("First content item validation PASS.");

            // Step 6: Scroll to the maximum horizontal position
            System.out.println("Scrolling to the end of the tray...");

            long maxAttempts = 10;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                js.executeScript(
                    "arguments[0].scrollLeft = arguments[0].scrollWidth;",
                    scrollContainer
                );

                Thread.sleep(300);

                @SuppressWarnings("unchecked")
                List<Number> details = (List<Number>) js.executeScript(
                    "return [" +
                    "arguments[0].scrollLeft, " +
                    "arguments[0].scrollWidth, " +
                    "arguments[0].clientWidth" +
                    "];",
                    scrollContainer
                );

                double currentPosition = details.get(0).doubleValue();
                double scrollWidth = details.get(1).doubleValue();
                double visibleWidth = details.get(2).doubleValue();
                double maximumPosition = scrollWidth - visibleWidth;

                System.out.println(
                    "Attempt " + attempt
                    + " | Current: " + currentPosition
                    + " | Maximum: " + maximumPosition
                );

                if (Math.abs(currentPosition - maximumPosition) <= 2) {
                    System.out.println("Reached the end of the horizontal tray.");
                    break;
                }
            }

            @SuppressWarnings("unchecked")
            List<Number> finalDetails = (List<Number>) js.executeScript(
                "return [" +
                "arguments[0].scrollLeft, " +
                "arguments[0].scrollWidth, " +
                "arguments[0].clientWidth" +
                "];",
                scrollContainer
            );

            double finalPosition = finalDetails.get(0).doubleValue();
            double finalMaximum = finalDetails.get(1).doubleValue()
                - finalDetails.get(2).doubleValue();

            if (Math.abs(finalPosition - finalMaximum) > 2) {
                throw new AssertionError(
                    "Horizontal scrolling did not reach the end. Current: "
                    + finalPosition + ", maximum: " + finalMaximum
                );
            }


            // Step 7: Re-find and validate the last item after scrolling
            System.out.println("Validating the last content item...");

            @SuppressWarnings("unchecked")
            List<WebElement> refreshedCards = (List<WebElement>) js.executeScript(
                "return Array.from(arguments[0].querySelectorAll(':scope > a'));",
                scrollContainer
            );

            if (refreshedCards.isEmpty()) {
                throw new AssertionError("No content cards found after scrolling.");
            }

            WebElement lastCard = refreshedCards.get(refreshedCards.size() - 1);

            String actualLastItem = lastCard
                .findElement(By.cssSelector("[data-testid='media-card-title']"))
                .getText()
                .trim();

            System.out.println("Number of cards after scrolling: " + refreshedCards.size());
            System.out.println("Last content item: " + actualLastItem);

            if (actualLastItem.equals(expectedLastContent)) {
                System.out.println("Last content item validation PASS.");
            } else {
                throw new AssertionError(
                    "Expected last item: " + expectedLastContent
                    + ", but found: " + actualLastItem
                );
            }

            System.out.println(
                "Test 16 PASS: First and last content items validated successfully."
            );




            // Test 17: Verify last content card is visible in the viewport
            System.out.println("START: Test 17 - Verify last card visibility");

            // Scroll the container fully to the right
            js.executeScript(
                "arguments[0].scrollLeft = arguments[0].scrollWidth;",
                scrollContainer
            );

            Thread.sleep(1000);

            // Get the last card's position and the container's visible boundaries
            @SuppressWarnings("unchecked")
            List<Object> bounds = (List<Object>) js.executeScript(
                "const container = arguments[0];" +
                "const card = arguments[1];" +
                "const c = container.getBoundingClientRect();" +
                "const e = card.getBoundingClientRect();" +
                "return [" +
                "  e.left >= c.left," +
                "  e.right <= c.right," +
                "  e.right > c.left && e.left < c.right" +
                "];",
                scrollContainer,
                cards.get(cards.size() - 1)
            );

            boolean lastCardStartsInside = (Boolean) bounds.get(0);
            boolean lastCardEndsInside = (Boolean) bounds.get(1);
            boolean lastCardIntersectsViewport = (Boolean) bounds.get(2);

            if (lastCardStartsInside && lastCardEndsInside) {
                System.out.println(
                    "Test 17 PASS: Last card is fully visible in the horizontal viewport."
                );
            } else if (lastCardIntersectsViewport) {
                throw new AssertionError(
                    "Last card is only partially visible in the horizontal viewport."
                );
            } else {
                throw new AssertionError(
                    "Last card is not visible in the horizontal viewport."
                );
            }

         


        } finally {
          //  driver.quit();
        }
    }
}
