package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
//import com.ceb.shl.test.DataAccessLayer.SQLServerConnection;
//import com.ceb.shl.test.DataModel.AssessmentContentDetails;
//import com.ceb.shl.test.DataModel.PageElements;
//import com.ceb.shl.test.DataModel.PageUrls;
//import com.ceb.shl.test.DataModel.TestMethodDetails;
//import com.ceb.shl.test.shlonline.pageModel.AdminReviewProjectDetailPage;
//import com.ceb.shl.test.util.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import test.lieteners.TestReporterListener;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;


public class BaseTest {

//	public HashMap<String, TestMethodDetails> testMethods = null;
	protected List<PageElements> pageWebElements = null;
//	protected static List<PageUrls> pageUrlsList = null;
	public static HashMap<String, WebElement> testMap = null;
	private String className = null;
	public static WebDriver driver;
	public static String product;
	public static File folder = null;
	public static File failFolder = null;
	public String targetLocationPath = null;
	public String targetLocationPathfailure = null;
//	private SpecializedScreenRecorder screenRecorder;
	public String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	public UUID uuid = UUID.randomUUID();
	public CaptureScreenShot captureScreenShot = new CaptureScreenShot();
	public static int cookiebotDialogCount = 0;
	public static String templatePath = null;
	public static String workingDirectory = null;
	public static String exportDataResultPathForReviewProject = null;
	public static String reportPath = null;
	public static String screenshotsPath = null;
	public static String logFilePath = null;
	public static String temproryPath = null;
	public static String qaDatabaseURL = null;
	public static String qaUserName = null;
	public static String qaPassword = null;
	public static String qaAutomationDatabaseName = null;
	public static String linuxServerName = null;
	public static String linuxUserDataBase = null;
	public static String linuxUserName = null;
	public static String linuxUserPassword = null;
	public static String qa2DBServerName = null;
	public static String qa2UserDataBase = null;
	public static String qa2DBUserName = null;
	public static String qa2DBUserPassword = null;
	public static String qa1DBServerName = null;
	public static String qa1UserDataBase = null;
	public static String qa1DBUserName = null;
	public static String qa1DBUserPassword = null;
	public static String qa3DBServerName = null;
	public static String qa3UserDataBase = null;
	public static String qa3DBUserName = null;
	public static String qa3DBUserPassword = null;
	public static String mumbaiDevDBServerName = null;
	public static String environmentPath = null;
	public static String emailIDs = null;
	public static String environment = null;
	public static String emailHost = null;
	public static String port = null;
	public static String userName = null;
	public static String password = null;
	public static String socketFactory = null;
	public static Boolean runBatch;
	public static Boolean isLocal;
	public static BaseLogger log;
	public static Boolean screenShotFlag;
	public static String integrationUserDataBase = null;
	public static String integrationServerName = null;
	public static String integrationUserName = null;
	public static String integrationUserPassword = null;
	public static String integrationUserDataBase1 = null;
	public static String integrationServerName1 = null;
	public static String integrationUserName1 = null;
	public static String integrationUserPassword1 = null;
	public static String integrationAutomationUserDataBase = null;
	public static String integrationAutomationServerName = null;
	public static String integrationAutomationUserName = null;
	public static String integrationAutomationUserPassword = null;
	public static String bulkImportFileLocation = null;
	public static String requestResponsePath = "RequestResponseFiles";
	public static String dllFilePath = null;
	public static String ThetaCalculationLOFT = null;
	public static String OPQ32rPrimaryScoresCalculator = null;
	public static String ThetaCalculationCAT = null;
	public static String PercentageCalculator = null;
	public static String CustomerServiceRoleSiftOut = null;
	public static String LearningPotential = null;
	public static String verify2CATScoring = null;
	public static String Verify2Mech_Penalty = null;
	public static String Verify2Deductive_Penalty = null;
	public static String Verify2Inductive_Penalty = null;
	public static String Verify2Numerical_Penalty = null;
	public static String Verify2Verbal_Penalty = null;
	public static String downloadPath = null;
	public static Map<String, String> defectMap;
	public static Map<String, String> testCaseId;
	public static String productName = null;
	public static Boolean isProd = false;
	public static Boolean isDevice = false;
	public static Boolean cdn = false;
	public static Boolean isbranding = true;
	public static Boolean isMessageCheck = false;
	public static String company_id = null;
	public static String integrationUserDataBase2 = null;
	public static String integrationServerName2 = null;
	public static String integrationUserName2 = null;
	public static String integrationUserPassword2 = null;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static File extentReportPath = null;
	public static int testMethodCount = 0;
	public static String extentReportName = null;
	public static String dev1DBServerName = null;
	public static String dev1UserDataBase = null;
	public static String dev1DBUserName = null;
	public static String dev1DBUserPassword = null;
//	public static List<AssessmentContentDetails> assessmentDetails = null;
	public static String gplusNumericalTheta = null;
	public static String gplusInductiveTheta = null;
	public static String gplusDeductiveTheta = null;
	public static String gplusOverallTheta = null;
	public static String GPlusAdjustedTheta = null;
	public static String GPlus_Penalty = null;
	public static String GPlus_Percentile = null;
	public static String ProofReading = null;
	public static String AptaThetaCalculator = null;
	public static String AptaThetaCalculatorJFA = null;
	public static String PFEntryLevelSalesSiftOut = null;
	public static String PFEntryLevelHotelFrontDeskSiftOut = null;
	public static String PFEntryLevelCashierSiftOut = null;
	public static String PFEntryLevelTechnicalSupport = null;
	public static String ProfessionalPotentialv4 = null;
	public static String Thoroughness = null;
	public static String ManagementJudgment = null;
	public static String ToleranceofSalesPressure = null;
	public static String ManagementPotential = null;
	public static String SafetyOrientation =null;
	public static String Achievement = null;
	public static String WillingnessToLearn = null;
	public static String Responsibility = null;
	public static String SalesFocus = null;
	public static String CustomerFocus = null; 
	public static String DummySolution3 = null;
	public static String FollowingInstruction = null;
	public static String FollowingInstructions_Penalty = null;
	public static String RemoteWorkQ = null;
	public static String ReadingComprehension = null;
	public static String ReadingComprehension_Penalty = null;
	public static String SpatialAbility = null;
	public static String SpatialAbility_Penalty = null;
	public static String DynamicWinfo = null;
	public static String DynamicWinfo_Penalty = null;
	public static String TechnicalChecking = null;
	public static String GAS = null;
	public static String GAS_Penalty = null;
	public static String Mocog_Theta = null;
	public static String Mocog_Deductive_Penalty = null;
	public static String Mocog_Number_Calc_Penalty = null;
	public static String Mocog_NumericalCalc_Theta = null;
	public static String Mocog_InteractiveGplus = null;
	public static String PjmScoring=null;
	public static String _envURL = null;
	public static Boolean isCleanUp = null;
	private static String browserName= null;
	public static String tcDateFormat = "dd-MM-yyyy";
	public static String euStageDBServerName = null;
	public static String euStageUserDataBase = null;
	public static String euStageDBUserName = null;
	public static String euStageDBUserPassword = null;
	public static String amStaginServerName = null;
	public static String amStaginDBUserName = null;
	public static String amStaginDBPassWord = null;
	public static String testMethodName = null;
	
	// Declaring a common error list
	public static ArrayList<String> commonErrorList = new ArrayList<String>();
	// Declaring a hashmap for content_packages with test session id
	public static HashMap<String, String> testSessionSet = new HashMap<String, String>();
	public static HashMap<String, String> getLocatorStatus = new HashMap<>();
	public static ArrayList<String> pendingTestCases = new ArrayList<String>();

	public BaseTest(String childClass) {
		this.className = childClass;
	}


    /**
	 * Method to set following parameters for Test methods. Cases where
	 * screenshot is not to be taken are taken into consideration by picking
	 * these values globally and locally to one test method when defined.
	 * 
	 * @param product
	 * @param browser
	 * @param screenShotFlag
	 */
	@Parameters({ "product", "browser", "screenShotFlag", "EnvUrlCO" })
	@BeforeMethod(groups = { "sanity", "functional", "regression" })
	public void beforeMethod(String product, String browser, Boolean screenShotFlag, String envURL, Method method) {
		try {
			commonErrorList.clear(); // clearing commonErrorList
			BaseTest.product = product;
			BaseTest.screenShotFlag = screenShotFlag;
			this.isProduction(envURL);
			beforeTestMethodSetup(product);
			beforeTestSetup();
			browserName=browser;
			initializeDriver(browser);
//			GetTestName getMethodName = new GetTestName();
			String methodName =" getTestMethodName";
			System.out.println(methodName);
			test = extent.createTest(methodName);
			test.log(Status.INFO, methodName + " test started.");
		} catch (Exception e) {
			e.printStackTrace();
			BaseTest.log.error(e.getMessage());
			BaseTest.log.error(e.getLocalizedMessage());
			commonErrorList.add("Error obtained in before method: "+e.getLocalizedMessage());
		}
	}

	public void isProduction(String envURL) {
		if (envURL.contains("https://talentcentral-uat.eu.shl.domains")
				|| envURL.contains("talentcentral.us.shl.com")
				|| envURL.contains("talentcentral.eu.shl.com")
				|| envURL.contains("talentcentral.au.shl.com")
				|| envURL.contains("talentcentral.cn.shl.com")
				|| envURL.contains("talentcentral.us2.shl.com")
				|| envURL.contains("integration-talentcentral.us.shl.com")
				|| envURL.contains("talentcentral.us.shl.domains")
				|| envURL.contains("talentcentral.cn.shl.domains")
				|| envURL.contains("talentcentral-1a.au.shl.com")
				|| envURL.contains("talentcentral-1a.cn.shl.com")
				|| envURL.contains("talentcentral-1a.us.shl.com")
				|| envURL.contains("talentcentral-1a.us.shl.com")
				|| envURL.contains("talentcentral.sa.shl.domains")
				|| envURL.contains("talentcentral.sa.shl.com")
				|| envURL.contains("myamcat.com/assessments")
				|| envURL.contains("employer.aspiringminds.com"))
			isProd = true;
		else
			isProd = false;
	}
	
	/*
	 * Fetch current execution env
	 * */
	public String getEnvironment(String envURL) {
		String env = ""; 
		if (envURL.contains("https://talentcentral-uat.eu.shl.domains")){
			env = "uat";
		}else if(envURL.contains("talentcentral.eu.shl.domains") || envURL.contains(("https://internal-amcat.aspiringminds.com"))) {
			env =  "eu_stage";
		}else if(envURL.contains("talentcentral-2a.eu.shl.zone")) {
			env = "qa2";
		}
		else if(envURL.contains("talentcentral-2b.eu.shl.zone")) {
			env = "qa1";
		}
		else if(envURL.contains("talentcentral-3b.eu.shl.zone")) {
			env = "qa3";
		}
		return env;
	}

	/*
	 * Fetch Env Urls
	 * */
	public static String getEnvironmentUrl(String envName) {
		String env = ""; 
		if (envName.contains("uat")){
			env = "https://talentcentral-uat.eu.shl.domains/admin";
			isProd=true;
			
		}else if(envName.equalsIgnoreCase("eustage")) {
			env =  "https://talentcentral.eu.shl.domains/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("qa2")) {
			env =  "https://talentcentral-2a.eu.shl.zone/admin";
		}
		else if(envName.equalsIgnoreCase("qa1")) {
			env =  "https://talentcentral-2b.eu.shl.zone/admin";
		}
		else if(envName.equalsIgnoreCase("qa3")) {
			env =  "https://talentcentral-3b.eu.shl.zone/admin";
		}

		else if (envName.equalsIgnoreCase("AU")){
			env ="https://talentcentral.au.shl.com/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("US")) {
			env =  "https://talentcentral.us.shl.com/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("USAzure")) {
			env =  "https://talentcentral.us2.shl.com/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("EU")) {
			env =  "https://talentcentral.eu.shl.com/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("CN")) {
			env =  "https://talentcentral.cn.shl.com/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("Saudi")) {
			env =  "https://talentcentral.sa.shl.domains/admin";
			isProd=true;
		}
		else if(envName.equalsIgnoreCase("SA")) {
			env =  "https://talentcentral.sa.shl.com/admin";
			isProd=true;
		}
		isProd=true;
		return env;
	}
	@BeforeClass(groups = { "sanity", "functional", "regression" })
	@Parameters({ "product" })
	public void beforeClass(String product) {
		beforeTestClassSetup(product);
	}

	@Parameters({ "browser" })
	@BeforeTest(groups = { "sanity", "functional", "regression" })
	public void beforeTest(String browser) {
		beforeTestSetup();
//		pageUrlsList = DatabaseConnection.getPageUrls();
	}

	@Parameters({"product", "EnvUrlCO", "browser", "emailID"})
	@BeforeSuite(groups = { "sanity", "functional", "regression" })
	public void beforeSuite(String product, String envURL, String browserParam, 
			String emailID) throws UnknownHostException {
		try {
			_envURL = envURL; 
			loadAutomationProperties();
			UUID idOne = UUID.randomUUID();
			extentReportPath = createExtentReport(product);
			extentReportName = "Extent_" + idOne +".html";
			htmlReporter = new ExtentHtmlReporter(extentReportPath + "\\" + extentReportName);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	        htmlReporter.config().setChartVisibilityOnOpen(true);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle("TC Automation");
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName("testing");
	        
	        extent.setSystemInfo("OS", "Win 8.1");
	        extent.setSystemInfo("Env", "QA");
	        extent.setSystemInfo("Host", InetAddress.getLocalHost().getHostName().toString());
			testMethodCount = 1;
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in beforeSuite method: " + e.getLocalizedMessage());
		}
	}
	
	public List<String> getSendersEmail(String emailIds){
		List<String> getEmailID = new ArrayList<>(); 
		try
        {
            if (environment.contains("UI"))
            {
                if (emailIds != null && !emailIds.isEmpty())
                {
                    getEmailID = Arrays.asList(emailIds.split(","));
                    System.out.println("Value of email ID is : " + getEmailID);
                }
                else
                {
                    System.out.println("No Email found.");
                }
            }
            else if (environment.contains("CI"))
            {
                if (emailIDs != null && !emailIDs.isEmpty())
                {
                    String emailIDs = BaseTest.emailIDs;
                    getEmailID = Arrays.asList(emailIDs.split(","));
                }
                else
                {
                    getEmailID = null;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            commonErrorList.add("Error obtained while fetching emailId for "
            		+ "acknowledge mail: "+e.getLocalizedMessage());
        }
		return getEmailID;
	}
	
	
	public File createExtentReport(String product) {
		File myDir = null;
		try {
			String path = reportPath;
			String dateFolder = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
	        path = path + "\\" + dateFolder;
	        myDir = new File(path);
	        if(!myDir.exists())
	        {
	            myDir.mkdir();
	        }

	        path = path + "\\" + product;
	        myDir = new File(path);
	        if(!myDir.exists())
	        {
	            myDir.mkdir();
	        }
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in createExtentReport method: " + e.getLocalizedMessage());
		}
        return myDir;
	}
	
	@Parameters({"product", "EnvUrlCO", "browser", "emailID"})
	@AfterMethod(groups = { "sanity", "functional", "regression" })
	public void afterMethod(String product, String envURL, String browserParam, String emailID) {
		try {
			afterTestMethodSetup();
			driver.quit();
			driver = null;
			extent.flush();
			if(testMethodCount == 1){
				List<String> getEmailID = getSendersEmail(emailID);
				if(getEmailID != null && getEmailID.size() != 0){
					MailFinalReportFile objMail  = new MailFinalReportFile();
					objMail.sendTestAcknowledgeMail(extentReportPath, extentReportName, 
							getEmailID, envURL, browserParam);
				}}testMethodCount++;
				TestReporterListener objListner = new TestReporterListener();
				objListner.uploadFoldersOnS3(null, "Screenshots\\"+BaseLogger.targetLocationPathForS3, null);
				BaseLogger.targetLocationPathForS3 = null;
				String extentPath = extentReportPath.toString().replace(BaseTest.workingDirectory, "");
				extentPath = extentPath + "\\"+ extentReportName;
				objListner.uploadFoldersOnS3(null, null, extentPath);
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in afterMethod method: " + e.getLocalizedMessage());
		}
	}

	@AfterClass(groups = { "sanity", "functional", "regression" })
	public void afterClass() {
		afterTestClassSetup();
	}

	@AfterTest(groups = { "sanity", "functional", "regression" })
	public void afterTest() {
		afterTestSetup();
	}

	protected void closeBrowser() {
		driver.quit();
	}

	@AfterSuite(groups = { "sanity", "functional", "regression" })
	public void afterSuite() {
		// afterTestSuiteSetup();
		//extent.flush();
		//extent.close();
	}

	@Parameters({ "product" })
	protected void beforeTestMethodSetup(String product) {
		productName = product;
		if (product.equals("Integration") || product.equals("Precise-Fit(Int)")) {
//			testMethods = DatabaseConnection.getIntegrationClassMethods(className, product);
//			defectMap = DatabaseConnection.getDefectIds();
//			testCaseId = DatabaseConnection.getTestCaseId();
		} else {
//			testMethods = DatabaseConnection.getClassMethods(className, product);
//			defectMap = DatabaseConnection.getDefectIds();
//			testCaseId = DatabaseConnection.getTestCaseId();
		}
	}

	@Parameters({ "product" })
	protected void beforeTestMethodSetupForIntegration(String product) {
		productName = product;
//		testMethods = DatabaseConnection.getIntegrationClassMethods(className, product);
//		defectMap = DatabaseConnection.getDefectIds();
	}

	protected void afterTestMethodSetup() {
//		ResponseDBConnection dbCon = new ResponseDBConnection();
//		dbCon.updateMethodNameAsPerStatus();
		if(getLocatorStatus != null)
			getLocatorStatus.clear();
	}

	protected void beforeTestClassSetup(String product) {
		try {
//			pageWebElements = DatabaseConnection.getWeElementDetails(className, product);
		} catch (Exception ex) {
			commonErrorList.add("Error obtained in beforeTestClassSetup method." + ex.getMessage());
		}
	}

	protected void beforeIntegrationTestClassSetup() {
		try {
//			pageWebElements = DatabaseConnection.getIntegrationWeElementDetails(className);
		} catch (Exception ex) {
			commonErrorList.add("Error obtained in beforeIntegrationTestClassSetup method."
			+ ex.getMessage());
		}
	}

	protected void afterTestClassSetup() {
		
	}

	protected void beforeTestSetup() {
		try {
//			SQLServerConnection.getQAConnection();
		} catch (Exception ex) {
			commonErrorList.add("Error obtained in beforeTestSetup  method." + ex.getMessage());
		}
	}

	protected void afterTestSetup() {
		
	}

	public WebDriver initializeDriver(String browser) {
		Map<String, String> mobileEmulation = new HashMap<>();
		try {
			if (driver == null) {
				switch (browser) {
				case "Firefox":
					if(environment.equalsIgnoreCase("UI")) {
						WebDriverManager.firefoxdriver().setup();
						driver = new FirefoxDriver();
					} else {
						String pathFirefox = Paths.get("browserdriver/geckodriver.exe").toAbsolutePath().toString();
						System.setProperty("webdriver.gecko.driver", pathFirefox);
//						System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
//						System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
						FirefoxOptions firefoxOptions = new FirefoxOptions();
						FirefoxProfile profile = new FirefoxProfile();
						profile.setPreference("browser.download.folderList", 2);
						profile.setPreference("browser.download.manager.showWhenStarting", false);
						profile.setPreference("browser.download.dir", downloadPath);
						profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
								"application/PDF , application/csv,application/ms-excel, text/csv, "
								+ "text/plain,application/octet-stream doc xls pdf txt,application/zip,"
								+ " application/vnd.ms-excel");
						profile.setPreference("pdfjs.disabled", true);
						firefoxOptions.setProfile(profile);
						firefoxOptions.setCapability("marionette", true);
						driver = new FirefoxDriver(firefoxOptions);
					}
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					break;
				case "Internet Explorer":
//					WebDriverManager.iedriver().setup();
//					DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//					ieCapabilities.setCapability(
//							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//					ieCapabilities.setCapability("ignoreZoomSetting", true);
//					ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
//					driver = new InternetExplorerDriver(ieCapabilities);
//					driver.manage().window().maximize();
//					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
					break;
				case "Chrome":
					DesiredCapabilities capabilities = new DesiredCapabilities();
//					capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
					capabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
					capabilities.setCapability("applicationCacheEnabled", false);
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);
					chromePrefs.put("download.default_directory", downloadPath);
					chromePrefs.put("download.prompt_for_download", false);
					chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
					chromePrefs.put("profile.default_content_setting_values.media_stream_mic", 1);
					chromePrefs.put("profile.default_content_setting_values.media_stream_camera", 1);
					chromePrefs.put("profile.default_content_setting_values.notifications", 1);
					chromePrefs.put("profile.managed_default_content_settings.popups", 1);
					chromePrefs.put("profile.default_content_setting_values.metro_switch_to_desktop", 1);
					chromePrefs.put("profile.default_content_setting_values.durable_storage", 1);
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-backgrounding-occluded-windows");
					options.addArguments("--disable-popup-blocking");
					options.setExperimentalOption("useAutomationExtension", false);
					options.addArguments("--disable-notifications");
					options.addArguments("--use-fake-device-for-media-stream");
					options.addArguments("--use-fake-ui-for-media-stream");
					options.addArguments("--disable-infobars");
					options.addArguments("--remote-allow-origins=*");
					options.addArguments("disable-infobars");
					options.merge(capabilities);
					options.setExperimentalOption("prefs", chromePrefs);

//					List <Object> versions = WebDriverManager.chromedriver().getDriverVersions().stream().
//							filter(version -> version.contains("116")).collect(Collectors.toList());
//					if (versions.size()>0)
//					WebDriverManager.chromedriver().setup();
//					else
//					System.setProperty("webdriver.chrome.driver", "drivers\\116.0.5845.97\\chromedriver.exe");
					try{
					driver = new ChromeDriver(options);}
					catch (Exception e){
						System.setProperty("webdriver.chrome.driver", "drivers\\chrome118\\chromedriver.exe");
						driver = new ChromeDriver(options);
					}
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					break;
				case "BrowserStack-Android":

					String url = "http://hub.browserstack.com/wd/hub/";
//					String userName = "nishithjoshi1";
//					String	accessKey = "ze3zA9xo61pyiJz2zjzY";
					String userName = "chaitanyasilawat_bvqL8C";
					String	accessKey = "dQ3VcZCUbEasB3jf1D3q";
					DesiredCapabilities caps1 = new DesiredCapabilities();
					caps1.setCapability("browserstack.user", userName);
					caps1.setCapability("browserstack.key", accessKey);
					caps1.setCapability("browserName", "android");
					caps1.setCapability("device", "Samsung Galaxy S9 Plus");
					caps1.setCapability("browserstack.idleTimeout", "700");
					caps1.setCapability("realMobile", "true");
					caps1.setCapability("os_version", "9.0");
					caps1.setCapability("browserstack.local", "false");
					//caps1.setCapability("browserstack.debug", "true");
					caps1.setCapability("browserstack.appium_version", "1.16.0");
					caps1.setCapability("browserstack.networkProfile", "4g-lte-high-latency");
					caps1.setCapability("browserstack.idleTimeout", "300");
//					driver = new RemoteWebDriver(new URL(url), caps1);
//              Newly added previous was above
					DesiredCapabilities caps11 = new DesiredCapabilities();
					caps11.setCapability("browserName", "ANDROID");
					caps11.setCapability("platformName", "ANDROID");
					caps11.setPlatform(Platform.ANDROID);
					caps11.setBrowserName("ANDROID");
					caps11.setAcceptInsecureCerts(true);
					caps11.setBrowserName("ANDROID");
					driver = new RemoteWebDriver(new URL("https://chaitanyasilawat_bvqL8C:dQ3VcZCUbEasB3jf1D3q@hub.browserstack.com/wd/hub"), caps11);
					isDevice = true;
					break;
				case "iOS_Device":
					mobileEmulation = new HashMap<>();
					mobileEmulation.put("deviceName", "iPhone 8");
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeOptions);
					isDevice = true;
					break;
				case "Edge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					driver.manage().window().maximize();
					break;
				case "BrowserStack-iOS":
					url = "http://hub.browserstack.com/wd/hub/";

					userName = "pankajkapruwan1";
					accessKey = "ttT7upZdFS6Tspuxjq7s";
					caps1 = new DesiredCapabilities();
					caps1 = new DesiredCapabilities();
					caps1.setCapability("browserstack.user", userName);
					caps1.setCapability("browserstack.key", accessKey);
					caps1.setCapability("os_version", "13");
					caps1.setCapability("device", "iPhone 11");
					caps1.setCapability("real_mobile", "true");
					caps1.setCapability("browserstack.appium_version", "1.17.0");
					caps1.setCapability("browserstack.local", "false");
					caps1.setCapability("browserstack.networkLogs", "true");
					driver = new RemoteWebDriver(new URL(url), caps1);
					isDevice = true;
					break;
				case "ChromeHeadless":
					ChromeOptions optionsHead = new ChromeOptions();  
					optionsHead.addArguments("window-size=1366,768");
					optionsHead.addArguments("--headless");  
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(optionsHead);
					break;
				case "Opera":
//					String pathOpera = Paths.get("browserdriver/operadriver.exe")
//					.toAbsolutePath().toString();
//					System.out.println("value of browserpath is:: " + pathOpera);
//					System.setProperty("webdriver.chrome.driver", pathOpera);
//					DesiredCapabilities operaCapabilities = DesiredCapabilities.chrome();
//					operaCapabilities.setCapability(ChromeDriverService.createDefaultService().toString(), true);
//					ChromeOptions operaOptions = new ChromeOptions();
//					operaOptions.addArguments("test-type");
//					operaCapabilities.setCapability("opera.binary", pathOpera);
//					operaCapabilities.setCapability(ChromeOptions.CAPABILITY, operaOptions);
					driver.manage().window().maximize();
					break;
				case "SauceLabs":
					String sauceUserName = "automationusershl";
					String sauceAccessKey = "237cf25d-8028-4f67-ae11-7545d2df69c4";

//					capabilities = DesiredCapabilities.android();
//					capabilities.setCapability("ID","LG_Nexus_5X_free");
//					capabilities.setCapability("deviceOrientation", "portrait");
//					capabilities.setCapability("platformVersion","Android 8.1.0");
//					capabilities.setCapability("platformName", "android");
//					capabilities.setCapability("browserName", "Chrome");
//					capabilities.setCapability("username", sauceUserName);
//					capabilities.setCapability("accessKey", sauceAccessKey);

//					driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);
					break;
				case "SauceLabsiPhone":
					sauceUserName = "pankajkapruwan";
					sauceAccessKey = "aacc4ff3-2bcd-4d34-8882-c9652d9edf47";

//					capabilities = DesiredCapabilities.iphone();
//					capabilities.setCapability("ID","iPhone_6_free");
//					capabilities.setCapability("deviceOrientation", "portrait");
//					capabilities.setCapability("platformVersion","iOS 12.1.4");
//					capabilities.setCapability("platformName", "iOS");
//					capabilities.setCapability("browserName", "Safari");
//					capabilities.setCapability("username", sauceUserName);
//					capabilities.setCapability("accessKey", sauceAccessKey);

//					driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);
					break;
				case "BrowserStack-Chrome":
					url = "http://hub.browserstack.com/wd/hub/";

					userName = "kishorenegi1";
					accessKey = "1cQSMJ3XoGzqFwADUCKP";

					caps1 = new DesiredCapabilities();
					caps1.setCapability("browserstack.user", userName);
					caps1.setCapability("browserstack.key", accessKey);
					caps1.setCapability("browser", "Chrome");
					caps1.setCapability("browser_version", "83.0");
					caps1.setCapability("os", "Windows");
					caps1.setCapability("os_version", "10");
					driver = new RemoteWebDriver(new URL(url), caps1);
					break;
				case "htmlUnitDriver":
//					driver = new HtmlUnitDriver(BrowserVersion.CHROME);
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					break;
				case "Chrome_ZAP":
//					Proxy proxy = new Proxy();
//					proxy.setHttpProxy("localhost:5555");
//					proxy.setFtpProxy("localhost:5555");
//					proxy.setSslProxy("localhost:5555");
//					String path = Paths.get("browserdriver/chromedriver.exe")
//							.toAbsolutePath().toString();
//					System.setProperty("webdriver.chrome.driver", path);
//					DesiredCapabilities capabilitiesy = DesiredCapabilities.chrome();
//					ChromeOptions optionsy = new ChromeOptions();
//					optionsy.addArguments("start-maximized");
//					capabilitiesy.setCapability(CapabilityType.PROXY, proxy);
//					driver = new ChromeDriver(capabilitiesy);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					break;
				default:
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					break;

				case "Chrome : Android-Emulator":
					mobileEmulation.put("deviceName", "Nexus 5");
					chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeOptions);
					driver.manage().window().maximize();
					isDevice = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in initializeDriver"
					+ " method." + e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		return driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * getLanguageWithinParanthesis method will get language within the
	 * parenthesis such that it maybe used for drop-downs et al
	 * 
	 * @param languageToConvert
	 * @param log
	 * @return String Author: deep.sehgal@shl.com
	 */

	public String getLanguageWithinParanthesis(String languageToConvert, BaseLogger log) {
		String lang = null;
		try {
			if (languageToConvert != null) {
				Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(languageToConvert);
				while (m.find()) {
					lang = m.group(1);
				}
			}
			return lang;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error: " + e.getLocalizedMessage());
			commonErrorList.add("Error obtained in getLanguageWithinParanthesis"
					+ " method." + e.getMessage());
		}
		return lang;
	}

	public File createScreenshotFolders(String product, String methodName, String language, Boolean videoFlag,
			BaseLogger log) {
		try {
			loadAutomationProperties();
			String screenPath = screenshotsPath;
			folder = new File(screenPath + currentDate + "\\" + product + "\\" + methodName + "\\" + language + "\\"
					+ uuid + "\\AllScreenShots\\");
			// Create current date folder

			if (!folder.exists()) {
				if (folder.mkdirs()) {
				} else {
					System.err.println("Failed to create directory!");
				}
			}
			this.targetLocationPath = folder.toURI().toString();

			if (targetLocationPath.contains("Screenshots")) {
				String[] commandArray = targetLocationPath.split("Screenshots/");
				targetLocationPath = "../" + "Screenshots/" + commandArray[1];
			}
			this.createFailFolder(product, methodName, language, videoFlag, log);

			//Start recording
			this.startRecordingTest(folder, uuid.toString(), videoFlag);

			return folder;
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in createScreenshotFolders"
					+ " method." + e.getMessage());
		}
		return null;
	}

	public void createFailFolder(String product, String methodName, String language, Boolean videoFlag,
			BaseLogger log) {
		try {
			loadAutomationProperties();
			String screenPath = screenshotsPath;
			failFolder = new File(screenPath + currentDate + "\\" + product + "\\" + methodName + "\\" + language + "\\"
					+ uuid + "\\Failed");
			
			// Create current date folder
			if (!failFolder.exists())
				failFolder.mkdirs();

			this.targetLocationPathfailure = failFolder.toURI().toString();

			if (targetLocationPathfailure.contains("Screenshots")) {
				String[] commandArray = targetLocationPathfailure.split("Screenshots/");
				targetLocationPathfailure = "../" + "Screenshots/" + commandArray[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in createFailFolder"
					+ " method." + e.getMessage());
		}
	}

	public void startRecordingTest(File file, String userName, Boolean videoFlag) throws Exception {
		try {
			if (videoFlag == true)
			{
				System.out.println("startRecording");
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int width = screenSize.width;
				int height = screenSize.height;

				Rectangle captureSize = new Rectangle(0, 0, width, height);

				GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
						.getDefaultConfiguration();

//				this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
//						new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
//						new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//								CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
//								Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
//						new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
//						null, file, userName);
//				this.screenRecorder.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in startRecordingTest"
					+ " method." + e.getMessage());
		}
	}

	public void commitReports(BaseLogger log, Boolean screenShotFlag, Boolean videoFlag, Boolean isFail,
			ArrayList<String> consolidatedErrorList) {
		String errorPrint = null;
		try {

			 //Print the list of errors. 
			// check error list has errors or not
			if (consolidatedErrorList != null && consolidatedErrorList.size() > 0) {
				// initialize a 'isFail' variable to set false if
				// 'consolidatedErrorList' has any error
				isFail = true;
				for (int i = 0; i < consolidatedErrorList.size(); i++) {
					int a = i + 1;
					if (errorPrint != null) {
						errorPrint = errorPrint + " Error " + a + " : " + consolidatedErrorList.get(i).toString()
								+ "</br>";
					} else
						errorPrint = " Error " + a + " : " + consolidatedErrorList.get(i).toString() + "</br>";
					test.log(Status.FAIL, consolidatedErrorList.get(i).toString());
				}
				// Remove comma(,) at the end of the last error line
				errorPrint = errorPrint.substring(0, errorPrint.length() - 1);

			} else {
				log.info("Test passed. No errors encountered");
			}
			 //Print the list of errors. 

			this.stopRecording(videoFlag);
			log.info("Test has completed");
			log.disposeLogger();
			System.out.println("first file path is:" + log.getFileName());
			Reporter.setEscapeHtml(true);
			Reporter.log(log.getFileName());

			if (screenShotFlag == true || videoFlag == true) {
				Reporter.setEscapeHtml(true);
				Reporter.log(targetLocationPath);
			}

			if (isFail == false) {
				// FileUtils.deleteDirectory(varTempFolder);//not needed
			} else {
				if (screenShotFlag == true || videoFlag == true) {
					Reporter.setEscapeHtml(true);
					Reporter.log(targetLocationPathfailure);
				}
				Assert.fail(errorPrint);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Obtained in commitReports method:" + e.getLocalizedMessage());
		}
	}

	public void stopRecording(boolean videoFlag) throws Exception {
		try {
			if (videoFlag == true) {
				try
				{
//					this.screenRecorder.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonErrorList.add("Error obtained in stopRecording"
					+ " method." + e.getMessage());
		}
	}

	public static void loadAutomationProperties() {
		Properties prop = new Properties();
		try {
				prop.load(new FileInputStream("Automation.properties"));
				templatePath = workingDirectory + "Templates\\";
				workingDirectory = prop.getProperty("workingDirectory");
				reportPath = workingDirectory + "TestExecutionReports\\";
				screenshotsPath = workingDirectory + "Screenshots\\";
				logFilePath = workingDirectory + "log\\";
				dev1DBServerName = prop.getProperty("dev1DBServerName");
				dev1UserDataBase = prop.getProperty("dev1UserDataBase");
				dev1DBUserName = prop.getProperty("dev1DBUserName");
				dev1DBUserPassword = prop.getProperty("dev1DBUserPassword");
				temproryPath = prop.getProperty("temproryPath");
				qaDatabaseURL = prop.getProperty("qaDatabaseURL");
				qaUserName = prop.getProperty("qaUserName");
				qaPassword = prop.getProperty("qaPassword");
				linuxServerName = prop.getProperty("linuxServerName");
				linuxUserDataBase = prop.getProperty("linuxUserDataBase");
				linuxUserName = prop.getProperty("linuxUserName");
				linuxUserPassword = prop.getProperty("linuxUserPassword");
				qaAutomationDatabaseName = prop.getProperty("qaAutomationDatabaseName");
				environmentPath = prop.getProperty("environmentPath");
				emailIDs = prop.getProperty("emailIDs").trim();
				environment = prop.getProperty("environment");
				emailHost = prop.getProperty("emailHost");
				port = prop.getProperty("port");
				userName = prop.getProperty("userName");
				password = prop.getProperty("password");
				socketFactory = prop.getProperty("socketFactory");
				runBatch = Boolean.parseBoolean(prop.getProperty("runBatch").trim());
				isLocal = Boolean.parseBoolean(prop.getProperty("isLocal").trim());
				bulkImportFileLocation = prop.getProperty("bulkImportFileLocation");
				exportDataResultPathForReviewProject = prop.getProperty("downloadPath");
				dllFilePath = workingDirectory + "Scoring_Macros\\jacob-1.18-x64.dll";
				ThetaCalculationLOFT = workingDirectory + "Scoring_Macros\\Theta_Estimation_Tool_For_Verify_1.xlsm";
				ThetaCalculationCAT = workingDirectory +"Scoring_Macros\\EAP No D Constant - JavaCat1.xlsm";
				PercentageCalculator = workingDirectory + "Scoring_Macros\\Verify2_Percentile.xlsm";
				OPQ32rPrimaryScoresCalculator = prop.getProperty("OPQ32rPrimaryScoresCalculator");
				verify2CATScoring = workingDirectory + "Scoring_Macros\\Verify2_CAT_Scoring_Fixed.xlsm";
				Verify2Mech_Penalty = prop.getProperty("Verify2Mech_Penalty");
				Verify2Deductive_Penalty = prop.getProperty("Verify2Deductive_Penalty");
				Verify2Inductive_Penalty = prop.getProperty("Verify2Inductive_Penalty");
				Verify2Numerical_Penalty = prop.getProperty("Verify2Numerical_Penalty");
				Verify2Verbal_Penalty = prop.getProperty("Verify2Verbal_Penalty");
				CustomerServiceRoleSiftOut = workingDirectory + "Scoring_Macros\\Solution_CustomerServiceRoleSiftOut.xlsm";
				gplusNumericalTheta = workingDirectory + "Scoring_Macros\\GPlus_Numerical_Theta.xlsm";
				gplusInductiveTheta = workingDirectory + "Scoring_Macros\\GPlus_Inductive_Theta.xlsm";
				gplusDeductiveTheta = workingDirectory + "Scoring_Macros\\GPlus_Deductive_Theta.xlsm";
				gplusOverallTheta = workingDirectory + "Scoring_Macros\\GPlus_Overall_Theta.xlsm";
				GPlusAdjustedTheta = workingDirectory + "Scoring_Macros\\GPlus_Adjusted_Theta.xlsm";
				GPlus_Penalty = prop.getProperty("GPlus_Penalty");
				GPlus_Percentile = workingDirectory + "Scoring_Macros\\GPlus_Percentile.xlsm";
				ProofReading = workingDirectory + "Scoring_Macros\\ProofreadingandReadingCompwithJovial.xlsm";
				SalesFocus = prop.getProperty("SalesFocus");
				CustomerFocus = prop.getProperty("CustomerFocus");
				LearningPotential = prop.getProperty("LearningPotential");
				ProfessionalPotentialv4 = workingDirectory + "Scoring_Macros\\Professional_Potential_v4_Jovial.xlsm";
				Thoroughness = workingDirectory + "Scoring_Macros\\Thoroughness_Jovial.xlsm";
				AptaThetaCalculator = workingDirectory + "Scoring_Macros\\AptaThetaCalculator.xlsm";
				AptaThetaCalculatorJFA = workingDirectory + "Scoring_Macros\\AptaThetaCalculatorJFA.xlsm";
				PFEntryLevelSalesSiftOut = workingDirectory + "Scoring_Macros\\Solution_PFEntryLevelSalesSiftOut.xlsm";
				PFEntryLevelHotelFrontDeskSiftOut = prop.getProperty("PFEntryLevelHotelFrontDeskSiftOut");
				PFEntryLevelCashierSiftOut = prop.getProperty("PFEntryLevelCashierSiftOut");
				PFEntryLevelTechnicalSupport = prop.getProperty("PFEntryLevelTechnicalSupport");
				downloadPath = prop.getProperty("downloadPath");
				integrationUserDataBase = prop.getProperty("integrationUserDataBase");
				integrationServerName = prop.getProperty("integrationServerName");
				integrationUserName = prop.getProperty("integrationUserName");
				integrationUserPassword = prop.getProperty("integrationUserPassword");
				integrationUserDataBase1 = prop.getProperty("integrationUserDataBase1");
				integrationServerName1 = prop.getProperty("integrationServerName1");
				integrationUserName1 = prop.getProperty("integrationUserName1");
				integrationUserPassword1 = prop.getProperty("integrationUserPassword1");
				integrationUserDataBase2 = prop.getProperty("integrationUserDataBase2");
				integrationServerName2 = prop.getProperty("integrationServerName2");
				integrationUserName2 = prop.getProperty("integrationUserName2");
				integrationUserPassword2 = prop.getProperty("integrationUserPassword2");
				integrationAutomationUserDataBase = prop.getProperty("integrationAutomationUserDataBase");
				integrationAutomationServerName = prop.getProperty("integrationAutomationServerName");
				integrationAutomationUserName = prop.getProperty("integrationAutomationUserName");
				integrationAutomationUserPassword = prop.getProperty("integrationAutomationUserPassword");
				qa2DBServerName = prop.getProperty("qa2DBServerName");
				qa2UserDataBase = prop.getProperty("qa2UserDataBase");
				qa2DBUserName = prop.getProperty("qa2DBUserName");
				qa2DBUserPassword = prop.getProperty("qa2DBUserPassword");
				qa1DBServerName = prop.getProperty("qa1DBServerName");
				qa1UserDataBase = prop.getProperty("qa1UserDataBase");
				qa1DBUserName = prop.getProperty("qa1DBUserName");
				qa1DBUserPassword = prop.getProperty("qa1DBUserPassword");
				qa3DBServerName = prop.getProperty("qa3DBServerName");
				qa3UserDataBase = prop.getProperty("qa3UserDataBase");
				qa3DBUserName = prop.getProperty("qa3DBUserName");
				qa3DBUserPassword = prop.getProperty("qa3DBUserPassword");
				dev1DBServerName = prop.getProperty("dev1DBServerName");
				dev1UserDataBase = prop.getProperty("dev1UserDataBase");
				dev1DBUserName = prop.getProperty("dev1DBUserName");
				dev1DBUserPassword = prop.getProperty("dev1DBUserPassword");
				euStageDBServerName = prop.getProperty("euStageDBServerName");
				euStageUserDataBase = prop.getProperty("euStageUserDataBase");
				euStageDBUserName = prop.getProperty("euStageDBUserName");
				euStageDBUserPassword = prop.getProperty("euStageDBUserPassword");
				ManagementJudgment = workingDirectory + "Scoring_Macros\\Management_Judgment.xlsm";
				ToleranceofSalesPressure = workingDirectory + "Scoring_Macros\\ToleranceofSalesPressure.xlsm";
				ManagementPotential = workingDirectory + "Scoring_Macros\\Management_Potential.xlsm";
				SafetyOrientation = workingDirectory + "Scoring_Macros\\SafetyOrientation.xlsm";
				Achievement = workingDirectory + "Scoring_Macros\\Achievement.xlsm";
				WillingnessToLearn = prop.getProperty("WillingnessToLearn");
				DummySolution3 = workingDirectory + "Scoring_Macros\\DummySolution3.xlsm";
				FollowingInstruction = workingDirectory + "Scoring_Macros\\FollowingInstruction.xlsm";
				FollowingInstructions_Penalty =  workingDirectory + "Scoring_Macros\\FollowingInstructions_Penalty.xlsm";
				ReadingComprehension = workingDirectory + "Scoring_Macros\\ReadingComprehension.xlsm";
				ReadingComprehension_Penalty = workingDirectory + "Scoring_Macros\\ReadingComprehension_Penalty.xlsm";
				SpatialAbility = workingDirectory + "Scoring_Macros\\SpatialAbility.xlsm";
				SpatialAbility_Penalty = prop.getProperty("SpatialAbility_Penalty");
				DynamicWinfo_Penalty = workingDirectory + "Scoring_Macros\\DynamicWinfo_Penalty.xlsm";
				DynamicWinfo = workingDirectory + "Scoring_Macros\\DynamicWinfo.xlsm";
				GAS = workingDirectory + "Scoring_Macros\\GAS";
				GAS_Penalty = workingDirectory + "Scoring_Macros\\GAS_Penalty";
				Responsibility =  prop.getProperty("Responsibility");
				Mocog_Theta = workingDirectory + "Scoring_Macros\\Mocog_Theta.xlsm";
				Mocog_Deductive_Penalty = workingDirectory + "Scoring_Macros\\Mocog_Deductive_Penalty.xlsm";
				Mocog_Number_Calc_Penalty = workingDirectory + "Scoring_Macros\\Mocog_Number_Calc_Penalty.xlsm";
				Mocog_NumericalCalc_Theta = workingDirectory + "Scoring_Macros\\Mocog_NumericalCalc_Theta.xlsm";
				Mocog_InteractiveGplus = workingDirectory + "Scoring_Macros\\GPlus_Verify_Interactive.xlsm";
                PjmScoring= workingDirectory + "Scoring_Macros\\PJM_Calculator_01.xlsx";
                RemoteWorkQ =  workingDirectory + "Scoring_Macros\\\\RWQ.xlsm";
                isCleanUp = Boolean.parseBoolean(prop.getProperty("isCleanUp"));
                mumbaiDevDBServerName = prop.getProperty("mumbaiDevDBServerName");
                amStaginServerName = prop.getProperty("amStaginServerName");
                amStaginDBUserName = prop.getProperty("amStaginDBUserName");
                amStaginDBPassWord = prop.getProperty("amStaginDBPassWord");
		} catch (Exception ex) {
			ex.printStackTrace();
			commonErrorList.add("Error obtained in loadAutomationProperties method. " + ex.getMessage());
		}
	}

	/**
	 * To get the url of the page.
	 * 
	 * @param envUrl
	 *            - url of the environment.
	 * @param pageName
	 *            - name of the page
	 * @return - page url
	 * @throws Exception
	 */
//	public static String getPageUrl(String envUrl, String pageName) throws Exception {
//		String pageUrl = null;
//		try {
//			for (PageUrls url : pageUrlsList) {
//				if (url.getPageName().equals(pageName)) {
//					if (envUrl.contains("talentcentral-1a.in.shl.zone")) {
//						pageUrl = url.getDevMumbaiUrl();
//						break;
//					} else if (envUrl.contains("https://talentcentral-2a.eu.shl.zone")
//							|| envUrl.contains("talentcentral-qa2.eu.shl.com")
//							|| envUrl.contains("https://integration-talentcentral-2a.eu.shl.zone/Integration")
//							|| envUrl.contains("https://vivid-vadevqa2.eu.shl.zone")) {
//						pageUrl = url.getQA2Url();
//						break;
//					} else if (envUrl.contains("talentcentral.us.shl.com")) {
//						pageUrl = url.getUSProdUrl();
//						break;
//					} else if (envUrl.contains("talentcentral.us2.shl.com")) {
//						pageUrl = url.getUSAzureProdUrl();
//						break;
//					} else if (envUrl.contains("talentcentral.eu.shl.com")) {
//						pageUrl = url.getEUProdUrl();
//						break;
//					} else if (envUrl.contains("talentcentral.au.shl.com")) {
//						pageUrl = url.getAUProdUrl();
//						break;
//					} else if (envUrl.contains("talentcentral.cn.shl.com")) {
//						pageUrl = url.getCNProdUrl();
//						break;
//					} else if (envUrl.contains("https://talentcentral-2b.eu.shl.zone")
//							|| envUrl.contains("https://integration-talentcentral-2b.eu.shl.zone/Integration")
//							|| envUrl.contains("https://vivid-vadevqa3.eu.shl.zone")) {
//						pageUrl = url.getQA1Url();
//						break;
//					}
//						else if (envUrl.contains("https://talentcentral-3b.eu.shl.zone")
//								|| envUrl.contains("https://integration-talentcentral-3b.eu.shl.zone/Integration")) {
//							pageUrl = url.getQA3Url();
//							break;
//					} else if (envUrl.contains("talentcentral-uat.eu.shl.domains/")
//							|| envUrl.contains("https://integration-talentcentral-uat.eu.shl.domains/Integration/")) {
//						pageUrl = url.getIntegrationURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral-1a.sasadaseu.shl.zone/")
//							|| envUrl.contains("https://integration-talentcentral-1a.eusad.shl.zone/Integration/")) {
//						pageUrl = url.getDevAWSURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral.eu.shl.domains/")
//							||(envUrl.contains("https://internal-amcat.aspiringminds.com")
//							|| envUrl.contains("https://integration-talentcentral.eu.shl.domains/Integration/"))) {
//						pageUrl = url.getAWSEUStageURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral-1a.eu.shl.zone/")
//							|| envUrl.contains("https://integration-talentcentral-1a.eu.shl.zone/Integration/")) {
//						pageUrl = url.getAWSEUDevURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral.us.shl.domains/")
//							|| envUrl.contains("https://integration-talentcentral.us.shl.domains/Integration/")) {
//						pageUrl = url.getAWSUSStageURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral.sa.shl.domains/")
//							|| envUrl.contains("https://integration-talentcentral.sa.shl.domains/Integration/")) {
//						pageUrl = url.getSaudiStageURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral.sa.shl.com/")
//							|| envUrl.contains("https://integration-talentcentral.sa.shl.com/Integration/")) {
//						pageUrl = url.getSaudiURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral-1a.us.shl.zone/")
//							|| envUrl.contains("https://integration-talentcentral-1a.us.shl.zone/Integration/")) {
//						pageUrl = url.getAWSUSDevURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral.cn.shl.domains/")
//							|| envUrl.contains("https://integration-talentcentral.cn.shl.domains/Integration/")) {
//						pageUrl = url.getAWSCNStageURL();
//						break;
//					} else if (envUrl.contains("https://talentcentral-1a.cn.shl.zone/")
//							|| envUrl.contains("https://integration-talentcentral-1a.cn.shl.zone/Integration/")) {
//						pageUrl = url.getAWSCNDevURL();
//						break;
//					}else if (envUrl.contains("https://talentcentral-1a.in.shl.zone")){
//						pageUrl = url.getAWSUSDevURL();
//						break;
//
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error("Error Obtained: " + e.getLocalizedMessage());
//			commonErrorList.add("Error obtained in getPageUrl method." + e.getMessage());
//			e.printStackTrace();
//		}
//		return pageUrl;
//	}

	/**
	 * This method launches the URL and handles any malformations in the URL.
	 * 
	 * 
	 * @param driver
	 * @param URL
	 * @return driver
	 * @author Deep.Sehgal
	 */
	public WebDriver launchURL(WebDriver driver, String URL, String projectId) {

		try {
			if (URL != null) {
				driver.get(URL);
			} else {
				log.error("URL has problems please check, ProjectId is:" + projectId);
				commonErrorList.add("URL has problems please check, ProjectId is:" + projectId);
				Assert.fail("URL has problems please check, ProjectId is:" + projectId);
			}
		} catch (Exception e) {
			log.error("Error Obtained while fetching link: " + e.getLocalizedMessage());
			commonErrorList.add("Error Obtained while fetching link: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return driver;
	}
	
	/**
	 * This method is to deleteAllCookie against provided  WebDriver instance. 
	 * will also reset the counter of BaseTest.cookiebotDialogCount to zero
	 * Method generated to handle CookiebotDialog behaviour
	 * 
	 * @author vinay.verma
	 */
	public void deleteAllCookie(WebDriver driver) {		
		driver.manage().deleteAllCookies();
		BaseTest.cookiebotDialogCount = 0;
	}

	/**
	 * This method gets the firefox browser installation path from
	 * Automation.Properties
	 * 
	 * @author ssharma4
	 */
	public String getFirefoxInstallPath() {
		Properties pro = new Properties();
		try {
			File src = new File("./Automation.properties");
			FileInputStream fis = new FileInputStream(src);
			pro.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured while getting FF browser " + "path from Property file" + e.getMessage());
			commonErrorList.add("Error occured while getting FF " + "browser path from Property file");
		}
		return pro.getProperty("firefoxInstallPath");
	}

//	public void closeAllSQLConnections() {
//		try {
//			if (SQLServerConnection.qaCon != null)
//				SQLServerConnection.qaCon.close();
//			if (SQLServerConnection.con != null)
//				SQLServerConnection.con.close();
//			if (SQLServerConnection.tcCon != null)
//				SQLServerConnection.tcCon.close();
//			SQLServerConnection.closeConnection();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			BaseTest.log.error("Error while closing db connections in base test page");
//			commonErrorList.add("Error while closing db connections in base test page");
//		}
//	}
	
	public static String getBrowserName() {
		return browserName;
	}
	
	public static String renameAndSetDownloadedFileLogsForSingleReport() {
		String updatedFilePath = null;
		try {
			String downloadPath = BaseTest.downloadPath.replace('\\', '/');
			String latestFileName = "AdminReviewProjectDetailPage.getLatestFile(downloadPath)";
			updatedFilePath = copyAndDeleteFile(BaseTest.downloadPath, BaseTest.logFilePath, latestFileName);
			TestReporterListener objReportObj = new TestReporterListener();
			objReportObj.uploadFoldersOnS3(null, null, BaseTest.logFilePath+"\\ScoreExcelReports\\"+latestFileName);
			
			File pathFolder = new File(".\\log\\ScoreExcelReports\\");
			if (!pathFolder.exists()) {
				pathFolder.mkdir();
			}
			String pathFile = pathFolder + "\\" + latestFileName;
			String currentPath = "." + pathFile;
			String excelHyperlink = "<wysiwyg><a href='" + currentPath
					+ "' target='_blank'>Download_Report(s) i.e. Zip</a></wysiwyg>";
			BaseTest.log.info("<wysiwyg><h4>Please click to download the report file of " + latestFileName + " - "
					+ excelHyperlink + "</h4></wysiwyg>");
			System.out
			.println("Please click to download the report file of " + latestFileName + " - " + excelHyperlink);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedFilePath;
	}
		
	public static String copyAndDeleteFile(String copyFrom, String copyTo, String fileName) {
		InputStream inStream = null;
		OutputStream outStream = null;
		File destinationPath = null;

		try {

			File file = new File(copyTo + "ScoreExcelReports");
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println(file + " Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}

			File sourcePath = new File(copyFrom + "" + fileName);
			destinationPath = new File(copyTo + "ScoreExcelReports\\" + fileName);

			inStream = new FileInputStream(sourcePath);
			outStream = new FileOutputStream(destinationPath);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			// delete the original file
			sourcePath.delete();

			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationPath + "";
	}
	/*This method gets the test method
	private static BasePage basePage;
	private static CaptureScreenShot objCapture;
	private static String methodName;*/

	public String beforeTestforTestName(String Languages, Boolean videoFlag) {
		try{

			BasePage basePage = new BasePage(getDriver());
			testMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
			String uuid = UUID.randomUUID().toString();
			log = new BaseLogger(uuid + "_" + testMethodName, logFilePath);
			BaseTest.log.info("Test Method is : "+testMethodName);
			// Fetch the log file path where the test's log file will get save
			String logFilePath = basePage.getAbsolutePath();

//			GetTestName getTestName = new GetTestName();

			String TestName = "testMethodName";
			//System.out.println("Method Name for Report: " + TestName);
			BaseTest.log.info("Test name: "+TestName);
			String languageUsed = getLanguageWithinParanthesis(Languages, log);
			this.createScreenshotFolders(product, testMethodName, languageUsed, videoFlag, log);
			log.info("Method name is:-" + testMethodName);
			this.createScreenshotFolders(product, testMethodName, languageUsed, videoFlag, log);
			return TestName;
		}catch (Exception e){e.printStackTrace(); BaseTest.log.error("Error while fetching TestName");
			commonErrorList.add("Error while fetching testname from ");
		} return null;
	}

	/**
	 * This method performs on two conditions:
	 * 1> if running on lower environments, It will provide the MUL if project id is provided
	 * otherwise it will return MUL from pageurls table
	 * 2> if running on production environments, It will provide the project id and MUL from pageurls
	 * if the parameters are 'Optional'
	 *
	 *
	 * @param getUrl
	 * @param projectId
	 * @param mul
	 * @param expectedMUL
	 * @param expectedProjectId
	 * @author pankaj.kapruwan
	 */
//	public HashMap<String, String> getMULAndProjectIdBasedOnProjectid(String getUrl, String projectId, String mul,String
//			expectedMUL, String expectedProjectId) {
//		HashMap<String, String> projectDetails = new HashMap<>();
//		Boolean isFound = false;
//		try {
//			ResponseDBConnection rdb = new ResponseDBConnection();
//			if (isProd) {
//				if (mul == null) {
//					isFound = false;
//				}
//				else{
//					if (mul.equalsIgnoreCase("optional")) {
//						mul = getPageUrl(getUrl, expectedMUL);
//						log.info("MUL is: " + getPageUrl(getUrl, expectedMUL));
//						projectDetails.put("MUL", mul);
//					}if (projectId.equalsIgnoreCase("optional")) {
//						projectId = getPageUrl(getUrl, expectedProjectId);
//						projectDetails.put("ProjectId", projectId);
//						log.info("Project Id is: " + getPageUrl(getUrl, expectedProjectId));
//					}else
//						log.info("Project Id is: " + getPageUrl(getUrl, expectedProjectId));
//				}
//			} else {
//				if (projectId.equalsIgnoreCase("optional")) {
//					projectId = getPageUrl(getUrl, expectedProjectId);
//					projectDetails.put("ProjectId", projectId);
//				}
//				if(rdb.getMultiuseLinkFromDB(getUrl, projectId) != null){
//					mul = rdb.getMultiuseLinkFromDB(getUrl, projectId);
//					projectDetails.put("MUL", mul);
//					isFound = true;
//					log.info("MUL is: " + mul);
//
//				}
//				if(!isFound){
//					commonErrorList.add("MUL not found for Project id " + projectId);
//					log.error("MUL not found for Project id " + projectId);
//					Assert.fail("MUL not found for Project id " + projectId);
//				}else
//					log.info("url is: " + mul);
//			}
//			return projectDetails;
//		}catch (Exception e) {
//			log.error("Error obtained while fetching ProjectID/MUL from DB"+e.getLocalizedMessage() + ""
//					+ "method name:"+new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName());
//			commonErrorList.add("Error obtained while fetching ProjectID/MUL from DB. "
//					+ "Please refer the deatailed report");
//		}
//		return null;
//	}
}