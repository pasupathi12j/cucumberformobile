package com.qa.engine;

import com.qa.steps.Hooks;
import com.qa.utils.CustomData;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

import static com.qa.engine.DriverManager.driver;

public abstract class ProjectBase {


    protected static final String MVP = "securramvp";

    protected static final String MVP1 = "securramvp1";


    protected static TestUtils utils = new TestUtils();
    protected static CustomData customData = new CustomData();
    protected static Properties properties;


    public static Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public static void logInfo(String text) {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).info(text);
        Hooks.printStream.print("\n" + text);
    }

    public static void logInfo(Object text) {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).info(text);
        Hooks.printStream.print("\n" + text);
    }

    public static void logInfo(String text, Object vars) {
        log().info(text, vars);
        Hooks.printStream.print("\n" + text);
    }

    public static void logError(String text, Object vars) {
        log().error(text, vars);
        Hooks.printStream.print("\n" + text);
    }

    public static void logError(String text) {
        log().error(text);
        Hooks.printStream.print("\n" + text);
    }

    public AppiumDriver getDriver() {
        return driver.get();
    }
}
