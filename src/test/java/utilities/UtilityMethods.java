package utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UtilityMethods {
    private static String fileSeperator = System.getProperty("file.separator");
    private static Properties properties = new Properties();
    private static Logger log = Logger.getLogger("UtilityMethods");
    private static final String API_URL = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";
    private static final String API_KEY = "01fe57e725msh459896ebceb25acp13b943jsnf38a75b0c8be"; // Replace with your actual RapidAPI key

    public static void loadProperties(){
        FileInputStream fis;
        String path = System.getProperty("user.dir")+fileSeperator+"src"+fileSeperator+"test"+
                fileSeperator+"java"+fileSeperator+"System.properties";
        try {
            fis = new FileInputStream(path);
            properties.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            log.error("Cannot find configuration file - " +"System.properties" + " at "
                    + path);
            Assert.fail("Cannot find configuration file - " + "System.properties" + " at "
                    + path);
        } catch (IOException e) {
            log.error("Cannot read configuration file - " + " at " + path);
            Assert.fail("Cannot read configuration file - " + " at " + path);
        }
    }

    public static String getProperty(String key) {
        String value = "";
        if (key != "") {
            loadProperties();
            try {
                if (!properties.getProperty(key).trim().isEmpty())
                    value = properties.getProperty(key).trim();
            } catch (NullPointerException e) {
                Assert.fail("Key - '" + key + "' does not exist or not given a value in "
                        + "System.properties file");
            }
        } else {
            log.error("key cannot be null.. ");
            Assert.fail("key cannot be null.. ");
        }
        return value;
    }

    public static void takeScreenshotOfAnElement(WebDriver driver, WebElement imageElement) throws IOException {
        // Scroll the element into view to ensure it's fully loaded
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", imageElement);
        // Take screenshot of the full page
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Save the cropped image
        File screenshotLocation = new File("Screenshots"+fileSeperator+"image_screenshot_"+getCurrentDateTime().replace(" ","").
                replace(":","")+".png");
        FileHandler.copy(screenshot, screenshotLocation);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).format(formatter);
    }

    public static By getNewLocator(By locator, String dynamicText) {
        String locatorType = locator.toString().split(": ")[0].split("\\.")[1];
        String newLocatorString = String.format(locator.toString().split(": ")[1], dynamicText);
        switch (locatorType) {
            case "xpath":
                locator = By.xpath(newLocatorString);
                break;
            case "cssSelector":
                locator = By.cssSelector(newLocatorString);
                break;
            case "id":
                locator = By.id(newLocatorString);
                break;
            case "className":
                locator = By.className(newLocatorString);
                break;
            case "name":
                locator = By.name(newLocatorString);
                break;
            case "linkText":
                locator = By.linkText(newLocatorString);
                break;
            case "partialLinkText":
                locator = By.partialLinkText(newLocatorString);
                break;
            case "tagName":
                locator = By.tagName(newLocatorString);
                break;
        }
        return locator;
    }

    public static String translateStringToEnglish(String spanishText){
        Response response = RestAssured.given()
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"from\": \"es\",\n" +
                        "    \"to\": \"en\",\n" +
                        "    \"q\": \"" + spanishText + "\"\n" +
                        "}")
                .post(API_URL);

        response.then().statusCode(200);

        String translatedText = response.jsonPath().getString("[0]");
        return translatedText;
    }

    public static void checkForDuplicateWordsInTitleAndPrint(ArrayList<String> articlesNames){
        //Initialize a hash map to store key value pairs of word and its occurrences
        Map<String,Integer> wordCounts = new HashMap<>();

        //Combine all the words present in the titles into a single String
        StringBuilder concatenatedString = new StringBuilder();
        for(String name : articlesNames){
            concatenatedString.append(name).append(" ");
        }

        //Split the whole string into array of words by splitting them with white spaces
        String[] arrayOfWords = concatenatedString.toString().toLowerCase().split("\\s+");

        //Add the words to a hash map, key as words and value is the count of the word occurrence
        for(String word: arrayOfWords){
            wordCounts.put(word, wordCounts.getOrDefault(word,0)+1);
        }

        boolean flag = false;
        System.out.println("**********************");
        for(Map.Entry<String,Integer> entry : wordCounts.entrySet()){
            if(entry.getValue()>2){
                flag = true;
                System.out.println(entry.getKey()+" is repeated "+entry.getValue()+" times");
            }
        }
        if(!flag){
            System.out.println("No duplicate words are present in top 5 fetched titles");
        }
    }
}
