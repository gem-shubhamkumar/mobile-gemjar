package com.gemini.generic;

import com.gemini.reporting.GemTestReporter;
import io.cucumber.java.*;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class MobileCucumberBase {


    @BeforeClass
    public static void beforeSuite() {
        System.out.println("running before suite");
        MobileGlobalVar.mobileProperty = PropertyListeners
                .loadProjectProperties(ClassLoader.getSystemResourceAsStream("mobile.properties"));
        setCucumberProperties();
//        before_all();
    }

    private static void setCucumberProperties() {
        System.out.println("running set cucumber properties");
        try {
            String stepDefinitionPackages = MobileGlobalVar.mobileProperty.getProperty("glueCode");
            System.setProperty("cucumber.glue", "com.gemini.generic," + stepDefinitionPackages);
            System.setProperty("cucumber.features",
                    new File(ClassLoader.getSystemResource("features").toURI()).getAbsolutePath());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @BeforeAll
    public static void before_all() throws FileNotFoundException, IOException {
        System.out.println("running before all");

	//	MobileGenericUtils.initializeMobileGlobalVariables();
     //   MobileDriverManager.driverInitialisation();
        MobileAction.mobileProperty();

        String projectName = MobileGenericUtils.getProjectName();
        String environmentName = MobileGenericUtils.getProjectEnvironment();
        GemTestReporter.startSuite(projectName, environmentName);
    }

    @Before
    public void before(Scenario scenario) throws FileNotFoundException, IOException {
        System.out.println("running before");
        MobileDriverManager.driverInitialisation();
        String testcaseName = scenario.getName();
        String featureFileName = new File(scenario.getUri()).getName();
        GemTestReporter.startTestCase(testcaseName, featureFileName.substring(0, featureFileName.lastIndexOf('.')),
                false);
    }


    @BeforeStep
    public void before_step(Scenario scenario) {
//        System.out.println(scenario.get);
    }

    @AfterStep
    public void afterStep() {

    }

    @After
    public void after(Scenario scenario) {
        System.out.println("running after");
        MobileDriverManager.closeDriver();
        GemTestReporter.endTestCase();
    }

    @AfterAll
    public static void after_all() {
        System.out.println("running after all");
        GemTestReporter.endSuite();
    }


}
