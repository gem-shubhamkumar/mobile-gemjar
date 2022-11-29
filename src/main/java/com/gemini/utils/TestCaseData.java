package com.gemini.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class TestCaseData {
    private static JsonObject projectTestCaseData = new JsonObject();
    private static final ThreadLocal<JsonObject> testCaseData = new ThreadLocal();
    private static final ThreadLocal<String> testCaseCategory = new ThreadLocal();
    private static final ThreadLocal<String> testCaseScenarioID = new ThreadLocal();
    private static final ThreadLocal<String> testCaseRunFlag = new ThreadLocal();
    private static final ThreadLocal<String> testCaseNameThread = new ThreadLocal();
    private static final ThreadLocal<JsonObject> testCaseInputData = new ThreadLocal();

    public TestCaseData() {
    }

    public static void setProjectTestCaseData(InputStream input) {
        try {
            JsonElement testCaseJsonElement = JsonParser.parseString(IOUtils.toString(input, Charset.defaultCharset()));
            projectTestCaseData = testCaseJsonElement.getAsJsonObject();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void setProjectTestCaseData(String pathname) {
        try {
            File testCaseDataFile = new File(pathname);
            FileInputStream fileInputStream = new FileInputStream(testCaseDataFile);
            JsonElement testCaseJsonElement = JsonParser.parseString(IOUtils.toString(fileInputStream, Charset.defaultCharset()));
            projectTestCaseData = testCaseJsonElement.getAsJsonObject();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void setCurrentTestCaseData(String testCaseName) {
        try {
            JsonObject testData = projectTestCaseData.get(testCaseName).getAsJsonObject();
            testCaseData.set(testData);
            testCaseNameThread.set(testCaseName);
            testCaseCategory.set(((JsonObject)testCaseData.get()).get("category").getAsJsonPrimitive().getAsString());
            testCaseScenarioID.set(((JsonObject)testCaseData.get()).get("scenarioID").getAsJsonPrimitive().getAsString());
            testCaseRunFlag.set(((JsonObject)testCaseData.get()).get("runFlag").getAsJsonPrimitive().getAsString());
            testCaseInputData.set(((JsonObject)testCaseData.get()).get("inputData").getAsJsonObject());
        } catch (Exception var2) {
            var2.printStackTrace();
            testCaseData.set((JsonObject) null);
        }

    }

    public static String getCurrentTestCaseName() {
        try {
            return (String)testCaseNameThread.get();
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static JsonObject getCurrentTestCaseData() {
        try {
            return (JsonObject)testCaseData.get();
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String getTestCaseCategory() {
        return (String)testCaseCategory.get();
    }

    public static String getTestCaseID() {
        return (String)testCaseScenarioID.get();
    }

    public static JsonObject getTestCaseInputData() {
        return (JsonObject)testCaseInputData.get();
    }

    public static String getTestCaseRunFlag() {
        return (String)testCaseRunFlag.get();
    }

    public static List<String> getTypeOfTestCases(String type) {
        return null;
    }
}
