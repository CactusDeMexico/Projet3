package com.openclassrooms.mpancarte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static Logger logger = LogManager.getLogger("Main");

    public static void main(String[] args) throws Exception {
        Menu LeMenu = new Menu();
        LeMenu.Menu();
        //Game UnChoix = new Game();


       // UnChoix.ChoixPc();

       // logger.info("Program started");
        //logger.error("Something happened");
       // Log obj = new Log();
       // obj.runMe("Main");
       // MasterMind Jeux = new MasterMind();
        //Jeux.Final();
        //Jeux.boardGame();
        //System.out.println(Jeux.Final());
    }
}
