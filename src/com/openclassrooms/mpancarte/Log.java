package com.openclassrooms.mpancarte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    //public static Logger logger = LogManager.getLogger(Log.class);
    private static final Logger logger = LogManager.getLogger(Log.class);


    public void runMe() {

        logger.debug("msg de debogage");

        logger.info("msg d'information");

        logger.error("msg d'erreur");

        logger.fatal("msg d'erreur fatale");
    }



}