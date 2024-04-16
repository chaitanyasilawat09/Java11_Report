package test.lieteners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

//import com.ceb.shl.test.DataAccessLayer.SQLServerConnection;
//import com.ceb.shl.test.base.BaseTest;
//import com.ceb.shl.test.util.GetTestName;
//import com.ceb.shl.test.util.MailFinalReportFile;
//import com.ceb.shl.test.util.ResponseDBConnection;

import io.restassured.RestAssured;
import test.BaseTest;
import test.MailFinalReportFile;

public class TestReporterListeners implements IReporter {
//    GetTestName getTestName = new GetTestName();
    ArrayList<String> logFileNameList = new ArrayList<String>();
    boolean failCheck = false;
    boolean skipCheck = false;
    ArrayList<String> passedTestMethodName = new ArrayList<String>();
    ArrayList<String> failedTestMethodName = new ArrayList<String>();
    ArrayList<String> skippedTestMethodName = new ArrayList<String>();
    int passedCount = 0;
    int failedCount = 0;
    int skippedCount = 0;
    String testReportContainerFormat = null;
    String testReportSummaryPartFormat = null;
    String testReportFailedTestContainerFormat = null;
    String testReportSummaryFormat = null;
    String testReportFailedTestSummaryFormat = null;
    long totalSkippedExecutionTime = 0;
    long totalFailedExecutionTime = 0;
    long totalPassedExecutionTime = 0;

    String reportHyperlinkFormat = "<a href='##ActualLink##' title='##ActualLink##' target='_blank'>Link</a>";
    String reportDetailsHyperlinkFormat = "<a href='##DetailReportActualLink##' title='##DetailReportActualLink##' target='_blank'>Detailed Report</a>";
    String reportJiraHyperlinkFormat = "<a href='##ActualJiraLink##' title='##ActualJiraLink##' target='_blank'>Link</a>";

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path).toAbsolutePath());
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public static boolean writeFile(String path, String content) throws IOException {
        BufferedWriter writer = null;
        File logFile = null;
        try {
            logFile = new File(Paths.get(path).toAbsolutePath().toString());
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String reportName = null;
        List<String> getEmailID = new ArrayList<>();
        BaseTest.loadAutomationProperties();

        try {
            testReportContainerFormat = readFile("ReportTemplateFiles/VerifyQuestionTest_ReportContainer.html",
                    Charset.defaultCharset());
            testReportSummaryPartFormat = readFile("ReportTemplateFiles/VerifyQuestionTest_TestSummary.html",
                    Charset.defaultCharset());
            testReportFailedTestContainerFormat = readFile(
                    "ReportTemplateFiles/VerifyQuestionTest_FailedTestReport.html", Charset.defaultCharset());
            testReportFailedTestSummaryFormat = readFile(
                    "ReportTemplateFiles/VerifyQuestionTest_FailedTestDetails.html", Charset.defaultCharset());
            testReportSummaryFormat = readFile(
                    "ReportTemplateFiles/VerifyQuestionTest_TestExecutionSummary_Container.html",
                    Charset.defaultCharset());
            // Get the Test Suite
            ISuite suite = suites.get(0);

            // Getting the results for the said suite
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            String testExecutionSummaryHTML = "", testFailedExecutionSummaryHTML = "";
            String mailReportSummary = "";
            int passedTests = 0, failedTests = 0, skippedTests = 0, failedTestCounter = 0;
            String productParam = null;
            String browserParam = null;
            String environmentParam = null;
            String env = "EnvironmentCouldnotDetermined";
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();

                Collection<ITestResult> allPassedTests = tc.getPassedTests().getAllResults();
                Collection<ITestResult> allFailedTests = tc.getFailedTests().getAllResults();
                Collection<ITestResult> allSkippedTests = tc.getSkippedTests().getAllResults();

                passedTests += allPassedTests.size();
                failedTests += allFailedTests.size();
                skippedTests += allSkippedTests.size();

                // Code to fetch final file name from testNG
                Map param = new HashMap();
                param = tc.getCurrentXmlTest().getAllParameters();
                productParam = param.get("product").toString();
                browserParam = param.get("browser").toString().replaceAll("\\s", "");

                environmentParam = param.get("EnvUrlCO").toString();
//                ResponseDBConnection dbConn = new ResponseDBConnection();
                if (environmentParam.endsWith("/"))
                    environmentParam = environmentParam.substring(0, environmentParam.length() - 1);
                if (environmentParam.endsWith("/login"))
                    environmentParam = environmentParam.replace("/login", "");

                try {
//                    env = dbConn.getEnvironmentName(environmentParam);
                    env = null;
                    if (env == null) {
                        env = "EnvironmentCouldnotDetermined";
                    }
//                } catch (SQLException e) {
                } catch (Exception e) {
                    env = "EnvironmentCouldnotDetermined";
                    e.printStackTrace();
                }
//                  TODO Here we are updating JIRA test case
//                Map<String, String>	getJiraDetails =  getTestName.getExecutionID(env);

                try {
                    if (BaseTest.environment.contains("UI")) {
                        if ((String) param.get("fileRandomNumber") != null) {
                            reportName = ((String) param.get("fileRandomNumber")).replaceAll("\\s", "");
                        } else
                            System.out.println("Value of random file not found");

                        if ((String) param.get("emailID") != null) {
                            String emailIDs = (String) param.get("emailID");
                            getEmailID = Arrays.asList(emailIDs.split(","));
                        } else {
                            System.out.println("Selected Email from UI is: " + getEmailID);
                        }
                    } else if (BaseTest.environment.contains("CI")) {
                        if (BaseTest.emailIDs != null && !BaseTest.emailIDs.isEmpty()) {
                            String emailIDs = BaseTest.emailIDs;
                            getEmailID = Arrays.asList(emailIDs.split(","));
                        } else {
                            getEmailID = null;
                        }

                        String timeStamp = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
                        reportName = randomNumberValue();
                        reportName = "Report_" + reportName + timeStamp + "_Product_" + productParam + "_Environment_"
                                + env + "_Browser_" + browserParam;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                testReportContainerFormat = testReportContainerFormat.replace("class=\"failureSectionShow\"",
                        "class=\"failureSectionHide\"");
                for (ITestResult testResult : allPassedTests) {
                    String lang = null;
                    String screenshotLink = null;
                    String logsLink = null;
                    String logFile = null;
                    int size = Reporter.getOutput(testResult).size();
                    Map x = new HashMap();
                    String reportLink = null;
                    x = testResult.getTestContext().getCurrentXmlTest().getLocalParameters();
                    Iterator iterator = x.keySet().iterator();

                    try {
                        lang = (String) x.get("language");
                        if (lang == null) {
                            lang = "English US(en_us)";
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String screenshotPath = null;
                    for (int i = 0; i < size; i++) {
                        if (Reporter.getOutput(testResult).get(i).toString().contains("log_")) {
                            logsLink = Reporter.getOutput(testResult).get(i).toString();
                            this.uploadFoldersOnS3(logsLink, null, null);
                        }
                        if (Reporter.getOutput(testResult).get(i).toString().contains("AllScreenShots")) {
                            if (Reporter.getOutput(testResult).get(i).toString() != null) {
                                if (BaseTest.environment.contains("UI")) {
                                    zipFiles(Reporter.getOutput(testResult).get(i).toString());
                                    String tempEnvPath = BaseTest.environmentPath
                                            .replaceAll("TestExecutionReports//", "Screenshots")
                                            .replaceAll("TestExecutionReports/", "Screenshots");
                                    screenshotPath = Reporter.getOutput(testResult).get(i).toString()
                                            .replaceAll("../Screenshots", tempEnvPath);
                                    screenshotPath = screenshotPath + "ClickToDownloadAllScreenshots.zip";
                                    this.uploadFoldersOnS3(null, Reporter.getOutput(testResult).get(i).toString()
                                            .replaceAll("../Screenshots", "Screenshots"), null);
                                }
                            }
                            screenshotLink = "../../" + Reporter.getOutput(testResult).get(i).toString();
                        }
                    }
                    long passedExecutionTime = testResult.getEndMillis() - testResult.getStartMillis();
                    String timePerTest = String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(passedExecutionTime),
                            TimeUnit.MILLISECONDS.toSeconds(passedExecutionTime)
                                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(passedExecutionTime)));

                    int total = testResult.getParameters().length;
                    // under Automation Interface project
                    logFile = "../../../log/" + logsLink;
                    logFileNameList.add(logsLink);

//                    String testMethodName = getTestName.getTestMethodName(tc.getName());
                    String testMethodName = "Test Method Name will get";
                    // Append test case id
                    String testCaseId = "N/A";
                    String testcaseIdForDashboard = "N/A";
//                    Map<String, String> testCaseIdMap = BaseTest.testCaseId;
                    Map<String, String> testCaseIdMap = new HashMap<>();
                    testCaseIdMap.put("NA","NA");

                    for (Entry<String, String> testId : testCaseIdMap.entrySet()) {
                        if (testId.getKey().equals(testMethodName)) {
                            testCaseId = testId.getValue();
                            if (BaseTest.environment.contains("UI")) {
                                reportLink = BaseTest.environmentPath.replace("/TestExecutionReports//",
                                        "/log/" + logsLink);
                                reportLink = reportLink.replace("/TestExecutionReports", "/log/" + logsLink);
//                               TODO Here we are updating JIRA test case
//                                this.updateJiraTestCases(testCaseId, "1", env, getJiraDetails, reportLink);
                            }
                            if (testCaseId == null) {
                                testCaseId = "No Known Test Case Id";
                                testcaseIdForDashboard = "No Known Test Case Id";
                            } else {
                                testcaseIdForDashboard = testCaseId;
                                testCaseId = "<a href='https://jira.shl.systems/browse/" + testCaseId + "' title='"
                                        + testCaseId + "' target='_blank'>" + testCaseId + "</a>";
                            }
                            break;
                        }
                    }

//                    HashMap<String, String> getOwners = getTestName.getTestOwnerName(tc.getName());
//                    String owner = getOwners.get("owner");
                    String owner = "QA owner Chaitanya";
//                    String moduleName = getTestName.getModuleName(tc.getName());
                    String moduleName = "ModuleName will get";
                    passedCount++;
                    testExecutionSummaryHTML += String.format(testReportSummaryPartFormat, "passed", testMethodName,
                            owner, lang, "Passed", timePerTest,
                            logsLink != null
                                    ? reportDetailsHyperlinkFormat.replaceAll("##DetailReportActualLink##", logFile)
                                    : "No detailed Report",
                            screenshotLink != null ? reportHyperlinkFormat.replaceAll("##ActualLink##", screenshotLink)
                                    : "No screenshots",
                            testCaseId);

                    totalPassedExecutionTime += passedExecutionTime;
                    String defectIDs = "N/A";
                    mailReportSummary += "<tr>" + "<td style ='padding : 0px 5px;'>" + testMethodName + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + moduleName + "</td>"
                            + "<td style ='padding : 0px 5px;'>" + owner + "</td>"
                            + "<td style ='padding : 0px 5px;'><b>Passed</b></td>" + "<td style ='padding : 0px 5px;'>"
                            + lang + "</td>" + "<td align='center' style ='padding : 0px 5px;'>" + timePerTest + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + defectIDs + "</td>" + "</tr>";

                    if (BaseTest.environment.contains("UI")) {
//                        getTestName.insertIntoDashboardTable(testMethodName, 5, env, testcaseIdForDashboard, defectIDs,
//                                getOwners.get("manual"), reportLink, screenshotPath, getOwners.get("group"));
                    }
                }

                for (ITestResult testResult : allFailedTests) {
                    String lang = null;
                    failCheck = true;
                    String screenshotLink = null;
                    String screenshotLinkFail = null;
                    String logsLink = null;
                    String logFile = null;
                    String reportLink = null;
                    int size = Reporter.getOutput(testResult).size();
                    Map x = new HashMap();
                    x = testResult.getTestContext().getCurrentXmlTest().getLocalParameters();
                    try {
                        lang = (String) x.get("language");
                        if (lang == null) {
                            lang = "English US(en_us)";
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String screenshotPath = null;
                    for (int i = 0; i < size; i++) {
                        if (Reporter.getOutput(testResult).get(i).toString().contains("log_")) {
                            logsLink = Reporter.getOutput(testResult).get(i).toString();
                            this.uploadFoldersOnS3(logsLink, null, null);
                        }
                        if (Reporter.getOutput(testResult).get(i).toString().contains("AllScreenShots")) {
                            if (Reporter.getOutput(testResult).get(i).toString() != null) {
                                if (BaseTest.environment.contains("UI")) {
                                    zipFiles(Reporter.getOutput(testResult).get(i).toString());
                                    String tempEnvPath = BaseTest.environmentPath
                                            .replaceAll("TestExecutionReports//", "Screenshots")
                                            .replaceAll("TestExecutionReports/", "Screenshots");
                                    screenshotPath = Reporter.getOutput(testResult).get(i).toString()
                                            .replaceAll("../Screenshots", tempEnvPath);
                                    screenshotPath = screenshotPath + "ClickToDownloadAllScreenshots.zip";
                                    this.uploadFoldersOnS3(null, Reporter.getOutput(testResult).get(i).toString()
                                            .replaceAll("../Screenshots", "Screenshots"), null);
                                }
                            }
                            screenshotLink = "../../" + Reporter.getOutput(testResult).get(i).toString();
                        }
                        if (Reporter.getOutput(testResult).get(i).toString().contains("Failed"))// FailedTest
                        {
                            if (BaseTest.environment.contains("UI"))
                                this.uploadFoldersOnS3(null, Reporter.getOutput(testResult).get(i).toString()
                                        .replaceAll("../Screenshots", "Screenshots"), null);
                            screenshotLinkFail = "../../" + Reporter.getOutput(testResult).get(i).toString();
                        }

                    }

                    long failedExecutionTime = testResult.getEndMillis() - testResult.getStartMillis();

                    String timePerTest = String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(failedExecutionTime),
                            TimeUnit.MILLISECONDS.toSeconds(failedExecutionTime)
                                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(failedExecutionTime)));

                    int total = testResult.getParameters().length;
                    // under Automation Interface project
                    logFile = "../../../log/" + logsLink;
                    logFileNameList.add(logsLink);
                    String testMethodName = "test Method Name";
                    failedTestMethodName.add(testMethodName);
//                    HashMap<String, String> getOwners = getTestName.getTestOwnerName(tc.getName());
//                    String owner = getOwners.get("owner");
                    String owner = "QA owner Chaitanya";
                    // Append test case id
                    String testCaseId = "N/A";
                    String testcaseIdForDashboard = "N/A";
                    Map<String, String> testCaseIdMap = BaseTest.testCaseId;

                    for (Entry<String, String> testId : testCaseIdMap.entrySet()) {
                        if (testId.getKey().equals(testMethodName)) {
                            testCaseId = testId.getValue();
                            if(BaseTest.environment.contains("UI")) {
                                reportLink = BaseTest.environmentPath.replace("/TestExecutionReports//",
                                        "/log/" + logsLink);
                                reportLink = reportLink.replace("/TestExecutionReports", "/log/" + logsLink);
                                //                               TODO Here we are updating JIRA test case
//                                this.updateJiraTestCases(testCaseId, "2", env, getJiraDetails, reportLink);
                            }
                            if (testCaseId == null) {
                                testCaseId = "No Known Test Case Id";
                                testcaseIdForDashboard = "No Known Test Case Id";
                            } else {
                                testcaseIdForDashboard = testCaseId;
                                testCaseId = "<a href='https://jira.shl.systems/browse/" + testCaseId + "' title='"
                                        + testCaseId + "' target='_blank'>" + testCaseId + "</a>";
                            }
                            break;
                        }
                    }
                    String defectIDs = "N/A";
                    Map<String, String> defectMap = BaseTest.defectMap;

                    for (Entry<String, String> defect : defectMap.entrySet()) {
                        if (defect.getKey().equals(testMethodName)) {
                            defectIDs = defect.getValue();
                            if (defectIDs == null)
                                defectIDs = "No Known Defect Found";
                            break;
                        }
                    }

                    String moduleName = "ModuleName";
                    failedCount++;
                    testExecutionSummaryHTML += String.format(testReportSummaryPartFormat, "failed", testMethodName,
                            owner, lang, "Failed", timePerTest,
                            logsLink != null
                                    ? reportDetailsHyperlinkFormat.replaceAll("##DetailReportActualLink##", logFile)
                                    : "No detailed Report",
                            screenshotLink != null ? reportHyperlinkFormat.replaceAll("##ActualLink##", screenshotLink)
                                    : "No screenshots",
                            testCaseId);

                    totalFailedExecutionTime += failedExecutionTime;
                    mailReportSummary += "<tr>" + "<td style ='padding : 0px 5px;'>" + testMethodName + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + moduleName + "</td>"
                            + "<td style ='padding : 0px 5px;'>" + owner + "</td>"
                            + "<td style ='padding : 0px 5px;'><b><font color='#FF2121'>Failed</font><b></td>"
                            + "<td style ='padding : 0px 5px;'>" + lang + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + timePerTest + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + defectIDs + "</td>" + "</tr>";

                    Throwable failureException = testResult.getThrowable();

                    String stackTraceOfException = "at ";
                    for (StackTraceElement trace : failureException.getStackTrace()) {
                        stackTraceOfException += trace.toString() + " in ";
                    }

                    testFailedExecutionSummaryHTML += testReportFailedTestSummaryFormat
                            .replaceAll("##SNo##", String.valueOf(++failedTestCounter))
                            .replaceAll("##TestName##", testMethodName).replaceAll("##QAOwner##", owner)
                            .replaceAll("##Language##", lang)
                            .replace("##Exception##", failureException.getClass().getName())
                            .replace("##Message##",
                                    (failureException.getMessage() != null) ? failureException.getMessage()
                                            : "No Message")
                            .replace("##StackTrace##", stackTraceOfException).replaceAll("##Defects##", defectIDs);

                    if (Reporter.getOutput(testResult).size() > 1) {
                        if (screenshotLinkFail != null) {

                            testFailedExecutionSummaryHTML = testFailedExecutionSummaryHTML.replaceAll("##ScreenShot##",
                                    reportHyperlinkFormat.replaceAll("##ActualLink##", screenshotLinkFail));
                        } else {
                            testFailedExecutionSummaryHTML = testFailedExecutionSummaryHTML.replaceAll("##ScreenShot##",
                                    "No screenshot attached.");
                        }
                    } else {
                        testFailedExecutionSummaryHTML = testFailedExecutionSummaryHTML.replaceAll("##ScreenShot##",
                                "No screenshot attached.");
                    }

                    if (BaseTest.environment.contains("UI")) {
//                        getTestName.insertIntoDashboardTable(testMethodName, 0, env, testcaseIdForDashboard, defectIDs,
//                                getOwners.get("manual"), reportLink, screenshotPath, getOwners.get("group"));
                    }

                }

                for (ITestResult testResult : allSkippedTests) {
                    String lang = null;
                    skipCheck = true;
                    String screenshotLink = null;
                    String logsLink = null;
                    String logFile = null;
                    String reportLink = null;
                    int size = Reporter.getOutput(testResult).size();
                    Map x = new HashMap();
                    x = testResult.getTestContext().getCurrentXmlTest().getLocalParameters();

                    try {
                        lang = (String) x.get("language");
                        if (lang == null) {
                            lang = "English US(en_us)";
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < size; i++) {
                        if (Reporter.getOutput(testResult).get(i).toString().contains("log_")) {
                            logsLink = Reporter.getOutput(testResult).get(i).toString();
                        }
                        if (Reporter.getOutput(testResult).get(i).toString().contains("AllScreenShots")) {
                            screenshotLink = "../../" + Reporter.getOutput(testResult).get(i).toString();
                        }
                    }

                    long skippedExecutionTime = testResult.getEndMillis() - testResult.getStartMillis();

                    String timePerTest = String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(skippedExecutionTime),
                            TimeUnit.MILLISECONDS.toSeconds(skippedExecutionTime) - TimeUnit.MINUTES
                                    .toSeconds(TimeUnit.MILLISECONDS.toMinutes(skippedExecutionTime)));

                    int total = testResult.getParameters().length;
                    logFile = "../../../log/" + logsLink;
                    logFileNameList.add(logsLink);
                    String testMethodName = "getTestName.getTestMethodName(tc.getName())";
                    failedTestMethodName.add(testMethodName);
//                    HashMap<String, String> getOwners = getTestName.getTestOwnerName(tc.getName());
//                    String owner = getOwners.get("owner");
                    String owner = "QA owner Chaitanya";
                    String testCaseId = "N/A";
                    String testcaseIdForDashboard = "N/A";
                    Map<String, String> testCaseIdMap = BaseTest.testCaseId;

                    for (Entry<String, String> testId : testCaseIdMap.entrySet()) {
                        if (testId.getKey().equals(testMethodName)) {
                            testCaseId = testId.getValue();
                            if(BaseTest.environment.contains("UI")) {
                                reportLink = BaseTest.environmentPath.replace("/TestExecutionReports//",
                                        "/log/" + logsLink);
                                reportLink = reportLink.replace("/TestExecutionReports", "/log/" + logsLink);
//                               TODO Here we are updating JIRA test case
//                                this.updateJiraTestCases(testCaseId, "2", env, getJiraDetails, reportLink);
                            }
                            if (testCaseId == null) {
                                testCaseId = "No Known Test Case Id";
                                testcaseIdForDashboard = "No Known Test Case Id";
                            } else {
                                testcaseIdForDashboard = testCaseId;
                                testCaseId = "<a href='https://jira.shl.systems/browse/" + testCaseId + "' title='"
                                        + testCaseId + "' target='_blank'>" + testCaseId + "</a>";
                            }
                            break;
                        }
                    }
                    String moduleName = "getTestName.getModuleName(tc.getName())";
                    skippedCount++;
                    testExecutionSummaryHTML += String.format(testReportSummaryPartFormat, "skipped", testMethodName,
                            owner, lang, "Skipped", timePerTest,
                            logsLink != null
                                    ? reportDetailsHyperlinkFormat.replaceAll("##DetailReportActualLink##", logFile)
                                    : "No detailed Report",
                            screenshotLink != null ? reportHyperlinkFormat.replaceAll("##ActualLink##", screenshotLink)
                                    : "No screenshots",
                            testCaseId);
                    totalSkippedExecutionTime += skippedExecutionTime;
                    String defectIDs = "N/A";

                    mailReportSummary += "<tr>" + "<td style ='padding : 0px 5px;'>" + testMethodName + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + moduleName + "</td>"
                            + "<td style ='padding : 0px 5px;'>" + owner + "</td>"
                            + "<td style ='padding : 0px 5px;'><b>Skipped</b></td>" + "<td style ='padding : 0px 5px;'>"
                            + lang + "</td>" + "<td align='center' style ='padding : 0px 5px;'>" + timePerTest + "</td>"
                            + "<td style ='padding : 0px 5px;' align='center'>" + defectIDs + "</td>" + "</tr>";

                    if (BaseTest.environment.contains("UI")) {
//                        getTestName.insertIntoDashboardTable(testMethodName, 0, env, testcaseIdForDashboard, defectIDs,
//                                getOwners.get("manual"), reportLink, "Screenshot link not found.",
//                                getOwners.get("group"));
                    }
                }

                if (failedTests > 0) {
                    testReportContainerFormat = testReportContainerFormat.replace("class=\"failureSectionHide\"",
                            "class=\"failureSectionShow\"");
                }

            }

            String totalTime = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS
                            .toMinutes(totalPassedExecutionTime + totalFailedExecutionTime + totalSkippedExecutionTime),
                    TimeUnit.MILLISECONDS
                            .toSeconds(totalPassedExecutionTime + totalFailedExecutionTime + totalSkippedExecutionTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(
                            totalPassedExecutionTime + totalFailedExecutionTime + totalSkippedExecutionTime)));

            String executionSummaryContentHTML = testReportSummaryFormat
                    .replaceAll("##TotalNumberofTests##", String.valueOf(passedTests + failedTests + skippedTests))
                    .replaceAll("##TestPassedNumber##", String.valueOf(passedTests))
                    .replaceAll("##TestSkippedNumber##", String.valueOf(skippedTests))
                    .replaceAll("##TestFailedNumber##", String.valueOf(failedTests))
                    .replaceAll("##TotalExecutionTime##", totalTime).replaceAll("##Product##", productParam)
                    .replaceAll("##Environment##", env).replaceAll("##Browser##", browserParam);

            String reportContent = testReportContainerFormat
                    .replaceAll("##TestSummaryData##", executionSummaryContentHTML)
                    .replaceAll("##ExecutionDynamicData##", testExecutionSummaryHTML)
                    .replace("##FailureDynamicData##", testFailedExecutionSummaryHTML);

            String path = BaseTest.reportPath;

            String dateFolder = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            String[] product = reportName.split("Product_");
            product[1] = product[1].substring(0, product[1].indexOf("_"));
            path = path + "\\" + dateFolder;
            File myDir = new File(path);
            if (!myDir.exists()) {
                myDir.mkdir();
            }

            path = path + "\\" + product[1];
            myDir = new File(path);
            if (!myDir.exists()) {
                myDir.mkdir();
            }

            String finalReport = path + "\\" + reportName + ".html";

            // create a temporary file
            writeFile(finalReport, reportContent); // If test execution file generates outside

            String getReportLinkForS3 = "TestExecutionReports\\"+dateFolder+"\\"+product[1]+"\\"+reportName+".html";
            this.uploadFoldersOnS3(null, null, getReportLinkForS3);

            MailFinalReportFile mailObj = new MailFinalReportFile();

            mailObj.sendMail(path, reportName + ".html", logFileNameList, failCheck, skipCheck, mailReportSummary,
                    passedTests, failedTests, skippedTests, getEmailID, productParam, env, browserParam,
                    environmentParam, failedTestMethodName, getReportLinkForS3);

//            SQLServerConnection.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String randomNumberValue() {
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String randomnumber = "";
        for (int i = 0; i < 4; i++) {
            randomnumber += numbers.get(i).toString();
        }
        return randomnumber;
    }

    private static String zipFiles(String filePaths) {
        String zipFileName = null;
        try {
            filePaths = filePaths.replace("../", "");
            File screenshotsFilePath = new File(BaseTest.workingDirectory + filePaths);
            File[] listOfFiles = screenshotsFilePath.listFiles();

            if (listOfFiles != null && listOfFiles.length > 0) {
                zipFileName = screenshotsFilePath + "\\ClickToDownloadAllScreenshots".concat(".zip");

                FileOutputStream fos = new FileOutputStream(zipFileName);
                ZipOutputStream zos = new ZipOutputStream(fos);

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        String fileExtension = FilenameUtils.getExtension(listOfFiles[i].toString());
                        if (fileExtension.equalsIgnoreCase("jpg")) {
                            zos.putNextEntry(new ZipEntry(new File(listOfFiles[i].getPath()).getName()));
                            byte[] bytes = Files.readAllBytes(Paths.get(listOfFiles[i].getPath()));
                            zos.write(bytes, 0, bytes.length);
                            zos.closeEntry();
                        }
                    }
                }
                zos.close();
            }

        } catch (Exception ex) {
            System.err.println("I/O error while zipping the files:: " + ex);
        }
        return zipFileName;
    }

    public void updateJiraTestCases(String jiraTestCaseId, String status, String env, Map<String, String> getJiraDetails,
                                    String reportLink) {
        try {
            if(jiraTestCaseId != null && getJiraDetails != null && !getJiraDetails.isEmpty()){
                String[] testcases = jiraTestCaseId.trim().split(",");
                for(int i = 0; i < testcases.length; i++) {
                    if(getJiraDetails.get(testcases[i]) != null && !getJiraDetails.get(testcases[i]).isEmpty()) {
                        RestAssured.baseURI = "https://jira.shl.systems/rest/zapi";
                        String updateIssueUrl = "/latest/execution/"+getJiraDetails.get(testcases[i])+"/execute";

                        Map<String,String> headerMap = new HashMap<>();
                        headerMap.put("Content-Type", "application/json");
                        String authString = "TCAutomateTesting" + ":" + "Qa1zmn2lp";

                        if(status.equalsIgnoreCase("1")) {
                            if(BaseTest.pendingTestCases.contains(testcases[i]))
                                status = "3";
                            else
                                status = "1";
                        }

                        headerMap.put("Authorization", "Basic "+ new String (Base64.encodeBase64(authString.getBytes())));
                        String statusPaylod = "{   \"comment\":\" "+reportLink+" \",\r\n" +
                                "   \"status\":"+status+",\r\n" +
                                "   \"defectList\": [],\r\n" +
                                "   \"updateDefectList\": \"true\"\r\n" +
                                "}";

                        RestAssured.useRelaxedHTTPSValidation();
                        RestAssured.given().log().all().headers(headerMap).body(statusPaylod).put(updateIssueUrl);
                    }}}
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void uploadFoldersOnS3(String logFilePath, String screenshotFilePath, String reportPath) {
        String command = null;
        try {
            if (logFilePath != null) {
                command = "powershell.exe  Write-S3Object -BucketName shlautomationresults -File "
                        + BaseTest.workingDirectory + "\\log\\" + logFilePath + " -Key  /AutomationResults/log/"
                        + logFilePath;
            } else if (screenshotFilePath != null)
                command = "powershell.exe  Write-S3Object -BucketName shlautomationresults -Folder "
                        + BaseTest.workingDirectory + screenshotFilePath + "   -KeyPrefix  /AutomationResults/"
                        + screenshotFilePath + " -recurse";
            else
                command = "powershell.exe  Write-S3Object -BucketName shlautomationresults -File "
                        + BaseTest.workingDirectory + reportPath + " -Key  /AutomationResults/" + reportPath;

            Process powerShellProcess = Runtime.getRuntime().exec(command);
            powerShellProcess.getOutputStream().close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
