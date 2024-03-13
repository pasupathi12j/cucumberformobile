package com.qa.utils;

import com.qa.engine.ProjectBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager extends ProjectBase {

    public Properties getProps() throws IOException {
        InputStream is = null;
        String propsFileName = "config/config.properties";

        if (properties.isEmpty()) {
            try {
                logInfo("loading config properties");
                is = getClass().getClassLoader().getResourceAsStream(propsFileName);
                properties.load(is);
            } catch (IOException e) {
                e.printStackTrace();
                logError("Failed to load config properties. ABORT!!" + e);
                throw e;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return properties;
    }
}
