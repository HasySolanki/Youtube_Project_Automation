package demo.wrappers;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Wrappers {
   /*
    * Write your selenium wrappers here
    */
   WebDriver driver;


   public Wrappers(WebDriver driver) {
       this.driver = driver;
   }


   public static void clickOnbutton(WebDriver driver, WebElement element) {
       try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
           wait.until(ExpectedConditions.visibilityOf(element));
           if (element != null) {
               element.click();
           }


       } catch (Exception e) {
           // TODO: handle exception
           System.out.println("Element is not clickable " + e.getMessage());
       }
   }


   public static void clickOnbuttonJS(WebDriver driver, WebElement element) {
       try {
          // Thread.sleep(5000);
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
           wait.until(ExpectedConditions.visibilityOf(element));
           if (element != null) {
               JavascriptExecutor js = (JavascriptExecutor) driver;
               js.executeScript("arguments[0].click();", element);
           }
       } catch (Exception e) {
           // TODO: handle exception
       }


   }
   public static void clickonlast(WebDriver driver, WebElement element){


       try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
           wait.until(ExpectedConditions.visibilityOf(element));
           while (element.isDisplayed()) {
               Thread.sleep(5000);
               element.click();
           }
          
       } catch (Exception e) {
           // TODO: handle exception
            System.out.println("Not Get last Element in movies "+ e.getMessage());
       }


   }

   public static String getSpitLeftString(WebDriver driver, WebElement element){
     String lefString = "";
    try {
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
           wait.until(ExpectedConditions.visibilityOf(element));
           if (element.isDisplayed()) {
               Thread.sleep(5000);
               String fullSting = element.getText();
             String[] partsString = fullSting.split("•");
            lefString = partsString[0].trim(); 
            System.out.println(lefString);
             return lefString;
           }

    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Not Get Left String in Whole String "+ e.getMessage());
    }
    return lefString;
   }

   public static int movieDetails(WebDriver driver , By locateby) throws InterruptedException{
    WebElement songsElement = driver.findElement(locateby);
    
    Thread.sleep(3000);
    
    List<WebElement> musicList = songsElement.findElements(By.xpath(".//div[@id='content']"));
    String playlistName=musicList.get(4).findElement(By.xpath(".//h3[contains(@class,'model__heading')]")).getText();
    System.out.println("PlaylistName: "+playlistName);
    String mostrightSongsCount= musicList.get(4).findElement(By.xpath(".//div[contains(@class,'yt-badge-shape__text')]")).getText();
    return Integer.parseInt(mostrightSongsCount.split(" ")[0]);

   }

   public static String findElementAndshowme(WebDriver driver, By locator , WebElement we , int elementNo){
   
    List<WebElement> elements = we.findElements(locator);
    String txt="";
    for (WebElement webElement : elements) {
        txt = elements.get(elementNo).getText();
    }
    return txt;
   }

   public static long convertLongValue(String value){

    value = value.trim().toUpperCase();
    char lastcharvalue= value.charAt(value.length()-1);
    int multiple=1;
    switch (lastcharvalue) {
        case 'K':
            multiple=1000;
            break;
         case 'M':
            multiple=1000000;
            break;  
        case 'B':
            multiple=1000000000;
            break;        
    
        default:
            if(Character.isDigit(lastcharvalue)){
                return Long.parseLong(value);
            }
            throw new IllegalArgumentException(value);
    }
    String numbricPart= value.substring(0, value.length()-1);
    double number = Double.parseDouble(numbricPart);

    return (long) (number *multiple);

   }



}

