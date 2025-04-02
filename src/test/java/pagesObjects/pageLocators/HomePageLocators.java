package pagesObjects.pageLocators;

import org.openqa.selenium.By;

public interface HomePageLocators {
    public static By ACCEPT_COOKIES = By.id("didomi-notice-agree-button");
    public static By OPINION_MENU_LINK = By.xpath("//a[contains(.,'Opinión')][@cmp-ltrk='portada_menu']");
    public static By IS_SPANISH_DEFAULT = By.xpath("//li[contains(@id,'edition_head')]/a[contains(.,'España')]");
    public static By IS_US_SPANISH_DEFAULT = By.xpath("//li[contains(@id,'edition_head')]/a[contains(.,'US Español')]");
    public static By OPINION_MENU = By.xpath("(//a[contains(.,'Opinión')])[1]");

}
