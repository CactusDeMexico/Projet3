
package com.openclassrooms.mpancarte;

class Menu {
    private String gameSelected = "";

    private void launcher(String mode) throws Exception {
        MasterMind gameM = new MasterMind();
        SecretNumber gameS = new SecretNumber();
        if (this.gameSelected.equalsIgnoreCase("MasterMind")) {
            gameM.boardGame(mode);
        } else {
            gameS.boardGame(mode);
        }
    }

    private void gameMode() throws Exception {
        int selection;
        do {
            System.out.println("__________Selection mode de jeux __________");
            System.out.println("1- Mode challenger \n  où vous devez trouver la combinaison secrète de l'ordinateur ___________");
            System.out.println("2-Mode défenseur \n  où c'est à l'ordinateur de trouver votre combinaison secrète ______________");
            System.out.println("3-Mode duel \n l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné_______________");
            selection = ConsoleUtils.intInput();
        } while (selection < 0 || selection > 3);
        switch (selection) {
            case 1:
                String gameMode = "challenger";

                launcher(gameMode);
                break;
            case 2:
                gameMode = "defenseur";
                launcher(gameMode);
                break;
            case 3:
                gameMode = "duel";

                launcher(gameMode);
                break;
        }
    }

    //definition variable
    void menu() throws Exception {
        int choix = 3;
        while (choix != 0) {
            System.out.println("\033[31m___________Accueil ___________");
            System.out.println("\033[35m1_________Nombre secret_______");
            System.out.println("\033[36m2________Mastermind___________");
            System.out.println("\033[33m0________Quitter______________");
            choix = ConsoleUtils.intInput();
            if (choix == 1) {
                this.gameSelected = "SecretNumber";
            }
            if (choix == 2) {

                this.gameSelected = "MasterMind";
            }
            if (choix < 3 && choix > 0) {
                this.gameMode();
            }
        }
    }
}
