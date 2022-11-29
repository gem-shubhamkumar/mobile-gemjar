package com.gemini.generic;

import com.gemini.reporting.GemTestReporter;
import com.gemini.reporting.STATUS;
import com.gemini.utils.GemJarGlobalVar;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.junit.ComparisonFailure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;

public class MobileAction extends MobileGenericUtils {
    public static void mobileProperty() {
        String rootDirectory = System.getProperty("user.dir");
        String resourceDirectory = rootDirectory + "/src/test/resources/";
        //Configure property File
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(resourceDirectory + "AppiumSample.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        appiumProperties = properties;
    }

    public static String takeSnapShot() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileWithPath;
        if (GemJarGlobalVar.reportLocation != null) {
            fileWithPath = GemJarGlobalVar.reportLocation + "/SS/SS" + timestamp.getTime() + ".png";
        } else {
            fileWithPath = "report" + "/SS/SS" + timestamp.getTime() + ".png";
        }
        WebDriver webdriver = MobileDriverManager.getAppiumDriver();
        TakesScreenshot scrShot = (TakesScreenshot) webdriver;
        File SrcFile = (File) scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return "SS/SS" + timestamp.getTime() + ".png";
    }

    public static WebElement getElement(By locator) {
        try {
            return MobileDriverManager.getAppiumDriver().findElement(locator);
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static List<WebElement> getElements(By locator) {
        try {
            return MobileDriverManager.getAppiumDriver().findElements(locator);
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String getElementText(By locator) {
        try {
            WebElement element = getElement(locator);
            return element.getText();
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static List<String> getElementsText(By locator) {
        try {
            List<WebElement> elements = getElements(locator);
            List<String> elementsText = new ArrayList<>();

            for (WebElement element : elements) {
                elementsText.add(getElementText(element));
            }
            return elementsText;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void click(By locator) {
        try {
            WebElement element = getElement(locator);
            element.click();
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while Click", "Error Occur", STATUS.FAIL);
            var2.printStackTrace();
        }
    }

    public static void click(By locator, String elementLabel) {
        try {
            WebElement element = getElement(locator);
            element.click();
            GemTestReporter.addTestStep("Click on ", "Click Successful on " + elementLabel, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep("Click on ", "Click Failed on " + elementLabel, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void click(By locator, String steps, String description) {
        try {
            WebElement element = getElement(locator);
            element.click();
            GemTestReporter.addTestStep(steps, description, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep(steps, description, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void click(WebElement element) {
        try {
            element.click();
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while Click", "Error Occur", STATUS.FAIL);
            var2.printStackTrace();
        }
    }

    public static void click(WebElement element, String elementLabel) {
        try {
            element.click();
            GemTestReporter.addTestStep("Click on ", "Click Successful on " + elementLabel, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep("Click on ", "Click Failed on " + elementLabel, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void click(WebElement element, String steps, String description) {
        try {
            element.click();
            GemTestReporter.addTestStep(steps, description, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep(steps, description, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void typeText(By locator, String textToEnter) {
        try {
            WebElement element = getElement(locator);
            element.sendKeys(textToEnter);
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while Click", "Error Occur", STATUS.FAIL, takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void typeText(By locator, String textToEnter, String elementLabel) {
        try {
            WebElement element = getElement(locator);
            element.sendKeys(textToEnter);
            GemTestReporter.addTestStep("Type on " + elementLabel, "Type Text Successful<BR>Type Text ~" + textToEnter, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep("Type on " + elementLabel, "Type Text Failed<BR>Type Text ~" + textToEnter, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void typeText(By locator, String textToEnter, String steps, String description) {
        try {
            WebElement element = getElement(locator);
            element.sendKeys(textToEnter);
            GemTestReporter.addTestStep(steps, description, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep(steps, description, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void typeText(WebElement element, String textToEnter) {
        try {
            element.sendKeys(textToEnter);
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while Click", "Error Occur", STATUS.FAIL, takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void typeText(WebElement element, String textToEnter, String elementLabel) {
        try {
            element.sendKeys(textToEnter);
            GemTestReporter.addTestStep("Type on " + elementLabel, "Type Text Successful<BR>Type Text ~" + textToEnter, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep("Type on " + elementLabel, "Type Text Failed<BR>Type Text ~" + textToEnter, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void typeText(WebElement element, String textToEnter, String steps, String description) {
        try {
            element.sendKeys(textToEnter);
            GemTestReporter.addTestStep(steps, description, STATUS.PASS, takeSnapShot());
        } catch (Exception var1) {
            var1.printStackTrace();
            GemTestReporter.addTestStep(steps, description, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void clearText(By locator) {
        try {
            MobileDriverManager.getAppiumDriver().findElement(locator).clear();
            GemTestReporter.addTestStep("Clear Text", " Successful", STATUS.PASS, takeSnapShot());
        } catch (Exception var2) {
            var2.printStackTrace();
            GemTestReporter.addTestStep("Some error occur", "Error Occur", STATUS.FAIL, takeSnapShot());
        }
    }

    public static void clearText(By locator, boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().findElement(locator).clear();
            if (report) {
                GemTestReporter.addTestStep("Clear Text", " Successful", STATUS.PASS, takeSnapShot());
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            GemTestReporter.addTestStep("Some error occur", "Error Occur", STATUS.FAIL, takeSnapShot());
        }
    }

    public static void clearText(WebElement element) {
        try {
            element.clear();
            GemTestReporter.addTestStep("Clear Text", " Successful", STATUS.PASS, takeSnapShot());
        } catch (Exception var2) {
            var2.printStackTrace();
            GemTestReporter.addTestStep("Some error occur", "Error Occur", STATUS.FAIL, takeSnapShot());
        }
    }

    public static void clearText(WebElement element, boolean report) {
        try {
            element.clear();
            if (report) {
                GemTestReporter.addTestStep("Clear Text", " Successful", STATUS.PASS, takeSnapShot());
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            GemTestReporter.addTestStep("Some error occur", "Error Occur", STATUS.FAIL, takeSnapShot());
        }
    }

    public static void waitSec(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setImplicitTimeOut(long seconds) {
        try {
            MobileDriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setScriptTimeOut(long seconds) {

        try {
            MobileDriverManager.getAppiumDriver().manage().timeouts().setScriptTimeout(seconds, TimeUnit.SECONDS); // seconds is being converted into Timeunit.SECONDS
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPageLoadTimeOut(long seconds) {
        try {
            MobileDriverManager.getAppiumDriver().manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refresh() {
        try {
            MobileDriverManager.getAppiumDriver().navigate().refresh();
        } catch (Exception e) {
            GemTestReporter.addTestStep("Some error occur while Refresh", "Error Occur", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void refresh(Boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().navigate().refresh();
            if (report) {
                GemTestReporter.addTestStep("Refresh Page", "Page Refresh Successful", STATUS.PASS, MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Refresh Page", "Page Refresh Failed", STATUS.FAIL, MobileAction.takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateBack() {
        try {
            MobileDriverManager.getAppiumDriver().navigate().back();
        } catch (Exception e) {
            GemTestReporter.addTestStep("Some error occur while Navigating Back", "Error Occur", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateBack(Boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().navigate().back();
            if (report) {
                GemTestReporter.addTestStep("Navigate Back", "Navigate Back Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Navigate Back", "Navigate Back Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateForward() {
        try {
            MobileDriverManager.getAppiumDriver().navigate().forward();
        } catch (Exception e) {
            GemTestReporter.addTestStep("Some error occur while Navigating Forward", "Error Occur", STATUS.FAIL, takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateForward(Boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().navigate().forward();
            if (report) {
                GemTestReporter.addTestStep("Navigate Forward", "Forward Navigation Successful", STATUS.PASS, MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Navigate Forward", "Forward Navigation Failed", STATUS.FAIL, MobileAction.takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateToUrl(String url) {
        try {
            MobileDriverManager.getAppiumDriver().navigate().to(url);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Some error occur while Navigate to URL", "Error Occur", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void navigateToUrl(String url, Boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().navigate().to(url);
            if (report) {
                GemTestReporter.addTestStep("Navigate To URL", "Navigation To URL Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Navigate To URL", "Navigation To URL Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void pressBackButton(Boolean report) {
        try {
            if (MobileGenericUtils.getCloudName().equalsIgnoreCase("perfecto")) {
                Map<String, Object> params = new HashMap<>();
                params.put("keySequence", "BACK");
                MobileDriverManager.getAppiumDriver().executeScript("mobile:presskey", params);
            } else {
                ((AndroidDriver) MobileDriverManager.getAppiumDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
                if (report) {
                    GemTestReporter.addTestStep("Mobile Back ", "Mobile Back Successful", STATUS.PASS,
                            MobileAction.takeSnapShot());
                }
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Mobile Back ", "Mobile Back Failes", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void pressHomeButton(Boolean report) {
        try {
            if (MobileGenericUtils.getCloudName().equalsIgnoreCase("perfecto")) {
                Map<String, Object> params = new HashMap<>();
                params.put("keySequence", "HOME");
                MobileDriverManager.getAppiumDriver().executeScript("mobile:presskey", params);
            } else {
                ((AndroidDriver) MobileDriverManager.getAppiumDriver()).pressKey(new KeyEvent(AndroidKey.HOME));
                if (report) {
                    GemTestReporter.addTestStep("Mobile Home ", "Mobile Home Successful", STATUS.PASS,
                            MobileAction.takeSnapShot());
                }
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Mobile Home ", "Mobile Home Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void tap(By locator, Boolean report) {
        try {
            TouchAction action = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver());
            WebElement element = getElement(locator);
            action.tap(new TapOptions().withElement(new ElementOption().withElement((element)))).perform();
            if (report) {
                GemTestReporter.addTestStep("Tap on ", "Tap on Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Tap on ", "Tap on Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void doubleTapGesture(By locator, Boolean report) {
        try {
            WebElement element = getElement(locator);
            ((JavascriptExecutor) MobileDriverManager.getAppiumDriver()).executeScript("mobile: doubleClickGesture",
                    ImmutableMap.of(
                            "elementId", ((RemoteWebElement) element).getId()
                    )
            );
            if (report) {
                GemTestReporter.addTestStep("Double Tap ", "Double Tap Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            e.printStackTrace();
            GemTestReporter.addTestStep("Double Tap ", "Double Tap Failed", STATUS.FAIL,
                    takeSnapShot());
        }
    }

    public static void longPress(By locator, Boolean report) {
        try {
            LongPressOptions longPressOptions = new LongPressOptions();
            WebElement element = MobileAction.getElement(locator);
            longPressOptions.withDuration(ofSeconds(2)).withElement(ElementOption.element(element));
            TouchAction action = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver());
            action.longPress(longPressOptions).release().perform();
            if (report) {
                GemTestReporter.addTestStep("Long Press ", "Long Press Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Long Press ", "Long Press Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void longPressGesture(By locator, Boolean report) {
        try {
            WebElement element = getElement(locator);
            ((JavascriptExecutor) MobileDriverManager.getAppiumDriver()).executeScript(
                    "mobile: longClickGesture", ImmutableMap.of(
                            "elementId", ((RemoteWebElement) element).getId(),
                            "duration", 2000)
            );
            if (report) {
                GemTestReporter.addTestStep("Long Press ", "Long Press Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Long Press ", "Long Press Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void moveTo(float start_X, double start_Y, double end_X, double end_Y, Boolean report) {
        try {
            TouchAction touch = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver());
            touch.press(PointOption.point(getXCoOrdinate(start_X), (getYCoOrdinate(start_Y))))
                    .waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(PointOption.point(getXCoOrdinate(end_X), (getYCoOrdinate(end_Y)))).release().perform();
            if (report) {
                GemTestReporter.addTestStep("Move To ", "Move to Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Move To ", "Move to Failes", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            e.printStackTrace();
        }
    }

    public static int getXCoOrdinate(double X_position) {
        int widthCoordinate = 0;
        try {
            Dimension dim = MobileDriverManager.getAppiumDriver().manage().window().getSize();
            int width = dim.width;
            widthCoordinate = (int) (width * (X_position));
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while getting X CoOrdinate", "Error Occur", STATUS.FAIL, takeSnapShot());
            var2.printStackTrace();
        }
        return widthCoordinate;
    }

    public static int getYCoOrdinate(double Y_position) {
        int heightCoordinate = 0;
        try {
            Dimension dim = MobileDriverManager.getAppiumDriver().manage().window().getSize();
            int height = dim.height;
            heightCoordinate = (int) (height * (Y_position));
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Some error occur while getting Y CoOrdinate", "Error Occur", STATUS.FAIL, takeSnapShot());
            var2.printStackTrace();
        }
        return heightCoordinate;
    }

    public static void swipeUp(By locator, Boolean report) {
        try {
            WebElement ele = getElement(locator);
            int centerX = ele.getRect().x + (ele.getSize().width / 2);
            double startY = ele.getRect().y + (ele.getSize().height * 0.9);
            double endY = ele.getRect().y + (ele.getSize().height * 0.1);

            TouchAction swipe = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver())
                    .press(PointOption.point(centerX, (int) startY))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point(centerX, (int) endY))
                    .release()
                    .perform();
            if (report) {
                GemTestReporter.addTestStep("Swipe Up", "Swipe Up Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe UP", "Swipe Up Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void swipeDown(By locator, Boolean report) {
        try {
            WebElement ele = MobileAction.getElement(locator);
            int centerX = ele.getRect().x + (ele.getSize().width / 2);
            double startY = ele.getRect().y + (ele.getSize().height * 0.1);
            double endY = ele.getRect().y + (ele.getSize().height * 0.9);

            TouchAction swipe = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver())
                    .press(PointOption.point(centerX, (int) startY))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point(centerX, (int) endY))
                    .release()
                    .perform();
            if (report) {
                GemTestReporter.addTestStep("Swipe Down", "Swipe Down Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe Down", "Swipe Down Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void swipeLeft(By locator, Boolean report) {
        try {
            WebElement ele = MobileAction.getElement(locator);
            int centerY = ele.getRect().y + (ele.getSize().height / 2);
            double startX = ele.getRect().x + (ele.getSize().width * 0.9);
            double endX = ele.getRect().x + (ele.getSize().width * 0.1);

            TouchAction swipe = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver())
                    .press(PointOption.point((int) startX, centerY))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point((int) endX, centerY))
                    .release()
                    .perform();
            if (report) {
                GemTestReporter.addTestStep("Swipe Left", "Swipe Left Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe Left", "Swipe Left Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void swipeRight(By locator, Boolean report) {
        try {
            WebElement ele = MobileAction.getElement(locator);
            int centerY = ele.getRect().y + (ele.getSize().height / 2);
            double startX = ele.getRect().x + (ele.getSize().width * 0.1);
            double endX = ele.getRect().x + (ele.getSize().width * 0.9);

            TouchAction swipe = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver())
                    .press(PointOption.point((int) startX, centerY))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point((int) endX, centerY))
                    .release()
                    .perform();
            if (report) {
                GemTestReporter.addTestStep("Swipe Right", "Swipe Right Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe Right", "Swipe Right Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static double getElementXCordinate(By locator, double coOrdinateposition) {
        WebElement element = MobileAction.getElement(locator);
        double elementXCoOrdinate = element.getRect().x + (element.getSize().width * coOrdinateposition);
        return elementXCoOrdinate;
    }

    public static double getElementYCordinate(By locator, double coOrdinateposition) {
        WebElement element = MobileAction.getElement(locator);
        double elementYCoOrdinate = element.getRect().y + (element.getSize().height * coOrdinateposition);
        return elementYCoOrdinate;
    }

    public static void scrollTo(By locator, double start_X, double start_Y, double end_X, double end_Y,
                                Boolean report) {
        try {
            double startX = getElementXCordinate(locator, start_X);
            double endX = getElementXCordinate(locator, end_X);
            double startY = getElementYCordinate(locator, start_Y);
            double endY = getElementYCordinate(locator, end_Y);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX, (int) startY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), (int) endX, (int) endY));
            swipe.addAction(finger.createPointerUp(0));
            MobileDriverManager.getAppiumDriver().perform(Arrays.asList(swipe));
            if (report) {
                GemTestReporter.addTestStep("Scroll ", "Scroll Successful", STATUS.PASS, takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Scroll ", "Scroll Failed", STATUS.FAIL, takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void scrollToElement(String text, Boolean report) {
        try {
            MobileDriverManager.getAppiumDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView" +
                    "(text(\"" + text + "\"));"));
            if (report) {
                GemTestReporter.addTestStep("Scroll to ", "Scroll Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception var2) {
            GemTestReporter.addTestStep("Scroll to ", "Scroll Failed", STATUS.FAIL,
                    MobileAction.takeSnapShot());
            var2.printStackTrace();
        }
    }

    public static void dragAndDrop(By sourceLocator, By targetLocator, Boolean report) {
        try {
            WebElement src = getElement(sourceLocator);
            WebElement dest = getElement(targetLocator);

            TouchAction swipe = new TouchAction((PerformsTouchActions) MobileDriverManager.getAppiumDriver())
                    .press(ElementOption.element(src))
                    .waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(ElementOption.element(dest))
                    .release()
                    .perform();
            if (report) {
                GemTestReporter.addTestStep("Drag and Drop", "Drag and Drop Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Drag and Drop", "Drag and Drop Failed  ", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void switchToWebView() {
        ((AndroidDriver) MobileDriverManager.getAppiumDriver()).context("WEBVIEW_" + getAppPackageName());
    }

    public static void switchToNativeView() {
        ((AndroidDriver) MobileDriverManager.getAppiumDriver()).context("NATIVE_APP");
    }

    public static String getTitle() {
        return MobileDriverManager.getAppiumDriver().getTitle();
    }

    public static String getTitle(Boolean report) {
        try {
            String title = MobileDriverManager.getAppiumDriver().getTitle();
            if (report) {
                GemTestReporter.addTestStep("Get Title", "Title ~" + title, STATUS.PASS, takeSnapShot());
            }
            return title;
        } catch (Exception var3) {
            GemTestReporter.addTestStep("Get Title", "Get Title Failed", STATUS.FAIL, takeSnapShot());
            var3.printStackTrace();
            return null;
        }
    }


    public static String getAttributeValue(By locator, String attribute) {
        try {
            WebElement element = getElement(locator);
            return element.getAttribute(attribute);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void waitUntilElementVisible(By locator, long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileDriverManager.getAppiumDriver(),
                    Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            GemTestReporter.addTestStep("Error occur", "Fail to Load", STATUS.FAIL,
                    MobileAction.takeSnapShot());
        }
    }

    public static void waitUntilElementVisible(By locator, long seconds, String messageOnFail) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileDriverManager.getAppiumDriver(),
                    Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            GemTestReporter.addTestStep("Error occur", messageOnFail, STATUS.FAIL,
                    MobileAction.takeSnapShot());
        }
    }

    public static void waitUntilElementClickable(By locator, long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileDriverManager.getAppiumDriver(),
                    Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            GemTestReporter.addTestStep("Error occur", "Fail to Load", STATUS.FAIL,
                    MobileAction.takeSnapShot());
        }
    }

    public static void waitUntilElementClickable(By locator, long seconds, String messageOnFail) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileDriverManager.getAppiumDriver(),
                    Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            GemTestReporter.addTestStep("Error occur", messageOnFail, STATUS.FAIL,
                    MobileAction.takeSnapShot());
        }
    }

    public static void verifyEquals(By locator, String expected) {
        String actual = "";
        try {
            actual = getElementText(locator);
            assertEquals(expected, actual);
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.PASS, takeSnapShot());
        } catch (ComparisonFailure e) {
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void swipeGesture(By locator, String direction, float percent, Boolean report) {
        // direction - up, down, left and right (case-insensitive)
        try {
            ((JavascriptExecutor) MobileDriverManager.getAppiumDriver()).executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) getElement(locator)).getId(),
                    "direction", direction,
                    "percent", percent
            ));
            if (report) {
                GemTestReporter.addTestStep("Swipe " + direction, "Swipe " + direction + " Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe " + direction, "Swipe " + direction + " Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static void swipeGesture(By locator, String direction, Boolean report) {
        // direction - up, down, left and right (case-insensitive)
        try {
            ((JavascriptExecutor) MobileDriverManager.getAppiumDriver()).executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) getElement(locator)).getId(),
                    "direction", direction,
                    "percent", 0.70
            ));
            if (report) {
                GemTestReporter.addTestStep("Swipe " + direction, "Swipe " + direction + " Successful", STATUS.PASS,
                        MobileAction.takeSnapShot());
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Swipe " + direction, "Swipe " + direction + " Failed", STATUS.FAIL,
                    takeSnapShot());
            e.printStackTrace();
        }
    }

    public static String getToastMessage() {
        try {
            By toastLocator = AppiumBy.xpath("(//android.widget.Toast)[1]");
            return getAttributeValue(toastLocator, "name");
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    public static String getToastMessage(By locator) {
        try {
            return getAttributeValue(locator, "name");
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    public static void verifyToastMessage(String expected) {
        String actual = "";
        try {
            actual = getToastMessage();
            assertEquals(expected, actual);
            GemTestReporter.addTestStep("Verifying Toast Message", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.PASS, takeSnapShot());
        } catch (ComparisonFailure e) {
            GemTestReporter.addTestStep("Verifying Toast Message", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void verifyToastMessage(By locator, String expected) {
        String actual = "";
        try {
            actual = getToastMessage(locator);
            assertEquals(expected, actual);
            GemTestReporter.addTestStep("Verifying Toast Message", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.PASS, takeSnapShot());
        } catch (ComparisonFailure e) {
            GemTestReporter.addTestStep("Verifying Toast Message", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void verifyEquals(String actual, String expected) {
        try {
            assertEquals(expected, actual);
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.PASS, takeSnapShot());
        } catch (ComparisonFailure e) {
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.FAIL, takeSnapShot());
        }
    }

    public static void verifyEquals(WebElement element, String expected) {
        String actual = "";
        try {
            actual = getElementText(element);
            assertEquals(expected, actual);
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.PASS, takeSnapShot());
        } catch (ComparisonFailure e) {
            GemTestReporter.addTestStep("Verifying Text", "Expected: " + expected + "<br>"
                    + "Actual: " + actual, STATUS.FAIL, takeSnapShot());
        }
    }

}