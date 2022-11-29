package com.gemini.utils;

import java.util.Set;

public class ProjectConfigData {
    public ProjectConfigData() {
    }

    public static String getProperty(String key) {
        try {
            String value = GemJarUtils.getGemJarConfigData(key);
            return value;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void setProperty(String key, String value) {
        try {
            GemJarUtils.getConfigObject().addProperty(key, value);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static Boolean containsKey(String key) {
        try {
            Set<String> keySet = GemJarUtils.getConfigObject().keySet();
            Boolean keyStatus = keySet.contains(key);
            return keyStatus;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Boolean isEmpty() {
        try {
            Boolean keyStatus = GemJarUtils.getConfigObject().isJsonNull() || GemJarUtils.getConfigObject().keySet().isEmpty();
            return keyStatus;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static Set<String> getStringPropertyNames() {
        try {
            Set<String> keyStatus = GemJarUtils.getConfigObject().keySet();
            return keyStatus;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static int getSize() {
        try {
            int keyStatus = GemJarUtils.getConfigObject().keySet().size();
            return keyStatus;
        } catch (Exception var1) {
            var1.printStackTrace();
            return 0;
        }
    }
}
