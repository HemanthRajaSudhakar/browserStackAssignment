package seleniumActions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testcases.AssignmentTest;
import utilities.TimeOuts;

import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Actions implements TimeOuts{
    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(Actions.class);



    public Actions(WebDriver driver){
        this.driver =driver;
    }

    //This method is to click on the desired element
    public void safeClick(By locator, String elementName, int...optionWaitTime){
        int waitTime=0;
        try{
            waitTime = getWaitTime(optionWaitTime);
            setImplicitWait(VERY_LONG_WAIT);
            if(isElementClickable(locator,waitTime)){
                WebElement element = driver.findElement(locator);
                element.click();
                log.info("clicked on the element "+elementName);
            }
            else{
                log.error(elementName+" is not clickable in time - "+waitTime+" Seconds");
                Assert.fail(elementName+" is not clickable in time - "+waitTime+" Seconds");
            }

        }
        catch(StaleElementReferenceException e){
            log.error(elementName+" is not attached to the page document StaleElementReferenceException "+ e);
        }
        catch(NoSuchElementException e){
            log.error(elementName + " was not found in DOM in time - " + waitTime + " Seconds" + " - NoSuchElementException "+ e);
        }
        catch(Exception e){
            log.error(elementName + " was not found on the web page "+e);
        }
    }

    //This method checks for the element in the DOM is clickable
    public boolean isElementClickable(By locator, int...optionWaitTime){
        int waitTime = getWaitTime(optionWaitTime);
        boolean flag = false;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); //nullify implicit wait
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            if(driver.findElement(locator).isDisplayed()){
                flag=true;
            }
        }
        catch(NoSuchElementException e){
            log.error("Element " + locator + " was not displayed in time - "+waitTime+" Seconds - NoSuchElementException");
        }
        catch (TimeoutException e)
        {
            log.error("Element " + locator + " was not found in time - "+waitTime+" Seconds - TimeoutException");
        }
        catch (Exception e)
        {
            log.error("Element " + locator + " was not found - " + e);
        }
        return flag;

    }

    public List<WebElement> findElementsBy(By Locator, String friendlyWebElementName) {
        List<WebElement> list = null;
        try {
            list = driver.findElements(Locator);
        } catch (Exception e) {
            log.error("Some exception occurred while retrieving web elements from " + friendlyWebElementName + " exception message: " + e);
            Assert.fail("Some exception occurred while retrieving web elements from " + friendlyWebElementName);
        }
        return list;
    }

    public String safeGetText(By locator, String friendlyWebElementName, int waitTime) {
        String sValue = null;
        try {
            if (isElementPresent(locator, waitTime)) {
                sValue = driver.findElement(locator).getText();
            } else {
                Assert.fail("Unable to find " + friendlyWebElementName + " in time - " + waitTime);
            }
        } catch (StaleElementReferenceException e) {
            log.error(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
            Assert.fail(friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
        } catch (NoSuchElementException e) {
            log.error(friendlyWebElementName + " was not found in DOM in time - " + waitTime + " - NoSuchElementException");
            Assert.fail(friendlyWebElementName + " was not found in DOM in time - " + waitTime + " - NoSuchElementException");
        } catch (Exception e) {
            log.error("Unable to get the text from " + friendlyWebElementName + " - " + e);
            Assert.fail("Unable to find " + friendlyWebElementName);
        }
        return sValue;
    }

    //This method waits for the page to load completely
    public void waitForPageToLoad(){
        try {
            int waitTime = 0;
            boolean isPageLoadComplete = false;
            do {
                isPageLoadComplete = ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete");
                Thread.sleep(500);
                waitTime++;
                if (waitTime > 500) {
                    break;
                }
            }
            while (!isPageLoadComplete);

                if (!isPageLoadComplete) {
                    log.error("Unable to load webpage with in the default timeout of 250 seconds");
                    Assert.fail("Unable to load webpage within the default timeout of 250 seconds");
                }
            }
            catch(Exception e){
                log.error("Unable to load web page - " + e);
                Assert.fail("unable to load webpage "+e);
            }
    }

    //This method is to fetch the optional Wait time from interface
    protected int getWaitTime(int[] optionalWaitArray)
    {
        if(optionalWaitArray.length<=0)
        {
            return MEDIUM_WAIT;
        }
        else
        {
            return optionalWaitArray[0];
        }
    }

    //This method sets implicit wait time
    protected void setImplicitWait(int waitTimeInSeconds)
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTimeInSeconds));
    }

    //This method is to check for the presence of element in the page loaded
    protected boolean isElementPresent(By locator, int waitTime)
    {
        boolean bFlag = false;
        log.info("Waiting for presence of element " + locator);
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if(driver.findElement(locator).isDisplayed()||driver.findElement(locator).isEnabled())
            {
                bFlag = true;
                log.info("Element " + locator + " is displayed");
            }
        }
        catch (NoSuchElementException e)
        {
            log.info("Element " + locator + " was not found in DOM in time - "+waitTime+" Seconds - NoSuchElementException");
        }
        catch (TimeoutException e)
        {
            log.info("Element " + locator + " was not displayed in time - "+waitTime+" Seconds"+" - TimeoutException");
        }
        catch (Exception e) {

            log.error("Element " + locator + " is not found - " + e);
        }
        return bFlag;
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public String getCurrentURL(){
        String sUrl = null;
        try {
            sUrl = driver.getCurrentUrl();
        } catch (Exception e) {
            log.error("Some exception occurred while retrieving current url, exception message: " + e);
            Assert.fail("Some exception occurred while retrieving current url");
        }
        return sUrl;
    }
}
