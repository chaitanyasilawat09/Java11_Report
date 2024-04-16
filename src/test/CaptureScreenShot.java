package test;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaptureScreenShot
{

    public String captureScreen(WebDriver driver, String pageName, Boolean flag, String Path)
    {
        if(flag)
        {
            try
            {
                String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
                String screenshotName =timeStamp +"_"+ pageName;
                FileOutputStream out = new FileOutputStream(Path +"//"+screenshotName+".jpg");
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                out.close();
                String[] getPath =  Path.split("\\Screenshots");
/*				Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
				ImageIO.write(screenshot.getImage(),"PNG",new File(Path +"//"+screenshotName+".jpg"));*/
                return "..\\Screenshots"+getPath[1]+"\\" +screenshotName+".jpg";
            }
            catch (UnhandledAlertException Ex) {
                driver.switchTo().alert().accept();
                driver.switchTo().defaultContent();
            }

            catch (Exception e)
            {
                BaseTest.log.error("Error obtained while capturing screenshot."+e.getLocalizedMessage());
                System.out.println("Error Obtained in Screen Shot capture"+e+"\nCause: "+e.getCause()+"\nStackTrace: "+e.getStackTrace());
                return null;
            }
        }
        return null;
    }
}
