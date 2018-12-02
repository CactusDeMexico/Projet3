package com.openclassrooms.mpancarte;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.InputStream;
import java.util.Properties;

class ConfigReader {
    private boolean developerMode;
    private int trials = 10;
    private int colors = 5;
    private int cases = 4;

    ConfigReader() throws Exception {
        Properties properties = new Properties();

        InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(input);
        developerMode = Boolean.valueOf(properties.getProperty("developerMode", "false"));
        try {
            cases = Integer.valueOf(properties.getProperty("NbCase", "4"));
            colors = Integer.valueOf(properties.getProperty("NbCouleurSelec", "5"));
            trials = Integer.valueOf(properties.getProperty("NbEssai", "10"));
        } catch (NumberFormatException e) {
            System.out.println("Error while reading configuration.");
        }
    }

    boolean isDeveloperMode() {
        return developerMode;
    }

    int getTrials() {
        return trials;
    }

    int getColors() {
        return colors;
    }

    int getCases() {
        return cases;
    }
}
