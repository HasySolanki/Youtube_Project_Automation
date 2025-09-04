package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;
        // Wrappers wapperClass = new Wrappers();
        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                 
        }

        @Test(enabled = true)
        public void testCase01() {
              driver.get("https://www.youtube.com/");

                WebElement aboutLink = driver.findElement(By.xpath("//a[contains(text(), 'About')]"));
                try {

                        if (!aboutLink.isDisplayed()) {
                                // Thread.sleep(1000);
                                WebElement menu_Button = driver.findElement(By.xpath(
                                                "(//span[contains(@class, 'yt-icon-shape style-scope yt-icon yt-spec-icon-shape')])[1]"));
                                Wrappers.clickOnbuttonJS(driver, menu_Button);
                                Wrappers.clickOnbuttonJS(driver, aboutLink);
                        } else {
                                // Thread.sleep(1000);
                                Wrappers.clickOnbuttonJS(driver, aboutLink);
                        }
                        WebElement titleElement = driver.findElement(
                                        By.xpath("//h1[contains(normalize-space(text()),'About YouTube')]"));
                        boolean status = titleElement.isDisplayed();
                        Assert.assertEquals(status, true);

                        System.out.println("titleElement :- " + titleElement.getText());
                        // Thread.sleep(10000);
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("About Element is not Visisble " + e.getMessage());
                }

        }

        @Test(enabled = true)
        public void testCase02() throws Throwable {
                driver.get("https://www.youtube.com/");
                System.out.println("Testcase 2 Starting...");

                SoftAssert softAssert = new SoftAssert();

                Thread.sleep(5000);
                WebElement movieElement = driver.findElement(By
                                .xpath("//yt-formatted-string[contains(text(),'Films') or contains(text(),'Movie')]"));
                Wrappers.clickOnbuttonJS(driver, movieElement);
                Thread.sleep(5000);
                WebElement topSellingElement = driver.findElement(By.xpath("//span[@id='title']"));

                boolean topSeEl = topSellingElement.getText().equals("Top selling");
                softAssert.assertEquals(topSeEl, true);
                Thread.sleep(5000);

                WebElement nextArrorElement = driver.findElement(By.xpath(
                                "(.//ytd-button-renderer[contains(@class,'style-scope yt-horizontal-list-renderer arrow')])[2]"));
                Wrappers.clickonlast(driver, nextArrorElement);
                WebElement lastThumbElement = driver.findElement(By.xpath(
                                ".//ytd-grid-movie-renderer[contains(@class,'style-scope yt-horizontal-list-renderer')][last()]"));
                boolean status1 = lastThumbElement.isDisplayed();
                WebElement movieMark = driver.findElement(By.xpath(
                                "(.//div[contains(@class,'badge  badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer')])[last()]"));

                String movieMarkString = movieMark.getText();
                softAssert.assertEquals(
                                movieMarkString.equalsIgnoreCase("U/A") ||
                                                movieMarkString.equalsIgnoreCase("U") ||
                                                movieMarkString.equalsIgnoreCase("A"),
                                true);
                WebElement drameElement = driver.findElement(By.xpath(
                                ".//ytd-grid-movie-renderer[contains(@class,'style-scope yt-horizontal-list-renderer')][last()]//span[starts-with(@class,\"grid\")]"));

                String DramStr = Wrappers.getSpitLeftString(driver, drameElement);

                softAssert.assertEquals(status1, true);
                softAssert.assertEquals(
                                DramStr.equalsIgnoreCase("Drama") ||
                                                DramStr.equalsIgnoreCase("Animation") ||
                                                DramStr.equalsIgnoreCase("Comedy"),
                                true);

                softAssert.assertAll();
        }

        @Test(enabled = true)
        public void testCase03() {

                try {
                       // driver.get("https://www.youtube.com/");
                        System.out.println("Start Testcases 3 :-");
                        WebElement musicElement = driver.findElement(By.xpath("//a[@title='Music']"));
                      
                       
                        Wrappers.clickOnbutton(driver, musicElement);
                         Thread.sleep(3000);
                          WebElement showmorWebElement = driver.findElement(By.xpath("//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']//yt-button-shape//span[text()='Show more']"));
                          Wrappers.clickOnbuttonJS(driver, showmorWebElement);
    
                        By locate_Elemnent = By.xpath(
                                        "//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']");
                        int soungCount = Wrappers.movieDetails(driver, locate_Elemnent);
                        System.out.println("soungCount" + soungCount);
                        SoftAssert sa = new SoftAssert();
                        sa.assertTrue(soungCount <= 50);
                        sa.assertAll();
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(" Testcases 3 :- Fail");
                }
                System.out.println("End Testcases 3");

        }
        @Test(enabled =true)
        public void testCase04(){
                try {
                   //     driver.get("https://www.youtube.com/");
                        System.out.println("Starting Testcase 4...");
                        WebElement newsElement = driver.findElement(By.xpath("//a[@title='News']"));
                       Wrappers.clickOnbutton(driver, newsElement);
                         Thread.sleep(3000);
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                     WebElement containsCards= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']")));
                       
                      Thread.sleep((new java.util.Random().nextInt(3)+2)*1000); 
                      long sumofVotes = 0;
                      for(int i =0;i<3;i++){
                       System.out.println( Wrappers.findElementAndshowme(driver, By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']//div[@id='header']"), containsCards, i));
                       System.out.println( Wrappers.findElementAndshowme(driver, By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']//div[@id='body']"), containsCards, i));
                       String res= Wrappers.findElementAndshowme(driver, By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']//span[@id='vote-count-middle']"), containsCards, i);

                        sumofVotes = sumofVotes + Wrappers.convertLongValue(res);

                        Thread.sleep((new java.util.Random().nextInt(3)+2)*1000); 
                      }
                      System.out.println("sumofVotes. "+sumofVotes);
                        System.out.println("Ends Testcase 4");
                        
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Testcase 4 is Failed");
                }
        }


        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}
