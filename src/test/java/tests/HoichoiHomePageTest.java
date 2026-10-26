package tests;

import base.BaseTest;
import pages.HoichoiHomePage;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HoichoiHomePageTest extends BaseTest {

    private HoichoiHomePage homePage;

    @BeforeClass
    public void createPageObject() {
        homePage = new HoichoiHomePage(driver);
    }

    @Test(priority = 1)
    public void verifyPageTitle() {

        String actualTitle = homePage.getPageTitle();

        Assert.assertTrue(
            actualTitle.contains("hoichoi"),
            "Page title should contain 'hoichoi'"
        );

        System.out.println("PASS: Page title validated.");
    }

    @Test(priority = 2)
    public void verifyPageUrl() {

        String actualUrl = homePage.getCurrentUrl();

        Assert.assertTrue(
            actualUrl.contains("hoichoi.tv"),
            "Page URL should contain 'hoichoi.tv'"
        );

        System.out.println("PASS: Page URL validated.");
    }

    @Test(priority = 3)
    public void verifyLogoIsDisplayed() {

        Assert.assertTrue(
            homePage.isLogoDisplayed(),
            "Hoichoi logo should be displayed"
        );

        System.out.println("PASS: Hoichoi logo is visible.");
    }

    @Test(priority = 4)
    public void verifySubscribeIsDisplayed() {

        Assert.assertTrue(
            homePage.isSubscribeDisplayed(),
            "Subscribe text should be displayed"
        );

        System.out.println("PASS: Subscribe is visible.");
    }

    
    @Test(priority = 5)
    public void verifyNavigationIsInitiallyCollapsed() {
        Assert.assertTrue(
            homePage.isHomeTextCollapsed(),
            "Home text should be collapsed initially"
        );

        System.out.println("PASS: Navigation is initially collapsed.");
    }

    @Test(priority = 6)
    public void verifyNavigationIconsAreDisplayed() {
        Assert.assertTrue(
            homePage.areNavigationIconsDisplayed(),
            "All six navigation icons should be displayed"
        );

        System.out.println("PASS: All six navigation icons are displayed.");
    }

    @Test(priority = 7)
    public void verifyNavigationExpandsOnSearchHover() {
        Assert.assertTrue(
            homePage.isNavigationExpandedAfterSearchHover(),
            "Navigation should expand when hovering over Search"
        );

        System.out.println("PASS: Navigation expanded after Search hover.");
    }

    
    @Test(priority = 8)
    public void verifyAllNavigationLabelsAreVisible() {
        Assert.assertTrue(
            homePage.areNavigationLabelsVisible(),
            "All navigation labels should be visible"
        );

        System.out.println("PASS: All six navigation labels are visible.");
    }

    @Test(priority = 9)
    public void verifyNavigationCollapsesWhenPointerLeaves() {
        Assert.assertTrue(
            homePage.collapseNavigationAndVerifyLabelsHidden(),
            "Navigation labels should be hidden when the pointer leaves"
        );

        System.out.println("PASS: Navigation collapsed successfully.");
    }

    @Test(priority = 10)
    public void verifyIconsRemainVisibleAfterCollapse() {
        Assert.assertTrue(
            homePage.areNavigationIconsDisplayedAfterCollapse(),
            "All navigation icons should remain visible after collapse"
        );

        System.out.println("PASS: All six icons remain visible after collapse.");
    }

    
    @Test(priority = 11)
    public void verifyWatchNowLinkIsDisplayed() {
        Assert.assertTrue(
            homePage.isWatchNowLinkDisplayed(),
            "Watch Now link should be displayed"
        );

        Assert.assertTrue(
            homePage.isWatchNowPlayIconDisplayed(),
            "Watch Now play icon should be displayed"
        );

        System.out.println("PASS: Watch Now link and play icon are displayed.");
    }

    @Test(priority = 12)
    public void verifyAddToWatchlistPlusIconIsDisplayed() {
        Assert.assertTrue(
            homePage.isAddToWatchlistPlusIconDisplayed(),
            "Add to Watchlist plus icon should be displayed"
        );

        System.out.println("PASS: Add to Watchlist plus icon is displayed.");
    }

    @Test(priority = 13)
    public void verifyLastTrayIsNationalAwardWinners() {
        Assert.assertTrue(
            homePage.isLastTrayNationalAwardWinners(),
            "The last content tray should be National Award Winners"
        );

        System.out.println("PASS: Last tray is National Award Winners.");
    }

    
    @Test(priority = 14)
    public void verifyFirstTrayTitle() {
        Assert.assertTrue(
            homePage.isFirstTrayWomenOfAuthority(),
            "First tray should be Women of Authority"
        );
        System.out.println("PASS: First tray is Women of Authority.");
    }

    @Test(priority = 15)
    public void verifyFirstAndLastContentCards() {
        Assert.assertTrue(
            homePage.areFirstAndLastCardsCorrect(),
            "First card should be Birangana and last card should be Jimmi"
        );
        System.out.println("PASS: First and last cards validated.");
    }

    @Test(priority = 16)
    public void verifyLastCardFullyVisible() {
        Assert.assertTrue(
            homePage.isLastCardFullyVisible(),
            "Last card should be fully visible in the horizontal viewport"
        );
        System.out.println("PASS: Last card is fully visible.");
    }

    @Test(priority = 17)
    public void verifyHeroBannerIsDisplayed() {
        Assert.assertTrue(
            homePage.isHeroTitleDisplayed(),
            "Hero banner title should be displayed"
        );
        Assert.assertTrue(
            homePage.isHeroMuteButtonDisplayed(),
            "Hero mute control should be displayed"
        );
        Assert.assertTrue(
            homePage.hasHeroSlides(),
            "Hero banner should expose more than one slide"
        );

        System.out.println("PASS: Hero banner, mute control, and slides are displayed.");
    }

    @Test(priority = 18)
    public void verifyHeroSlideUpdatesTitle() {
        Assert.assertTrue(
            homePage.doesSecondHeroSlideUpdateTitle(),
            "Selecting another hero slide should update the banner title"
        );

        System.out.println("PASS: Hero slide updated the banner title.");
    }

    @Test(priority = 19)
    public void verifySubscribeLinksToPlans() {
        Assert.assertTrue(
            homePage.doesSubscribeLinkToPlans(),
            "Subscribe should link to plans"
        );

        System.out.println("PASS: Subscribe links to plans.");
    }

    @Test(priority = 20)
    public void verifyLogoLinksToHome() {
        Assert.assertTrue(
            homePage.doesLogoLinkToHome(),
            "Logo should link to the home page"
        );

        System.out.println("PASS: Logo links to home.");
    }

    @Test(priority = 21)
    public void verifyHomeNavigationIsActive() {
        Assert.assertTrue(
            homePage.isHomeNavigationActive(),
            "Home should be the active navigation item"
        );

        System.out.println("PASS: Home navigation item is active.");
    }

    @Test(priority = 22)
    public void verifyViewAllIsDisplayed() {
        Assert.assertTrue(
            homePage.isViewAllDisplayed(),
            "A content tray should show View All"
        );

        System.out.println("PASS: View All is displayed.");
    }

    @Test(priority = 23)
    public void verifyFooterIsDisplayed() {
        Assert.assertTrue(
            homePage.isFooterDisplayed(),
            "Footer should show policy links and the copyright line"
        );

        System.out.println("PASS: Footer is displayed.");
    }

}