package base;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utilities.UtilityMethods;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BaseSetup {
    ThreadLocal<WebDriver> threadSafeDriver = new ThreadLocal<WebDriver>();
    private WebDriver driver;
    public static final String USERNAME = "hemanthraja_livsaU";
    public static final String ACCESS_KEY = "3yqf5ns63pkZHRpGUzKs";
    public static final String BROWSER_STACK_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void initializeDesiredBrowser(@Optional("chrome")String browser) throws MalformedURLException {
        String executionMode = UtilityMethods.getProperty("Execution");
        if(executionMode.equalsIgnoreCase("Local")){
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            setDriver(driver);
        }
        else{
            DesiredCapabilities caps = new DesiredCapabilities();

            switch (browser) {
                case "chrome":
                    MutableCapabilities capabilities = new MutableCapabilities();
                    HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
                    capabilities.setCapability("browserName", "Chrome");
                    bstackOptions.put("os", "Windows");
                    bstackOptions.put("osVersion", "10");
                    bstackOptions.put("browserVersion", "120.0");
                    bstackOptions.put("userName", "hemanthraja_livsaU");
                    bstackOptions.put("accessKey", "3yqf5ns63pkZHRpGUzKs");
                    bstackOptions.put("consoleLogs", "info");
                    capabilities.setCapability("bstack:options", bstackOptions);
                    driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), capabilities);
                    driver.manage().window().maximize();
                    setDriver(driver);
                    break;
                case "firefox":
                    MutableCapabilities ffcapabilities = new MutableCapabilities();
                    HashMap<String, Object> ffbstackOptions = new HashMap<String, Object>();
                    ffcapabilities.setCapability("browserName", "Firefox");
                    ffbstackOptions.put("os", "Windows");
                    ffbstackOptions.put("osVersion", "10");
                    ffbstackOptions.put("browserVersion", "latest");
                    ffbstackOptions.put("userName", "hemanthraja_livsaU");
                    ffbstackOptions.put("accessKey", "3yqf5ns63pkZHRpGUzKs");
                    ffcapabilities.setCapability("bstack:options", ffbstackOptions);
                    driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), ffcapabilities);
                    driver.manage().window().maximize();
                    setDriver(driver);
                    break;
                case "edge":
                    MutableCapabilities edgeCapabilities = new MutableCapabilities();
                    HashMap<String, Object> edgeBstackOptions = new HashMap<String, Object>();
                    edgeCapabilities.setCapability("browserName", "Edge");
                    edgeBstackOptions.put("os", "Windows");
                    edgeBstackOptions.put("osVersion", "11");
                    edgeBstackOptions.put("browserVersion", "latest");
                    edgeBstackOptions.put("userName", "hemanthraja_livsaU");
                    edgeBstackOptions.put("accessKey", "3yqf5ns63pkZHRpGUzKs");
                    edgeCapabilities.setCapability("bstack:options", edgeBstackOptions);
                    driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), edgeCapabilities);
                    driver.manage().window().maximize();
                    setDriver(driver);
                    break;
                case "safari":
                    MutableCapabilities safariCapabilities = new MutableCapabilities();
                    HashMap<String, Object> safariBstackOptions = new HashMap<String, Object>();
                    safariCapabilities.setCapability("browserName", "safari");
                    safariBstackOptions.put("osVersion", "14");
                    safariBstackOptions.put("deviceName", "iPad Air 4");
                    safariBstackOptions.put("userName", "hemanthraja_livsaU");
                    safariBstackOptions.put("accessKey", "3yqf5ns63pkZHRpGUzKs");
                    safariCapabilities.setCapability("bstack:options", safariBstackOptions);
                    driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), safariCapabilities);
                    driver.manage().window().maximize();
                    setDriver(driver);
                    break;
                case "chrome_mobile":
                    MutableCapabilities mobCapabilities = new MutableCapabilities();
                    HashMap<String, Object> mobBstackOptions = new HashMap<String, Object>();
                    mobCapabilities.setCapability("browserName", "chrome");
                    mobBstackOptions.put("osVersion", "13.0");
                    mobBstackOptions.put("deviceName", "Samsung Galaxy S23 Ultra");
                    mobBstackOptions.put("userName", "hemanthraja_livsaU");
                    mobBstackOptions.put("accessKey", "3yqf5ns63pkZHRpGUzKs");
                    mobBstackOptions.put("consoleLogs", "info");
                    mobCapabilities.setCapability("bstack:options", mobBstackOptions);
                    driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), mobCapabilities);
                    driver.manage().window().maximize();
                    setDriver(driver);
                    break;
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    public WebDriver getDriver(){
        return threadSafeDriver.get();
    }

    public void setDriver(WebDriver driver){
        threadSafeDriver.set(driver);
    }

    public String loadURL(){
        String autURL = UtilityMethods.getProperty("App.URL");
        return autURL;
    }

}
