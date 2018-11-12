package com.openclassrooms.mpancarte;

import com.openclassrooms.mpancarte.Mastermind.MasterMind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static Logger logger = LogManager.getLogger("Main");

    public static void main(String[] args) throws Exception {
        Choix UnChoix = new Choix();
        UnChoix.Menu();
       // UnChoix.ChoixPc();

       // logger.info("Program started");
        //logger.error("Something happened");
       // Log obj = new Log();
       // obj.runMe("Main");
       // MasterMind Jeux = new MasterMind();
        //Jeux.Final();
        //Jeux.Plateau();
        //System.out.println(Jeux.Final());
    }
}
