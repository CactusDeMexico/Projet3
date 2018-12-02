package com.openclassrooms.mpancarte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Log {

    private static final Logger logger = LogManager.getLogger(Log.class);

    public void runMe() {

        logger.debug("msg de debogage");

        logger.info("msg d'information");

        logger.error("msg d'erreur");

        logger.fatal("msg d'erreur fatale");
    }

    protected void resul2t(String gameMode, String selectedGame, String mode, String player, String combinaison, String answer, boolean win) {
        logger.trace("Debut");
        logger.info("Mode joueur =" + gameMode);
        logger.info(" Mode de Jeux selectionné :" + selectedGame);
        logger.info("Mode selectionné =" + mode);
        if (!mode.equals("duel")) {
            logger.info(" c'est le  :" + player + " qui joue");
            logger.info("Combinaisaon à trouver :" + combinaison);
            if (win) {
                logger.info(" le " + player + " propose " + answer + " et il gagne");
            } else {
                logger.info(" le " + player + " propose " + answer + " et il perd ");
            }
        } else {
            if (win) {
                logger.info(" le " + player + " propose " + answer + " et il gagne");
            } else {
                logger.info(" le " + player + " propose " + answer + " et il perd ");
            }
        }
    }

    protected void setInfo(boolean gameMode, String selectedGame, String combinaison,String player,String mode) {
        logger.trace("Debut");
        if (gameMode) {
            logger.info("Mode dev");
        } else {
            logger.info("Mode joueur");
        }
        logger.info("  Jeux selectionné :" + selectedGame);
        logger.info(" Mode de Jeux selectionné :" + mode);

        logger.info(" c'est le "+player);
        logger.info("Combinaisaon à trouver :" + combinaison);
    }
    protected void result(String answer,String secretNumber,int trials,String player){
        if (secretNumber.equals(answer)) {
            logger.info("  reponse c proposé  " + answer + " et il gagne ");
        } else {
            logger.info(" reponse  proposé" + answer + " et il perd il reste  "+trials+" essais");
        }

    }
}