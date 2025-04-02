package pagesObjects.pageAssertions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pagesObjects.pageLocators.HomePageLocators;
import seleniumActions.Actions;
import utilities.TimeOuts;

public class HomePageAssertions extends Actions implements HomePageLocators {
    public HomePageAssertions(WebDriver driver){
        super(driver);
    }

    String expectedPageURLSlug = "elpais.com/";

    public void verifyPageIsLoaded(){
        waitForPageToLoad();
        acceptIfCookiesNotificationsAppears();
        boolean isPageLoaded = getCurrentURL().contains(expectedPageURLSlug);
        Assert.assertTrue(isPageLoaded,"Expected page not loaded, "+getCurrentURL()+" is loaded");

        boolean isOpinionMenuLinkPresent = isElementPresent(OPINION_MENU_LINK,TimeOuts.VERY_SHORT_WAIT);
        Assert.assertTrue(isOpinionMenuLinkPresent,"Opinion Menu link is not present in the page");
    }

    public void acceptIfCookiesNotificationsAppears(){
        safeClick(ACCEPT_COOKIES,"Accept btn ", TimeOuts.SHORT_WAIT);
    }

    public void verifySpanishIsSelectedByDefault(){
        boolean isSpanishSelected = isElementPresent(IS_SPANISH_DEFAULT,TimeOuts.SHORT_WAIT);
        boolean isUSSpanishSelected = isElementPresent(IS_US_SPANISH_DEFAULT,TimeOuts.SHORT_WAIT);
        Assert.assertTrue(isSpanishSelected||isUSSpanishSelected,"Spanish is not selected by default");
    }

}
