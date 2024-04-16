package test;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailFinalReportFile {

    public void sendMail(String path, String reportName, List<String> logFileNameList, boolean failCheck,
                         boolean skipCheck, String reportSummary, int passTestCount, int failTestCount, int skipTestCount,
                         List<String> getEmailIDs, String productParam, String env, String browserParam, String environmentParam,
                         List<String> failedTestMethodName, String getReportLinkForS3) {
        String envResultStatus;
        BaseTest.loadAutomationProperties();
        try {
            if (getEmailIDs != null && getEmailIDs.size() > 0) {
                final String username = BaseTest.userName;
                final String password = BaseTest.password;

                String finalReport = path + "\\" + reportName;
                String[] finalReportDateFolderPath = path.split("TestExecutionReports\\\\");
                String[] extentReportDateFolderPath = BaseTest.extentReportPath.toString().split("TestExecutionReports\\\\");

                ArrayList<String> attachments = new ArrayList<String>();

                attachments.add(finalReport);

                Properties properties = new Properties();
                Session session = null;

                properties.put("mail.smtp.host", BaseTest.emailHost);
                properties.put("mail.smtp.user", username); // User name
                properties.put("mail.smtp.password", password); // password
                properties.put("mail.smtp.port", BaseTest.port);

                if (BaseTest.isLocal == true) {
                    properties.put("mail.smtp.socketFactory.class", BaseTest.socketFactory);
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    session = Session.getInstance(properties, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
                } else {
                    properties.put("mail.smtp.auth", "false");
                    session = Session.getInstance(properties);
                }

                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));

                    InternetAddress[] addressTo = new InternetAddress[getEmailIDs.size()];
                    for (int i = 0; i < getEmailIDs.size(); i++) {
                        addressTo[i] = new InternetAddress(getEmailIDs.get(i));
                    }
                    message.setRecipients(MimeMessage.RecipientType.TO, addressTo);

                    if (failCheck) {
                        envResultStatus = "Build Test Run Failed";
                    } else if (skipCheck) {
                        envResultStatus = "Build Test Run Skipped";
                    } else {
                        envResultStatus = "Build Test Run Passed";
                    }

                    if (BaseTest.productName.equalsIgnoreCase("Integration"))
                        envResultStatus = "TCI " + this.setEnvironmentName(environmentParam) + " " + envResultStatus;
                    else if (BaseTest.productName.equalsIgnoreCase("Precise-Fit(Int)"))
                        envResultStatus = "Precise-Fit " + this.setEnvironmentName(environmentParam) + " "
                                + envResultStatus;
                    else if (BaseTest.productName.equalsIgnoreCase("Precise-Fit"))
                        envResultStatus = "Precise-Fit " + this.setEnvironmentName(environmentParam) + " "
                                + envResultStatus;
                    else if (BaseTest.productName.equalsIgnoreCase("AM"))
                        envResultStatus = "AM " + this.setEnvironmentName(environmentParam) + " "
                                + envResultStatus;
                    else if (BaseTest.productName.equalsIgnoreCase("MFS"))
                        envResultStatus = "MFS " + this.setEnvironmentName(environmentParam) + " "
                                + envResultStatus;
                    else
                        envResultStatus = "TalentCentral " + this.setEnvironmentName(environmentParam) + " "
                                + envResultStatus;

                    message.setSubject(envResultStatus);

                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setHeader("Content-Type", "text/html");
                    int totalTestRun = passTestCount + failTestCount + skipTestCount;

                    String updatedURL = BaseTest.environmentPath.replaceAll("(?<!http:)//", "/").replaceAll("/\\z", "");
                    String link = (updatedURL + finalReportDateFolderPath[1]+ "/" + reportName).replace("\\", "/");
                    String extentReportPath = (updatedURL + "/"+ extentReportDateFolderPath[1] + "/" +BaseTest.extentReportName).replace("\\", "/");

                    String htmlPart = ("<html><body><h3>Test(s) Execution Summary:</h3><table cellpadding='0' cellspacing='0' border = '1'>"
                            + "<tr><td style ='padding : 0px 5px;' align='center'><b>Total Test Run</b></td><td style ='padding : 0px 5px;' align='center'><b>Total Test Passed</b></td><td style ='padding : 0px 5px;' align='center'><b>Total Test Failed</b></td><td style ='padding : 0px 5px;' align='center'><b>Total Test Skipped</b></td><td style ='padding : 0px 5px;' align='center'><b>Product</b></td><td style ='padding : 0px 5px;' align='center'><b>Environment</b></td><td style ='padding : 0px 5px;' align='center'><b>Browser</b></td></tr>"
                            + "<tr><td style ='padding : 0px 5px;' align='center'>" + totalTestRun
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + passTestCount
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + failTestCount
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + skipTestCount
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + productParam
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + env
                            + "</td><td style ='padding : 0px 5px;' align='center'>" + browserParam + "</td></tr>"
                            + "</table>");

                    getReportLinkForS3 = getReportLinkForS3.replace("\\", "/");

                    htmlPart += ("<h3>Test(s) Execution Details:</h3><table cellpadding='0' cellspacing='0' border = '1'>" +

                            "<tr><td style ='padding : 0px 5px;'><b>Test Method Name</b></td><td style ='padding : 0px 5px;'><b>Module Name</b></td><td style ='padding : 0px 5px;'><b>QA Owner</b></td><td style ='padding : 0px 5px;'><b>Status</b></td><td style ='padding : 0px 5px;'><b>Language</b></td><td style ='padding : 0px 5px;'><b>Execution Time</b></td><td style ='padding : 0px 5px;'><b>Defect(s), If Any</b></td></tr>"
                            + reportSummary
                            + "</table><p><b>Please click the link below for the final execution report : </b></br></br>Link : <a href="
                            + link + "> " + link + "</a><p></br>" + "Extent Report: </br></br><a href="
                            + extentReportPath + "> " + extentReportPath + "</a><p></br><b>" + "Or, Results can be viewed by AWS S3 link: </b></br></br><a href="
                            + "http://shlautomationresults.shl.systems/AutomationResults/"+getReportLinkForS3+ "> "
                            + "http://shlautomationresults.shl.systems/AutomationResults/"+getReportLinkForS3 + "</a><p></body></html>");

                    messageBodyPart.setContent(htmlPart, "text/html");

                    // Attachment code
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    message.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                    Transport.send(message);
                    transport.close();

                    System.out.println("Mail Sent");

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("No Email IDs found");
            }}catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String setEnvironmentName(String environmentParam) {
        String envName  = null;
        try {
            if (environmentParam.contains("talentcentral-1a.in.shl.zone"))
                envName = "Dev1";
            else if (environmentParam.contains("qa-hotfix-bld"))
                envName = "Hotfix";
            else if (environmentParam.contains("talentcentral-2a.eu.shl.zone"))
                envName = "QA2";
            else if (environmentParam.contains("talentcentral-hotfix"))
                envName = "Hotfix";
            else if ( environmentParam.contains("talentcentral.us.shl.com/"))
                envName = "US Prod";
            else if (environmentParam.contains("talentcentral.eu.shl.com/"))
                envName = "EU Prod";
            else if ( environmentParam.contains("talentcentral.au.shl.com/"))
                envName = "AU Prod";
            else if ( environmentParam.contains("talentcentral.cn.shl.com/"))
                envName = "CN Prod";
            else if (environmentParam.contains("qa1-talentcentral")
                    || environmentParam.contains("talentcentral-qa1.eu.shl.com/"))
                envName = "QA1";
            else if (environmentParam.contains("talentcentral-uat.eu.shl.domains")
                    || environmentParam.contains("integration-talentcentral-uat.eu.shl.domains/"))
                envName = "Integration";
            else if(environmentParam.contains("talentcentral-1a.au.shl.com"))
                envName = "aws-env";
            else if(environmentParam.contains("talentcentral.eu.shl.domains"))
                envName = "aws-EU-Stage";
            else if(environmentParam.contains("talentcentral.us.shl.domains"))
                envName = "aws-US-Stage";
            else if(environmentParam.contains("talentcentral-3b.eu.shl.zone"))
                envName = "Linux";
            else if(environmentParam.contains("talentcentral-1a.eu.shl.zone"))
                envName = "EU-Dev";
            else if(environmentParam.contains("internal-amcat.aspiringminds.com"))
                envName = "Stage";
            else {
                envName = "QA";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return envName;
    }

    public String getAllMethodNames(ArrayList<String> testMethodName) {
        String retString = "";
        if (testMethodName.size() > 0) {

            for (int i = 0; i < testMethodName.size(); i++) {
                retString = retString + "<span>" + testMethodName.get(i) + "</span>";
                if (i != testMethodName.size() - 1) {
                    retString = retString + "<hr>";
                }
            }
        }
        else
            retString = retString + "<span></span>";
        return retString;
    }

    public void sendTestAcknowledgeMail(File path, String reportName, List<String> getEmailIDs,
                                        String env, String browserParam)
    {
        try {
            BaseTest.loadAutomationProperties();
            if (getEmailIDs != null && getEmailIDs.size() > 0)
            {
                final String username = BaseTest.userName;
                final String password = BaseTest.password;

                String[] dateProductFolder = path.toString().split("TestExecutionReports\\\\");

                Properties properties = new Properties();
                Session session = null;

                properties.put("mail.smtp.host", BaseTest.emailHost);
                properties.put("mail.smtp.user", username); // User name
                properties.put("mail.smtp.password", password); // password
                properties.put("mail.smtp.port", BaseTest.port);

                if (BaseTest.isLocal == true)
                {
                    properties.put("mail.smtp.socketFactory.class", BaseTest.socketFactory);
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    session = Session.getInstance(properties,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });
                }
                else
                {
                    properties.put("mail.smtp.auth", "false");
                    session = Session.getInstance(properties);
                }
                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));

                    InternetAddress[] addressTo = new InternetAddress[getEmailIDs.size()];
                    for (int i = 0; i < getEmailIDs.size(); i++)
                    {
                        addressTo[i] = new InternetAddress(getEmailIDs.get(i));
                    }
                    message.setRecipients(MimeMessage.RecipientType.TO, addressTo);

                    String mailSubject = this.getEnvironmentName(env);

                    message.setSubject(mailSubject);

                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setHeader("Content-Type", "text/html");

                    String link = BaseTest.environmentPath + dateProductFolder[1]+ "\\" + reportName;
                    link = (link.replaceAll("(?<!http:)//", "/").replaceAll("/\\z", "")).replace("\\", "/");

                    String  htmlPart = ("<html><body><p><b>This is to notify you that your test(s) has been started on "
                            + "environment. Below given link will be accessible once the first test(if running multiple tests)"
                            + " will be completed. </b></br></br>Link : <a href="
                            + link + "> " + link + "</a><p></body></html>");

                    messageBodyPart.setContent(htmlPart, "text/html");

                    // Attachment code
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    message.setContent(multipart);
                    Transport transport = session.getTransport("smtp");
                    Transport.send(message);
                    transport.close();

                    System.out.println("Automation Started Mail Sent");
                }
                catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                System.out.println("No Email IDs found");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getEnvironmentName(String environmentParam) {
        String testStartSubject = null;
        try {
            if ((environmentParam.contains("talentcentral-staging.us.shl.com"))) {
                testStartSubject = "Test(s) has started on Stage environment";
            } else if (environmentParam.contains("talentcentral-1a.in.shl.zone")) {
                testStartSubject = "Test(s) has started on Dev environment.";
            } else if (environmentParam.contains("talentcentral.eu.shl.domains")) {
                testStartSubject = "Test(s) has started on EU-Stage environment.";
            } else if (environmentParam.contains("qa-hotfix-bld")) {
                testStartSubject = "Test(s) has started on Hotfix environment.";
            } else if (environmentParam.contains("talentcentral-2a.eu.shl.zone")){
                testStartSubject = "Test(s) has started on QA2 environment.";
            } else if (environmentParam.contains("talentcentral-hotfix")) {
                testStartSubject = "Test(s) has started on Hotfix environment.";
            } else if (environmentParam.contains("prodsupport")) {
                testStartSubject = "Test(s) has started on Prod-Support environment.";
            } else if ((environmentParam.contains("talentcentral.us.shl.com"))) {
                testStartSubject = "Test(s) has started on US Prod environment.";
            } else if (environmentParam.contains("talentcentral.eu.shl.com")) {
                testStartSubject = "Test(s) has started on EU Prod environment.";
            } else if (environmentParam.contains("talentcentral.au.shl.com")) {
                testStartSubject = "Test(s) has started on AU Prod environment.";
            } else if(environmentParam.contains("talentcentral.cn.shl.com")) {
                testStartSubject = "Test(s) has started on CN Prod environment.";
            } else if (environmentParam.contains("talentcentral-2b.eu.shl.zone")) {
                testStartSubject = "Test(s) has started on QA1 environment.";
            } else if (environmentParam.contains("talentcentral-uat.eu.shl.domains") ||
                    (environmentParam.contains("integration-talentcentral-uat.eu.shl.domains")
                            || (environmentParam.contains("talentcentral-uat.eu.shl.domains")))) {
                testStartSubject = "Test(s) has started on Integration environment.";
            } else if (environmentParam.contains("https://talentcentral-3b.eu.shl.zone")) {
                testStartSubject = "TalentCentral Linux ";
            }else if (environmentParam.contains("https://talentcentral-1a.eu.shl.zone")) {
                testStartSubject = "TalentCentral EU-Dev ";
            }else if(environmentParam.contains("internal-amcat.aspiringminds.com"))
                testStartSubject = "Test(s) has started on Stage environment.";
            else {
                testStartSubject = "TalentCentral QA ";
            }

            if (BaseTest.productName.equalsIgnoreCase("Integration"))
                testStartSubject = "TCI- " + testStartSubject;
            else if ((BaseTest.productName.equalsIgnoreCase("Precise-Fit(Int)")) ||
                    BaseTest.productName.equalsIgnoreCase("Precise-Fit"))
                testStartSubject = "PreciseFit- " + testStartSubject;
            else if ((BaseTest.productName.equalsIgnoreCase("AM")))
                testStartSubject = "AM- " + testStartSubject;
            else
                testStartSubject = "TalentCentral- " +testStartSubject;

        } catch (Exception e) {
            e.printStackTrace();
            BaseTest.commonErrorList.add("Error obtained while fecthing env name");
        }
        return testStartSubject;
    }
}

