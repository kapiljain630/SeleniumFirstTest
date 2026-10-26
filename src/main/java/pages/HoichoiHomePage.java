package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;

public class HoichoiHomePage {

    private WebDriver driver;

    // Locators
    private By pageLogo = By.cssSelector("img[alt='logo']");
    private By subscribeText =
        By.xpath("//*[normalize-space()='Subscribe']");

    public HoichoiHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Get page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Get current page URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Check whether the logo is displayed
    public boolean isLogoDisplayed() {
        WebElement logo = driver.findElement(pageLogo);
        return logo.isDisplayed();
    }

    // Check whether Subscribe is displayed
    public boolean isSubscribeDisplayed() {
        WebElement subscribe = driver.findElement(subscribeText);
        return subscribe.isDisplayed();
    }

    
    // Navigation icon names
    private String[] navigationIcons = {
        "Search", "Home", "Movies", "Shows", "Free", "Login"
    };

    // Test 5: Check whether Home text is collapsed
    public boolean isHomeTextCollapsed() {
        WebElement homeText = driver.findElement(
            By.xpath("//a[@href='/' and not(@aria-label) and normalize-space()='Home']")
        );

        return homeText.getSize().getWidth() == 0
            && homeText.getSize().getHeight() == 0;
    }

    // Test 6: Check all navigation icons are displayed
    public boolean areNavigationIconsDisplayed() {
        for (String iconName : navigationIcons) {
            WebElement icon = driver.findElement(
                By.cssSelector("[aria-label='" + iconName + "']")
            );

            if (!icon.isDisplayed()) {
                return false;
            }
        }

        return true;
    }

    // Test 7: Hover over Search to expand navigation
    public boolean isNavigationExpandedAfterSearchHover() {
        WebElement searchIcon = driver.findElement(
            By.cssSelector("a[aria-label='Search']")
        );

        new Actions(driver)
            .moveToElement(searchIcon)
            .pause(Duration.ofSeconds(1))
            .perform();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(5)
        );

        WebElement homeLabel = driver.findElement(
            By.xpath("//span[normalize-space()='Home']")
        );

        wait.until(d ->
            "visible".equals(homeLabel.getCssValue("visibility"))
            && Double.parseDouble(homeLabel.getCssValue("opacity")) > 0
        );

        return true;
    }


    // Test 8: Validate all navigation labels when expanded
    public boolean areNavigationLabelsVisible() {
        String[] labels = {
            "Search", "Home", "Movies", "Shows", "Free", "Login"
        };

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(5)
        );

        for (String label : labels) {
            WebElement navLabel = driver.findElement(
                By.xpath("//span[normalize-space()='" + label + "']")
            );

            wait.until(d ->
                "visible".equals(navLabel.getCssValue("visibility"))
                && Double.parseDouble(navLabel.getCssValue("opacity")) > 0
            );
        }

        return true;
    }

    // Test 9: Collapse navigation by moving the pointer away
    public boolean collapseNavigationAndVerifyLabelsHidden() {
        new Actions(driver)
            .moveByOffset(300, 400)
            .perform();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(5)
        );

        String[] labels = {
            "Search", "Home", "Movies", "Shows", "Free", "Login"
        };

        for (String label : labels) {
            WebElement navLabel = driver.findElement(
                By.xpath("//span[normalize-space()='" + label + "']")
            );

            wait.until(d ->
                "hidden".equals(navLabel.getCssValue("visibility"))
                || Double.parseDouble(navLabel.getCssValue("opacity")) == 0
            );
        }

        return true;
    }

    // Test 10: Validate icons remain displayed after collapse
    public boolean areNavigationIconsDisplayedAfterCollapse() {
        for (String iconName : navigationIcons) {
            WebElement icon = driver.findElement(
                By.cssSelector("[aria-label='" + iconName + "']")
            );

            if (!icon.isDisplayed()) {
                return false;
            }
        }

        return true;
    }

    
    // Test 11: Validate Watch Now link and play icon
    public boolean isWatchNowLinkDisplayed() {
        WebElement watchNowLink = driver.findElement(
            By.xpath("//a[.//span[normalize-space()='Watch Now']]")
        );

        return watchNowLink.isDisplayed();
    }

    public boolean isWatchNowPlayIconDisplayed() {
        WebElement watchNowLink = driver.findElement(
            By.xpath("//a[.//span[normalize-space()='Watch Now']]")
        );

        WebElement playIcon = watchNowLink.findElement(
            By.cssSelector("img[src*='web-watch-play.svg']")
        );

        return playIcon.isDisplayed();
    }

    // Test 12: Validate Add to Watchlist plus icon
    public boolean isAddToWatchlistPlusIconDisplayed() {
        WebElement plusIcon = driver.findElement(
            By.cssSelector("img[src*='plus-01.svg']")
        );

        return plusIcon.isDisplayed();
    }

    // Test 13: Scroll down and validate the last content tray
    public boolean isLastTrayNationalAwardWinners() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String expectedTitle = "National Award Winners";
        int maxScrollAttempts = 40;
        int scrollStep = 500;

        for (int attempt = 1; attempt <= maxScrollAttempts; attempt++) {
            java.util.List<WebElement> trays = driver.findElements(
                By.cssSelector("section[data-component='media-rail']")
            );

            if (!trays.isEmpty()) {
                String lastTrayTitle = trays.get(trays.size() - 1)
                    .findElement(By.cssSelector("h2"))
                    .getText()
                    .trim();

                if (lastTrayTitle.equals(expectedTitle)) {
                    return true;
                }
            }

            int previousTrayCount = trays.size();

            js.executeScript(
                "window.scrollBy(0, arguments[0]);",
                scrollStep
            );

            try {
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                    d -> d.findElements(
                        By.cssSelector("section[data-component='media-rail']")
                    ).size() > previousTrayCount
                );
            } catch (org.openqa.selenium.TimeoutException e) {
                // Continue scrolling if no new tray loads yet.
            }
        }

        return false;
    }

    
    // Test 14: Scroll to top and validate first content tray
    public boolean isFirstTrayWomenOfAuthority() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(d -> !d.findElements(
            By.cssSelector("section[data-component='media-rail']")
        ).isEmpty());

        List<WebElement> trays = driver.findElements(
            By.cssSelector("section[data-component='media-rail']")
        );

        String firstTrayTitle = trays.get(0)
            .findElement(By.cssSelector("h2"))
            .getText()
            .trim();

        return firstTrayTitle.equals("Women of Authority");
    }


// Find the horizontal container in the first tray
private WebElement getFirstTrayScrollContainer() {
    WebElement firstTray = driver.findElements(
        By.cssSelector("section[data-component='media-rail']")
    ).get(0);

    return (WebElement) ((JavascriptExecutor) driver).executeScript(
        "return Array.from(arguments[0].querySelectorAll('*'))" +
        ".filter(e => e.classList.contains('flex-nowrap'))" +
        ".find(e => e.scrollWidth > e.clientWidth + 5);",
        firstTray
    );
}

// Scroll to the actual end and verify the maximum position
private WebElement scrollFirstTrayToEnd() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement container = getFirstTrayScrollContainer();

    if (container == null) {
        throw new AssertionError(
            "Horizontal scrolling container was not found."
        );
    }

    for (int attempt = 1; attempt <= 10; attempt++) {
        js.executeScript(
            "arguments[0].scrollLeft = arguments[0].scrollWidth;",
            container
        );

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Scrolling was interrupted.", e);
        }

        double current = ((Number) js.executeScript(
            "return arguments[0].scrollLeft;", container
        )).doubleValue();

        double maximum = ((Number) js.executeScript(
            "return arguments[0].scrollWidth - arguments[0].clientWidth;",
            container
        )).doubleValue();

        System.out.println(
            "Scroll attempt " + attempt +
            " | Current: " + current +
            " | Maximum: " + maximum
        );

        if (Math.abs(current - maximum) <= 2) {
            return container;
        }
    }

    throw new AssertionError(
        "Could not reach the end of the horizontal tray."
    );
}

// Test 15: Validate the first and last content cards
public boolean areFirstAndLastCardsCorrect() {
    WebElement container = getFirstTrayScrollContainer();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript("arguments[0].scrollLeft = 0;", container);

    List<WebElement> cards = container.findElements(
        By.cssSelector(":scope > a")
    );

    if (cards.isEmpty()) {
        throw new AssertionError("No content cards found.");
    }

    String firstItem = cards.get(0)
        .findElement(By.cssSelector("[data-testid='media-card-title']"))
        .getText().trim();

    WebElement endContainer = scrollFirstTrayToEnd();

    List<WebElement> endCards = endContainer.findElements(
        By.cssSelector(":scope > a")
    );

    String lastItem = endCards.get(endCards.size() - 1)
        .findElement(By.cssSelector("[data-testid='media-card-title']"))
        .getText().trim();

    System.out.println("Actual first card: " + firstItem);
    System.out.println("Actual last card: " + lastItem);

   return firstItem.equals("Birangana") && lastItem.equals("Jimmi");

}

// Test 16: Verify the last card is fully visible at the end
public boolean isLastCardFullyVisible() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    WebElement container = scrollFirstTrayToEnd();

    List<WebElement> cards = container.findElements(
        By.cssSelector(":scope > a")
    );

    if (cards.isEmpty()) {
        throw new AssertionError("No content cards found.");
    }

    WebElement lastCard = cards.get(cards.size() - 1);

    @SuppressWarnings("unchecked")
    List<Boolean> result = (List<Boolean>) js.executeScript(
        "const c = arguments[0].getBoundingClientRect();" +
        "const e = arguments[1].getBoundingClientRect();" +
        "return [e.left >= c.left, e.right <= c.right];",
        container, lastCard
    );

    System.out.println(
        "Last card starts inside container: " + result.get(0)
    );
    System.out.println(
        "Last card ends inside container: " + result.get(1)
    );

    return result.get(0) && result.get(1);
}

    private By heroTitle = By.cssSelector("[data-testid='hero-banner-title']");
    private By heroMuteButton = By.cssSelector("[data-testid='hero-mute-btn']");
    private By heroSlides = By.cssSelector("button[aria-label^='Show slide']");
    private By activeHomeNav = By.cssSelector("[data-testid='active-nav-home']");
    private By viewAllButton = By.cssSelector("[data-testid^='button-view-all']");
    private By footerMenu = By.cssSelector("[data-testid='footer-menu']");

    public boolean isHeroTitleDisplayed() {
        WebElement title = driver.findElement(heroTitle);
        return title.isDisplayed() && !title.getText().trim().isEmpty();
    }

    public boolean isHeroMuteButtonDisplayed() {
        return driver.findElement(heroMuteButton).isDisplayed();
    }

    public boolean hasHeroSlides() {
        return driver.findElements(heroSlides).size() >= 2;
    }

    // Select another hero slide and confirm the banner title follows it.
    public boolean doesSecondHeroSlideUpdateTitle() {
        List<WebElement> slides = driver.findElements(heroSlides);

        if (slides.size() < 2) {
            return false;
        }

        WebElement secondSlide = slides.get(1);
        String label = secondSlide.getAttribute("aria-label");
        int separator = label.indexOf(':');
        String expectedTitle = separator >= 0
            ? label.substring(separator + 1).trim()
            : label.trim();

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();",
            secondSlide
        );

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
            expectedTitle.equals(
                d.findElement(heroTitle).getText().trim()
            )
        );

        return true;
    }

    public boolean doesSubscribeLinkToPlans() {
        WebElement subscribe = driver.findElement(
            By.xpath("//a[normalize-space()='Subscribe']")
        );
        String href = subscribe.getAttribute("href");
        return subscribe.isDisplayed()
            && href != null
            && href.contains("/plans");
    }

    public boolean doesLogoLinkToHome() {
        WebElement logo = driver.findElement(pageLogo);
        WebElement logoLink = logo.findElement(By.xpath("./ancestor::a[1]"));
        String href = logoLink.getAttribute("href");
        return logo.isDisplayed()
            && href != null
            && (href.endsWith("/") || href.endsWith("/global"));
    }

    public boolean isHomeNavigationActive() {
        return driver.findElement(activeHomeNav).isDisplayed();
    }

    public boolean isViewAllDisplayed() {
        List<WebElement> viewAllButtons = driver.findElements(viewAllButton);
        return !viewAllButtons.isEmpty() && viewAllButtons.get(0).isDisplayed();
    }

    public boolean isFooterDisplayed() {
        WebElement footer = driver.findElement(footerMenu);
        String footerText = footer.getText();
        return footer.isDisplayed()
            && footerText.contains("Privacy Policy")
            && footerText.contains("Terms Of Service")
            && footerText.contains("All Rights Reserved");
    }

}