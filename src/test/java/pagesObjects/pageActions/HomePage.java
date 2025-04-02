package pagesObjects.pageActions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pagesObjects.pageLocators.HomePageLocators;
import seleniumActions.Actions;
import utilities.TimeOuts;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Actions implements HomePageLocators {
    public HomePage(WebDriver driver){
        super(driver);
    }

    public void clickOnOpinionMenu(){
        safeClick(OPINION_MENU,"Opinion menu", TimeOuts.SHORT_WAIT);
    }
}
