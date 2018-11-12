package com.openclassrooms.mpancarte;

import java.io.InputStream;
import java.util.Properties;

public class ReadValues {
    protected Properties prop;
    protected InputStream input = ReadValues.class.getClassLoader().getResourceAsStream("config.properties");

    public ReadValues() throws Exception{

        prop = new Properties();

        prop.load(input);
    }
    public String getModeJoueur() {
        return prop.getProperty("ModeJoueur");
    }
    public String getNbCase() {
        return prop.getProperty("NbCase");
    }
    public String getNbEssai() { return prop.getProperty("NbEssai");}
    public String getNbCouleur(){ return  prop.getProperty("NbCouleurSelec");}
    public String getNbCaseCouleur(){ return  prop.getProperty("NbCaseCouleur");}
    // Properties file path.
    //String filePath = "src/resources/config.properties";

}
