package test;

//import com.ceb.shl.test.DataModel.PageElements;
//import com.ceb.shl.test.Integration.pagemodel.TCITestHarness;
//import com.ceb.shl.test.helper.*;
//import com.ceb.shl.test.shlonline.pageModel.*;
//import com.ceb.shl.test.shlonline.tests.TestCeDpn;
//import com.ceb.shl.test.util.*;
//import com.ceb.shl.test.util.entity.UtilClass;
//import com.ceb.shl.test.vr.pageModel.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.function.Function;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class BasePage {

	String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	public String timeStamp = new SimpleDateFormat("ddMMyyHHmmss").format(Calendar.getInstance().getTime());
	// String screenshotsPath = BaseTest.screenshotsPath;

	String screenshotsPath = null;
	File currentDateFolder = null;
	public static String targetLocationString;
	public static String targetLocationPath;
	public static String targetLocationPathfailure;
	public String jobID;

//	private SpecializedScreenRecorder screenRecorder;
	Boolean isEmpty = false;
	protected WebDriver driver = null;
	protected WebDriverWait wait; // = new WebDriverWait(driver, 30);

	protected List<PageElements> pageWebElements = null;
	protected GetWebElements getWebElements = new GetWebElements();

	// Define object for Screen shot.
	public CaptureScreenShot objCapture = new CaptureScreenShot();

	/**
	 * Constructor to set the WebDriver instance.
	 * 
	 * @param driver
	 *            Driver instance to be set.
	 */
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Get all the WebElements.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public WebElement getWebElement(String elementName) {
		return getWebElements.getVal(driver, pageWebElements, elementName);
	}

	public String getLocatorValue(String elementName) {
		return getWebElements.getLocValue(driver, pageWebElements, elementName);

	}

	/**
	 * Get all the WebElements Text.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementText(String elementName) {
		return getWebElements.getElementText(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements isEnabled value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementIsEnabledValue(String elementName) {
		return getWebElements.getElementIsEnabled(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements isEnabled value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementLocatorValue(String elementName) {
		return getWebElements.getLocatorValue(driver, pageWebElements, elementName);
	}

	/**
	 * This method will wait and then click on the element
	 *
	 * @param element: element that we want to click
	 */
	public void waitAndClickOnElement(WebElement element) {
		waitForElementToBeClickable(element);
		element.click();
	}

	/**
	 * This method will wait for JS and JQuery to load
	 *
	 * @return: true/false
	 */
	public boolean waitForJSandJQueryToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(10));
		try {
			// wait for jQuery to load
			ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					try {
						return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
					} catch (Exception e) {
						// no jQuery present
						return true;
					}
				}
			};

			// wait for Javascript to load
			ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState")
							.toString().equals("complete");
				}
			};
			return wait.until(jQueryLoad) && wait.until(jsLoad);
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * This method is used to check if element has attribute or not
	 *
	 * @param element:   element
	 * @param attribute: attribute name
	 * @return: true/false
	 */
	public boolean verifyIfElementHasAttribute(WebElement element, String attribute) {
		if (element.getAttribute(attribute) == null)
			return false;
		return true;
	}

	/**
	 * Get all the WebElements isDisplayed value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementIsDisplayedValue(String elementName) {
		return getWebElements.getElementIsDisplayed(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements isSelected value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementIsSelectedValue(String elementName) {
		return getWebElements.getElementIsSelected(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements isWaterMarkText value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementIsWaterMarkTextValue(String elementName) {
		return getWebElements.getElementIsWaterMarkText(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements ElementDisplayName value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementDisplayName(String elementName) {
		return getWebElements.getElementDisplayName(driver, pageWebElements, elementName);
	}

	/**
	 * Get all the WebElements ElementType value.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return PageWebElement - Get all the elements related to the page.
	 */
	public String getWebElementType(String elementName) {
		return getWebElements.getElementType(driver, pageWebElements, elementName);
	}

	/**
	 * Get the WebElement when the element is visible.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return WebElement
	 */
	public WebElement getAndWaitForElement(String elementName) {
		/*
		 * return wait.until(ExpectedConditions.visibilityOf(getWebElement(
		 * elementName).getWebElement()));
		 */

		return wait.until(ExpectedConditions.visibilityOf((WebElement) getWebElement(elementName)));
	}

	/**
	 * Get the WebElement when the element is visible.
	 * 
	 * @param elementName
	 *            - Name of the element.
	 * @return WebElement
	 */
	public WebElement getAndWaitForElement(WebElement elementName) {
		/*
		 * return wait.until(ExpectedConditions.visibilityOf(getWebElement(
		 * elementName).getWebElement()));
		 */
		wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOf(elementName));
//		return wait.until(ExpectedConditions.visibilityOf(elementName));
	}

	/*
	 * public WebElement getAndWaitForElementToBeClickable(WebElement elementName) {
	 * /* return wait.until(ExpectedConditions.visibilityOf(getWebElement(
	 * elementName).getWebElement()));
	 */
	// wait = new WebDriverWait(driver, 60);
	// return wait.until(ExpectedConditions.elementToBeClickable(elementName));
	// }

	public void implicitWait(int t) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
	}

	public WebElement getWebElementOrNull(String elementName) {
		return getWebElements.getWebElementOrNull(driver, pageWebElements, elementName);
	}

	public WebElement getWebElementOrNull(By locator) {
		WebElement ele = null;
		try {
			ele = driver.findElement(locator);
			return ele;
		}
		catch(Exception e) {
			return ele;
		}
	}
	
	public WebElement getWebElementOrNullByParentElement(WebElement parentElement, By locator) {
		WebElement ele = null;
		try {
			ele = parentElement.findElement(locator);
			return ele;
		}
		catch(Exception e) {
			return ele;
		}
	}

	public List<WebElement> getWebElementsList(String elementName) {
		return getWebElements.getListElements(driver, pageWebElements, elementName);
	}

	public void startRecording(File file, String userName, Boolean videoFlag) throws Exception {
		if (videoFlag == true)

		{
			BaseTest.log.info("startRecording");
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = screenSize.width;
			int height = screenSize.height;

			Rectangle captureSize = new Rectangle(0, 0, width, height);

			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration();

//			this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
//					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
//					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
//							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
//					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
//					null, file, userName);
//			BaseTest.log.info("startRecording--screenRecorder_Initializa");
//			this.screenRecorder.start();
		}
	}
	public void hoverOverElement(String elementName) {
		try {
			waitForElementVisibility(elementName);
			Actions action = new Actions(driver);
			WebElement element =getWebElement(elementName);
			action.moveToElement(element).perform();
		}
		catch(Exception e){
			BaseTest.log.error("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	public void hoverOverElement(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
		}
		catch(Exception e){
			BaseTest.log.error("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void hoverOverElementandClick(String elementName) {
		try {
			waitForElementVisibility(elementName);
			Actions action = new Actions(driver);
			WebElement element =getWebElement(elementName);
			action.moveToElement(element).click().build().perform();
		}
		catch(Exception e){
			BaseTest.log.error("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void stopRecording(boolean videoFlag) throws Exception {
		if (videoFlag == true) {
			try

			{
//				this.screenRecorder.stop();
			} catch (Exception e) {
				BaseTest.log.error("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
				BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage() + "\nStack Trace: "+ e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}

	public File createTempFolder() {
		BaseTest.loadAutomationProperties();
		screenshotsPath = BaseTest.screenshotsPath;
		currentDateFolder = new File(screenshotsPath + currentDate);
		UUID idOne = UUID.randomUUID();
		File tempFolder = new File(currentDateFolder + "//Temporary" + idOne);
		if (!(currentDateFolder.exists() && currentDateFolder.isDirectory())) {
			currentDateFolder.mkdir();
		}

		// new File(currentDate+"//User_Credential").exists();
		if (!(tempFolder.exists() && tempFolder.isDirectory())) {
			tempFolder.mkdir();
		}
		/*
		 * else { File[] listOfFiles = tempFolder.listFiles(); if(listOfFiles.length>0
		 * && isEmpty==false) { for (File file: listOfFiles) if (!file.isDirectory())
		 * file.delete(); isEmpty=true; } }
		 */

		return tempFolder;
	}

	public File createSessionFolder(String currentSessionID, File varTempFolder) {

		BaseTest.loadAutomationProperties();

//		ResponseDBConnection obj1 = new ResponseDBConnection();
		ArrayList<String> jobList = new ArrayList<String>();
		// String currentSessionID = ceLoginPage.testSessionId;
//		jobList = obj1.getJobID("jobs_id", currentSessionID);
		// System.out.println("value of joblist is"+jobList);
		String jobID = jobList.get(0);

		// File currentDateFolder = new File(screenshotsPath +
		// currentDate.toString());
		screenshotsPath = BaseTest.screenshotsPath;
		currentDateFolder = new File(screenshotsPath + currentDate);
		File jobIDFolder = new File(currentDateFolder + "//JOB_ID-" + jobID);

		File sessionIDFolder = new File(jobIDFolder + "//Session_ID-" + currentSessionID);

		File allScreenShots = new File(sessionIDFolder + "//AllScreenShots");

		targetLocationPath = allScreenShots.toURI().toString();

		if (targetLocationPath.contains("webapps")) {
			String[] commandArray = targetLocationPath.split("webapps/");

			// targetLocationPath = "../../"+commandArray[1]; // If wants to
			// give path of
			// screenshots folder from AutomationInterface folder
			targetLocationPath = "../" + commandArray[1];
			BaseTest.log.info("Path of the target file is after split under createsessionfolder" + targetLocationPath);
		}

		if (!currentDateFolder.exists() && currentDateFolder.isDirectory()) {
			currentDateFolder.mkdir();
		}
		if (!(jobIDFolder.exists() && jobIDFolder.isDirectory())) {
			jobIDFolder.mkdir();
		}
		if (!(sessionIDFolder.exists() && sessionIDFolder.isDirectory())) {
			sessionIDFolder.mkdir();
		}
		if (!(allScreenShots.exists() && allScreenShots.isDirectory())) {
			allScreenShots.mkdir();
		}
		try {
			// File sourceLocation = new File(currentDateFolder+"//Temporary");
			File sourceLocation = varTempFolder;

			// FileUtils.copyFile(afile, bfile);

			File[] listOfFiles = sourceLocation.listFiles();
			if (listOfFiles.length > 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					// File targetLocation = new
					// File(jobIDFolder+"//Session_ID-"+currentSessionID+"//"+PassedTest+"//"+listOfFiles[i].getName());
					File targetLocation = new File(allScreenShots + "//" + listOfFiles[i].getName());
					File afile = new File(sourceLocation + "//" + listOfFiles[i].getName());
					FileUtils.copyFile(afile, targetLocation);
					// afile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionIDFolder;
	}

	public File createFolderOnFailure(String currentSessionID) {
		File failureFolder = null;
		try {
//			ResponseDBConnection obj1 = new ResponseDBConnection();
			ArrayList<String> jobList = new ArrayList<String>();
			// String currentSessionID = ceLoginPage.testSessionId;
//			jobList = obj1.getJobID("jobs_id", currentSessionID);
			String jobID = jobList.get(0);
			BaseTest.loadAutomationProperties();
			screenshotsPath = BaseTest.screenshotsPath;
			currentDateFolder = new File(screenshotsPath + currentDate);
			// File currentDateFolder = new File(screenshotsPath +
			// currentDate.toString());

			File jobIDFolder = new File(currentDateFolder + "//JOB_ID-" + jobID);
			if (!currentDateFolder.exists() && currentDateFolder.isDirectory()) {
				currentDateFolder.mkdir();
			}
			if (!(jobIDFolder.exists() && jobIDFolder.isDirectory())) {
				jobIDFolder.mkdir();
			}

			File sessionIDFolder = new File(jobIDFolder + "//Session_ID-" + currentSessionID);
			if (!(sessionIDFolder.exists() && sessionIDFolder.isDirectory())) {
				sessionIDFolder.mkdir();
			}

			failureFolder = new File(sessionIDFolder + "//FailedTest");
			if (!(failureFolder.exists() && failureFolder.isDirectory())) {
				failureFolder.mkdir();
			}

			targetLocationPathfailure = failureFolder.toURI().toString();

			if (targetLocationPathfailure.contains("webapps")) {

				String[] commandArray = targetLocationPathfailure.split("webapps/");
				// targetLocationPathfailure = "../../"+commandArray[1];
				targetLocationPath = "../" + commandArray[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return failureFolder;
	}

	public void movefailedfile(File failureFolder, File tempFolder) {
		try {
			targetLocationPathfailure = failureFolder.toURI().toString();
			// System.out.println("Path of the target file is"+targetLocationPathfailure);
			if (targetLocationPathfailure.contains("webapps")) {
				String[] commandArray = targetLocationPathfailure.split("webapps/");
				targetLocationPathfailure = "../../" + commandArray[1];
			}

			/*
			 * String[] getSessionIDFolder = (failureFolder.toString()).split("webapps");
			 * String sessionIDFolder = getSessionIDFolder[0]; System.out.println(
			 * "Value of sessionIDFolder in basepage is:" +sessionIDFolder);
			 */

			File sourceLocation = tempFolder;

			// FileUtils.copyFile(afile, bfile);
			File[] listOfFiles = sourceLocation.listFiles();
			if (listOfFiles.length > 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].toString().contains("UserVideo") == true) {
						File targetLocation = new File(failureFolder + "//" + listOfFiles[i].getName());
						File afile = new File(sourceLocation + "//" + listOfFiles[i].getName());
						FileUtils.copyFile(afile, targetLocation);
						// afile.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAbsolutePath() {
		String logFilePath = null;
		try {
			BaseTest.loadAutomationProperties();
			logFilePath = BaseTest.logFilePath;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return logFilePath;
	}

	public String getAbsolutePathForFiles(String fileName) {
		String filePath = null;
		try {
			filePath = BaseTest.workingDirectory + fileName;
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}

	/**
	 * Method to check the web element presence and assertion on element.
	 * 
	 * @param errorList
	 *            - Instance of errorList
	 *            - instance of base logger
	 * @param element
	 *            - Web Element to be asserted
	 * @param elementNameToDisplay
	 *            - element name to be displayed in log file
	 * @param elementNameIndb
	 *            - element name defined in database
	 * @param elementType
	 *            - type of element like textbox, button etc.
	 */
	public void verifyWebElement(ArrayList<String> errorList, WebElement element, String elementNameToDisplay,
			String elementNameIndb, String elementType) {
		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_DISPLAYED = " not displayed.";
		final String TEXT_NOT_MATCH = " text doesn't match.";
		final String ACTUAL_TEXT = " Actual text : ";
		final String EXPECTED_TEXT = " Expected text : ";

		element = getWebElementOrNull(elementNameIndb);
		if (element == null) {
			BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND);
			errorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
		} else {
			if (!wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed()) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
				errorList.add(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
			}
			if (elementType.equalsIgnoreCase("label") || elementType.equalsIgnoreCase("button")
					|| elementType.equalsIgnoreCase("link")) {
				if (!element.getText().trim().equals(getWebElementText(elementNameIndb))) {
					BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + element.getText().trim()
							+ EXPECTED_TEXT + getWebElementText(elementNameIndb));
					errorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + element.getText().trim()
							+ EXPECTED_TEXT + getWebElementText(elementNameIndb));
				}
			}
		}
	}

	/**
	 * Method to check the web element presence and assertion on element.
	 *
	 *            - instance of base logger
	 * @param element
	 *            - Web Element to be asserted
	 * @param elementNameToDisplay
	 *            - element name to be displayed in log file
	 * @param elementNameIndb
	 *            - element name defined in database
	 * @param elementType
	 *            - type of element like textbox, button etc.
	 */
	public void verifyWebElement(WebElement element, String elementNameToDisplay, String elementNameIndb,
			String elementType) {
		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_DISPLAYED = " not displayed.";
		final String TEXT_NOT_MATCH = " text doesn't match.";
		final String ACTUAL_TEXT = " Actual text : ";
		final String EXPECTED_TEXT = " Expected text : ";

		element = getWebElementOrNull(elementNameIndb);
		if (element == null) {
			BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND);
			BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
		} else {
			if (!wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed()) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
				BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
			}
			if (elementType.equalsIgnoreCase("label") || elementType.equalsIgnoreCase("button")
					|| elementType.equalsIgnoreCase("link")) {
				if (!element.getText().trim().equals(getWebElementText(elementNameIndb))) {
					BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + element.getText().trim()
							+ EXPECTED_TEXT + getWebElementText(elementNameIndb));
					BaseTest.commonErrorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
							+ element.getText().trim() + EXPECTED_TEXT + getWebElementText(elementNameIndb));
				}
			}
		}
	}


	/**
	 * Method helps to check the content displayed on the element<br>
	 * If elementType is selected as dropdown, then Selected text will be verified.<br><br>
	 * This function helps to verify the Dynamic Expected Text, which cannot be added to the database, and will reduce the ROI<br>
	 * if done so.
	 * 
	 * @param DBElementNameTobeVerified : enter the ElementName which is added in the database, helping the function to fetch the webelement
	 * @param elementNameToDisplay : This is element name which will be displayed in the reports
	 * @param expectedText : expected dynamic text
	 * @param elementType : type of element, to make the function understand, what to verify<br? values can be used: label, button, textfield, dropdown
	 */
	public void verifyWebElementText(String DBElementNameTobeVerified, String elementNameToDisplay, String expectedText,
			String elementType) {
		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_DISPLAYED = " not displayed.";
		final String TEXT_NOT_MATCH = " text doesn't match.";
		final String ACTUAL_TEXT = " Actual text : ";
		final String EXPECTED_TEXT = " Expected text : ";

		WebElement tobeVerifiedElement = getWebElementOrNull(DBElementNameTobeVerified);

		if (tobeVerifiedElement == null) {
			BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND);
			BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
		} else {
			if (!wait.until(ExpectedConditions.visibilityOf(tobeVerifiedElement)).isDisplayed()) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
				BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
			}
			if (elementType.equalsIgnoreCase("label") || elementType.equalsIgnoreCase("button")
					|| elementType.equalsIgnoreCase("link") || elementType.equalsIgnoreCase("textfield")) {
				if (!tobeVerifiedElement.getText().trim().equals(expectedText)) {
					BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + tobeVerifiedElement.getText().trim()
							+ EXPECTED_TEXT + expectedText);
					BaseTest.commonErrorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
							+ tobeVerifiedElement.getText().trim() + EXPECTED_TEXT + expectedText);
				}
			}else if(elementType.equalsIgnoreCase("dropdown")) {
				Select select = new Select(tobeVerifiedElement);
				if(!select.getFirstSelectedOption().equals(expectedText)) {
					BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + select.getFirstSelectedOption()
					+ EXPECTED_TEXT + expectedText);
					BaseTest.commonErrorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
							+ select.getFirstSelectedOption() + EXPECTED_TEXT + expectedText);
				}
			}else if(elementType.equalsIgnoreCase("datetime")) {
				if(!tobeVerifiedElement.getAttribute("value").equals(expectedText)) {
					BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT + tobeVerifiedElement.getAttribute("value")
					+ EXPECTED_TEXT + expectedText);
					BaseTest.commonErrorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
							+ tobeVerifiedElement.getAttribute("value") + EXPECTED_TEXT + expectedText);
				}
			}
		}
	}



	/**
	 * Method to check the web element is enabled or not.
	 * 
	 * @param errorList
	 *            - Instance of errorList
	 *            - instance of base logger
	 * @param element
	 *            - Web Element to be asserted
	 * @param elementNameToDisplay
	 *            - element name to be displayed in log file
	 * @param elementNameIndb
	 *            - element name defined in database
	 */
	public void isElementEnabled(ArrayList<String> errorList, WebElement element, String elementNameToDisplay,
			String elementNameIndb) {
		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_ENABLED = " not enabled.";
		element = getWebElementOrNull(elementNameIndb);
		if (element == null) {
			BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND);
			errorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
		} else {
			if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_ENABLED);
				errorList.add(elementNameToDisplay + ELEMENT_NOT_ENABLED);
			}
		}
	}

	/**
	 * To check the presence of element on page.
	 *            - instance of logger
	 * @param elementsArray
	 *            - Array of web elements to verify
	 */
	public void verifyPageUI(List<String> elementsArray) {
		String elementName=null;;
		String elementNameToDisplay;
		String checkForEnabled;
		String checkForDisplayed;
		String checkForSelected;
		String checkForWaterMarkText;
		String elementType;

		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_DISPLAYED = " not displayed.";
		final String ELEMENT_NOT_ENABLED = " not enbaled.";
		final String ELEMENT_NOT_SELECTED = " not selected.";
		final String ELEMENT_SELECTED = " selected.";
		final String TEXT_NOT_MATCH = " text doesn't match.";
		final String ACTUAL_TEXT = " Actual text : ";
		final String EXPECTED_TEXT = " Expected text : ";
		
		BaseTest.log.info("Element Size: "+elementsArray.size(),true);
		
		for (int counter = 0; counter < elementsArray.size(); counter++) {
			elementName = elementsArray.get(counter);
			BaseTest.log.info("Element Name: "+elementName,true);
			elementNameToDisplay = getWebElementDisplayName(elementName);
			checkForEnabled = getWebElementIsEnabledValue(elementName);
			checkForDisplayed = getWebElementIsDisplayedValue(elementName);
			checkForSelected = getWebElementIsSelectedValue(elementName);
			checkForWaterMarkText = getWebElementIsWaterMarkTextValue(elementName);
			elementType = getWebElementType(elementName);

			// Find the element on the page.
			WebElement element = getWebElementOrNull(elementName);

			if (element == null) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND, true);
				BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
				objCapture.captureScreen(driver, elementNameToDisplay + " Not Found", BaseTest.screenShotFlag,
						BaseTest.failFolder.toString());
			} else {
				// Check element state for Display Mode.
				if (checkForDisplayed.equalsIgnoreCase("true")) {
					if (!element.isDisplayed()) {
						BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_DISPLAYED, true);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
						objCapture.captureScreen(driver, elementNameToDisplay + " Not Displayed",
								BaseTest.screenShotFlag, BaseTest.failFolder.toString());
					} else {
						BaseTest.log.info(elementName + "Element is found", true);
					}
				}
				// Check element state for Enabled mode.
				if (checkForEnabled.equalsIgnoreCase("true")) {
					// If element is not enabled then log the error.
					if (!element.isEnabled()) {
						BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_ENABLED, true);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_ENABLED);
						objCapture.captureScreen(driver, elementNameToDisplay + " Not Enabled", BaseTest.screenShotFlag,
								BaseTest.failFolder.toString());
					}
				}
				// Check the element state for selected mode (Check box).
				if (checkForSelected.equalsIgnoreCase("true")) {
					// If check box is not selected then log the error.
					if (!element.isSelected()) {
						BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_SELECTED, true);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_SELECTED);
						objCapture.captureScreen(driver, elementNameToDisplay + " Not Selected",
								BaseTest.screenShotFlag, BaseTest.failFolder.toString());
					}
				}
				// Check the element for non selected mode (Check box).
				if (checkForSelected.equalsIgnoreCase("false")) {
					// IF check box is selected then log the error.
					if (element.isSelected()) {
						BaseTest.log.error(elementNameToDisplay + ELEMENT_SELECTED, true);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_SELECTED);
						objCapture.captureScreen(driver, elementNameToDisplay + " Is Selected", BaseTest.screenShotFlag,
								BaseTest.failFolder.toString());
					}
				}
				// Check the text box placeholder
				if (checkForWaterMarkText.toLowerCase().equals("true")) {
					// If the actual place holder is not matched with expected
					// then log the error.
					if (!element.getAttribute("placeholder").trim().equals(getWebElementText(elementName))) {
						BaseTest.log.error(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
								+ element.getAttribute("placeholder").trim().trim() + EXPECTED_TEXT + getWebElementText(elementName), true);
						BaseTest.commonErrorList.add(elementNameToDisplay + TEXT_NOT_MATCH + ACTUAL_TEXT
								+ element.getAttribute("placeholder").trim().trim() + EXPECTED_TEXT + getWebElementText(elementName));

						objCapture.captureScreen(driver, elementNameToDisplay + " Text Not Matched",
								BaseTest.screenShotFlag, BaseTest.failFolder.toString());
					}
				}
			}
		}
	}

	/**
	 * To check the presence of necessary element on Page.
	 *            - instance of logger
	 * @param elementsArray
	 *            - Array of web elements to verify
	 */
	public void checkNecessaryElementPresence(List<String> elementsArray) {
		String elementName = null;
		String elementNameToDisplay;
		String checkForEnabled;
		String checkForDisplayed;
		String checkForSelected;
		String elementType;
		boolean elementsFound = true;

		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_DISPLAYED = " not displayed.";
		final String ELEMENT_NOT_ENABLED = " not enbaled.";
		final String ELEMENT_NOT_SELECTED = " not selected.";
		final String ELEMENT_SELECTED = " selected.";

		for (int counter = 0; counter < elementsArray.size(); counter++) {

			elementName = elementsArray.get(counter);
			elementNameToDisplay = getWebElementDisplayName(elementName);
			checkForEnabled = getWebElementIsEnabledValue(elementName);
			checkForDisplayed = getWebElementIsDisplayedValue(elementName);
			checkForSelected = getWebElementIsSelectedValue(elementName);
			elementType = getWebElementType(elementName);

			// Find the element on the page.
			WebElement element = getWebElementOrNull(elementName);

			if (element == null) {
				// If element is not found on the page log the error.
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND,true);
				BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
				elementsFound = false;
			} else {
				// Check element state for Display Mode.
				if (checkForDisplayed.equalsIgnoreCase("true")) {
					if (!element.isDisplayed()) {
						// If element is not display then log the error.
						BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_DISPLAYED);
						elementsFound = false;
					}
				}
				// Check element state for Enabled mode.
				if (checkForEnabled.equalsIgnoreCase("true")) {
					// If element is not enabled then log the error.
					if (!element.isEnabled()) {
						BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_ENABLED);
						BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_ENABLED);
						elementsFound = false;
					}

				}

				// Check the element type 'checkbox'
				if (elementType.equalsIgnoreCase("checkbox")) {
					// Check the element state for selected mode (Check box).
					if (checkForSelected.equalsIgnoreCase("true")) {
						// If check box is not selected then log the error.
						if (!element.isSelected()) {
							BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_SELECTED);
							BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_NOT_SELECTED);
							elementsFound = false;
						}
					}
					// Check the element for non selected mode (Check box).
					if (checkForSelected.equalsIgnoreCase("false")) {
						// IF check box is selected then log the error.
						if (element.isSelected()) {
							BaseTest.log.error(elementNameToDisplay + ELEMENT_SELECTED);
							BaseTest.commonErrorList.add(elementNameToDisplay + ELEMENT_SELECTED);
							elementsFound = false;
						}
					}
				}
			}
		}

		if (!elementsFound) {
			objCapture.captureScreen(driver, elementName + "NecessaryElementNotFound", BaseTest.screenShotFlag,
					BaseTest.failFolder.toString());
			Assert.assertTrue(false, "Required elements for this test case are not present.");
		}

	}

	public void scrollUptheScreen() {
		JavascriptExecutor jseUp = (JavascriptExecutor) driver;
		jseUp.executeScript("window.scrollBy(document.body.scrollHeight,0)", "");
	}

	/**
	 * To scroll up the screen
	 * 
	 * @param dimension
	 *            - dimension to scroll up
	 */
	public void scrollUptheScreen(int dimension) {
		try {
		dimension = dimension * -1;
		JavascriptExecutor jseUp = (JavascriptExecutor) driver;
		jseUp.executeScript("window.scrollBy(0," + dimension + ")", "");
		}
		catch(Exception e) {
			
		}
	}

	/**
	 * To scroll up the screen
	 * 
	 * @param dimension
	 *            - dimension to scroll down
	 */
	public void scrollDowntheScreen(int dimension) {
		JavascriptExecutor jseUp = (JavascriptExecutor) driver;
		jseUp.executeScript("window.scrollBy(0," + dimension + ")", "");
	}

	/**
	 * To scroll up the screen
	 * 
	 * @param dimension
	 *            - dimension to scroll down
	 */
	public void scrollDowntheScreen(WebDriver driver, int dimension) {
		JavascriptExecutor jseUp = (JavascriptExecutor) driver;
		jseUp.executeScript("window.scrollBy(0," + dimension + ")", "");
	}

	/**
	 * To scroll up the screen to a webElement
	 * 
	 * @param e
	 */
	public void scrollDownToElement(WebElement e) {
		JavascriptExecutor jseUp = (JavascriptExecutor) driver;
		jseUp.executeScript("arguments[0].scrollIntoView();", e);
	}
	
	//This method is used to get the files count in the given folder
	public int getFileCount(String Path) {
		File directory = new File(Path);
		int fileCount = directory. list().length;
		BaseTest.log.info("Available files ==> "+ fileCount);
		return fileCount;
	}


	public void DisplayElement(WebElement ele) {
		scrollDownToElement(ele);
		scrollUptheScreen(100);
	}
	public boolean selectOptions(WebElement dropdownvalue, String optionValue) {
		Boolean isSelected = false;
		try {
			List<WebElement> liClass = dropdownvalue.findElements(By.tagName("label"));

			// Loop for match the Name in all rows
			for (WebElement option : liClass) {
				//UtilClass.removeStaleElementException(option);
				// To check whether project name present in this row.
				if (option.getText().trim().equalsIgnoreCase(optionValue) ) {
					clickByjs(option);
					isSelected = true;
					return true;
				} else if(option.getText().contains("...")){
					String value=option.findElement(By.tagName("input")).getAttribute("value");
					if(value.trim().equalsIgnoreCase(optionValue)){
						clickByjs(option);
						isSelected = true;
						return true;
				}
			}}
			if (!isSelected) {
				BaseTest.log.error("Expected value i.e. " + optionValue + " can not be found.", true);
				BaseTest.commonErrorList.add("Expected value i.e. " + optionValue + " can not be found.");
				objCapture.captureScreen(driver, optionValue + "_NotFound", true, BaseTest.failFolder.toString());
			}
		} catch (Exception e) {
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage());
			BaseTest.log.error("Error Obtained:" + e.getLocalizedMessage(), true);
			e.printStackTrace();
		}
		return false;
	}
	public String getAttribute(WebElement element , String attributeName){
		return element.getAttribute(attributeName);
	}

	public Boolean selectDropDownValuesOneByOne(WebElement dropdownvalue, WebElement indexToClick) {
		try {
			clearSelectedItems(dropdownvalue);
			indexToClick.click();

		} catch (Exception e) {
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage());
			BaseTest.log.error("Error Obtained:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean clearSelectedItems(WebElement dropdownvalue) {
		try {
			clickByJS_WebElement(dropdownvalue.findElements(By.tagName("li")).get(1));
			BaseTest.log.info("Clear selected item is clicked",true);
			wait_Until_Requests_to_Complete();
		} catch (Exception e) {
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage());
			BaseTest.log.error("Error Obtained:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void clearValues() {
//		try {
//			// TODO: Pankaj
//			AdminCreateNewProjectPage.projectId = 0;
//
//			if(VACandidatePage.localisedValue!=null) {
//				VACandidatePage.localisedValue.clear();
//			}
//
//			if (Core51Assessments.allAnswerResponses != null)
//				Core51Assessments.allAnswerResponses.clear();
//
//			if (CeVerifyAssessment.allAnswerResponses != null)
//				CeVerifyAssessment.allAnswerResponses.clear();
//
//			if (ResponseDBConnection.discrimination != null)
//				ResponseDBConnection.discrimination.clear();
//
//			if (ResponseDBConnection.difficulty != null)
//				ResponseDBConnection.difficulty.clear();
//
//			if (ResponseDBConnection.guessing != null)
//				ResponseDBConnection.guessing.clear();
//
//			if (ResponseDBConnection.i_discrimination != null)
//				ResponseDBConnection.i_discrimination.clear();
//
//			if (ResponseDBConnection.i_difficulty != null)
//				ResponseDBConnection.i_difficulty.clear();
//
//			if (ResponseDBConnection.i_guessing != null)
//				ResponseDBConnection.i_guessing.clear();
//
//			if (ResponseDBConnection.n_discrimination != null)
//				ResponseDBConnection.n_discrimination.clear();
//
//			if (ResponseDBConnection.n_difficulty != null)
//				ResponseDBConnection.n_difficulty.clear();
//
//			if (ResponseDBConnection.n_guessing != null)
//				ResponseDBConnection.n_guessing.clear();
//
//			if (ResponseDBConnection.d_discrimination != null)
//				ResponseDBConnection.d_discrimination.clear();
//
//			if (ResponseDBConnection.d_difficulty != null)
//				ResponseDBConnection.d_difficulty.clear();
//
//			if (ResponseDBConnection.d_guessing != null)
//				ResponseDBConnection.d_guessing.clear();
//
//			if (ResponseDBConnection.v_discrimination != null)
//				ResponseDBConnection.v_discrimination.clear();
//
//			if (ResponseDBConnection.v_difficulty != null)
//				ResponseDBConnection.v_difficulty.clear();
//
//			if (ResponseDBConnection.v_guessing != null)
//				ResponseDBConnection.v_guessing.clear();
//
//			if (StringMismatchIdentification.SodaQuestionCodes != null)
//				StringMismatchIdentification.SodaQuestionCodes.clear();
//
//			if (StringMismatchIdentification.TctoSodaQuestionCodeMap != null)
//				StringMismatchIdentification.TctoSodaQuestionCodeMap.clear();
//
//			if (StringMismatchIdentification.SodaQuestionCodesForPassage != null)
//				StringMismatchIdentification.SodaQuestionCodesForPassage.clear();
//
//			if (StringMismatchIdentification.TctoSodaQuestionCodeMapForPassage != null)
//				StringMismatchIdentification.TctoSodaQuestionCodeMapForPassage.clear();
//
//			if (AdminCreateNewProjectPage.candidateEmailList != null)
//				AdminCreateNewProjectPage.candidateEmailList.clear();
//
//			if (CeVerifyAssessment.allQuestionIds != null)
//				CeVerifyAssessment.allQuestionIds.clear();
//
//			if (CeVerifyAssessment.allAnswerResponses != null)
//				CeVerifyAssessment.allAnswerResponses.clear();
//
//			if (CeAssessmentHomePage.gPlusAssessmentSequence != null)
//				CeAssessmentHomePage.gPlusAssessmentSequence.clear();
//
//			if (CeVerifyAssessment.gplusInductiveQuestionIds != null)
//				CeVerifyAssessment.gplusInductiveQuestionIds.clear();
//
//			if (CeVerifyAssessment.gplusNumericalQuestionIds != null)
//				CeVerifyAssessment.gplusNumericalQuestionIds.clear();
//
//			if (CeVerifyAssessment.gplusDeductiveQuestionIds != null)
//				CeVerifyAssessment.gplusDeductiveQuestionIds.clear();
//
//			if (CeVerifyAssessment.gplusVerbalQuestionIds != null)
//				CeVerifyAssessment.gplusVerbalQuestionIds.clear();
//
//			if (CeVerifyAssessment.allquestionCodesForInductive != null)
//				CeVerifyAssessment.allquestionCodesForInductive.clear();
//
//			if (CeVerifyAssessment.allquestionCodesForNumerical != null)
//				CeVerifyAssessment.allquestionCodesForNumerical.clear();
//
//			if (CeVerifyAssessment.allquestionCodesForDeductive != null)
//				CeVerifyAssessment.allquestionCodesForDeductive.clear();
//
//			if (CeVerifyAssessment.allquestionCodesForVerbal != null)
//				CeVerifyAssessment.allquestionCodesForVerbal.clear();
//
//			if (CeVerifyAssessment.gplusInductiveAnswerResponsesType != null)
//				CeVerifyAssessment.gplusInductiveAnswerResponsesType.clear();
//
//			if (CeVerifyAssessment.gplusNumericalAnswerResponsesType != null)
//				CeVerifyAssessment.gplusNumericalAnswerResponsesType.clear();
//
//			if (CeVerifyAssessment.gplusDeductiveAnswerResponsesType != null)
//				CeVerifyAssessment.gplusDeductiveAnswerResponsesType.clear();
//
//			if (CeVerifyAssessment.gplusVerbalAnswerResponsesType != null)
//				CeVerifyAssessment.gplusVerbalAnswerResponsesType.clear();
//
//			if (CeVerifyAssessment.allquestionCodes != null)
//				CeVerifyAssessment.allquestionCodes.clear();
//
//			if (DSIPage.totalResponses != null)
//				DSIPage.totalResponses.clear();
//
//			if (BaseTest.assessmentDetails != null)
//				BaseTest.assessmentDetails.clear();
//
//			if (Core51Assessments.qCodes != null)
//				Core51Assessments.qCodes.clear();
//
//			if (Core51Assessments.qResponses != null)
//				Core51Assessments.qResponses.clear();
//
//			if (CeVerifyAssessment.questionIdListBeforeExit != null)
//				CeVerifyAssessment.questionIdListBeforeExit.clear();
//
//			if (AdminReviewProjectDetailPage.reptname != null)
//				AdminReviewProjectDetailPage.reptname.clear();
//
//			if(PlayerRegistration.addValue!=null)
//				PlayerRegistration.addValue="1";
//
//			if(AdminWorkFlowElement.deviceRestrictionDefaultText!=null)
//				AdminWorkFlowElement.deviceRestrictionDefaultText=null;
//
//			if(AdminCreateNewVRProject.customAppForm!=null)
//				AdminCreateNewVRProject.customAppForm=null;
//
//
//			Core51Assessments.totalQuestions = 0;
//			CeVerifyAssessment.totalQuestions = 0;
//			ExcelReader.ThetaValue = 0.0;
//			ExcellWriter.overall_score = 0.0;
//			ResponseDBConnection.mean = 0.0;
//			ResponseDBConnection.deviation = 0.0;
//			ResponseDBConnection.skewness = 0.0;
//			ResponseDBConnection.kurtosis = 0.0;
//			ResponseDBConnection.i_mean = 0.0;
//			ResponseDBConnection.i_deviation = 0.0;
//			ResponseDBConnection.i_skewness = 0.0;
//			ResponseDBConnection.i_kurtosis = 0.0;
//			ResponseDBConnection.n_mean = 0.0;
//			ResponseDBConnection.n_deviation = 0.0;
//			ResponseDBConnection.n_skewness = 0.0;
//			ResponseDBConnection.n_kurtosis = 0.0;
//			ResponseDBConnection.d_mean = 0.0;
//			ResponseDBConnection.d_deviation = 0.0;
//			ResponseDBConnection.d_skewness = 0.0;
//			ResponseDBConnection.d_kurtosis = 0.0;
//			AdminReviewProjectDetailPage.totalattachment =0;
//			CeVerifyAssessment.assessmentName = null;
//			AdminManageVRProjectPage.isResetTemplateEnable=false;
//			CeVerifyAssessment.ceMessagingHurdleMessage = false;
//			AdminWorkFlowElement.isLibraryContentBackGroud=false;
//			AdminAssessmentSolution.isCustomCheckBoxEnable=false;
//			PlayerRJPMinQualFeedback.isVerifyMinqualQualificationColorEnable=false;
//			CeVerifyAssessment.session_id = null;
//			AdminCreateNewProjectPage.isReminderEnable=false;
//			VerifyInteractiveAssessments.test_session = null;
//			CeRegisterPage.getEmail = null;
//			NavigateToAHP.assessmentType = null;
//			CeVerifyAssessment.isResponses = null;
//			PlayerWelcomeHomePage.isVerifyLocationOnWelcomePage=false;
//			AdminCreateNewVRProject.isLocationEnable=false;
//			AdminCreateNewProjectPage.candidateEmail = null;
//			AdminAssessmentSolution.isAdd3rdQuestion=false;
//			CeRegisterPage.getLastName = null;
//			AdminCreateNewTemplate.isDemoChcek=false;
//			BaseTest.cdn = false;
//			AdminCreateNewProjectPage.isProjectTypeUpdatingEnable = false;
//			CESolutions.isSkippedEnableVerificationIsEnable=false;
//			AdminAssessmentSolution.isTcSettingisEnable=false;
//			AdminAssessmentSolution.isGeneralCaseEnable=true;
//			AdminCreateNewVRProject.isVerifyTemplate=false;
//			CeAssessmentHomePage.projectIDinAHP = 0;
//			CeAssessmentHomePage.packageId = 0;
//			CeAssessmentHomePage.langCode = null;
//			CeTestPLayer.isCombinationCompleted = null;
//			CeTestPLayer.testSessionID = null;
//			CeTestPLayer.sessionID= null;
//			AdminCreateNewTemplate.isPreviewEnable=false;
//			CESolutions.sessionID = null;
//			CESolutions.SurveySessionID=null;
//			AdminWorkFlowElement.isBackGround=false;
//			AdminCreateNewVRProject.iscustomAppFormEnable=false;
//			Integration.isReportDownload = false;
//			AdminCreateNewTemplate.isVerifySummitForApproval=false;
//			AdminWorkFlowElement.isPublished = false;
//			CeBasicSetting.isMinqualLocalizationVerification=false;
//			CommonVrPlayerTemplet.minqualLocalizationVerificationlangPlayer=null;
//			AdminWorkFlowElement.isJobFriendlyNameEnable=true;
//			AdminWorkFlowElement.isSecondRjp=false;
//			AdminAssessmentSolution.isVerifysecondRjp=false;
//			TCITestHarness.verifyScoreType=false;
//			TestCeDpn.ADMCheckedForClosedProject="";
//			TestCeDpn.checked="";
//			CeTestPLayer.isnewbrowser=false;
//			AdminSettingPage.minqualAndAPP = null;
//			AdminCreateCFCPage.cfcName=null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			BaseTest.log.info("Error obtained while cleaing value " + "from the basepage" + e.getLocalizedMessage());
//		}
	}

	public Boolean selectValuesFromPagingDropDown(WebElement dropdownvalue, WebElement dropDownToClick,
			String optionValue) {
		try {
			dropDownToClick.click();
			List<WebElement> liClass = dropdownvalue.findElements(By.tagName("li"));
			// Loop for match the Name in all rows
			for (WebElement option : liClass) {
				List<WebElement> inputValue = option.findElements(By.tagName("input"));
				for (WebElement inputValues : inputValue) {

					// To check whether project name present in this row.
					//	System.out.println(inputValues.getText().trim());
					String value = inputValues.getAttribute("value");
					if (value.trim().equalsIgnoreCase(optionValue)) {
						inputValues.click();
						return true;
					}
				}
			}
		} catch (Exception e) {
			BaseTest.commonErrorList.add("\n\nFAILURE!! Error Obtained: " + e.getMessage());
			BaseTest.log.error("Error Obtained:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void pageLoadMethod(String loadingElement) {
		implicitWait(45);
		try {
			if(waitForElementVisibility(loadingElement,5,"Element"))
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingElement)));
		} catch (Exception e) {
		}
		implicitWait(30);
	}

	// Method Overloading
	public void pageLoadMethod(String loadingElement, int timeoutinseconds, String locator) {
		// implicitWait(45);
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds)))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingElement)));
		} catch (Exception e) {
		}
	}

	/**
	 * This method change system Timezone
	 * For indian Time Pass - 'India Standard Time'
	 * For UK Time - Pass - 'GMT Standard Time'
	 * @param TimeZone
	 */
	public void updateSystemTimeZone(String TimeZoneName) {
		try {
			Runtime rt =  Runtime.getRuntime();
			rt.exec("tzutil /s \""+TimeZoneName+"\"");
	
			TimeZone.setDefault(null);
	        System.setProperty("user.timezone", "");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param TimeZoneNameForSystem - to change system timezone pass sttanded time..you can check timezone system in cmd by command tzutil /g
	 * @param JVMTimezone - check in db table country_timezone_map
	 */
	public void updateSystemTimeZone(String TimeZoneNameForSystem,String JVMTimezone) {
		try {
			Runtime rt =  Runtime.getRuntime();
			rt.exec("tzutil /s \""+TimeZoneNameForSystem+"\"");
			TimeZone.setDefault(TimeZone.getTimeZone(JVMTimezone));
	        System.setProperty("user.timezone",JVMTimezone);
	     
			rt.exec("java -Duser.timezone="+JVMTimezone);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy mm:hh");
			String dateString = dateFormat.format(new Date()).toString();
			BaseTest.log.info("Current Time "+dateString);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void switchToFrame(WebElement frameElement) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameElement);

	}


	public void switchToTwoFrame(String Frame1, String Frame2) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Frame1);
		driver.switchTo().frame(Frame2);	
	}

	public void waitForElementToDisappear(WebElement ele){
		try {
			wait.until(ExpectedConditions.invisibilityOfAllElements(ele));
		} catch (Exception e) {
		}
	}

	public Boolean waitForElementToDisappear(String loadingElement) {
		Boolean isDisplayed = false;
		int waitTime = 15;
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver,Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.invisibilityOfElementLocated(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.invisibilityOfElementLocated(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.invisibilityOfElementLocated(By.linkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.invisibilityOfElementLocated(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.invisibilityOfElementLocated(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.invisibilityOfElementLocated(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.invisibilityOfElementLocated(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(waitTime)))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
			isDisplayed = true;
			return isDisplayed;
		}
		implicitWait(30);
		return isDisplayed;
	}
	public Boolean waitForElementVisibility(String loadingElement) {
		Boolean isDisplayed = true;
		int waitTime = 25;
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.linkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(waitTime)))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
			isDisplayed = false;
			return isDisplayed;
		}
		implicitWait(30);
		return isDisplayed;
	}


	public Boolean waitForElementVisibility(String loadingElement, int timeoutinseconds, String elementName) {
		Boolean isDisplayed = true;
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(
							ExpectedConditions.visibilityOfElementLocated(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(ExpectedConditions
							.visibilityOfElementLocated(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(ExpectedConditions
							.visibilityOfElementLocated(By.linkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(
							ExpectedConditions.visibilityOfElementLocated(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver,Duration.ofSeconds(timeoutinseconds))).until(ExpectedConditions
							.visibilityOfElementLocated(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds))).until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds)))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
			isDisplayed = false;
			return isDisplayed;
		}
		return isDisplayed;
	}

	public void handleAlert() throws InterruptedException {
		try 
		{ 
			Thread.sleep(1200);
			driver.switchTo().alert().accept(); 
			driver.switchTo().defaultContent();
			BaseTest.log.info("Unexpected alert found");
		}   
		catch (NoAlertPresentException Ex) 
		{ 
			BaseTest.log.info("Unexpected alert does not found");
		}
	}


	public void handleAlert(boolean screenShotFlag) {
		try {
			Thread.sleep(700);
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			BaseTest.log.info("Alert Not detected: {}",true );
		}
	}

	public void waitForElementVisibility(WebDriver driver, String loadingElement, int timeoutinseconds,
			String elementName) {
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds)))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loadingElement)));
		} catch (Exception e) {
		}
	}

	public void pageLoadMethod(WebDriver driver, String loadingElement, int timeoutinseconds, String locator) {
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(timeoutinseconds)))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingElement)));
		} catch (Exception e) {
		}
	}

	// Method to get CDN version
	public void getCDNVersion() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String tempcdnURL = (String) js.executeScript("return $('script[src*=cdn]').eq(1).attr('src');");

			String tempcdnURLParts[] = tempcdnURL.split("/");
			//	System.out.println("Application CDN: " + tempcdnURLParts[4]);
			BaseTest.log.info("<wysiwyg><h3>Application CDN: " + tempcdnURLParts[4] + "</h3></wysiwyg>");
			if (!tempcdnURLParts[4].startsWith("31") && !tempcdnURLParts[4].startsWith("29") && !tempcdnURLParts[4].startsWith("30"))
				BaseTest.cdn = true;
			else
				BaseTest.cdn = false;
		} catch (Exception e) {
			BaseTest.log.error("Error Occurred while getting CDN verion Number");
			BaseTest.commonErrorList.add("Error Occurred while getting CDN verion Number");
		}
	}

	/**
	 * This method used for switching between the multiple Tabs
	 * 
	 * @param url
	 */
	public String switchBetweenTabs(String url) {
		try {
			Thread.sleep(1000);
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			if (tabs.size() >= 1) {
				driver.switchTo().window(tabs.get(1));
				url = driver.getCurrentUrl();
				driver.close();
				driver.switchTo().window(tabs.get(0));
			} else {
				url = driver.getCurrentUrl();
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return url;

	}

	public void explicitWait(String loadingElement, int timeout) {
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if(elementType!=null) {
				WebElement myDynamicElement = (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getWebElementLocatorValue(loadingElement))));
			}else{
				(new WebDriverWait(driver, Duration.ofSeconds(timeout)))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * This function helps to verify the element is present on the page or not.
	 * It will check the presence of element code in DOM for 1 minutes.<br>
	 * will break only in the case, it is not loaded for the user.
	 *
	 */
	public void waitForElementToBePresentOnThePage(String elementName) {
		implicitWait(1);
		try {
			for(int i=0;i<120;i++) {
				if(getWebElementsList(elementName).size() != 0) {
					break;
				}else {
					Thread.sleep(700);
				}
			}
		}catch(Exception e) {
			implicitWait(60);
			e.printStackTrace();
		}
		implicitWait(120);
	}

	public WebElement presenceOfElement(Boolean screenShot, String xpath, String elementName) {
		List<WebElement> list = driver.findElements(By.xpath(xpath));
		if(!list.isEmpty()) {
			BaseTest.log.info("Element : "+elementName+" found",screenShot);
			return list.get(0);
		}
		else {
			BaseTest.log.error("Element : "+elementName+" not found");
			return null;
		}	
	}


	public void waitForElementToBeClickable(WebElement toBeClickable){
		try{
			(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
			.until(ExpectedConditions.elementToBeClickable(toBeClickable));
		}catch (Exception e){
		}
	}
	public void waitForElementToBeClickable(String loadingElement) {
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10))).until(
							ExpectedConditions.elementToBeClickable(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.linkText(loadingElement)));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10))).until(ExpectedConditions
							.elementToBeClickable(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10))).until(
							ExpectedConditions.elementToBeClickable(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(10)))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
		}
		implicitWait(30);
	}

	public String getPageWidget(WebDriver driver) {
		String pageName = null;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			boolean isTC = (boolean) js.executeScript(
					"function onTalentCentral()" + "{return typeof CEBWidget == 'function'} return onTalentCentral();");
			if (isTC) {
				pageName = (String) js
						.executeScript("return $('div#wrapper').find" + "('.cebwidget').attr('widget-name');");
			} else {
				BaseTest.log.info("You are not on TalentCentral page", true);
				pageName = "NonTC";
			}
		} catch (Exception e) {
			e.printStackTrace();
			BaseTest.log.error("Error occured while getting main page widget name" + e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while getting main page widget name");
		}
		return pageName;
	}

	public void waitForPageToDisappear(WebDriver driver, String pageWidget, String page, int limit) {
		try {System.out.println();
			for (int i = 1; i <= limit; i++) {

				String pageName = getPageWidget(driver);

				if (pageName != null && pageName.equalsIgnoreCase(pageWidget)) {
					Thread.sleep(1000);
				} else {
					BaseTest.log.info("Time took on " + page + ":: " + i + " sec");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// This is not an error, so modifying it to a warning, so that it will be
			// captured in the logs, but should not show as an ERROR.
			BaseTest.log.warning("Page is not visible " + e.getLocalizedMessage(), true);
		}
	}

	public boolean FilterEnabled(String filter) {
		boolean enabled = false;
		try {
			waitForElementVisibility(filter);
			WebElement element = getWebElementOrNull(filter);
			if (element != null) {
				try {
					element.getAttribute("disabled").toString();
				} catch (Exception e) {
					enabled = true;
				}
			}
		} catch (Exception e) {
			enabled = false;
		}
		return enabled;
	}

	public int verifyFilterValues(String filter, String filterVal) {
		List<WebElement> totalValues = null;
		try {
			waitForElementVisibility(filter);
			WebElement element = getWebElementOrNull(filter);
			element.click();
			if (element != null) {
				Thread.sleep(2000);
				getAndWaitForElement(getWebElementOrNull(filterVal));
				WebElement element1 = getWebElementOrNull(filterVal);

				if (element1 != null) {
					totalValues = element1.findElements(By.tagName("li"));
					try{element.click();}
					catch (Exception e){
						WebElement searchField = getAndWaitForElement(driver.findElement(By.xpath("//div[@id=\"viewAssessmentProfiles_filter\"]//input")));
						clickByJS_WebElement(searchField);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalValues.size();
	}

	public String getAssessmentType(WebDriver driver) {
		String pageName = null;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			pageName = (String) js.executeScript(
					"return $('div#wrapper').find" + "('.cebwidget').attr('widget-name') && assessmentType;");

		} catch (Exception e) {
			e.printStackTrace();
			BaseTest.log.error("Error occured while getting main widget name" + e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while getting main widget name" + e.getLocalizedMessage());
		}
		return pageName;
	}

	/**
	 * Random password creation for the page.
	 */
	public String generateRandomPassword() {
		Random rand = new Random();
		int n = 8888;
		int temp = rand.nextInt(n);
		String password = "P@ssword";
		password = password + temp;
		BaseTest.log.info("Random Password generated: " + password);
		return password;
	}

	/**
	 * Gets today's date in mm-dd-yyyy format
	 */
	public String getTodaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCandidateEmail(HashMap<Integer, HashMap<String, String>> candidate, int count) {
		String email = null;
		HashMap<String, String> info = new HashMap<>();

		info = candidate.get(count);

		// Map<String,String> map = new HashMap<>();
		HashMap.Entry<String, String> entry = info.entrySet().iterator().next();
		email = entry.getKey();

		return email;
	}

	public String getCandidateLink(HashMap<Integer, HashMap<String, String>> candidate, int count) {
		String link = null;
		HashMap<String, String> info = new HashMap<>();

		info = candidate.get(count);

		// Map<String,String> map = new HashMap<>();
		HashMap.Entry<String, String> entry = info.entrySet().iterator().next();
		link = entry.getValue();

		return link;
	}

	public String changeDateFormat(String sdate, String format) {
		String date1 = null;
		SimpleDateFormat formatter = null;
		Date date = null;
		try {
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			date = formatter.parse(sdate);
			DateFormat dateFormat = new SimpleDateFormat(format);
			date1 = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

	public Calendar addDaysInDate(String dateToChange, int days) {
		Calendar c = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Date date=sdf.parse(dateToChange);
			c = Calendar.getInstance();
			c.setTime(date); // Now use the date.
			c.add(Calendar.DATE, days); // Adding "days" day(s)

		} catch (Exception e) {
			e.printStackTrace();
			BaseTest.log.error("Error occured while calculating "
					+ "deadline date for a candidate: " + e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while "
					+ "calculating deadline date for a candidate");
		}
		return c;
	}

	/**
	 * Verifies color code for an element 
	 * 
	 * @param elementtoCheck
	 * @param colorCode
	 * @param screenShotFlag
	 * @param Elementname
	 * @param scenario
	 * @param shouldMatch
	 * @author Shashank Sharma
	 */
//	public void verifyBrandingColorCode(String elementtoCheck, String colorCode, Boolean screenShotFlag,
//			String Elementname, String scenario, Boolean shouldMatch) {
//		try {
//			waitForElementVisibility(elementtoCheck);
//			WebElement el = getWebElementOrNull(elementtoCheck);
//
//			if(el != null) {
//				String a = el.getCssValue("color");
//				String color1[];
//				String hexcodeUI = null;
//				if (a.contains("rgba")) {
//					color1 = a.replace("rgba(", "").split(",");
//					color1[3] = color1[3].replace(")", "");
//					hexcodeUI = String.format("#%02x%02x%02x", Integer.parseInt(color1[0].trim()),
//							Integer.parseInt(color1[1].trim()), Integer.parseInt(color1[2].trim()),
//							Integer.parseInt(color1[3].trim()));
//				} else {
//					color1 = a.replace("rgb(", "").split(",");
//					color1[2] = color1[2].replace(")", "");
//					hexcodeUI = String.format("#%02x%02x%02x", Integer.parseInt(color1[0].trim()),
//							Integer.parseInt(color1[1].trim()), Integer.parseInt(color1[2].trim()));
//				}
//				if(shouldMatch) {
//					if (hexcodeUI.equalsIgnoreCase(colorCode)) {
//
//						BaseTest.log.info("Hex color code value i.e. " + hexcodeUI + " is "
//								+ " matched with value: " + colorCode + " for " + Elementname, screenShotFlag);
//					} else {
//
//						UtilClass.addFailTestSetup("Hex color code value i.e. " + hexcodeUI + " is "
//								+ " not matched with value: " + colorCode + " for " + Elementname,true);
//
//					}
//				} else {
//					if (hexcodeUI.equalsIgnoreCase(colorCode)) {
//
//						UtilClass.addFailTestSetup("Hex Color code for" + " " + colorCode + " " + "is not correct in UI",true);
//
//					} else {
//					}
//				}
//			} else {
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Verifies backgroud color code for an element 
	 * 
	 * @param elementtoCheck
	 * @param colorCode
	 * @param screenShotFlag
	 * @param Elementname
	 * @param scenario
	 * @param shouldMatch
	 * @author Shashank Sharma
	 */
//	public void verifyBrandingBackgroundCode(String elementtoCheck, String colorCode, Boolean screenShotFlag,
//			String Elementname, String scenario, Boolean shouldMatch) {
//		WebElement el=null;
//		try {
//			waitForElementVisibility(elementtoCheck);
//			try {
//				el = getWebElementOrNull(elementtoCheck);
//
//			}catch(StaleElementReferenceException e) {
//
//				el = getWebElementOrNull(elementtoCheck);
//			}
//			if(el != null) {
//				String a = el.getCssValue("background-color");
//
//				String color1[];
//				String hexcodeUI = null;
//				if(a.contains("rgba")) {
//					color1 = a.replace("rgba(", "").split(",");
//					color1[3] = color1[3].replace(")", "");
//					hexcodeUI = String.format("#%02x%02x%02x", Integer.parseInt(color1[0].trim()),
//							Integer.parseInt(color1[1].trim()), Integer.parseInt(color1[2].trim()),
//							Integer.parseInt(color1[3].trim()));
//				}else {
//					color1 = a.replace("rgb(", "").split(",");
//					color1[2] = color1[2].replace(")", "");
//					hexcodeUI = String.format("#%02x%02x%02x", Integer.parseInt(color1[0].trim()),
//							Integer.parseInt(color1[1].trim()), Integer.parseInt(color1[2].trim()));
//				}
//				if(shouldMatch) {
//					if (hexcodeUI.equalsIgnoreCase(colorCode)) {
//
//						BaseTest.log.info("Hex color code value i.e. " + hexcodeUI + " is "
//								+ " matched with value: " + colorCode + " for " + Elementname, screenShotFlag);
//					} else {
//						UtilClass.addFailTestSetup("Hex color code value i.e. " + hexcodeUI + " is "
//								+ " not matched with value: " + colorCode + " for " + Elementname,screenShotFlag);
//
//					}
//				} else {
//					if (!hexcodeUI.equalsIgnoreCase(colorCode)) {
//						BaseTest.log.info("Hex color code value i.e. " + hexcodeUI + " is "
//								+ "not matched with value: " + colorCode + " for " + Elementname
//								+ " when " + scenario + ": PASS", screenShotFlag);
//					} else {
//
//						UtilClass.addFailTestSetup("Hex color code value i.e. " + hexcodeUI + " is not "
//								+ " matched with value: " + colorCode + " for " + Elementname
//								+ " when " + scenario + ": FAIL",screenShotFlag);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			UtilClass.addFailTestSetup("Error occured while verifying background color of "
//					+ Elementname + ": " ,e, true);
//
//	}}

	/**
	 * Verifies backgroud color for textbox 
	 * 
	 * @param elementtoCheck
	 * @param colorCode
	 * @param screenShotFlag
	 * @param Elementname
	 * @param scenario
	 * @param shouldMatch
	 * @author Shashank Sharma
	 */
//	public void verifyBrandingBackgroundCodeTextBox(String elementtoCheck, String colorCode, Boolean screenShotFlag,
//			String Elementname, String scenario, Boolean shouldMatch) {
//		try {
//			WebElement el = getWebElementOrNull(elementtoCheck);
//			if(el != null) {
//				String colorCodeUI = el.getAttribute("value");
//				if(shouldMatch) {
//					//
//					//System.out.println();
//				} else {
//					if (!colorCodeUI.equalsIgnoreCase(colorCode)) {
//
//						BaseTest.log.info("Hex color code value i.e. " + colorCodeUI + " is "
//								+ "not matched with value: " + colorCode + " for " + Elementname
//								+ " when " + scenario + ": PASS", true);
//					} else {
//
//						UtilClass.addFailTestSetup("Hex color code value i.e. " + colorCodeUI + " is "
//								+ " matched with value: " + colorCode + " for " + Elementname
//								+ " when " + scenario + ": FAIL",true);
//					}
//				}
//			} else {
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			BaseTest.log.error("Error occured while verifying background color of "
//					+ Elementname + ": " + e.getLocalizedMessage(), true);
//			BaseTest.commonErrorList.add("Error occured while verifying background color of "
//					+ "element " + Elementname);
//			objCapture.captureScreen(driver, "Error_CompareBackColorTextCode", screenShotFlag,
//					BaseTest.failFolder.toString());
//		}
//	}

	public String getNormCodeFromHash(String hash) {
		String[] hashArray = hash.replace("{", "")
				.replace("}", "").split("=");
		String hashcode = hashArray[1];
		return hashcode;
	}

	// Get Random Number
	public int generateRandomPassword(int numberToRandomize) {
		Random rand = new Random();
		int temp = rand.nextInt(numberToRandomize);
		return temp;
	}

	public void acceptSHLCookies(Boolean screenShotFlag) {
		try {
			Thread.sleep(800);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			boolean isCookiebot = (boolean) js.executeScript("return CookiebotDialog.visible");
			BaseTest.log.info("<wysiwyg><h3> Cookies box </h3></wysiwyg>",driver,false);
			if (isCookiebot) {
				BaseTest.cookiebotDialogCount = BaseTest.cookiebotDialogCount+1;
				js.executeScript("CookiebotDialog.submitConsent()");
				BaseTest.log.info("CookiebotDialog appeared.",screenShotFlag);
				Thread.sleep(2000);
				isCookiebot = (boolean) js.executeScript("return CookiebotDialog.visible");
				while (isCookiebot) {
					BaseTest.log.warning("CookiebotDialog is still appearing.",screenShotFlag);
					js.executeScript("CookiebotDialog.submitConsent()");
					break;
				}
			}
			else {
				BaseTest.log.info("CookiebotDialog not appeared.",driver,screenShotFlag);
			}
		}
		catch (Exception e) {
			if (BaseTest.cookiebotDialogCount > 0 )
				BaseTest.log.info("CookiebotDialog not appeared.",driver,true);
			else
				BaseTest.log.warning("Expected CookiebotDialog not found.");
		}
	}

	public void clickMethod(String elementName,String message,Boolean screenShotFlag,int...times){
		int time = (times.length > 0) ? times[0] : 0;
		time++;
		try{
			WebElement el=getWebElementOrNull(elementName);
			if(el==null){
				waitForElementVisibility(elementName);
			}

			waitForElementToBeClickable(elementName);
			WebElement ele=getWebElementOrNull(elementName);
			if(!ele.isDisplayed()){
				scrollDownToElement(ele);
				scrollUptheScreen(100);
			}
			ele.click();
			try { 
				BaseTest.log.info(message,screenShotFlag);
			} catch (Exception e) {
			}
		}catch (Exception e){
			if (time <= 1){
				clickMethod(elementName,message,screenShotFlag,time);
			}else {
				message="Failed in "+message;
//				UtilClass.addFailTestSetup(message,true);
			}

		}
	}
	public void clickMultipleCheckBox(String LabelNames) {
		WebElement ele;
		String labelName = null;
		String[] names = LabelNames.split(",");
		try {
			for(String name : names) {
				try {
					driver.findElement(By.xpath("//div[@class='btn-group ceb-dropdown-container show']//input[@type='text']")).clear();
					driver.findElement(By.xpath("//div[@class='btn-group ceb-dropdown-container show']//input[@type='text']")).sendKeys(name);

					ele=driver.findElement(By.xpath("(//a//label[contains(text(),'"+name+"')])[2]"));

				}catch(Exception e) {
					labelName=name;
					BaseTest.log.info("Not able to find checkbox element in normal list looking into elipsis list",true);
					ele=driver.findElement(By.xpath("//label[contains(text(),'"+name+"')])[2]"));
				}
				clickByjs(ele);
				BaseTest.log.info("Checkbox is clicked",true);
			}
		}catch(Exception ep) {
			BaseTest.log.error("Checkbox with label : " +labelName+ "is not visible");
			BaseTest.commonErrorList.add("Checkbox with label : " +labelName+ "is not visible");
		}

	}

//	public void sendDataMethod(String elementName,String elementData,String message,Boolean screenShotFlag,int...times){
//		int time = (times.length > 0) ? times[0] : 0;
//		time++;
//		try{
//			WebElement ele=getWebElement(elementName);
//			ele.click();
////			Thread.sleep(2000);
//			ele.clear();
//			ele.sendKeys(elementData);
//			message="Enter "+elementData+" in "+message;
//			BaseTest.log.info(message,screenShotFlag);
//		}catch (Exception e){
//			if (time <= 2){
//				sendDataMethod(elementName,elementData,message,screenShotFlag,time);
//			}else {
//				message="Failed in "+message;
//				UtilClass.addFailTestSetup(message,true);
//			}
//		}
//	}

	public void getCDNVersionForNewAdmin(String envURL) {
		try {
			String json = null;

			String cdnVersion = this.sendJSONRequest(envURL, json);
			BaseTest.log.info("<wysiwyg><h3>Application CDN : " + cdnVersion + "</h3></wysiwyg>");
			if (cdnVersion.contains("19") || cdnVersion.contains("20") || cdnVersion.contains("23")) {
				BaseTest.cdn = true;
			}else {
				BaseTest.cdn = false;
			}
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while getting CDN verion Number");
			BaseTest.commonErrorList.add("Error Occurred while getting CDN verion Number");
		}
	}

	/**
	 * After working through the complete application using Selenium ,it is noticed firefox and specially IE
	 * application is working very slow and hindering the flows.<br>
	 * This function helps (with a switch ON/OFF) - the common wait functions to take a breather<br> 
	 * before started trying to interact with the application back again.<br>
	 * Instead of typing in multiple hardcodes, applying it here, which can be switched Off when required.
	 * 
	 */
	public static void givingTimeToFirefoxIEToLoad(boolean flag) {
		try {
			if(flag)
				// This is giving time at many places for IE to load and render the page as expected.
				// Do not decrease the time, as it is been calculated by seeing the average time taken by the browser
				Thread.sleep(500);
		}catch(Exception e) {

		}
	}


	public String sendJSONRequest(String url, String json) throws JSONException {
		String appVersion = null;
		// Split environment url into form of api url
		String[] URL = null;
		try {
			if (url.contains("/player")) {
				URL = url.split("/player");
			} else {
				URL = url.split("/ce");
			}
			String apiURL = URL[0];
			url = apiURL + "/versioninfo/appversionjson";

			URL url1 = new URL(url);
			URLConnection request = (URLConnection) url1.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject rootobj = root.getAsJsonObject();
			appVersion = rootobj.get("appVersion").getAsString();

		} catch (Exception e) {
			e.printStackTrace();
			BaseTest.log.error("Error obtained while creating get score url in 'sendJSONRequest' testmethod.", true);
			BaseTest.commonErrorList
			.add("Error obtained while creating get score url in 'sendJSONRequest' testmethod.");
		}

		return appVersion;
	}


	/**
	 * Check whether an element exists on a webpage or not.
	 *
	 * @param elementName
	 * @return   True if an element exists false in an doestn't appear on webpage.
	 */
	public boolean doesElementExists(String elementName) {
		if (getWebElementsList(elementName).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	//This method will wait until all AJAX call complete.It requires jquery enabled
	public void wait_Until_Requests_to_Complete() {
		try {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40), Duration.ofSeconds(10));
			wait.until(new Function<WebDriver,Boolean>() {
				public Boolean apply(WebDriver driver) {
					JavascriptExecutor je = (JavascriptExecutor) driver;
					Boolean flag = false;
					if(Boolean.parseBoolean(String.valueOf(je.executeScript(" return jQuery.active === 0")))) {
						try {
							Thread.sleep(600);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(Boolean.parseBoolean(String.valueOf(je.executeScript(" return jQuery.active === 0")))) {
							flag=true;
						}
					}
					return flag;
				}
			});
		}catch(Exception e) {
		}
	}


	public void waitAndClick(WebElement element) {
		if(BaseTest.getBrowserName().equals("Internet Explorer"))
			clickByjs(element);
		else {
			waitForElementToBeClickable(element);
			element.click();
		}
	}

	public void clickByjs(WebElement ele) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("arguments[0].click();", ele);
	}

	/**
	 * Wait for Visibility of Brand Logo
	 */
	public void waitForVisibilityOfBrandLogoAtAdminSide() {
		try {
			this.waitForValuePresentInAttribute(By.xpath(getLocatorValue("companyBrandLogo")), "style", "background-image:");
		} catch (Exception ex) {
		}
	}

	/**
	 * Wait for In-Visibility of Brand Logo
	 */
	public void waitForInvisibilityOfBrandLogoAtAdminSide() {
		try {
			this.waitForValuePresentInAttribute(By.xpath(getLocatorValue("companyBrandLogo")), "style", "display: none");
		} catch (Exception ex) {
		}
	}

	/**
	 * This method is used to wait for value present in attribute
	 *
	 * @param element:   web element
	 * @param attribute: attribute name
	 * @param value:     attribute value
	 */
	public void waitForValuePresentInAttribute(By element, String attribute, String value) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.attributeContains(element, attribute, value));
		} catch (Exception ex) {
		}
	}

	/**
	 * This method is used to get list of WebElement from db and it replaces the text that we want to replace
	 *
	 * @param elementName:   element name
	 * @param textToReplace: text to replace
	 * @param replaceValue:  value with which we want to replace our text
	 * @return: WebElement
	 */
	public List<WebElement> getDynamicWebElementsList(String elementName, String textToReplace, String replaceValue) {
		return getWebElements.getDynamicListElements(driver, pageWebElements, elementName, textToReplace, replaceValue);
	}

	/**
	 * This method is used to get WebElement from db and it replaces the text that we want to replace
	 *
	 * @param elementName:   element name
	 * @param textToReplace: text to replace
	 * @param replaceValue:  value with which we want to replace our text
	 * @return: WebElement
	 */
	public WebElement getDynamicWebElement(String elementName, String textToReplace, String replaceValue) {
		return getWebElements.getDynamicVal(driver, pageWebElements, elementName, textToReplace, replaceValue);
	}


	/*
	 * this function clear values in given text box element---
	 */
	public void clearTextFromSelectedElement(WebElement elementValue)
	{
		elementValue.clear();
	}


	/*
	 * Verifies the presence of text in given element---
	 */
	public boolean isTextPresent(WebElement element)
	{
		if(getTextByJavascriptExecutor(element).isEmpty())
			return false;
		else
			return true;
	}

	/*
	 * returns text value using javascript in textbox---
	 */
	public String getTextByJavascriptExecutor(WebElement elementToRead)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return arguments[0].value;",elementToRead).toString();
	}
	//this function is used to send keys to particular webelement
	public void sendTextToElement(WebElement requiredElement,String textRequiredToSend)
	{
		requiredElement.sendKeys(Keys.CONTROL + "a");
		requiredElement.sendKeys(Keys.DELETE);
		requiredElement.sendKeys(textRequiredToSend);
		BaseTest.log.info("Entered Value : "+textRequiredToSend,true);
	}
	//this element is used for waiting till the particular element becomes invisible from web page
	public void waitUntillElementInvisbility(WebElement element) {
		new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.invisibilityOf(element));
	}

	/*
	 * returns text innerHTML using javascript in textbox---
	 */
	public String getInnerTextByJavascriptExecutor(WebElement elementToRead)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return arguments[0].innerText;",elementToRead).toString();
	}

	/**
	 * This method is used to wait for title present on the page
	 *
	 * @param title:   title to verify
	 * @param timeOut: timeout
	 */
	public void waitForTitlePresent(String title, int timeOut) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeOut)).until(ExpectedConditions.titleContains(title));
		} catch (Exception ex) {
		}
	}

	/**
	 * Wait for element to be clickable
	 *
	 * @param loadingElement: element
	 * @param timeOut:        timeout
	 */
	public void waitForElementToBeClickable(String loadingElement, int timeOut) {
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10))).until(
							ExpectedConditions.elementToBeClickable(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.linkText(loadingElement)));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10)))
					.until(ExpectedConditions.elementToBeClickable(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10))).until(ExpectedConditions
							.elementToBeClickable(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10))).until(
							ExpectedConditions.elementToBeClickable(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(10)))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * This method will verify that language is changed or not, we just have to provide two inputs:
	 *
	 * @param element:        with the help of this element we are getting the text from the UI
	 * @param textToVerify:   this is used to validate the text from the UI
	 * @param screenshotFlag: true if you want to take screenshots
	 */
//	public void verifyLanguageIsChanged(String element, String textToVerify, boolean screenshotFlag) {
//		try {
//			wait_Until_Requests_to_Complete();
//			waitForElementVisibility(element,45,textToVerify);
//			UtilClass.isEqual(getWebElement(element).getText(), textToVerify, "Verify language is changed", "Language is not changed", screenshotFlag);
//		} catch (Exception ex) {
//			objCapture.captureScreen(driver, "verifyLanguageIsChanged", screenshotFlag, BaseTest.failFolder.toString());
//			BaseTest.log.error("Error obtained while verifying language is changed" + ex.getLocalizedMessage());
//			BaseTest.commonErrorList.add("Error obtained while verifying language is changed" + ex.getLocalizedMessage());
//			ex.printStackTrace();
//		}
//	}

	/**
	 * This method is to verify the presence of scrollbar
	 *
	 * @param screenshotFlag: true if we want to take screenshot
	 * @param scrollBarName:  scroll bar name (locator name from db)
	 */
//	public void verifyScrollBarIsPresent(boolean screenshotFlag, String scrollBarName) {
//		try {
//			waitForElementToBeClickable(scrollBarName);
//			if (!getWebElement(scrollBarName).getAttribute("style").contains("display: block")) {
//				UtilClass.addFailTestSetup("Scroll bar is not present", true);
//			}
//		} catch (Exception e) {
//			objCapture.captureScreen(driver, "verifyScrollBarIsPresent", screenshotFlag, BaseTest.failFolder.toString());
//			BaseTest.log.error("Error Obtained while verifying scrollbar:" + e.getLocalizedMessage());
//			BaseTest.commonErrorList.add("Error Obtained while verifying scrollbar:" + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//	}

	/**
	 * This Method is used to press "Tab" Key of Keyboard Using Action Class
	 */
	public void pressTabKeyFromKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.TAB).build().perform();
			BaseTest.log.info("Tab key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the TAB Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the TAB Key");
		}
	}
	
	/**
	 * This Method is used to press "Escape" Key of Keyboard Using Action Class
	 */
	public void pressEscKeyFromKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.ESCAPE).build().perform();
			BaseTest.log.info("Escape key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the TAB Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the TAB Key");
		}
	}


	//This method will wait for until project add loader disappear.
	public void waitUntillAllLoaderInvisible() throws Exception {
		boolean visible = true;
		int count = 0;
		try {
			waitForElementVisibility(".//div[@class='commonloadingicon']/parent::div[contains(@style,'display: block;')]", 5, "loading element");
		}catch(TimeoutException e) {
			visible = false;
		}
		while(visible) {
			if(count == 2){
				throw new Exception("Loader is still loading after a long wait....");
			}
			try {
				new WebDriverWait(driver,Duration.ofSeconds(5), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='commonloadingicon']/parent::div[contains(@style,'display: block;')]")));
				count ++;
				Thread.sleep(5000);	
			}catch(TimeoutException e){
				break;
			}
		}
	}
	
	/**
	 * This is used to wait for loader to disappear designed for Aspiring Mind Module Assessments.
	 * @throws Exception WHen all load time is elapsed and loader is still visible.
	 * @author Vinay.Verma
	 */
	public void waitUntillLoaderToDisapperForAmModule() throws Exception {
		implicitWait(5);
		boolean isLoaderInvisible = false;
		int count = 0;
		try {
			isLoaderInvisible = (new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofSeconds(10))).until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains (@data-ng-if, 'model.showLoader')]")));
		}catch(TimeoutException e) {
			isLoaderInvisible = false;
		}
		while(!isLoaderInvisible) {//re-iterate if loader is NOT invisible
			if(count == 10)
				throw new Exception("Loader is still loading after a long wait....");
			try {
				isLoaderInvisible = new WebDriverWait(driver,Duration.ofSeconds(5), Duration.ofSeconds(10)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains (@data-ng-if, 'model.showLoader')]")));
				count ++;
			}catch(TimeoutException e){
				isLoaderInvisible = false;
			}
		}
		implicitWait(30);
	}

	/**
	 * This method is used to verify pagination functionality on any page
	 *
	 * @param gridLocator:            pass grid locator name that is saved in db
	 * @param nextElementLocator:     pass next element locator name that is saved in db
	 * @param previousElementLocator: pass previous element locator name that is saved in db
	 * @param firstElementLocator:    pass first element locator name that is saved in db
	 * @param lastElementLocator:     pass last element locator name that is saved in db
	 * @param screenshotFlag:         true if you want to take screenshot
	 */
//	public void verifyPaginationFunctionality(String gridLocator, String nextElementLocator, String previousElementLocator,
//			String firstElementLocator, String lastElementLocator, boolean screenshotFlag) {
//		try {
//			WebElement next, previous, first, last;
//			List<String> currentPageList;
//			List<String> newPageList;
//			next = getWebElementOrNull(nextElementLocator);
//			scrollToBottomOfThePage(screenshotFlag);
//			if (next != null) {
//				waitForJSandJQueryToLoad();
//				currentPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				waitAndClick(next);
//				BaseTest.log.info("Next button is clicked",screenshotFlag);
//				newPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				BaseTest.log.info("List of elements before clicking next button = "+currentPageList,screenshotFlag);
//				BaseTest.log.info("List of elements after clicking next button = "+newPageList,screenshotFlag);
//				if (verifyIfTwoListsAreEqualOrNot(currentPageList, newPageList, screenshotFlag)) {
//					UtilClass.addFailTestSetup("Next button functionality is not correct", screenshotFlag);
//				}
//				currentPageList.clear();
//				newPageList.clear();
//			}
//			previous = getWebElementOrNull(previousElementLocator);
//			if (previous != null) {
//				waitForJSandJQueryToLoad();
//				currentPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				waitAndClick(previous);
//				BaseTest.log.info("Previous button is clicked",screenshotFlag);
//				newPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				BaseTest.log.info("List of elements before clicking previous button = "+currentPageList,screenshotFlag);
//				BaseTest.log.info("List elements after clicking previous button = "+newPageList,screenshotFlag);
//				if (verifyIfTwoListsAreEqualOrNot(currentPageList, newPageList, screenshotFlag)) {
//					UtilClass.addFailTestSetup("Previous button functionality is not correct", screenshotFlag);
//				}
//				currentPageList.clear();
//				newPageList.clear();
//			}
//			last = getWebElementOrNull(lastElementLocator);
//			if (last != null) {
//				waitForJSandJQueryToLoad();
//				currentPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				waitAndClick(last);
//				BaseTest.log.info("Last button is clicked",screenshotFlag);
//				newPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				BaseTest.log.info("List of elements before clicking last button = "+currentPageList,screenshotFlag);
//				BaseTest.log.info("List of elements after clicking last button = "+newPageList,screenshotFlag);
//				if (verifyIfTwoListsAreEqualOrNot(currentPageList, newPageList, screenshotFlag)) {
//					UtilClass.addFailTestSetup("Last button functionality is not correct", screenshotFlag);
//				}
//				currentPageList.clear();
//				newPageList.clear();
//			}
//			first = getWebElementOrNull(firstElementLocator);
//			if (first != null) {
//				waitForJSandJQueryToLoad();
//				currentPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				waitAndClick(first);
//				BaseTest.log.info("First button is clicked",screenshotFlag);
//				newPageList = getListOfValuesFromListOfWebElements(getWebElementsList(gridLocator), screenshotFlag);
//				BaseTest.log.info("List of elements before clicking first button = "+currentPageList,screenshotFlag);
//				BaseTest.log.info("List of elements after clicking first button = "+newPageList,screenshotFlag);
//				if (verifyIfTwoListsAreEqualOrNot(currentPageList, newPageList, screenshotFlag)) {
//					UtilClass.addFailTestSetup("First button functionality is not correct", screenshotFlag);
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			objCapture.captureScreen(driver, "verifyPaginationFunctionality", screenshotFlag, BaseTest.failFolder.toString());
//			BaseTest.log.error("Error occurred while verifying pagination on page" + ex.getLocalizedMessage(), true);
//			BaseTest.commonErrorList.add("Error occurred while verifying pagination on page");
//		}
//		scrollToTopOfThePage(screenshotFlag);
//	}

	/**
	 * This method is used to convert list of web element into list of their values
	 *
	 * @param listOfElement:  list of element
	 * @param screenshotFlag: true if you want to take screenshot
	 * @return: list of values
	 */
	public List<String> getListOfValuesFromListOfWebElements(List<WebElement> listOfElement, boolean screenshotFlag) {
		List<String> listOfString = new ArrayList<>();
		try {
			for (WebElement element : listOfElement) {
				listOfString.add(element.getText());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "convertListOfWebElementIntoListOfListOfValues", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while converting list of web element into list of values" + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while converting list of web element into list of values");
		}
		return listOfString;
	}

	/**
	 * This method is used to verify that if two list are equal or not
	 *
	 * @param listOne:        list one
	 * @param listTwo:        list  two
	 * @param screenshotFlag: true if you want to take screenshot
	 * @return true/false
	 */
	public boolean verifyIfTwoListsAreEqualOrNot(List<String> listOne, List<String> listTwo, boolean screenshotFlag) {
		try {
			if (listOne.size() != listTwo.size()) {
				return false;
			} else {
				return listOne.equals(listTwo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "verifyIfTwoListsAreEqualOrNot", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while verifying if two list are equal or not" + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while verifying if two list are equal or not");
		}
		return false;
	}

	/**
	 * This method is used to wait for text present in element
	 *
	 * @param element:        element
	 * @param expectedText:   expected text
	 * @param screenshotFlag: true if you want to take screenshot
	 */
	public void waitForTextPresentInElement(WebElement element, String expectedText, boolean screenshotFlag) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30),Duration.ofSeconds(10)).until(ExpectedConditions.textToBePresentInElement(element, expectedText));
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "waitForTextPresentInElement", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while waiting for text present in element" + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while waiting for text present in element");
		}
	}

	/**
	 * This method is used to check if element is displayed on ui or not
	 *
	 * @param element: element
	 * @return: true/false
	 */
	public boolean isElementDisplayed(String element) {
		try {
			WebElement e = getWebElementOrNull(element);
			if(e.isDisplayed()==true)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}	
	/**
	 * This method is used to verify that if element is available or not
	 *
	 * @param element: elment name in db
	 * @param elementName: element name on ui
	 * @param screenshotFlag: true if you want to take screenshots
	 */
//	public boolean verifyIfElementIsAvailableOrNot(String element, String elementName, boolean screenshotFlag) {
//		boolean isAvailable = isElementDisplayed(element);
//		if (isAvailable) {
//			BaseTest.log.info(elementName + " is available", screenshotFlag);
//		} else {
//			UtilClass.addFailTestSetup(elementName + " is not available", screenshotFlag);
//		}
//		return isAvailable;
//	}

	/**
	 * This method is used to scroll to top of the page
	 *
	 * @param screenshotFlag: true if you want to take screenshots
	 */
	public void scrollToTopOfThePage(boolean screenshotFlag) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			Thread.sleep(700);
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "scrollToTopOfThePage", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while scrolling to top of the page" + ex.getLocalizedMessage(), screenshotFlag);
			BaseTest.commonErrorList.add("Error occurred while scrolling to top of the page");
		}
	}

	/**
	 * This method is used to scroll to bottom of the page
	 *
	 * @param screenshotFlag: true if you want to take screenshots
	 */
	public void scrollToBottomOfThePage(boolean screenshotFlag){
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
			Thread.sleep(5000);
			BaseTest.log.info("Page Scrolled for verification", screenshotFlag);
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "scrollToBottomOfThePage", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while scrolling to bottom of the page" + ex.getLocalizedMessage(), screenshotFlag);
			BaseTest.commonErrorList.add("Error occurred while scrolling to bottom of the page");
		}
	}

	/**
	 * This method will select a value from select drop down 
	 * 
	 * @param textValue   text value you want to select
	 * @param selectElement select web element
	 * @param screenShotFlag true if you want to take screenshot
	 */
	public void selectValueFromDropdownByVisibleText(String textValue, WebElement selectElement, boolean screenShotFlag) {
		try {
			Select selectOption = new Select(selectElement);
			selectOption.selectByVisibleText(textValue);
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "selectValueFromDropdownByVisibleText", screenShotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while selecting value from dropdown" + ex.getLocalizedMessage(), screenShotFlag);
			BaseTest.commonErrorList.add("Error occurred while selecting value from dropdown");
		}
	}

	/**
	 * This method will select a value from select drop down on the
	 * basis of value**
	 * @param textValue   text value you want to select
	 * @param selectElement select web element
	 * @param screenShotFlag true if you want to take screenshot
	 */
	public void selectFromDropdownByValue(String textValue, WebElement selectElement, boolean screenShotFlag) {
		try {
			waitForJSandJQueryToLoad();
			Select selectOption = new Select(selectElement);
			selectOption.selectByValue(textValue);
			wait_Until_Requests_to_Complete();
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "selectValueFromDropdownByVisibleText", screenShotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while selecting value from dropdown" + ex.getLocalizedMessage(), screenShotFlag);
			BaseTest.commonErrorList.add("Error occurred while selecting value from dropdown");
		}
	}
	
	/**
	 * This method will select a value from select drop down on the
	 * basis of index
	 * @param index   index value you want to select
	 * @param selectElement select web element
	 * @param screenShotFlag true if you want to take screenshot
	 */
//	public String selectFromDropdownByIndex(int index, String element, boolean screenShotFlag) {
//		String selectedValue=null;
//		try {
//			if(getWebElementOrNull(element)!=null) {
//			Select selectOption = new Select(getWebElementOrNull(element));
//			selectOption.selectByIndex(index);
//			Thread.sleep(1000);
//			selectedValue=selectOption.getFirstSelectedOption().getText();
//			BaseTest.log.info("Selected Value in the drop down : "+selectedValue,screenShotFlag);
//			}else
//				UtilClass.addFailTestSetup(element +" Not available", screenShotFlag);
//		} catch (Exception e) {
//			UtilClass.addFailTestSetup("Erro obtained while selecting value from dropdown",e, screenShotFlag);
//		}
//		return selectedValue;
//	}


	/**
	 * Get available language for product
	 *
	 * @param elementName: locator name
	 * @param screenshotFlag: true if you want to take screenshots
	 * @return: list of available language
	 */
//	public List<String> getAvailableLanguageForProduct(String elementName, boolean screenshotFlag) {
//		List<String> listOfAvailableLanguages = new ArrayList<>();
//		WebElement languageDropDownElement;
//		try {
//			waitForElementVisibility(elementName);
//			languageDropDownElement = getWebElementOrNull(elementName);
//			if (languageDropDownElement != null) {
//				listOfAvailableLanguages = getListOfValuesFromListOfWebElements(new Select(languageDropDownElement).getOptions()
//						, screenshotFlag);
//			} else {
//				UtilClass.addFailTestSetup("Language dropdown is not available", screenshotFlag);
//			}
//		} catch (Exception ex) {
//			objCapture.captureScreen(driver, "getAvailableLanguageForProduct", screenshotFlag, BaseTest.failFolder.toString());
//			BaseTest.log.error("Error obtained while getting available language for product " + ex.getLocalizedMessage());
//			BaseTest.commonErrorList.add("Error obtained while getting available language for product " + ex.getLocalizedMessage());
//			ex.printStackTrace();
//		}
//		return listOfAvailableLanguages;
//	}

	/**
	 * Verify all language are available for product
	 *
	 * @param listOfExpectedLanguages: list of expected language
	 * @param elementName: locator name
	 * @param screenshotFlag: true if you want to take screenshots
	 */
//	public void verifyAllLanguagesAreAvailableForProduct(List<String> listOfExpectedLanguages, String elementName, boolean screenshotFlag) {
//		try {
//			List<String> listOfAvailableLanguages = getAvailableLanguageForProduct(elementName, screenshotFlag);
//			if(listOfAvailableLanguages.size()==0){
//				listOfAvailableLanguages = getAvailableLanguageForProductUsingWebElement(driver.findElement(By.xpath("((//div[@id='accordion'])[6]//select[@data-bind='selectedLanguage'])[1]")), screenshotFlag);
//			}
//			Collections.sort(listOfAvailableLanguages);
//			Collections.sort(listOfAvailableLanguages);
//			if (listOfAvailableLanguages.size() == listOfExpectedLanguages.size()) {
//				if (listOfAvailableLanguages.containsAll(listOfExpectedLanguages)) {
//					BaseTest.log.info("All languages are available i.e. " + listOfAvailableLanguages, screenshotFlag);
//				} else {
//					UtilClass.addFailTestSetup("All languages are not available for product.<br /><br />"
//							+ "Expected = " + listOfExpectedLanguages + " <br /><br /> but Found = " + listOfAvailableLanguages, screenshotFlag, screenshotFlag);
//				}
//			} else {
//				UtilClass.addFailTestSetup("All languages are not available for product.<br /><br />"
//						+ "Expected = " + listOfExpectedLanguages + " <br /><br /> but Found = " + listOfAvailableLanguages, screenshotFlag, screenshotFlag);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			objCapture.captureScreen(driver, "verifyAllLanguagesAreAvailableForProduct", screenshotFlag, BaseTest.failFolder.toString());
//			BaseTest.log.error("Error occurred while verifying all languages are available for product: " + ex.getLocalizedMessage(), true);
//			BaseTest.commonErrorList.add("Error occurred while verifying all languages are available for product");
//		}
//	}
//
	/**
	 * This method is used to press enter from keyboard
	 *
	 * @param screenshotFlag: true if you want to take screenshots
	 */
	public void pressEnterFromKeyboard(boolean screenshotFlag) {
		try {
			new Actions(driver).sendKeys(Keys.ENTER).perform();
			BaseTest.log.info("Enter key is pressed",screenshotFlag);
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "pressEnterFromKeyboard", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while pressing enter key from keyboard: " + ex.getLocalizedMessage(), screenshotFlag);
			BaseTest.commonErrorList.add("Error occurred while pressing enter key from keyboard");
		}
	}

	/**
	 * This method will switch to new tab after clicking on given element 
	 * 
	 * @param elementToBeClicked  pass the element which need to be clicked
	 * @param screenShotFlag      true if you want to take screenshot
	 * 
	 * @return  previous window handle if you want to switch back to old window
	 */
	public String switchToNewTab(String elementToBeClicked, boolean screenShotFlag) {
		String currentWindowHandle =  driver.getWindowHandle();
		Set<String> windowHandleBefore =  driver.getWindowHandles();
		waitForElementToBeClickable(elementToBeClicked);
		WebElement element = getWebElementOrNull(elementToBeClicked);
		if (element != null) {
			element.click();
			wait_Until_Requests_to_Complete();
			BaseTest.log.info("Clicked given element: "+ elementToBeClicked, screenShotFlag);
			Set<String> windowHandleAfter =  driver.getWindowHandles();
			windowHandleAfter.removeAll(windowHandleBefore);
			driver.switchTo().window(windowHandleAfter.stream().findFirst().get());
			waitForElementVisibility(elementToBeClicked);
		} else {
			BaseTest.log.info("Given element is not visisble:" + elementToBeClicked, screenShotFlag);
		}

		return currentWindowHandle;
	}

	/**
	 * This method will return a date in given formate
	 * 
	 * @param format  date format in which you want a date like yyyy-MM-dd
	 * 
	 * @return  formated date in string form
	 */
	public String getCurrentDateInStringFormate(String format, String timeZone) {
		ZoneOffset zoneOffset = ZoneOffset.of(timeZone);
		ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);
		LocalDate now = LocalDate.now(zoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		String formatCurrentDate = now.format(formatter);

		return formatCurrentDate;
	}

	
	/**
	 * This method will rerturn a time in same format after adding minutes in it
	 * 
	 * @param format  date format in which we provide a time like hh:mm
	 * @param min     minutes which need to add in time
	 * @param time    time in which addition reuqired
	 * 
	 * @return  formated date in string form
	 */
	public String addMinutesToTime(String format, int min,String time) {
		String updatedFormatTime=null;
		try {
		 SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		 Date parsedTime = dateFormat.parse(time);
		 Calendar c = Calendar.getInstance();
	        c.setTime(parsedTime);
	        c.add(Calendar.MINUTE, min);
	        Date updateTime = c.getTime();
	        updatedFormatTime= dateFormat.format(updateTime);
	        }
		catch(Exception e) {
			e.printStackTrace();
		}
		return updatedFormatTime;
	}
	/**
	 * This method will return file name from a given path
	 * 
	 * @param filePath path of file from which you want to get file name
	 *  
	 * @return  file name
	 */
	public String getFileNameFromGivenPath(String filePath) {
		Path path = Paths.get(filePath);

		return path.getFileName().toString();
	}

	public WebElement getLastElementOfWebElementList(String elementName) {
		WebElement element=null;
		try {
			List<WebElement> elementList=getWebElementsList(elementName);
			int n=elementList.size()-1;
			element=elementList.get(n);
		} catch (Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "getLastElementOfWebElementList", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while getting Last Element Of WebElementList " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while getting Last Element Of WebElementList");
		}
		return element;
	}

	/**
	 * Change date format
	 *
	 * @param sdate: date
	 * @param currentFormat: date format before change
	 * @param changedFormat: format after change
	 */
	public String changeDateFormat(String sdate, String currentFormat, String changedFormat) {
		String date1 = null;
		SimpleDateFormat formatter = null;
		Date date = null;
		try {
			formatter = new SimpleDateFormat(currentFormat);
			date = formatter.parse(sdate);
			DateFormat dateFormat = new SimpleDateFormat(changedFormat);
			date1 = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

	//Mehtod will return list of text 
	public List<String> getElementsText(By locator) {
		List<WebElement> listElement = new ArrayList<WebElement>();
		List<String> listElementText = new ArrayList<String>();
		listElement = driver.findElements(locator);
		for(WebElement element : listElement) {
			listElementText.add(element.getText());
		}
		return listElementText;
	}

	/**
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public Date addDays(Date date, int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); 
		return cal.getTime();
	}

//	public void vrClearValues(boolean screanShotFlag) {
//		try {
//
//			PlayerSolutionFeedback.isProgressIndicatorConditionEnable= true;
//			AdminCreateNewTemplate.spanishmap=null;
//			AdminCreateNewTemplate.chineseMap=null;
//			AdminCreateNewTemplate.ArabicMap=null;
//			AdminSettingPage.isChangingMinqualResponseEnable=false;
//			BaseTest.log.info("Value is cleared ",screanShotFlag);
//		} catch (Exception e) {
//			e.printStackTrace();
//			BaseTest.log.info("Error obtained while cleaing value " + "from the basepage" + e.getLocalizedMessage());
//		}
//	}

	/**
	 * This Method is used to press "Space" Key of Keyboard Using Action Class
	 */
	public void pressSpaceKeyFromKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.SPACE).build().perform();
			BaseTest.log.info("Space key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the SPACE Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the SPACE Key");
		}
	}

	/**
	 * This function clicks Shift key from keyboard
	 */
	public void pressShiftKeyFromKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.SHIFT).build().perform();
			BaseTest.log.info("Shift key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the SHIFT Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the SHIFT Key");
		}
	}

	/**
	 * This function clicks Shift and tab key from keyboard
	 */
	public void pressShiftAndTabKeyFromKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.keyDown(Keys.SHIFT).perform();
			ac.sendKeys(Keys.TAB).perform();
			ac.keyUp(Keys.SHIFT).perform();
			BaseTest.log.info("Shift and TAB key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the SHIFT Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the SHIFT Key");
		}
	}

	/**
	 * THIS method is used to press arrow keys
	 * @param interaction
	 */
	public void pressArrowKeysFromKeyboard(String interaction) {
		try {
			Actions ac = new Actions(driver);
			switch(interaction) {
			case "ARROW_UP":ac.sendKeys(Keys.ARROW_UP).build().perform();
			BaseTest.log.info("ARROW UP is pressed",true);
			break;
			case "ARROW_DOWN":ac.sendKeys(Keys.ARROW_DOWN).build().perform();
			BaseTest.log.info("ARROW DOWN is pressed",true);
			break;
			case "ARROW_LEFT":ac.sendKeys(Keys.ARROW_LEFT).build().perform();
			BaseTest.log.info("ARROW LEFT is pressed",true);
			break;
			case "ARROW_RIGHT":ac.sendKeys(Keys.ARROW_RIGHT).build().perform();
			BaseTest.log.info("ARROW RIGHT is pressed",true);
			break;
			}
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the ARROW Keys");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the ARROW Keys");
		}
	}

	/**
	 * This method contains keyboard interaction function calls
	 * @param interaction
	 */
	public void keyboardInteraction(String interaction) {
		switch(interaction) {
		case "TAB": pressTabKeyFromKeyboard();
		break;
		case "ENTER": pressEnterFromKeyboard(true);
		break;
		case "ESC": pressEscKeyFromKeyboard();
		break;
		case "SHIFT":pressShiftKeyFromKeyboard();
		break;
		case "SPACE": pressSpaceKeyFromKeyboard();
		break;
		case "ARROW_UP": pressArrowKeysFromKeyboard(interaction);
		break;
		case "ARROW_DOWN": pressArrowKeysFromKeyboard(interaction);
		break;
		case "ARROW_LEFT":pressArrowKeysFromKeyboard(interaction);
		break;
		case "ARROW_RIGHT": pressArrowKeysFromKeyboard(interaction);
		break; 
		case "SHIFTTAB": pressShiftAndTabKeyFromKeyboard();
		break;
		}
	}

	/**
	 * This function performs the interaction given number of times
	 * @param number
	 * @param interaction
	 */
	public void numberOfKeyBoardInteraction(int number,String interaction) {
		for(int i=0;i<number;i++) {
			keyboardInteraction(interaction);
		}
		BaseTest.log.info(interaction+" key is pressed "+number+" times",true);
	}

	/**
	 * This method is used to get the active element on the webPage
	 * @return
	 */
	public WebElement getactiveElement() {
		WebElement activeElement=null;
		try {
			activeElement = driver.switchTo().activeElement();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "getActiveElement", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while getting active Element of the WebPage " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while getting active Element of the WebPage");
		}
		return activeElement;
	}

	/**
	 * 
	 * @param element
	 * @param screenShotFlag
	 * @return
	 */
	public boolean verifyCorrectElementFocussed(WebElement element,Boolean screenShotFlag, String attribute)
	{
		Boolean flag=false;

		try {
			WebElement activeElement=getactiveElement();
			if(element.equals(activeElement)) {
				BaseTest.log.info("The focus is on Element : <b>"+attribute+"</b>",true);
				givingTimeToFirefoxIEToLoad(true);
				flag=true;
			}
			else {
//				UtilClass.addFailedTestStep("The focus is not on the correct element. Element : <b>"+attribute+"</b>",screenShotFlag, 0);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "verifyFocusToElement", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while verifying to Element of the WebPage " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while verifying to Element of the WebPage ");
		}
		return flag;
	}

	/**
	 * 
	 * @param element
	 */
	public void makeElementFocussed(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].focus();",element);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "makeElementFocussed", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while focussing Element " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while focussing Element ");
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getTodaysDateInDDMMYYYY() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	/**
	 * 
	 * @return
	 */
	public String getTodaysDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}


	/**
	 * 
	 * @param element
	 * @param screenShotFlag
	 */
	public int navigateAcrossPageUsingKeyboard(WebElement element,String interaction,Boolean screenShotFlag) {
		int count=0;
		try {

			while(!getactiveElement().equals(element)) {
				if(count>1000) {
					break;
				}
				numberOfKeyBoardInteraction(1,interaction);
				count++;
			}
			BaseTest.log.info("The total number of times "+interaction+" is pressed is "+count,screenShotFlag,true);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "naviagatingviaKeyboard", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while navigating via keyboard " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while navigating via keyboard ");
		}
		return count;
	}
	public Boolean waitUntilElementVisibility(String loadingElement) {
		Boolean isDisplayed = true;
		int waitTime = 90;
		try {
			String elementType = getWebElements.getElementLocatorType(driver, pageWebElements, loadingElement);
			if (elementType != null) {
				if (elementType.contains("id")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.id(getLocatorValue(loadingElement))));
				} else if (elementType.contains("class")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.className(getLocatorValue(loadingElement))));
				} else if (elementType.contains("linkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.linkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("xpath")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath(getLocatorValue(loadingElement))));
				} else if (elementType.contains("name")) {
					(new WebDriverWait(driver,Duration.ofSeconds(waitTime))).until(
							ExpectedConditions.visibilityOfElementLocated(By.name(getLocatorValue(loadingElement))));
				} else if (elementType.contains("partialLinkText")) {
					(new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.partialLinkText(getLocatorValue(loadingElement))));
				} else if (elementType.contains("cssSelector")) {
					(new WebDriverWait(driver,Duration.ofSeconds(waitTime))).until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector(getLocatorValue(loadingElement))));
				}
			} else {
				(new WebDriverWait(driver, Duration.ofSeconds(waitTime)))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loadingElement)));
			}
		} catch (Exception e) {
			isDisplayed = false;
			return isDisplayed;
		}
		return isDisplayed;
	}
	/**
	 * verify all elements of a list are distinct
	 * @param arr
	 * @return
	 */
	public  boolean areDistinct(List<String> arr) 
	{ 
		// Put all array elements in a HashSet 
		Set<String> s =  
				new HashSet<String>(arr); 

		// If all elements are distinct, size of 
		// HashSet should be same array. 
		return (s.size() == arr.size()); 
	} 

	/**
	 * Method to check the web element is enabled or not.
	 * 
	 * @param errorList
	 *            - Instance of errorList
	 *            - instance of base logger
	 * @param elementNameToDisplay
	 *            - element name to be displayed in log file
	 * @param elementNameIndb
	 *            - element name defined in database
	 */
	public void isElementEnabled(ArrayList<String> errorList, String elementNameToDisplay,
			String elementNameIndb,boolean screenShotFlag) {
		final String ELEMENT_NOT_FOUND = " not found.";
		final String ELEMENT_NOT_ENABLED = " not enabled.";
		WebElement element = getWebElementOrNull(elementNameIndb);
		if (element == null) {
			BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_FOUND, screenShotFlag);
			errorList.add(elementNameToDisplay + ELEMENT_NOT_FOUND);
		} else {
			if (/*!wait.until(ExpectedConditions.visibilityOf(element))*/!element.isEnabled()) {
				BaseTest.log.error(elementNameToDisplay + ELEMENT_NOT_ENABLED,screenShotFlag);
				errorList.add(elementNameToDisplay + ELEMENT_NOT_ENABLED);
			} else {
				BaseTest.log.info(elementNameToDisplay + " element is enabled", screenShotFlag);
			}
		}
	}

	/**
	 * Click on a web element with null check and with error and info logs
	 * 
	 * @param screenShotFlag
	 * 
	 */
	public void clickWebElement(Boolean screenShotFlag, String elmentName, String elementDescription) {

		try {
			waitForElementVisibility(elmentName);

			WebElement webElment = getWebElementOrNull(elmentName);

			if (webElment != null) {
				webElment.click();
				objCapture.captureScreen(driver, "clicked on " + elementDescription, screenShotFlag, BaseTest.folder.toString());
				BaseTest.log.info("clicked on " + elementDescription, screenShotFlag);
			} else {
				BaseTest.log.error("Error Clicking On " + elementDescription, screenShotFlag);
				BaseTest.commonErrorList.add("Error Clicking On " + elementDescription);
				objCapture.captureScreen(driver, "Error Clicking On " + elementDescription, screenShotFlag,
						BaseTest.failFolder.toString());
			}
		} catch (Exception e) {
			objCapture.captureScreen(driver, "Error Clicking On " + elementDescription, screenShotFlag,
					BaseTest.failFolder.toString());
			BaseTest.log.error(
					"Error Clicking On " + elementDescription + e.getLocalizedMessage(),
					screenShotFlag);
			BaseTest.commonErrorList.add("Error Clicking On " + elementDescription);
			e.printStackTrace();
		}
	}
	/**
	 * Perform mouse hover on a given element
	 * 
	 * @param screenShotFlag
	 * @param elmentName
	 * @param elementDescription
	 */
	public void mouseHover(Boolean screenShotFlag, String elmentName, String elementDescription) {

		try {
			waitForElementVisibility(elmentName);

			WebElement webElment = getWebElementOrNull(elmentName);

			if (webElment != null) {
				Actions actions = new Actions(driver);
				actions.moveToElement(webElment).perform();
				BaseTest.log.info("hover on " + elementDescription, screenShotFlag);
				objCapture.captureScreen(driver, "hover on " + elementDescription, screenShotFlag, BaseTest.folder.toString());
			} else {
				BaseTest.log.error("Error hovering on " + elementDescription, screenShotFlag);
				BaseTest.commonErrorList.add("Error hovering on " + elementDescription);
				objCapture.captureScreen(driver, "Error hovering on " + elementDescription, screenShotFlag,
						BaseTest.failFolder.toString());
			}
		} catch (Exception e) {
			objCapture.captureScreen(driver, "Error hovering on " + elementDescription, screenShotFlag,
					BaseTest.failFolder.toString());
			BaseTest.log.error(
					"Error hovering on " + elementDescription + e.getLocalizedMessage(),
					screenShotFlag);
			BaseTest.commonErrorList.add("Error hovering on " + elementDescription);
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public String getNumberFromString(String str)
	{
		str = str.replaceAll("[^\\d]", " "); 
		str = str.trim(); 
		str = str.replaceAll(" +", " "); 
		if (str.equals("")) 
		{return "-1";} 

		return str; 
	}

	//Mehtod will return list of text BY JS
	public List<String> getElementsTextByJS(By locator) {
		List<WebElement> listElement = new ArrayList<WebElement>();
		List<String> listElementText = new ArrayList<String>();
		listElement = driver.findElements(locator);
		for(WebElement element : listElement) {
			listElementText.add(getInnerTextByJavascriptExecutor(element));
		}
		return listElementText;
	}

	/**
	 * this function gives string of random alphates of particular size
	 */
	public String getRandomString(int size)
	{
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < size) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return (saltStr);
	}

	/** open recently closed tab using keyboard ctrl+shift+T
	 * 
	 */
	public void openRecentlyCloseTabUsingKeyboard() {
		try {
			Actions ac = new Actions(driver);
			ac.keyDown(Keys.CONTROL).perform();
			ac.keyDown(Keys.SHIFT).perform();
			ac.sendKeys("T");
			ac.keyUp(Keys.SHIFT).perform();
			ac.keyUp(Keys.CONTROL).perform();
			/*ac.sendKeys(Keys.CONTROL, Keys.SHIFT , "t");*/
			BaseTest.log.info("Control+Shift+t key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the SHIFT Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the SHIFT Key");
		}
	}

	/**open new tab
	 * 
	 */
	public void openNewTabUsingKeyboard() {
		try {
			

			JavascriptExecutor jseUp = (JavascriptExecutor) driver;
			jseUp.executeScript("window.open();");

			BaseTest.log.info("Control+t key is pressed",true);
		}
		catch (Exception e) {
			BaseTest.log.error("Error Occurred while Pressing the SHIFT Key");
			BaseTest.commonErrorList.add("Error Occurred while Pressing the SHIFT Key");
		}
	}

	/**
	 * Verifies color code for an element
	 * 
	 * @param elementtoCheck
	 * @param colorCode
	 * @param screenShotFlag
	 * @param Elementname
	 * @param scenario
	 * @param shouldMatch
	 * @author Nishant Saxena
	 */
	public void verifyBrandingColorCodeForSVGElement(String elementtoCheck, String colorCode, Boolean screenShotFlag,
			String Elementname, String scenario, Boolean shouldMatch) {
		try {
			/* waitForElementVisibility(elementtoCheck); */
			WebElement el = getWebElementOrNull(elementtoCheck);

			if (el != null) {
				String a = el.getCssValue("fill");
				String color2 = a.replace("rgb(", "");
				color2 = color2.replace(")", "");
				String color1[];
				color1 = color2.split(",");

				String hexCodeUI = String.format("#%02x%02x%02x", Integer.parseInt(color1[0].trim()),
						Integer.parseInt(color1[1].trim()), Integer.parseInt(color1[2].trim()));

				if (shouldMatch) {
					if (hexCodeUI.equalsIgnoreCase(colorCode)) {

						BaseTest.log.info("Hex color code value i.e. " + hexCodeUI + " is " + " matched with value: "
								+ colorCode + " for " + Elementname, true);
					} else {

//						UtilClass.addFailTestSetup("Hex color code value i.e. " + hexCodeUI + " is "
//								+ " not matched with value: " + colorCode + " for " + Elementname, true);

					}
				} else {
					if (hexCodeUI.equalsIgnoreCase(colorCode)) {

//						UtilClass.addFailTestSetup(
//								"Hex Color code for" + " " + colorCode + " " + "is not correct in UI", true);

					} else {
						// Nothing to do
					}
				}
			} else {
				// Nothing to do
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param elementDisplayName- user defined at run time
	 * @param loadingElement-could be saved element in DB or Xpath
	 * @param loadTime-  element loading time
	 * 
	 * */
	
	public void clickElement(String elementDisplayName, String loadingElement, boolean screenshot, int... loadTime)
			throws InterruptedException {
		try {
			boolean isTrue = (loadTime.length > 0) ? true : false;
			if (isTrue)
				waitForElementVisibility(loadingElement, loadTime[0], elementDisplayName);				
			else
				waitForElementVisibility(loadingElement);
			waitForElementToBeClickable(loadingElement);
			getWebElement(loadingElement).click();
			BaseTest.log.info(elementDisplayName + "is clicked", screenshot);
		} catch (Exception e) {
			BaseTest.log.error("Error obtained while clicking Element=" + elementDisplayName, screenshot);
			BaseTest.commonErrorList.add("Error obtained while clicking Element=" + elementDisplayName
					+"\n<br>"+new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName());
		}
	}
	
	/**
	 * @param elementDisplayName- user defined at run time
	 * @param loadingElement-could be saved element in DB or Xpath 
	 * @param loadTime-  element loading time
	 * @param inputValue- string value that needs to be typed in textbox
	 * */
	
	public void insertValues(String inputValue, String elementDisplayName, String loadingElement, boolean screenshot,
			int... loadTime) throws InterruptedException {
		try {
			boolean isTrue = (loadTime.length > 0) ? true : false;;
			if (isTrue)
				waitForElementVisibility(loadingElement);
			else
				waitForElementVisibility(loadingElement, loadTime[0], elementDisplayName);
			waitForElementToBeClickable(loadingElement);
			getWebElement(loadingElement).sendKeys(inputValue);
			Thread.sleep(1000);
			BaseTest.log.info(inputValue + "is entered in " + elementDisplayName + " Field", screenshot);
		} catch (Exception e) {
			BaseTest.log.error("Error obtained while entering " + inputValue + " in " + elementDisplayName + "Field",
					screenshot);
			BaseTest.commonErrorList.add("Error obtained while entering " + inputValue + " in " + elementDisplayName
					+ " Field" + "\n<br>" + new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName());
		}
	}
	
	/**
	 * switch to next tab
	 */
	public void switchToNextTab(boolean screenshotFlag) {
		try {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size() > 1) {
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(1500);
		}else {
			BaseTest.log.error("Tab is not opned", screenshotFlag);
			BaseTest.commonErrorList.add("Tab is not opned");
		}
		}catch(Exception e) {
//			UtilClass.addFailTestSetup("Error while switching Window", e);
		}
	}
	
	/**
	 * Modify url
	 *
	 * @param url: url
	 * @param textToReplace: text to replace
	 * @param replaceValue: replace value
	 * @return: modified url
	 */
	public String modifyUrl(String url, String textToReplace, String replaceValue) {
		String modifiedUrl = null;
		try {
			modifiedUrl = url.replace(textToReplace, replaceValue);
		} catch (Exception e) {
			e.printStackTrace();
//			UtilClass.addFailTestSetup("Error occurred while modifying url : " + e.getLocalizedMessage(), false);
		}
		return modifiedUrl;
	}
	public int generateRandomNumberWithinRange(int min,int max)
	{
		Random r = new Random();
		int result = r.nextInt(max-min) + min;
		return result;
	}
	
	/**
	 * This method will print the error or info in the log file.
	 *
	 * @param printLine
	 * @param value
	 * @return
	 */
	public void printCommandMethod(String printLine, Boolean value) {
		if (value == true) {
			BaseTest.log.error(printLine, true);
			BaseTest.commonErrorList.add(printLine);
		} else {
			BaseTest.log.info(printLine, true);
		}
	}

	public String getSelectedValueFromDropdown(WebElement element, String type, boolean screenshotFlag) {
		String value = null;
		try{
			Select select = new Select(element);
			WebElement selectedElement = select.getFirstSelectedOption();
			switch (type){
				case "text":
					value = selectedElement.getText();
					break;
				case "value":
					value = selectedElement.getAttribute("value");
					break;
				default:
					throw new Exception("Pass correct type i.e. text/value");
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
//			UtilClass.addFailTestSetup("Error occurred while getting value from drop down: "+ ex.getLocalizedMessage(),screenshotFlag);
		}
		return value;
	}
	

	public void verifyAbsenceOfElement(String element, Boolean screenShotFlag) throws SQLException {
		try {
		
			BaseTest.log.info("Verify absence of element = "+element, screenShotFlag);
			implicitWait(10);
			if (getWebElementsList(element).size()==0) {
				BaseTest.log.info("Element is not present on page = "+element, screenShotFlag);
			} else {
				BaseTest.log.error("Element is present on page, expected to be absent "+element,screenShotFlag);
				BaseTest.commonErrorList.add("Element is present on page, expected to be absent "+element);
			}			
			
		} catch (Exception e) {
			BaseTest.log.error("Error occured while verifying the absence of element"+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while verifying the absence of element");
		}
	}
	
	public void verifyVisibilityOfElement(String element,Boolean isVisible, Boolean screenShotFlag) throws SQLException {
		try {
		
			BaseTest.log.info("Verify visibility of element = "+element, screenShotFlag);
			implicitWait(10);
			if(isVisible)
			{
				if (getWebElement(element).isDisplayed()) {
					BaseTest.log.info("Element is getting displayed on page = "+element, screenShotFlag);
				} else {
						BaseTest.log.error("Element is not getting displayed on page. "+element,screenShotFlag);
						BaseTest.commonErrorList.add("Element is invisible on page, expected to be visible! "+element);
					}	
			}
			else
			{
				if (!getWebElement(element).isDisplayed()) {
					BaseTest.log.info("Element is not getting displayed on page = "+element, screenShotFlag);
				} else {
						BaseTest.log.error("Element is  getting displayed on page. It should not display! "+element,screenShotFlag);
						BaseTest.commonErrorList.add("Element is  getting displayed on page. It should not display! "+element);
					}	
			}
			
			
		} catch (Exception e) {
			BaseTest.log.error("Error occured while verifying the visibility of element"+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while verifying the visibility of element");
		}
	}
	
	public void verifyPresenceOfElementOnCurrentPage(String element, Boolean screenShotFlag) throws SQLException {
		try {
			waitForJSandJQueryToLoad();
			BaseTest.log.info("Verify presence of element = "+element, screenShotFlag);
			implicitWait(10);
			if (getWebElementsList(element).size()>0) {
				BaseTest.log.info("Element is present on page = "+element, screenShotFlag);
			} else {
//				UtilClass.addFailTestSetup("Element is absent on page, expected to be present "+element,screenShotFlag);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
//			UtilClass.addFailTestSetup("Error occured while verifying the presence of element",e,true);
		}
	}
	
	public void verifyElementCountOnCurrentPage(String element,int expectedCount, Boolean screenShotFlag) throws SQLException {
		try {
		
			BaseTest.log.info("Verify count of element = "+element, screenShotFlag);
			implicitWait(10);
			int actualCount = getWebElementsList(element).size();
			if (actualCount==expectedCount) {
				BaseTest.log.info("Correct count of element is present. Element is = "+element, screenShotFlag);
			} else {
				BaseTest.log.error("Incorrect count of element is present. Element is = "+element,screenShotFlag);
				BaseTest.commonErrorList.add("Incorrect count of element is present. Element is = "+element);
			}			
			
		} catch (Exception e) {
			BaseTest.log.error("Error occured while verifying the count of element"+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while verifying the count of element");
		}
	}
	
	
	
	public void sendKeysByJs(WebElement element,String text,boolean screenShotFlag) {
		try {
			JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
			myExecutor.executeScript("arguments[0].value='"+text+"';", element);
			element.click();
		}
		catch(Exception e) {
			BaseTest.log.error("Error occured while sending text to element"+ e.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error occured while sending text to element");
	
		}
	}
	
	public void selectIFrame(int iframeNumber) {
		try {
			driver.switchTo().frame(iframeNumber);
		} catch (Exception e) {
		}
	}
	
	public void comeOutOfIframe() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
		}
	}
	
	public void waitForElementPropertyGetChange(WebDriver driver, String locatorValue, 
			String attribute, String expectedAttribute) {
		try {
				for(int i = 0; i <= 50; i++ ) {
				if(driver.findElement(By.xpath(locatorValue)).getAttribute(attribute).
						contains(expectedAttribute))
					break;
				else
					Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int randomintGenarator(int i) {

		Random rnd = new Random();
		int ansoption = rnd.nextInt(i);
		return ansoption;

	    }
	    /**
		 * Generate integer data of specific count
		 * 
		 * @param digitCount
		 * @return
		 */
		public String IntegerDataGeneration(int digitCount) {
			String numericData = RandomStringUtils.randomNumeric(digitCount);
			return numericData;
		}

		/**
		 * Generate alphabetic data of specific count
		 * 
		 * @param digitCount
		 * @return
		 */

		public String AlphabeticDataGeneration(int digitCount) {
			String alphaData = RandomStringUtils.randomAlphabetic(digitCount);
			return alphaData;
		}

		/**
		 * Generate alphanumeric data of specific count
		 * 
		 * @param digitCount
		 * @return
		 */
		public String AlphaNumericDataGeneration(int digitCount) {
			String alphaNumericData = RandomStringUtils.randomAlphanumeric(digitCount);
			return alphaNumericData;
		}

        /**
         * Gets the height & width of specific DOM
         *
         * @param element
         * @param screenshot
         * @return
         */
        public HashMap<String, Integer>  getDomDimensions(WebElement element,boolean screenshot,String index) {
            HashMap<String, Integer> dimensions = new HashMap<>();
            String i="";
            try{
            waitForJSandJQueryToLoad();
            dimensions.put("width"+index,element.getSize().getWidth());
            dimensions.put("height"+index,element.getSize().getHeight());
        //	BaseTest.log.info("Width for the Dom isis:" +element.getSize().getWidth()+" pixels",screenshot,true);
            BaseTest.log.info("height for the Dom  is: "+element.getSize().getHeight()+" pixels",screenshot,true);
        }
        catch(Exception e){
//            UtilClass.addFailTestSetup("Error obtained in fetching dimention for DOM",e,screenshot);
        }return dimensions;}


	public void acceptCookiesFacebook(boolean screenShotFlag) {
		try {
			BaseTest.log.info("accept cookies facebook", screenShotFlag);
			waitForElementVisibility("AcceptAllCookiesFB");
			getWebElementOrNull("AcceptAllCookiesFB").click();
		}
		catch(Exception e) {
//			UtilClass.addFailTestSetup("Error obtained while accepting FaceBook Cookies ", screenShotFlag);

		}
	}
	
	
	/**
	 * This method convert Date/time respect to TimeZone of System
	 * @param inputFormat
	 * @param outputFormat
	 * @param date
	 * @param inputTimeZone - TimeZone in which date is provided e.g IST, GMT
	 * @return
	 */
	public String convertTimeZoneOfDate(String inputFormat, String outputFormat,String date,String inputTimeZone) {
		String outputDate = null;
		try {
			TimeZone.getDefault();
			SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
			dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
			Date inputTime= dateFormat.parse(date);
			DateFormat dateFormatString = new SimpleDateFormat(outputFormat);
			outputDate= dateFormatString.format(inputTime);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return outputDate;
		
		
	}

	/***
	 * to scroll up in frames
	 * 
	 * **/
	public void scrollUpInFrames() throws InterruptedException {
		try {
			comeOutOfIframe();
			scrollToTopOfThePage(false);
			Thread.sleep(1000);
			driver.switchTo().frame(0);
		} catch (Exception e) {
			scrollToTopOfThePage(false);
		}
	}
	
	// Handle drag and drop action
		public void dragAndDrop(WebElement draggable, WebElement to){
			try
			{
				Thread.sleep(250);
				// build and perform drag and drop with Advanced User Interactions API
				Actions builder = new Actions(driver);
				builder.dragAndDrop(draggable, to).perform();
			}
			catch(Exception e)
			{
//				UtilClass.addFailTestSetup("Error obtained while drag and drop.", e, true);
			}
		}
		
		public int genrateRandomOption(int numberToRandomize, int exceptDigit) {
			int temp = 0;
			try {
				Random rand = new Random();
				temp = rand.nextInt((numberToRandomize - 0) + 1);
				if (temp == exceptDigit) {
					temp = this.genrateRandomOption(numberToRandomize, exceptDigit);
				}
			} catch (Exception e) {
//				UtilClass.addFailTestSetup("Error obtained while generating the random numbers.", e, false);
			}
			return temp;
		}

		public void refreshPage(Boolean screenShotFlag){
			try{
				BaseTest.log.info("refresh current page", screenShotFlag);
				driver.navigate().refresh();
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
				waitForJSandJQueryToLoad();
			}
			catch(Exception e){
//				UtilClass.addFailTestSetup("Error Occured on page reload",e,true);
			}
		}
		
		/***
		 * 
		 * send text using Action
		 * **/
		public void sendTextUsingAction(boolean screenshotflag,WebElement element,String inputString) {
			try {
				Actions actions = new Actions(driver);
				actions.moveToElement(element);
				actions.click();
				actions.sendKeys(inputString);
				actions.build().perform();
				Thread.sleep(1000);
				BaseTest.log.info("Entered value : "+inputString,screenshotflag);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
			int random = start + rnd.nextInt(end - start + 1 - exclude.length);
			for (int ex : exclude) {
				if (random < ex) {
					break;
				}
				random++;
			}
			return random;
		}
		
		public void waitForNewWindowToOpen(int  NumberOfWindows) {
			try {
				for(int i=0;i<120;i++) {
					if(driver.getWindowHandles().size() == NumberOfWindows) {
						System.out.println("New Window opened : "+driver.getWindowHandles().size()+" Tabs Found");
						break;
					}else {
						System.out.println("Waiting for new window open ....");
						Thread.sleep(700);
					}
				}
			}catch(Exception e) {
				implicitWait(60);
				e.printStackTrace();
			}
			
		}
		
		public void continueSessionExpire() 
		{	try {
			Thread.sleep(800);
			
			WebElement session_expire= getWebElementOrNull(By.xpath("(//div[@id='generic-updates-modal-body']/..//button)[2]"));
			if(session_expire != null)
				session_expire.click();
		}
		catch (Exception e) {
			BaseTest.log.info("Session expire msg not found");
		}
	}
		
		//This method verifies the Dimension of the element which is displayed
		public void verifyDimensionOfTheElement(String WebElement, String cssValueName, String expectedDimension, String elementName,  Boolean screenShotFlag) {
			try {
				implicitWait(12);
				BaseTest.log.info("Verifying Dimension of the element which is displayed", screenShotFlag);
				String actualDimension = getWebElementOrNull(WebElement).getCssValue(cssValueName);
//				UtilClass.isEqual (actualDimension , expectedDimension , cssValueName +" is matched for " + elementName , cssValueName +" is not matched for " + elementName  , screenShotFlag);
			}catch (Exception e) {
				e.printStackTrace();
//				UtilClass.addFailTestSetup("Error obtained on Verifying Dimension of the element which is displayed", screenShotFlag);
			}
		}
		
		//This method verifies the color of the element which is displayed
		public void verifyColorOfTheElement(String WebElement, String cssValueName, String expectedColor, String elementName,  Boolean screenShotFlag) {
			try {
				implicitWait(12);
				BaseTest.log.info("Verifying color of the element which is displayed", screenShotFlag);
				String color = getWebElementOrNull(WebElement).getCssValue(cssValueName);
				String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");     
				hexValue[0] = hexValue[0].trim();
				int hexValue1 = Integer.parseInt(hexValue[0]);                 
				hexValue[1] = hexValue[1].trim();
				int hexValue2 = Integer.parseInt(hexValue[1]);                   
				hexValue[2] = hexValue[2].trim();
				int hexValue3 = Integer.parseInt(hexValue[2]);     
				String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);			
//				UtilClass.isEqual (actualColor.toUpperCase() , expectedColor , "Color is matched for " + elementName , "Color is not matched for " + elementName  , screenShotFlag);
			}catch (Exception e) {
				e.printStackTrace();
//				UtilClass.addFailTestSetup("Error obtained on Verifying color of the element which is displayed", screenShotFlag);
			}
		}

	//This method Click on element using JS
		public void clickOnElementUsingJavaScriptExecutor(WebElement l){
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("arguments[0].click();", l);
		}

	//This method verify Filter Values With WebElement
	public int verifyFilterValuesWithWebElement(String filter, String filterVal) {
		List<WebElement> totalValues = null;
		try {
			waitForElementVisibility(filter);
			WebElement element = getWebElementOrNull(filter);
			element.click();
			if (element != null) {
				Thread.sleep(2000);
				getAndWaitForElement(driver.findElement(By.xpath("//ul[@data-json_key='candidate."+filterVal+"']//ul[@class='multiselect-container dropdown-menu show']")));
				WebElement element1 = getAndWaitForElement(driver.findElement(By.xpath("//ul[@data-json_key='candidate."+filterVal+"']//ul[@class='multiselect-container dropdown-menu show']")));

				if (element1 != null) {
					totalValues = element1.findElements(By.tagName("li"));
					element.click();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalValues.size();
	}

	public List<String> getAvailableLanguageForProductUsingWebElement(WebElement elementName, boolean screenshotFlag) {
		List<String> listOfAvailableLanguages = new ArrayList<>();
		WebElement languageDropDownElement;
		try {
			getAndWaitForElement(elementName);
			languageDropDownElement = getAndWaitForElement(elementName);
			if (languageDropDownElement != null) {
				listOfAvailableLanguages = getListOfValuesFromListOfWebElements(new Select(languageDropDownElement).getOptions()
						, screenshotFlag);
			} else {
//				UtilClass.addFailTestSetup("Language dropdown is not available", screenshotFlag);
			}
		} catch (Exception ex) {
			objCapture.captureScreen(driver, "getAvailableLanguageForProduct", screenshotFlag, BaseTest.failFolder.toString());
			BaseTest.log.error("Error obtained while getting available language for product " + ex.getLocalizedMessage());
			BaseTest.commonErrorList.add("Error obtained while getting available language for product " + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		return listOfAvailableLanguages;
	}

	//This method Click on element using normal click then JS in catch block
	public void clickByJS_WebElement(WebElement element){
			waitForElementToBeClickable(element);
//			scrollDownToElement(element);
			try {
				waitAndClick(element);
				wait_Until_Requests_to_Complete();
				waitUntillAllLoaderInvisible();
			}
			catch (Exception e){
				try{
					clickByjs(element);
					wait_Until_Requests_to_Complete();
					waitUntillAllLoaderInvisible();
				}
				catch (Exception e1){
					objCapture.captureScreen(driver, "getAvailableLanguageForProduct", true, BaseTest.failFolder.toString());
					BaseTest.log.error("Error obtained while clicking on element" + e1.getLocalizedMessage());
					BaseTest.commonErrorList.add("Error obtained while clicking on element" + e1.getLocalizedMessage());
					e1.printStackTrace();
				}
			}
	}

	public void waitForElement(int Time)
	{
		try {
			wait_Until_Requests_to_Complete();
			Thread.sleep(Time);
		}
		catch (Exception e)
		{
			wait_Until_Requests_to_Complete();
			e.printStackTrace();
		}
	}

	public void clickByJS_WebElement_With_ScrollOn_Element(WebElement element){
		waitForElementToBeClickable(element);
			scrollDownToElement(element);
		try {
			waitAndClick(element);
			wait_Until_Requests_to_Complete();
			waitUntillAllLoaderInvisible();
		}
		catch (Exception e){
			try{
				clickByjs(element);
				wait_Until_Requests_to_Complete();
				waitUntillAllLoaderInvisible();
			}
			catch (Exception e1){
				objCapture.captureScreen(driver, "getAvailableLanguageForProduct", true, BaseTest.failFolder.toString());
				BaseTest.log.error("Error obtained while clicking on element" + e1.getLocalizedMessage());
				BaseTest.commonErrorList.add("Error obtained while clicking on element" + e1.getLocalizedMessage());
				e1.printStackTrace();
			}
		}
	}

	public int moveAcrossPageUsingKeyboard(WebElement element,String interaction,Boolean screenShotFlag) {
		int count=0;
		try {

			while(!getactiveElement().equals(element)) {
				if(count>30) {
					break;
				}
				numberOfKeyBoardInteraction(1,interaction);
				count++;
			}
			BaseTest.log.info("The total number of times "+interaction+" is pressed is "+count,screenShotFlag,true);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			objCapture.captureScreen(driver, "naviagatingviaKeyboard", true, BaseTest.failFolder.toString());
			BaseTest.log.error("Error occurred while navigating via keyboard " + ex.getLocalizedMessage(), true);
			BaseTest.commonErrorList.add("Error occurred while navigating via keyboard ");
		}
		return count;
	}
}