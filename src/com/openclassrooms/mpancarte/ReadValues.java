package com.openclassrooms.mpancarte;

import java.io.InputStream;
import java.util.Properties;

public class ReadValues {
    protected Properties prop;
    protected InputStream input = ReadValues.class.getClassLoader().getResourceAsStream("config.properties");

    public ReadValues() throws Exception {

        prop = new Properties();

        prop.load(input);
    }

    public String getModeJoueur() {
        return prop.getProperty("ModeJoueur");
    }

    public int getNbCase() {
        return  Integer.valueOf(prop.getProperty("NbCase"));
    }

    public int getNbEssai() {
        return  Integer.valueOf(prop.getProperty("NbEssai"));
    }

    public int getNbCouleur() {
        return  Integer.valueOf(prop.getProperty("NbCouleurSelec"));
    }

    public int getNbCaseCouleur() {
        return  Integer.valueOf(prop.getProperty("NbCaseCouleur"));
    }
    // Properties file path.
    //String filePath = "src/resources/config.properties";

}
