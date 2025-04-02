package pagesObjects.pageAssertions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pagesObjects.pageLocators.OpinionPageLocators;
import seleniumActions.Actions;
import utilities.TimeOuts;

public class OpinionPageAssertions extends Actions implements OpinionPageLocators {
    String opinionPageURLSlug = "/opinion/";
    public OpinionPageAssertions(WebDriver driver){
        super(driver);
    }

    public void verifyOpinionPageIsLoaded(){
        waitForPageToLoad();
        boolean isOpinionPageLoaded = getCurrentURL().contains(opinionPageURLSlug);
        Assert.assertTrue(isOpinionPageLoaded,"Opinion Page is not loaded "+getCurrentURL()+" is loaded");
        boolean isOpinionPageHeaderPresent = isElementPresent(OPINION_PAGE_HEADER, TimeOuts.MEDIUM_WAIT);
        Assert.assertTrue(isOpinionPageHeaderPresent,"Opinion Page header is not present");
    }
}
