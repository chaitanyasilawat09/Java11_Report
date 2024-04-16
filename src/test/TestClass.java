package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class TestClass {

    @Test
    public void beforeMethod() {

        WebDriver driver = new FirefoxDriver();
//        WebDriver driver = new EdgeDriver();
        driver.get("https://www.google.com/");
        int a = 10;
// selenium 3
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(2, TimeUnit.MINUTES);
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
// selenium 4
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));


//        Selenium 3
//        new WebDriverWait(driver, 3)
//                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#id")));

//        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
//                .withTimeout(30, TimeUnit.SECONDS)
//                .pollingEvery(5, TimeUnit.SECONDS)
//                .ignoring(NoSuchElementException.class);
//Selenium 4

//        new WebDriverWait(driver, Duration.ofSeconds(3))
//                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#id")));
//
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(Duration.ofSeconds(30))
//                .pollingEvery(Duration.ofSeconds(5))
//                .ignoring(NoSuchElementException.class);
//
//        WebElement element = driver.findElement(with(By.tagName("input")).toRightOf(By.xpath("(//input[@value='Google Search'])[2]")));

//        element.click();

//        driver.quit();

    }
}
































//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.manager.SeleniumManagerOutput;
//import org.openqa.selenium.remote.service.DriverFinder;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestClass {
//
//    @Test
//    public void test() {
////        System.setProperty("webdriver.http.factory", "jdk-http-client");
////        System.setProperty("webdriver.chrome.driver", "chrome118\\chromedriver.exe");
//
//        ChromeOptions chromeOptions = new ChromeOptions();
////        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver(chromeOptions);
//
////
////        // Create an object of Chrome Options class
////        ChromeOptions options = new ChromeOptions();
////
////        // pass the argument -–headless and maximize to Chrome Options class.
////        options.addArguments("--start-maximize");
////        options.addArguments("--headless=new");
////
////        // Create an object of Chrome Driver class and pass the Chrome Options object as
////        // an argument
////        WebDriver driver =new ChromeDriver();
////
////
////        System.out.println("Executing Chrome Driver in Headless mode..");
////        driver.get("https://duckduckgo.com/");
////
////        String titlePage = driver.getTitle();
////        System.out.println("Title of Page :" + titlePage);
//////        Assertions.assertEquals("DuckDuckGo — Privacy, simplified.",titlePage);
////
////        // Close the driver
////        driver.close();
//
//    }
//}
