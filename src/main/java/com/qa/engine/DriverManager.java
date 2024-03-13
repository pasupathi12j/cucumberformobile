package com.qa.engine;

import com.qa.utils.VideoManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;

public class DriverManager extends ProjectBase {
    protected static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }

    public void initializeDriver(String app) throws Exception {
        AppiumDriver driver = null;
        GlobalParams params = new GlobalParams();
        customData.setPlatformName(params.getPlatformName());
        try {
            log().info("initializing Appium driver...");
            switch (params.getPlatformName()) {
                case "Android":
                    driver = new AndroidDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCapabilities(app));
                    //                   driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), new CapabilitiesManager().getCapabilities(app));
//                    driver = new AndroidDriver(new URL(capabilitiesManager.getCloudURL()), capabilitiesManager.getCapabilities(app));
                    break;
                case "iOS":
//                    driver = new IOSDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCapabilities(app));
                    driver = new IOSDriver(new URL("http://127.0.0.1:4723/"), new CapabilitiesManager().getCapabilities(app));
                    break;
            }
            if (driver == null) {
                throw new Exception("driver is null.");
            }
            log().info("Driver is initialized");
            DriverManager.driver.set(driver);
            this.setDriver(driver);
        } catch (IOException e) {
            e.printStackTrace();
            log().fatal("Driver initialization failed.");
            throw e;
        }
        if (Boolean.parseBoolean(properties.getProperty("screenRecording"))) {
            new VideoManager().startRecording();
            logInfo("Screen recording has been started....");
        }
    }
}
