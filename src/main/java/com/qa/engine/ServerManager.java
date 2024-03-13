package com.qa.engine;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;

public class ServerManager extends ProjectBase {
    private static final ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    public AppiumDriverLocalService getServer() {
        return server.get();
    }

    public void startServer() {
        AppiumDriverLocalService server = WindowsGetAppiumService();

        server.start();

        if (!server.isRunning()) {
            log().fatal("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }
//        server.clearOutPutStreams();
        ServerManager.server.set(server);
        log().info("Appium server started");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService WindowsGetAppiumService() {
        GlobalParams params = new GlobalParams();

        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("ANDROID_HOME", System.getenv("ANDROID_HOME"));
        environment.put("JAVA_HOME", System.getenv("JAVA_HOME"));

        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(BASEPATH, "/wd/hub")
                .withTimeout(Duration.ofSeconds(30))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error") // --> set this as 'debug' to see the appium server logs
                .withEnvironment(environment)
                .withLogFile(new File("target\\Server.log")));
    }

    public AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = new GlobalParams();
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/bin:/Users/Om/Library/Android/sdk/tools:/Users/Om/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", properties.getProperty("androidHome"));
        environment.put("JAVA_HOME", properties.getProperty("javaHome"));
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingAnyFreePort()
                .withArgument(BASEPATH, "/wd/hub")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }
}
