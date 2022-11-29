package com.gemini.generic;

import com.gemini.reporting.GemTestReporter;
import com.gemini.reporting.STATUS;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileDriverManager extends MobileGenericUtils{

	private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	public static void setAppiumDriver(AppiumDriver AppiumDriver) {
		driver.set(AppiumDriver);
	}

	public static AppiumDriver getAppiumDriver() {
		return driver.get();
	}

	public static void closeDriver() {
		try {
			GemTestReporter.addTestStep("Close Driver", "Driver Close Successful", STATUS.PASS);
			getAppiumDriver().quit();
		} catch (Exception e) {
			GemTestReporter.addTestStep("Close Driver", "Driver Close Failed", STATUS.FAIL);
			e.printStackTrace();
		}
	}


	public static void driverInitialisation() throws FileNotFoundException, IOException {
		try {
			String platform = getplatformName().toLowerCase(); //android
			String cloud = getCloudName().toLowerCase(); // perfecto

			if (platform.equals("android")) {
				switch (cloud) {
					case "perfecto":
						System.out.println("perfecto_android");
						perfectoAndroidStart();
						break;
					case "browserstack":
						System.out.println("browserstack_android");
						browserStackAndroidStart();
						break;
					default:
						System.out.println("local_android");
						androidStart();
						break;
				}
			} else if (platform.equals("ios")) {
				switch (cloud) {
					case "perfecto":
						System.out.println("perfecto_ios");
						//TODO have to add perfecto_ios
						break;
					case "browserstack":
						System.out.println("browserstack_ios");
						//TODO have to add browserstack_ios
						break;
					default:
						System.out.println("local_ios");
						iOSStart();
						break;
				}
			} else {
				System.out.println("android");
				androidStart();
			}
			getAppiumDriver().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

		} catch (Exception e) {
			System.out.println("Error occur while initializing driver");
		}
	}


	// shubham
	public static void androidStart() {
		try {
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/app");
			File app = new File(appDir, getapp());

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, getplatformName());
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getplatformVersion());
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, getdeviceName());
			cap.setCapability(MobileCapabilityType.UDID, getudid());
			cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			cap.setCapability("autoAcceptAlerts", "true");
			cap.setCapability("autoGrantPermissions", "true");

			setAppiumDriver(new AndroidDriver(new URL(getappiumUrl()), cap));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void iOSStart() {
		try {
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/app");
			File app = new File(appDir, getapp());

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, getplatformName());
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getplatformVersion());
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, getdeviceName());
			cap.setCapability(MobileCapabilityType.UDID, getudid());
			cap.setCapability("bundleId", getBundelID());
			cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

			setAppiumDriver(new IOSDriver(new URL(getappiumUrl()), cap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void browserStackAndroidStart() {
		try {
			String url = getBrowserStackURL();

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, getplatformName());
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getplatformVersion());
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, getdeviceName());
			cap.setCapability(MobileCapabilityType.APP, getBrowserStackApp());
			cap.setCapability("project", getProjectName());
			cap.setCapability("autoAcceptAlerts", "true");
			cap.setCapability("autoGrantPermissions", "true");

			System.out.println(cap.toString());

			setAppiumDriver(new AndroidDriver(new URL(url), cap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void perfectoAndroidStart() {
		try {
			String url = getPerfectoURL();

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("perfecto:platformName", getplatformName());
			cap.setCapability("perfecto:platformVersion", getplatformVersion());
			cap.setCapability("perfecto:app", getPerfectoApp());
			cap.setCapability("perfecto:securityToken", getPerfectoSecurityToken());
			cap.setCapability("model", getPerfectoDeviceModel());
			cap.setCapability("automationName", "Appium");
			cap.setCapability("appiumVersion", "1.22.3");
			cap.setCapability("autoAcceptAlerts", "true");
			cap.setCapability("autoGrantPermissions", "true");
			setAppiumDriver(new AndroidDriver(new URL(url), cap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
