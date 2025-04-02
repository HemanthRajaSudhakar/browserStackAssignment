package pagesObjects.pageLocators;
import org.openqa.selenium.By;

public interface OpinionPageLocators {
    public static By OPINION_PAGE_HEADER = By.xpath("//h1//a[contains(.,'Opinión')]");
    public static By ARTICLES_NAMES = By.xpath("//article//h2//a");
    public static By ARTICLES_CONTENTS = By.xpath("//article//header//following-sibling::p");
    public static By ARTICLE_IMAGE = By.xpath("(//article)[%s]//img");

}
