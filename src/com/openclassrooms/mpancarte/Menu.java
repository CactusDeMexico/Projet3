package com.openclassrooms.mpancarte;

public class Menu {

    public String GameMode = "";

    private String GameSelected = "";


    public void Launcher(String Mode) throws Exception{
        MasterMind JeuM = new MasterMind();
        SecretNumber JeuS = new SecretNumber();
        if (this.GameSelected.equalsIgnoreCase("MasterMind")) {
            JeuM.Plateau(Mode);

        } else {
            JeuS.Plateau(Mode);
        }
    }

    public void ModeJeux()throws Exception {
        int selection = 0;
        do {
            System.out.println("__________Selection mode de jeux __________");
            System.out.println("1- Mode challenger \n  où vous devez trouver la combinaison secrète de l'ordinateur ___________");
            System.out.println("2-Mode défenseur \n  où c'est à l'ordinateur de trouver votre combinaison secrète ______________");
            System.out.println("3-Mode duel \n l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné_______________");
            selection = ConsoleUtils.saisirInt();
        } while (selection < 0 || selection > 3);
        switch (selection) {
            case 1:
                this.GameMode = "challenger";

                Launcher(this.GameMode);
                break;
            case 2:
                this.GameMode = "defenseur";
                Launcher(this.GameMode);
                break;
            case 3:
                this.GameMode = "duel";

                Launcher(this.GameMode);
                break;
        }
    }

    //definition variable
    public void Menu() throws Exception {
        int choix = 3;
        while (choix != 0) {
            System.out.println("\033[31m___________Accueil ___________");
            System.out.println("\033[35m1_________Nombre secret_______");
            System.out.println("\033[36m2________Mastermind___________");
            System.out.println("\033[33m0________Quitter______________");
            choix = ConsoleUtils.saisirInt();
            if (choix == 1) {
                this.GameSelected = "SecretNumber";
            }
            if (choix == 2) {

                this.GameSelected = "MasterMind";
            }
            if (choix < 3 && choix > 0) {
                this.ModeJeux();
            }
        }

    }
}
