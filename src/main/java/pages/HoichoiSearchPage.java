package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HoichoiSearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchNavLink = By.cssSelector("a[aria-label='Search']");
    private final By homeNavLink = By.cssSelector("a[aria-label='Home']");
    private final By searchInput = By.cssSelector("[data-testid='search-input']");
    private final By searchPlaceholder = By.cssSelector(".search-placeholder");
    private final By clearSearchButton = By.cssSelector("[data-testid='search-clear-btn']");
    private final By activeSearchNav = By.cssSelector("[data-testid='active-nav-search']");
    private final By trendingHeading =
        By.xpath("//h2[normalize-space()='Trending Searches']");
    private final By resultTitle = By.cssSelector("[data-testid='search-card-title']");
    private final By allTab = By.cssSelector("[data-testid='search-tab-all']");
    private final By moviesTab = By.cssSelector("[data-testid='search-tab-movies']");
    private final By seriesTab = By.cssSelector("[data-testid='search-tab-series']");
    private final By noResultsHeading =
        By.xpath("//*[normalize-space()='No results found']");
    private final By noResultsHint =
        By.xpath("//*[normalize-space()='Try another title, actor, movie or show.']");
    private final By youMayAlsoLike =
        By.xpath("//*[normalize-space()='You may also like']");

    public HoichoiSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openFromSideNavigation() {
        driver.findElement(searchNavLink).click();

        wait.until(d ->
            d.getCurrentUrl().contains("/search")
                && !d.findElements(searchInput).isEmpty()
        );
    }

    public boolean isSearchPageOpen() {
        return driver.getCurrentUrl().contains("/search")
            && driver.findElement(searchInput).isDisplayed()
            && driver.findElement(activeSearchNav).isDisplayed();
    }

    public boolean isSearchPlaceholderShown() {
        WebElement placeholder = driver.findElement(searchPlaceholder);
        String text = placeholder.getAttribute("textContent");

        return placeholder.isDisplayed()
            && text.contains("Search")
            && text.contains("shows")
            && text.contains("Movies")
            && text.contains("artists")
            && text.contains("documentaries")
            && text.contains("short films");
    }

    public boolean isTrendingSearchesShown() {
        WebElement heading = driver.findElement(trendingHeading);
        return heading.isDisplayed()
            && driver.findElements(resultTitle).size() > 0;
    }

    public void searchFor(String query) {
        WebElement input = driver.findElement(searchInput);
        input.click();

        if (input.getAttribute("value") != null
            && !input.getAttribute("value").isEmpty()) {
            driver.findElement(clearSearchButton).click();
            wait.until(d ->
                d.findElement(searchInput).getAttribute("value").isEmpty()
            );
            input = driver.findElement(searchInput);
            input.click();
        }

        input.sendKeys(query);

        wait.until(d ->
            d.getCurrentUrl().toLowerCase().contains(
                "search=" + query.toLowerCase()
            )
        );
    }

    public boolean isResultTitleDisplayed(String title) {
        wait.until(d -> d.findElements(resultTitle).stream()
            .anyMatch(card -> title.equals(card.getText().trim()))
        );

        return resultTitles().stream().anyMatch(title::equals);
    }

    public boolean areResultFilterTabsDisplayed() {
        return driver.findElement(allTab).isDisplayed()
            && driver.findElement(moviesTab).isDisplayed()
            && driver.findElement(seriesTab).isDisplayed()
            && isTabSelected(allTab);
    }

    public boolean filterSeriesShowsTitle(String title) {
        driver.findElement(seriesTab).click();

        wait.until(d -> isTabSelected(seriesTab) && !isTabSelected(allTab));

        return isResultTitleDisplayed(title);
    }

    public boolean selectMoviesFilter() {
        driver.findElement(moviesTab).click();

        wait.until(d -> isTabSelected(moviesTab) && !isTabSelected(seriesTab));

        return true;
    }

    public void clearSearch() {
        driver.findElement(clearSearchButton).click();

        wait.until(d ->
            d.findElement(searchInput).getAttribute("value").isEmpty()
                && !d.findElements(trendingHeading).isEmpty()
        );
    }

    public void searchWithNoMatches(String query) {
        searchFor(query);

        wait.until(d -> !d.findElements(noResultsHeading).isEmpty());
    }

    public boolean isNoResultsStateDisplayed() {
        return driver.findElement(noResultsHeading).isDisplayed()
            && driver.findElement(noResultsHint).isDisplayed()
            && driver.findElement(youMayAlsoLike).isDisplayed()
            && driver.findElements(resultTitle).size() > 0;
    }

    public void returnToHome() {
        driver.findElement(homeNavLink).click();

        wait.until(d ->
            !d.getCurrentUrl().contains("/search")
                && !d.findElements(
                    By.cssSelector("[data-testid='active-nav-home']")
                ).isEmpty()
        );
    }

    public boolean isHomeVisibleAgain() {
        return !driver.getCurrentUrl().contains("/search")
            && driver.findElement(
                By.cssSelector("[data-testid='hero-banner-title']")
            ).isDisplayed();
    }

    private boolean isTabSelected(By tab) {
        return !driver.findElement(tab)
            .findElements(By.cssSelector("span"))
            .isEmpty();
    }

    private List<String> resultTitles() {
        return driver.findElements(resultTitle).stream()
            .map(card -> card.getText().trim())
            .toList();
    }

    // Result cards are links. Confirm the matching title points at a title page
    // without leaving Search.
    public boolean resultLinksToTitle(String title) {
        WebElement match = driver.findElements(resultTitle).stream()
            .filter(card -> title.equals(card.getText().trim()))
            .findFirst()
            .orElseThrow(() -> new AssertionError(
                "Search result was not found: " + title
            ));

        WebElement link = (WebElement) ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].closest('a');",
            match
        );

        if (link == null) {
            return false;
        }

        String href = link.getAttribute("href");
        return href != null && !href.isBlank();
    }
}
