package com.qa.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class GlobalParams extends ProjectBase {
    private static final ThreadLocal<String> platformName = new ThreadLocal<String>();
    private static final ThreadLocal<String> platformVersion = new ThreadLocal<String>();
    private static final ThreadLocal<String> udid = new ThreadLocal<String>();
    private static final ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static final ThreadLocal<String> systemPort = new ThreadLocal<String>();
    private static final ThreadLocal<String> chromeDriverPort = new ThreadLocal<String>();
    private static final ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static final ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();

    protected static void loadProperties() {
        try {
            properties = new Properties();
            properties.load(Files.newInputStream(Paths.get("./src/main/resources/config/config.properties")));
            log().info("loading config properties..");
        } catch (IOException ex) {
            log().fatal("Failed to load config properties. ABORT!!");
            ex.printStackTrace();
        }
    }

    public String getPlatformName() {
        return platformName.get();
    }

    public void setPlatformName(String platformName1) {
        platformName.set(platformName1);
    }

    public String getPlatformVersion() {
        return platformVersion.get();
    }

    public void setPlatformVersion(String platformVersion) {
        GlobalParams.platformVersion.set(platformVersion);
    }

    public String getUDID() {
        return udid.get();
    }

    public void setUDID(String udid2) {
        udid.set(udid2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public String getSystemPort() {
        return systemPort.get();
    }

    public void setSystemPort(String systemPort2) {
        systemPort.set(systemPort2);
    }

    public String getChromeDriverPort() {
        return chromeDriverPort.get();
    }

    public void setChromeDriverPort(String chromeDriverPort2) {
        chromeDriverPort.set(chromeDriverPort2);
    }

    public String getWdaLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String wdaLocalPort2) {
        wdaLocalPort.set(wdaLocalPort2);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String webkitDebugProxyPort2) {
        webkitDebugProxyPort.set(webkitDebugProxyPort2);
    }

    public void initializeGlobalParams() {
        loadProperties();

        String platformName;
        String platformVersion;
        String udid;
        String deviceName;

        try {
            platformName = System.getProperty("platformName") != null ?
                    System.getProperty("platformName") : properties.getProperty("platformName");
            udid = System.getProperty("udid") != null ?
                    System.getProperty("udid") : properties.getProperty("udid");
            deviceName = System.getProperty("deviceName") != null ?
                    System.getProperty("deviceName") : properties.getProperty("deviceName");
            platformVersion = System.getProperty("platformVersion") != null ?
                    System.getProperty("platformVersion") : properties.getProperty("platformVersion");
        } catch (Exception e) {
            logInfo("Error while setting up the device config details");
            throw e;
        }

        GlobalParams params = new GlobalParams();
        params.setPlatformName(platformName);
        params.setPlatformVersion(platformVersion);
        params.setUDID(udid);
        params.setDeviceName(deviceName);

        customData.setPlatformName(platformName);

        switch (params.getPlatformName()) {
            case "Android":
                params.setSystemPort(System.getProperty("systemPort", "10000"));
                params.setChromeDriverPort(System.getProperty("chromeDriverPort", "11000"));
                break;
            case "iOS":
                params.setWdaLocalPort(System.getProperty("wdaLocalPort", "10001"));
                params.setWebkitDebugProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));
                break;
            default:
                throw new IllegalStateException("Invalid Platform Name.");
        }
    }
}
