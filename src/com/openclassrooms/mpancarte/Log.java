package com.openclassrooms.mpancarte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Log {

    private static final Logger logger = LogManager.getLogger(Log.class);



    protected void setInfo(boolean gameMode, String selectedGame, String combinaison,String player,String mode) {
        logger.trace("Debut");
        if (gameMode) {
            logger.info("Mode dev");
        } else {
            logger.info("Mode joueur");
        }
        logger.info("  Jeux selectionné :" + selectedGame);
        logger.info(" Mode de Jeux selectionné :" + mode);

        logger.info(" c'est le "+player+" qui choisi");
        logger.info("Combinaisaon à trouver :" + combinaison);
    }
    protected void result(String answer,String secretNumber,int trials,String player){
        if (secretNumber.equals(answer)) {
            logger.info(player+" propose  " + answer + " et il gagne ");
        } else {
            logger.info(player+" reponse  proposé" + answer + " et il perd il reste  "+trials+" essais");
        }

    }
}