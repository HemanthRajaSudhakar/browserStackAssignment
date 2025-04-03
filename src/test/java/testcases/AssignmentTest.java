package testcases;

import base.BaseSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesObjects.pageActions.HomePage;
import pagesObjects.pageActions.OpinionPage;
import pagesObjects.pageAssertions.HomePageAssertions;
import pagesObjects.pageAssertions.OpinionPageAssertions;
import utilities.UtilityMethods;

import java.io.IOException;
import java.util.ArrayList;

public class AssignmentTest extends BaseSetup {
    private static final Logger log = LogManager.getLogger(AssignmentTest.class);


    @Test
    public void testCase1() throws InterruptedException, IOException {
        //Below code is equivalent to driver.get("https://example.com");
        //But it is thread safe and URL is parameterized instead of hard coding
        getDriver().get(loadURL());

        //Initialize Page Objects
        HomePage homePage = new HomePage(getDriver());
        HomePageAssertions homePageAssertions = new HomePageAssertions(getDriver());
        OpinionPageAssertions opinionPageAssertions = new OpinionPageAssertions(getDriver());
        OpinionPage opinionPage = new OpinionPage(getDriver());

        homePageAssertions.verifyPageIsLoaded();
        homePageAssertions.verifySpanishIsSelectedByDefault();

        homePage.clickOnOpinionMenu();

        opinionPageAssertions.verifyOpinionPageIsLoaded();
        ArrayList<String> articleNames = opinionPage.printAndGetTitleOfArticles();
        opinionPage.takeScreenshotIfAnyCoverImagePresent();

        ArrayList<String> articlesNamesInEnglish = opinionPage.translateTitleToEnglishAndPrint(articleNames);
        UtilityMethods.checkForDuplicateWordsInTitleAndPrint(articlesNamesInEnglish);
    }
}
