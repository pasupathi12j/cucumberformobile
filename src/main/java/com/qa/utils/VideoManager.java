package com.qa.utils;

import com.qa.engine.ProjectBase;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;

public final class VideoManager extends ProjectBase {

    public void startRecording() {
        ((CanRecordScreen) getDriver()).startRecordingScreen(
                new AndroidStartScreenRecordingOptions()
                        .withTimeLimit(Duration.ofMinutes(20))
                        .withVideoSize("960x540")
                        .withBitRate(5000000));
    }

    public void stopRecording(String name) {
        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
        name = name.replace("-", "");

        File videoDir = new File("target\\screenRecording");

        try (FileOutputStream stream = new FileOutputStream(videoDir + File.separator + name + ".mp4")) {
            stream.write(Base64.decodeBase64(media));
            logInfo("Screen recording completed.");
        } catch (Exception ignored) {
            logInfo("Error while recording the session " + ignored);
        }
    }
}
