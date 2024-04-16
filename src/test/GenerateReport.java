package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

import java.util.UUID;

public class GenerateReport  extends BaseTest{

    public GenerateReport() {
        super("GenerateReport");
    }

    Boolean isFail= false;

    @Test
    public void test12() throws InterruptedException {

        BasePage basePage = new BasePage(getDriver());
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[0].getMethodName();
        // Fetch the log file path where the test's log file will get save
        String logFilePath = basePage.getAbsolutePath();
        String uuid = UUID.randomUUID().toString();
        log = new BaseLogger(uuid + "_" + methodName, logFilePath);
            log.info("Method name is" + methodName);

            log.info("My Test", true);
        this.createScreenshotFolders(product, methodName, "languageUsed", false, log);
        basePage.clearValues();
        this.commitReports(log, screenShotFlag, false, isFail, BaseTest.commonErrorList);


        driver.quit();
    }

    @Test
    public void test122() throws InterruptedException {

//        WebDriver driver = new EdgeDriver();
//        driver.get("https://www.google.com/");
//        Thread.sleep(5000);
//        driver.manage().window().maximize();
//        driver.quit();

        BasePage basePage = new BasePage(getDriver());
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[0].getMethodName();
        // Fetch the log file path where the test's log file will get save
        String logFilePath = basePage.getAbsolutePath();
        String uuid = UUID.randomUUID().toString();
        log = new BaseLogger(uuid + "_" + methodName, logFilePath);
        log.info("Method name is" + methodName);

        log.info("My Test", true);
        this.createScreenshotFolders(product, methodName, "languageUsed", false, log);
        basePage.clearValues();
        this.commitReports(log, screenShotFlag, false, isFail, BaseTest.commonErrorList);


        driver.quit();


    }
}
