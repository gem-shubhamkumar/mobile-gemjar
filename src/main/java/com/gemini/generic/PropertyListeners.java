package com.gemini.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyListeners {
	public static Properties loadProjectProperties(InputStream resourceInputStream) {
		Properties property = new Properties();
		try {
			property.load(resourceInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			property = null;
		}
		return property;
	}

	public static Properties loadProjectProperties(String pathOfProperty) {
		Properties property = new Properties();
		try {
			File file = new File(pathOfProperty);
			InputStream is = new FileInputStream(file);
			property.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			property = null;
		}
		return property;
	}

	public static void updatePropertyKey(Properties property, String propertyKey, String propertyValue) {
		Properties properties = property;
		properties.setProperty(propertyKey, propertyValue);
	}
}
