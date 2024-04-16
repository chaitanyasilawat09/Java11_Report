package test;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class BaseLogger {
    private String uniqueID = null;
    private File file = null;
    private Writer writer = null;    
    private int i=0;
    private String fileName = null;
    private String filePath = null;
    File folder = null;
    String targetLocationPath = null;
   public static String targetLocationPathForS3 = null;
    
    public String getFileName(){
        return this.fileName;
    }

    public String getFilePath(){
        return this.filePath;
    }
    public BaseLogger() {
    	
    }
    public BaseLogger(String id,String path)
    {
        uniqueID = id;
       // stringBuilder.append(HtmlHead);
    
    try {
        this.fileName = "log_" + uniqueID + ".html";
        this.filePath =path + this.fileName ;
        file = new File(this.filePath);
        //writer = new BufferedWriter(new FileWriter(file));
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF8")); 
        writer.append(HtmlHead);
        
        BaseTest.loadAutomationProperties();
		String screenPath = BaseTest.screenshotsPath;
		String ssPath = new SimpleDateFormat("yyyy-MM-dd")
				.format(Calendar.getInstance().getTime()) +"\\" + UUID.randomUUID();
		folder = new File(screenPath + ssPath);
		if (!folder.exists()) {
			if (folder.mkdirs()) {
				System.out.println("Directory is created!");

			} else {
				System.out.println("Failed to create directory!");
			}
		}
		targetLocationPath = folder.toString();
		targetLocationPathForS3 = ssPath;
        }
        catch (Exception e) {
              // TODO Auto-generated catch block
             e.printStackTrace();
        }
        
    }
    private static final String HtmlFormat = "<tr>" +
            "<td>%s</td>" +
            "<td title='%s thread'>%s</td>" +
            "<td title='Level'>%s</td>" +
            "<td title='%s category'>%s</td>" +
            "<td>%s</td>" +
            "<td title='Message'>%s</td>" +
            "</tr>";
  private static String HtmlHead= "<head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\">�" +
                  "<style type='text/css'>" +
                  ".logTable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}"+
                  ".logTable th {font-size:12px;background-color:#00aeef;border-width: 1px;padding: 8px;border-style: solid;border-color:" +"#729ea5;text-align:left;}" +
                  ".logTable tr:nth-child(odd) {background-color:#cfd5d6;}" +
                  ".logTable tr:nth-child(even) {background-color:#ffffff;}" +
                  ".logTable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #cfd5d6;}" +
                  ".logTable tr:hover {background-color:#5cb3c4;}" +
                  ".txtColor {color:red;}" +
                  ".warningText {color:yellow;}" +
                  "</style></head><body>" +
                  "<table class='logTable' border='1'>" +
                  "<tr><th>Index</th><th>Screenshot</th><th>DateTime</th><th>LogLevel</th><th>File:Line</th><th>Message</th></tr>";
                  
    public void setID(String id){
        uniqueID = id;
    }
    
    public void logMessage(String message, String loglevel, Boolean isScreenshot, int... num) throws Exception{
    	if(message.contains("<img")) 
    	{
    		message = message.replace("<", "&lt;");
    		message = message.replace(">", "&gt;");
    	}
    	int	getClassName=(num.length == 0 || num[0] == 0)? 3:num[0];    	
    	
    	String fullClassName = Thread.currentThread().getStackTrace()[getClassName].getClassName();            
    	String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    	String methodName = Thread.currentThread().getStackTrace()[getClassName].getMethodName();
    	int lineNumber = Thread.currentThread().getStackTrace()[getClassName].getLineNumber();
    	String stack = className + "." + methodName + "():" + lineNumber;

    	message =  getHtml(loglevel,message,stack,isScreenshot);
    	writer.append(message);
    }
    
    public String replaceHTMLTag(String message){
    	if(message.contains("wysiwyg")){
    		message = message.replace("<wysiwyg>", "");
    		message = message.replace("</wysiwyg>", "");
    		if(message.contains("h1")){
    			message = message.replace("<h1>", "");
    			message = message.replace("</h1>", "");
    		}else if(message.contains("h2")){
    			message = message.replace("<h2>", "");
    			message = message.replace("</h2>", "");
    		}else if(message.contains("h3")){
    			message = message.replace("<h3>", "");
    			message = message.replace("</h3>", "");
    		}else if(message.contains("h4")){
    			message = message.replace("<h4>", "");
    			message = message.replace("</h4>", "");
    		}else if(message.contains("h5")){
    			message = message.replace("<h5>", "");
    			message = message.replace("</h5>", "");
    		}else if(message.contains("h6")){
    			message = message.replace("<h6>", "");
    			message = message.replace("</h6>", "");
    		}
    	}
    	return message;
    }
    
    public void info(String infoMessage,Boolean isScreenshot)
    {
    	try {
            logMessage(infoMessage, "info", isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void info(String infoMessage)
    {
        info(infoMessage,false);
    }
    
    public void info(String infoMessage,WebDriver driver2,Boolean isScreenshot)
    {
    	try {
            logMessage(infoMessage, "info",driver2,isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void warning(String debugMessage,Boolean isScreenshot)
    {
        try {
            logMessage(debugMessage, "warning",isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    public void warning(String debugMessage)
    {
        warning(debugMessage,false);
    }
    public void error(String errorMessage, Exception e,Boolean isScreenshot)
    {
        try {
            logMessage(errorMessage + "\n\t" + e.getMessage(), "error",isScreenshot);
        }
        catch (Exception e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
        }
    }
    public void error(String errorMessage, Exception e)
    {
    	error(errorMessage,e,false);
    }
    public void error(String errorMessage,Boolean isScreenshot)
    {
        try {
            logMessage(errorMessage, "error",isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    
    public void error(String errorMessage,WebDriver driver2,Boolean isScreenshot)
    {
        try {
            logMessage(errorMessage, "error",driver2,isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    public void error(String errorMessage)
    {	
    	error(errorMessage,false);	
    }
    public void fatal(String fatalMessage,Boolean isScreenshot)
    {
        try {
            logMessage(fatalMessage, "fatal", isScreenshot);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    public void fatal(String fatalMessage)
    {
    	fatal(fatalMessage,false);
    }
    
    public void disposeLogger() throws IOException {
        
    writer.append("</table></body></html>");
        writer.flush();
    writer.close();
    file = null;
    }
    
    private String formatString(String type, String message ){
        return (String.format(HtmlFormat, 
                System.currentTimeMillis(),
                "main", 
                "Main",
                type,
                "Main Class",
                "Logger",
                "Line 1",          
                message));
    }
  
  /*private String getHtml(String type, String message, String info){
      i=i+1;
       
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:Ss");
      Date date = new Date();
      dateFormat.format(date);
      return "<tr><td>" + i + "</td><td>" + dateFormat.format(date) + "</td><td>" + type + "</td><td>"+info+"</td><td>" + message + "</td></tr>";
  }*/
    private String getHtml(String loglevel, String message, String stack, Boolean isScreenshot) throws InterruptedException {
    	String extentReportPath = null;
    	//Capture screen shot
    	CaptureScreenShot objCapture = new CaptureScreenShot();

    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:Ss");
    	Date date = new Date();

    	i=i+1;
    	String screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
    			targetLocationPath);
    	String screenshotLink;

    	if(isScreenshot) {
    		screenshotLink=  "<td><b><a href="+screenshotPath+" >" + "Screenshot_"+i+".jpg</a></b></td>" ;
    		String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
    		Thread.sleep(1000);
    		extentReportPath = screenshotPath.replace("..", envPath[0]);
    		extentReportPath = "<a href="+extentReportPath+" >" + this.replaceHTMLTag(message)+"</a>";
    	}else {
    		screenshotLink =	"<td>Screenshot Option Not Opted</td>" ;
    		extentReportPath = this.replaceHTMLTag(message);
    	}


    	if(loglevel.equalsIgnoreCase("error")){
    		loglevel = "<td class='txtColor'>" + loglevel + "</td>" ;
    		BaseTest.test.log(Status.ERROR, extentReportPath);
    	}else if(loglevel.equalsIgnoreCase("warning")) {
    		loglevel = "<td class='warningText'>" + loglevel + "</td>" ;
    		BaseTest.test.log(Status.WARNING, extentReportPath);
    	}else if(loglevel.equalsIgnoreCase("info")){
    		BaseTest.test.log(Status.INFO, extentReportPath);
    		loglevel = "<td>" + loglevel + "</td>" ;
    	}else if(loglevel.equalsIgnoreCase("fatal")){
    		loglevel = "<td class='txtColor'>" + loglevel + "</td>" ;
    		BaseTest.test.log(Status.FATAL, extentReportPath);
    	}

    	return  "<tr>" + 
    	"<td>" + i + "</td>" +
    	screenshotLink+
    	"<td>" + dateFormat.format(date) + "</td>" +   
    	loglevel +   
    	"<td>" + stack + "</td>" +
    	"<td>" + message + "</td>" +
        "</tr>";
    }
    private String  getHtml(String loglevel, String message, String stack,WebDriver driver2, Boolean isScreenshot){
    	//Capture screen shot
        CaptureScreenShot objCapture = new CaptureScreenShot();
        
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:Ss");
        Date date = new Date();
        
        if(loglevel.equalsIgnoreCase("error")){
            loglevel = "<td class='txtColor'>" + loglevel + "</td>" ;
        }else if(loglevel.equalsIgnoreCase("warning")) {
        	 loglevel = "<td class='warningText'>" + loglevel + "</td>" ;
        }else{
            loglevel = "<td>" + loglevel + "</td>" ;
        }
        i=i+1;
     
        String screenshotLink;
        String screenshotPath =  objCapture.captureScreen(driver2, "Screenshot_"+i, isScreenshot,
        		targetLocationPath);
        if(isScreenshot)
        	screenshotLink=  "<td><b><a href="+screenshotPath+" >" + "Screenshot_"+i+".jpg</a></b></td>" ;
        else
            screenshotLink =	"<td>Screenshot Option Not Opted</td>" ;
        return  "<tr>" + 
                    "<td>" + i + "</td>" +
                    screenshotLink+
                    "<td>" + dateFormat.format(date) + "</td>" +   
                             loglevel +   
                    "<td>" + stack + "</td>" +
                    "<td>" + message + "</td>" +
                    
                "</tr>";
        }
   /* public void logMessage(String message, String loglevel, Boolean isScreenshot,int num) throws IOException{
        CaptureScreenShot objCapture = new CaptureScreenShot();
        // message = StringEscapeUtils.escapeHtml3(message);
        if(message.contains("<img"))
        {
            message = message.replace("<", "&lt;");
            message = message.replace(">", "&gt;");
        }
        String fullClassName = Thread.currentThread().getStackTrace()[num].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[num].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[num].getLineNumber();
        // String info = className + "." + methodName + "():" + lineNumber;
        String stack = className + "." + methodName + "():" + lineNumber;
        // message = getHtml(loglevel,message,stack);
        // System.out.println("Stack :"  +stack);

        String extentReportContent = this.replaceHTMLTag(message);
        switch (loglevel.trim().toLowerCase())
        {
            case "info":
                if(isScreenshot){
                    String screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
                            targetLocationPath);
                    String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
                    screenshotPath = screenshotPath.replace("..", envPath[0]);
                    screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
                    BaseTest.test.log(Status.INFO, screenshotPath);
                    // BaseTest.logger.log(LogStatus.INFO, screenshotPath);
                }
                else
                    BaseTest.test.log(Status.PASS, extentReportContent);

                //BaseTest.logger.log(LogStatus.INFO, extentReportContent);
                break;
            case "warning":
                if(isScreenshot){
                    String screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
                            targetLocationPath);
                    String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
                    screenshotPath = screenshotPath.replace("..", envPath[0]);
                    screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
                    BaseTest.test.log(Status.WARNING, screenshotPath);
                    // BaseTest.logger.log(LogStatus.INFO, screenshotPath);
                }else
                    BaseTest.test.log(Status.WARNING, extentReportContent);
                break;
            case "error":
                //  BaseTest.logger.log(LogStatus.ERROR, extentReportContent);
                if(isScreenshot){
                    String screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
                            targetLocationPath);
                    String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
                    screenshotPath = screenshotPath.replace("..", envPath[0]);
                    screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
                    BaseTest.test.log(Status.ERROR, screenshotPath);
                    // BaseTest.logger.log(LogStatus.INFO, screenshotPath);
                }else
                    BaseTest.test.log(Status.ERROR, extentReportContent);
                break;
            case "fatal":
                BaseTest.test.log(Status.FAIL, extentReportContent);
            screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
            		targetLocationPath);
            BaseTest.logger.log(LogStatus.FATAL, screenshotPath);
                break;
        }

        message =  getHtml(loglevel,message,stack,isScreenshot);
        switch (loglevel.trim().toLowerCase())
        {
            case "info":
                writer.append(message);
                //BaseTest.logger.log(LogStatus.INFO, message);
                break;
            case "warning":
                writer.append(message);
                break;
            case "error":
                writer.append(message);
                //  BaseTest.logger.log(LogStatus.ERROR, message);
                break;
            case "fatal":
                writer.append(message);
                // BaseTest.logger.log(LogStatus.FAIL, message);
                break;
            default:
                writer.append(message);
                break;
        }
    }*/
    public void info(String infoMessage,Boolean isScreenshot,boolean isBold)
    {
    	try {
            logMessage("<wysiwyg><h3>"+infoMessage+"</h3></wysiwyg>", "info",isScreenshot);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logMessage(String message, String loglevel,WebDriver driver2,Boolean isScreenshot) throws IOException{
    	CaptureScreenShot objCapture = new CaptureScreenShot();
    	// message = StringEscapeUtils.escapeHtml3(message);
    	if(message.contains("<img")) 
    	{
    		message = message.replace("<", "&lt;");
    		message = message.replace(">", "&gt;");
    	}
    	String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
    	String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    	String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
    	int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
    	String stack = className + "." + methodName + "():" + lineNumber;
    	String screenshotPath=null ;
    	String extentReportContent = this.replaceHTMLTag(message);
    	switch (loglevel.trim().toLowerCase())
    	{
    	case "info":
    		if(isScreenshot){
    			screenshotPath =  objCapture.captureScreen(driver2, "Screenshot_"+i, isScreenshot,
    					targetLocationPath);
    			String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
    			screenshotPath = screenshotPath.replace("..", envPath[0]);
    			screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
    			BaseTest.test.log(Status.INFO, screenshotPath);
    			// BaseTest.logger.log(LogStatus.INFO, screenshotPath);
    		}
    		else
    			BaseTest.test.log(Status.INFO, extentReportContent);

    		//BaseTest.logger.log(LogStatus.INFO, extentReportContent);
    		break;
    	case "warning":
    		if(isScreenshot){
    			screenshotPath =  objCapture.captureScreen(driver2, "Screenshot_"+i, isScreenshot,
    					targetLocationPath);
    			String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
    			screenshotPath = screenshotPath.replace("..", envPath[0]);
    			screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
    			BaseTest.test.log(Status.WARNING, screenshotPath);
    			// BaseTest.logger.log(LogStatus.INFO, screenshotPath);
    		}else
    			BaseTest.test.log(Status.WARNING, extentReportContent);
    		break;
    	case "error":
    		//  BaseTest.logger.log(LogStatus.ERROR, extentReportContent);
    		if(isScreenshot){
    			screenshotPath =  objCapture.captureScreen(driver2, "Screenshot_"+i, isScreenshot,
    					targetLocationPath);
    			String[] envPath = BaseTest.environmentPath.split("TestExecutionReports");
    			screenshotPath = screenshotPath.replace("..", envPath[0]);
    			screenshotPath = "<a href="+screenshotPath+" >" + extentReportContent+"</a>";
    			BaseTest.test.log(Status.ERROR, screenshotPath);
    			// BaseTest.logger.log(LogStatus.INFO, screenshotPath);
    		}else
    			BaseTest.test.log(Status.ERROR, extentReportContent);
    		break;
    	case "fatal":
    		BaseTest.test.log(Status.FAIL, extentReportContent);
    		/* screenshotPath =  objCapture.captureScreen(BaseTest.driver, "Screenshot_"+i, isScreenshot,
         		targetLocationPath);
         BaseTest.logger.log(LogStatus.FATAL, screenshotPath);*/
    		break;
    	}

    	message =  getHtml(loglevel,message,stack,driver2,isScreenshot);
    	switch (loglevel.trim().toLowerCase())
    	{
    	case "info":
    		writer.append(message);
    		//BaseTest.logger.log(LogStatus.INFO, message);
    		break;
    	case "warning":
    		writer.append(message);
    		break;
    	case "error":
    		writer.append(message);
    		//  BaseTest.logger.log(LogStatus.ERROR, message);
    		break;
    	case "fatal":
    		writer.append(message);
    		// BaseTest.logger.log(LogStatus.FAIL, message);
    		break;
    	default:
    		writer.append(message);
    		break;
    	}
    }}

