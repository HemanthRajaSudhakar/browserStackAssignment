package pagesObjects.pageActions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagesObjects.pageLocators.OpinionPageLocators;
import seleniumActions.Actions;
import utilities.TimeOuts;
import utilities.UtilityMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpinionPage extends Actions implements OpinionPageLocators {
    public OpinionPage(WebDriver driver){
        super(driver);
    }

    public ArrayList<String> printAndGetTitleOfArticles(){
        List<WebElement> articlesName = findElementsBy(ARTICLES_NAMES,"Articles names");
        List<WebElement> articlesContent = findElementsBy(ARTICLES_CONTENTS,"Articles Content");
        int i =0;
        ArrayList<String> headerNames = new ArrayList<>();
        int j;
        for(i=0;i<5;i++){
            WebElement articleName = articlesName.get(i);
            WebElement articleContent = articlesContent.get(i);
            j=i+1;
            System.out.println("Article no. "+j+" name is "+articleName.getText());
            System.out.println("Article no. "+j+" content is "+articleContent.getText());
            headerNames.add(articleName.getText());
            System.out.println("-------");
        }
        return headerNames;
    }

    public void takeScreenshotIfAnyCoverImagePresent() throws IOException {
        for(int i=1;i<6;i++){
            boolean isCoverPhotoPresent =
                    isElementPresent(UtilityMethods.getNewLocator(ARTICLE_IMAGE,Integer.toString(i)), TimeOuts.VERY_SHORT_WAIT);
            if(isCoverPhotoPresent)
            {
                WebElement ele = driver.findElement(UtilityMethods.getNewLocator(ARTICLE_IMAGE,Integer.toString(i)));
                UtilityMethods.takeScreenshotOfAnElement(driver,ele);
            }
        }
    }

    public ArrayList<String> translateTitleToEnglishAndPrint(ArrayList<String> articlesNames){
        ArrayList<String> englishTitles = new ArrayList<>();
        int j;
        for(int i=0;i<articlesNames.size();i++){
            String englishTitle = UtilityMethods.translateStringToEnglish(articlesNames.get(i));
            j=i+1;
            System.out.println("Translated Article no. "+j+" name in English is "+englishTitle);
            englishTitles.add(englishTitle);
        }
        return englishTitles;
    }
}
