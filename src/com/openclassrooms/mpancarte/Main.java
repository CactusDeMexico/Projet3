package com.openclassrooms.mpancarte;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {




    public static void main(String[] args) throws Exception {

        Log log= new Log();
        log.runMe();
        Menu leMenu = new Menu();
        leMenu.menu();

    }
}