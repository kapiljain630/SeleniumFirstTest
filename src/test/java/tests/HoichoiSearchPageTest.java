package tests;

import base.BaseTest;
import pages.HoichoiSearchPage;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HoichoiSearchPageTest extends BaseTest {

    private static final String KNOWN_TITLE = "Birangana";
    private static final String UNKNOWN_QUERY = "zzzznotatitle999";

    private HoichoiSearchPage searchPage;

    @BeforeClass
    public void createPageObject() {
        searchPage = new HoichoiSearchPage(driver);
    }

    @Test(priority = 1)
    public void verifySearchOpensFromNavigation() {
        searchPage.openFromSideNavigation();

        Assert.assertTrue(
            searchPage.isSearchPageOpen(),
            "Search should open from the side navigation"
        );

        System.out.println("PASS: Search page opened from navigation.");
    }

    @Test(priority = 2)
    public void verifySearchPlaceholder() {
        Assert.assertTrue(
            searchPage.isSearchPlaceholderShown(),
            "Search placeholder should list the kinds of content a viewer can search"
        );

        System.out.println("PASS: Search placeholder is shown.");
    }

    @Test(priority = 3)
    public void verifyTrendingSearches() {
        Assert.assertTrue(
            searchPage.isTrendingSearchesShown(),
            "Trending searches should list at least one title"
        );

        System.out.println("PASS: Trending searches are shown.");
    }

    @Test(priority = 4)
    public void verifyKnownTitleSearch() {
        searchPage.searchFor(KNOWN_TITLE);

        Assert.assertTrue(
            searchPage.isResultTitleDisplayed(KNOWN_TITLE),
            "Search results should include Birangana"
        );
        Assert.assertTrue(
            searchPage.resultLinksToTitle(KNOWN_TITLE),
            "Birangana result should link to a title page"
        );

        System.out.println("PASS: Birangana search result is shown.");
    }

    @Test(priority = 5)
    public void verifySearchFilters() {
        Assert.assertTrue(
            searchPage.areResultFilterTabsDisplayed(),
            "All, Movies, and Series filters should be displayed"
        );
        Assert.assertTrue(
            searchPage.filterSeriesShowsTitle(KNOWN_TITLE),
            "Series filter should still show Birangana"
        );
        Assert.assertTrue(
            searchPage.selectMoviesFilter(),
            "Movies filter should become the selected tab"
        );

        System.out.println("PASS: Search filters are displayed and selectable.");
    }

    @Test(priority = 6)
    public void verifyClearSearchRestoresTrending() {
        searchPage.clearSearch();

        Assert.assertTrue(
            searchPage.isTrendingSearchesShown(),
            "Clearing search should restore trending searches"
        );

        System.out.println("PASS: Clear search restored trending searches.");
    }

    @Test(priority = 7)
    public void verifyNoResultsState() {
        searchPage.searchWithNoMatches(UNKNOWN_QUERY);

        Assert.assertTrue(
            searchPage.isNoResultsStateDisplayed(),
            "An unknown query should show the empty state and suggestions"
        );

        System.out.println("PASS: No-results state is shown.");
    }

    @Test(priority = 8)
    public void verifyReturnToHome() {
        searchPage.returnToHome();

        Assert.assertTrue(
            searchPage.isHomeVisibleAgain(),
            "Home navigation should leave Search and show the home banner"
        );

        System.out.println("PASS: Returned to the Home page.");
    }
}
