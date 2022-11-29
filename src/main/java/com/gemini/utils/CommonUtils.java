package com.gemini.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class CommonUtils {
//    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    public CommonUtils() {
    }

    public static String convertJsonElementToString(JsonElement jsonElement) {
        return jsonElement != null ? (jsonElement.isJsonNull() ? null : (jsonElement.isJsonPrimitive() ? jsonElement.getAsJsonPrimitive().getAsString() : jsonElement.toString())) : null;
    }

    public static String getDecryptedPwd(String encryptedPwd) {
        String decryptedPwd = "";

        for(int i = encryptedPwd.length() - 1; i >= 0; --i) {
            decryptedPwd = decryptedPwd + (char)(encryptedPwd.charAt(i) - 1);
        }

        return decryptedPwd;
    }

    public static void writeDataToOutputStream(OutputStream outputStream, String jsonStringPayload) {
        OutputStream os = null;

        try {
            os = outputStream;
            outputStream.write(jsonStringPayload.getBytes());
        } catch (Exception var12) {
//            logger.info("Exception Occured while Writing Data to Stream");
        } finally {
            try {
                os.close();
                os.flush();
                outputStream.flush();
                outputStream.close();
            } catch (IOException var11) {
//                logger.info("Exception Occured while closing Stream");
                throw new RuntimeException(var11);
            }
        }

    }

    public static String readPayLoad(Object requestPayload) {
        StringBuilder payload = null;
        if (requestPayload instanceof File) {
            File requestPayLoadFile = (File)requestPayload;
            FileReader fr = null;
            payload = new StringBuilder();

            Object var5;
            try {
                fr = new FileReader(requestPayLoadFile);

                int i;
                while((i = fr.read()) != -1) {
                    payload.append((char)i);
                }

                return payload.toString();
            } catch (Exception var15) {
//                logger.info("Exception Occured while Reading Payload");
                var5 = null;
            } finally {
                try {
                    fr.close();
                } catch (IOException var14) {
//                    logger.info("Exception Occured while closing Stream");
                    throw new RuntimeException(var14);
                }
            }

            return (String)var5;
        } else {
            payload = new StringBuilder((String)requestPayload);
            return payload.toString();
        }
    }

    public static String getDataFromBufferedReader(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        if (inputStream == null) {
            return null;
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            try {
                String output;
                try {
                    while((output = br.readLine()) != null) {
                        builder.append(output);
                    }
                } catch (IOException var13) {
//                    logger.info("Exception Occured while getting Data From Buffered Reader");
                    builder = new StringBuilder(var13.getMessage());
                }
            } finally {
                try {
                    br.close();
                } catch (IOException var12) {
//                    logger.info("I/O Exception Occured while getting Data From Buffered Reader");
                    throw new RuntimeException(var12);
                }
            }

            return builder.toString();
        }
    }

    public static Object genericInvokeMethod(Object obj, String methodName, Object... params) {
        int paramCount = params.length;
        Object requiredObj = null;
        Class<?>[] classArray = new Class[paramCount];

        for(int i = 0; i < paramCount; ++i) {
            classArray[i] = params[i].getClass();
        }

        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, classArray);
            method.setAccessible(true);
            requiredObj = method.invoke(obj, params);
        } catch (NoSuchMethodException var8) {
//            logger.info("No Such Method Exception Occured invoking Method");
        } catch (IllegalArgumentException var9) {
//            logger.info("Exception Occured in Arguments Provided Reader");
        } catch (IllegalAccessException var10) {
//            logger.info("Illegal Access Exception");
        } catch (InvocationTargetException var11) {
//            logger.info("Inovation Target Exception");
        }

        return requiredObj;
    }

//    public static Response invokeRequestMethod(Request request) {
//        Object obj = genericInvokeMethod(new ApiInvocation(), "executeRequest", request);
//        return null != obj ? (Response)obj : null;
//    }

    public static JsonElement convertStringInToJsonElement(String value) {
        try {
            return JsonParser.parseString(value);
        } catch (JsonSyntaxException var2) {
            return JsonParser.parseString("\"" + value + "\"");
        }
    }
}
