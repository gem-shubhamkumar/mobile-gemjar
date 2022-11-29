package com.gemini.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class GemJarGlobalVar {
    public static String projectName;
    public static String environment;
    public static String reportName;
    public static String testCaseFileName;
    public static String testCaseDataJsonPath;
    public static List<String> testCasesToRun;
    public static String reportLocation;
    public static boolean cucumberFlag = false;
    public static String browserInTest;
    public static String report_type;
    public static JsonObject CONFIG_JSON_OBJECT = new JsonObject();
    public static JsonElement suiteDetail;
    public static Map<String, JsonElement> globalResponseHM;

    public GemJarGlobalVar() {
    }
}