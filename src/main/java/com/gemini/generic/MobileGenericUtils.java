package com.gemini.generic;

//import com.gemini.listners.PropertyListeners;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MobileGenericUtils extends MobileGlobalVar {

    public static String getplatformName() {
        String platformName = appiumProperties.getProperty("platformName");
//	        String platformNameFromPropertiesFile = MobileGlobalVar.projectProperty.getProperty("platformName");
        String platform = platformName != null ? platformName : "Android";
        return platformName;
    }

    public static String getdeviceName() {
        // 	System.out.println(System.getProperties());
        String deviceName = appiumProperties.getProperty("deviceName");
//	        System.out.println(deviceName);
//	        String deviceNameFromPropertiesFile = MobileGlobalVar.projectProperty.getProperty("deviceName");
//	        String device = deviceName != null ? deviceName
//	                : deviceNameFromPropertiesFile != null ? deviceNameFromPropertiesFile : "Android Emulator";
        return deviceName;
    }

    public static String getplatformVersion() {
        String platformVersion = appiumProperties.getProperty("platformVersion");
        String cloudName = getCloudName().toLowerCase();

        if (platformVersion.indexOf('.') > 0) {
            if (cloudName.equals("perfecto")) {
                return platformVersion.substring(0, platformVersion.indexOf('.'));
            }
            return platformVersion;
        } else {
            if (cloudName.equals("local") || cloudName.equals("browserstack")) {
                return platformVersion + ".0";
            }
            return platformVersion;
        }
    }

    public static String getapp() {
        System.out.println(System.getProperties());
        String app = appiumProperties.getProperty("app");

//	        String appFromPropertiesFile = MobileGlobalVar.projectProperty.getProperty("app");
//	        String app1 = app != null ? app
//	                : appFromPropertiesFile != null ? appFromPropertiesFile : "user.dir";
        return app;

    }

    public static String getudid() {
        String app = appiumProperties.getProperty("udid");
//	        String udidFromPropertiesFile = MobileGlobalVar.projectProperty.getProperty("udid");
//	        String udid1 = udid != null ? udid
//	                : udidFromPropertiesFile != null ? udidFromPropertiesFile : "192.168.157.102:5555";
        return udid;

    }

    public static String getappiumUrl() {
        String appiumUrl = appiumProperties.getProperty("appiumUrl");
        System.out.println(appiumUrl);
//	        String platformNameFromPropertiesFile = MobileGlobalVar.projectProperty.getProperty("platformName");
        String appium = appiumUrl != null ? appiumUrl : "http://0.0.0.0:4723/wd/hub/";
        return appiumUrl;
    }

    public static String getBundelID() {
        String bundleId = appiumProperties.getProperty("bundleID");
        return bundleId;
    }

    public static String getProjectName() {
        String sysProjectName = String.valueOf(Paths.get(System.getProperty("user.dir")).getFileName());
        String sysPropProjectName = appiumProperties.getProperty("projectName");
        String projectName = sysPropProjectName != null && sysPropProjectName.length() >= 1 ?
                sysPropProjectName : sysProjectName;
        return projectName;
    }

    public static String getProjectReportName() {
        String sysPropReportName = appiumProperties.getProperty("reportName");
        String reportName = sysPropReportName != null && sysPropReportName.length() >= 1 ? sysPropReportName
                : "Gem-Jar-Report";
        return reportName;
    }

    public static String getTestCaseFileName() {
        String sysTestCaseFileName = System.getProperty("testCaseFileName");
        String testCaseFileNameFromProjProp = projectProperty.getProperty("testCaseFileName");
        String testCaseFileName = sysTestCaseFileName != null ? sysTestCaseFileName
                : testCaseFileNameFromProjProp != null ? testCaseFileNameFromProjProp
                : projectName + "_testCase.json";
        return testCaseFileName;
    }

    public static String getProjectEnvironment() {
        String sysPropEnvironmentName = appiumProperties.getProperty("environment");
        String environmentName = sysPropEnvironmentName != null && sysPropEnvironmentName.length() >= 1 ?
                sysPropEnvironmentName : "beta";
        return environmentName;
    }

    private static String getReportLocation() {
        try {
            String systemQuanticReportLocation = System.getProperty("QuanticReportLocation");
            String reportLocationFromSystemProperty = ProjectProperties.getProperty("reportLocation");
            String loc = reportLocationFromSystemProperty != null && !reportLocationFromSystemProperty.isEmpty()
                    ? reportLocationFromSystemProperty
                    : (System.getProperty("user.dir") != null ? System.getProperty("user.dir") : "");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
            DateTimeFormatter hms = DateTimeFormatter.ofPattern("HHmmss");
            loc = loc + "/Report/" + dtf.format(now) + "/" + hms.format(now);
            return loc;
        } catch (Exception e) {
            System.out.println("Some Error Occur With reportLocation . Default reportLocation Set");
            return "";
        }
    }

    public static void initializeMailingList() {
        String mailProperties = projectName + "_Mail.properties";
        mailingProperty = PropertyListeners.loadProjectProperties(
                ClassLoader.getSystemResourceAsStream(mailProperties));
        failMail = mailingProperty.getProperty("failMail");
        ccMail = mailingProperty.getProperty("ccMail");
        passMail = mailingProperty.getProperty("passMail");
        mail = mailingProperty.getProperty("mail");
    }

    public static String getAppPackageName() {
        String appPackageName = appiumProperties.getProperty("packageName");
        return appPackageName;
    }

    public static void initializeMobileGlobalVariables() {
        mobileProperty = PropertyListeners
                .loadProjectProperties(ClassLoader.getSystemResourceAsStream("Mobile.properties"));
        projectName = getProjectName();
        ProjectProperties.setProjectProperties(
                ClassLoader.getSystemResourceAsStream(projectName + ".properties"));
        projectProperty = PropertyListeners.loadProjectProperties(
                ClassLoader.getSystemResourceAsStream(projectName + ".properties"));
        environment = getProjectEnvironment();
        reportName = getProjectReportName();
        testCaseFileName = getTestCaseFileName();
        testCaseDataJsonPath = System.getProperty("TestCaseDataJsonPath");
        if (projectProperty.getProperty("sendMail") == null) {
            sendMail = "true";
        } else {
            sendMail = projectProperty.getProperty("sendMail");
        }
        reportLocation = getReportLocation();
        initializeMailingList();
    }

    public static String getCloudName() {
        System.out.println(appiumProperties.getProperty("cloudName"));
        return appiumProperties.getProperty("cloudName");
    }

    // browserStack
    public static String getBrowserStackUserName() {
        System.out.println(appiumProperties.getProperty("browserStackUserName"));
        return appiumProperties.getProperty("browserStackUserName");
    }

    public static String getBrowserStackAccessKey() {
        System.out.println(appiumProperties.getProperty("browserStackAccessKey"));
        return appiumProperties.getProperty("browserStackAccessKey");
    }

    public static String getBrowserStackURL() {
        String url = appiumProperties.getProperty("browserStackUrl");
        url = url.replace("username", getBrowserStackUserName());
        url = url.replace("accesskey", getBrowserStackAccessKey());
        System.out.println(url);
        return url;
    }

    public static String getBrowserStackApp() {
        System.out.println(appiumProperties.getProperty("browserStackApp"));
        return appiumProperties.getProperty("browserStackApp");
    }

    // Perfecto
    public static String getPerfectoSecurityToken() {
        System.out.println(appiumProperties.getProperty("perfectoToken"));
        return appiumProperties.getProperty("perfectoToken");
    }

    public static String getPerfectoURL() {
        System.out.println(appiumProperties.getProperty("perfectoUrl"));
        return appiumProperties.getProperty("perfectoUrl");
    }

    public static String getPerfectoApp() {
        System.out.println(appiumProperties.getProperty("perfectoApp"));
        return appiumProperties.getProperty("perfectoApp");
    }

    public static String getPerfectoDeviceModel() {
        System.out.println(appiumProperties.getProperty("perfectoDeviceModel"));
        return appiumProperties.getProperty("perfectoDeviceModel");
    }
}