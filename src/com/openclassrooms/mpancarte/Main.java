package com.openclassrooms.mpancarte;

public class Main {
    public static boolean superUser = false;

    public boolean isSuperUser() {
        return superUser;
    }

    public static void main(String[] args) throws Exception {
        //in terminal type  java -jar Projet3.jar dev to force dev mod
        if (args.length == 0) {
        } else {
            superUser = args[0].equalsIgnoreCase("dev");
        }

        ConfigReader data = new ConfigReader();
        data.setGamerMode(superUser);
        Menu leMenu = new Menu();
        leMenu.menu();
    }
}