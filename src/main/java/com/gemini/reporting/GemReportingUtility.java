package com.gemini.reporting;

import com.gemini.utils.GemJarGlobalVar;
import com.gemini.utils.ProjectConfigData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Objects;

public class GemReportingUtility {
    public GemReportingUtility() {
    }

    public static void createReport(String suiteDetail, String stepJson, String reportLoc) {
        try {
            String htmlTemplate = IOUtils.toString((InputStream) Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("GemjarReport.html")), Charset.defaultCharset());
            htmlTemplate = htmlTemplate.replace("var obj = '';", "var obj = " + suiteDetail + ";");
            GemJarGlobalVar.reportName = GemJarGlobalVar.reportName != null ? GemJarGlobalVar.reportName : "GemjarTestReport";
            if(reportLoc!=null) {
                FileUtils.writeStringToFile(new File(reportLoc + File.separator + GemJarGlobalVar.reportName + ".html"), htmlTemplate, Charset.defaultCharset());
            } else {
                FileUtils.writeStringToFile(new File("report" + File.separator + GemJarGlobalVar.reportName + ".html"), htmlTemplate, Charset.defaultCharset());
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public static void createReport(String suiteDetail, String stepJson) {
        createReport(suiteDetail, stepJson, (String) null);
    }

    public static long getCurrentTimeInSecond() {
        return Instant.now().getEpochSecond();
    }

    public static long getCurrentTimeInMilliSecond() {
        return Instant.now().getEpochSecond() * 1000L;
    }

    public static String getMachineName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception var1) {
            return null;
        }
    }

    public static String getCurrentUserName() {
        return ProjectConfigData.containsKey("gemjarUserName") ? ProjectConfigData.getProperty("gemjarUserName") : System.getProperty("user.name");
    }
}