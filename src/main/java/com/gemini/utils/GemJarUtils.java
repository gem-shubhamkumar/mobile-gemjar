package com.gemini.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GemJarUtils {
    public GemJarUtils() {
    }

    public static void loadGemJarConfigData() {
        String configData = null;

        try {
            configData = IOUtils.toString(ClassLoader.getSystemResourceAsStream("gemjar-config.json"), Charset.defaultCharset());
            JsonObject configJson = JsonParser.parseString(configData).getAsJsonObject();
            String environment = CommonUtils.convertJsonElementToString(configJson.get("environment"));
            JsonElement envJsonElement = configJson.get(environment);
            if (envJsonElement != null) {
                JsonObject envJsonObject = envJsonElement.getAsJsonObject();
                Set<String> envKeys = envJsonObject.keySet();
                Iterator var6 = envKeys.iterator();

                while(var6.hasNext()) {
                    String envKey = (String)var6.next();
                    GemJarGlobalVar.CONFIG_JSON_OBJECT.addProperty(envKey, CommonUtils.convertJsonElementToString(envJsonObject.get(envKey)));
                    configJson.remove(envKey);
                }

                configJson.remove(environment);
                Set<String> parentConfigKeys = configJson.keySet();
                Iterator var11 = parentConfigKeys.iterator();

                while(var11.hasNext()) {
                    String parentConfigKey = (String)var11.next();
                    GemJarGlobalVar.CONFIG_JSON_OBJECT.addProperty(parentConfigKey, CommonUtils.convertJsonElementToString(configJson.get(parentConfigKey)));
                }
            } else {
                GemJarGlobalVar.CONFIG_JSON_OBJECT = configJson;
            }

        } catch (IOException var9) {
            throw new RuntimeException(var9);
        }
    }

    public static JsonObject getConfigObject() {
        return GemJarGlobalVar.CONFIG_JSON_OBJECT;
    }

    public static String getGemJarKeyValue(String key) {
        String valueFromSystemProperty = System.getProperty(key);
        String valueFromConfigFile = CommonUtils.convertJsonElementToString(GemJarGlobalVar.CONFIG_JSON_OBJECT.get(key));
        return valueFromSystemProperty != null && !valueFromSystemProperty.isEmpty() ? valueFromSystemProperty : valueFromConfigFile;
    }

    protected static JsonElement getEnvironmentBasedValue(String key) {
        JsonObject jsonObject = GemJarGlobalVar.CONFIG_JSON_OBJECT;
        JsonElement environmentData = jsonObject.get(GemJarGlobalVar.environment) != null ? jsonObject.get(GemJarGlobalVar.environment) : null;
        return environmentData != null ? environmentData.getAsJsonObject().get(key) : null;
    }

    public static String getGemJarConfigData(String key) {
        String value = CommonUtils.convertJsonElementToString(GemJarGlobalVar.CONFIG_JSON_OBJECT.get(key));
        return value;
    }

    public static void initializeGemJARGlobalVariables() {
        loadGemJarConfigData();
        GemJarGlobalVar.projectName = getGemJarKeyValue("projectName");
        GemJarGlobalVar.environment = getGemJarKeyValue("environment");
        GemJarGlobalVar.reportName = getGemJarKeyValue("reportName");
        GemJarGlobalVar.reportLocation = getReportLocation();
        GemJarGlobalVar.browserInTest = getGemJarKeyValue("browserName");
        GemJarGlobalVar.testCaseDataJsonPath = System.getProperty("testCaseDataJsonPath");
        GemJarGlobalVar.testCasesToRun = getTestCasesToRunFromSystemProperties();
        if (!GemJarGlobalVar.cucumberFlag) {
            GemJarGlobalVar.testCaseFileName = getTestCAseFileName();
            TestCaseData.setProjectTestCaseData(ClassLoader.getSystemResourceAsStream(GemJarGlobalVar.testCaseFileName));
        }

    }

    private static String getTestCAseFileName() {
        String testcaseFileName = getGemJarKeyValue("testCaseFileName");
        return testcaseFileName != null ? testcaseFileName : "testcase.json";
    }

    private static List<String> getTestCasesToRunFromSystemProperties() {
        String testCaseString = System.getProperty("testCasesToRun");
        String[] testCaseArray = testCaseString != null ? testCaseString.split(",") : null;
        ArrayList testCasesToRun;
        if (testCaseArray != null) {
            testCasesToRun = new ArrayList();
            String[] var3 = testCaseArray;
            int var4 = testCaseArray.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String testcase = var3[var5];
                testCasesToRun.add(testcase.trim());
            }
        } else {
            testCasesToRun = null;
        }

        return testCasesToRun;
    }

    private static String getReportLocation() {
        try {
            String reportLoc = getGemJarKeyValue("reportLocation");
            String loc = reportLoc != null ? reportLoc : System.getProperty("user.dir");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            DateTimeFormatter hms = DateTimeFormatter.ofPattern("HH-mm-ss");
            loc = loc + File.separator + "Report" + File.separator + dtf.format(now) + File.separator + hms.format(now);
            return loc;
        } catch (Exception var5) {
            System.out.println("Some Error Occur With reportLocation . Default reportLocation Set");
            return "";
        }
    }
}
