package com.openclassrooms.mpancarte;


import com.openclassrooms.mpancarte.Mastermind.MasterMind;
import com.openclassrooms.mpancarte.SecretNumber.SecretNumber;

import java.util.ArrayList;
import java.util.Random;

public class Choix {
    public int NbCase;
    public int NbEssai;
    public String ModeJoueur = "";
    public String ModeJeux = "";
    public int NbCouleurSelec;
    public int NbCaseCouleur;
    private String JeuxSelec = "";


    public void Launcher(String Mode) {
        MasterMind JeuM = new MasterMind();
        SecretNumber JeuS = new SecretNumber();
        if (this.JeuxSelec.equalsIgnoreCase("MasterMind")) {
            JeuM.Plateau(Mode);

        } else {
            JeuS.Plateau(Mode);
        }
    }

    public void ModeJeux() {
        int selection = 0;
        do {
            System.out.println("__________Selection mode de jeux __________");
            System.out.println("1- Mode challenger \n  où vous devez trouver la combinaison secrète de l'ordinateur ___________");
            System.out.println("2-Mode défenseur \n  où c'est à l'ordinateur de trouver votre combinaison secrète ______________");
            System.out.println("3-Mode duel \n l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné_______________");
            selection = Console.saisirInt();
        } while (selection < 0 || selection > 3);
        switch (selection) {
            case 1:
                this.ModeJeux = "challenger";
                System.out.println(this.JeuxSelec.equalsIgnoreCase("MasterMind"));
                Launcher(this.ModeJeux);
                break;
            case 2:
                this.ModeJeux = "defenseur";
                Launcher(this.ModeJeux);
                break;
            case 3:
                this.ModeJeux = "duel";
                System.out.println(this.JeuxSelec.equalsIgnoreCase("MasterMind"));
                Launcher(this.ModeJeux);
                break;
        }
    }
    //definition variable
    public void Menu() throws Exception {
        ReadValues Data = new ReadValues();
        this.ModeJoueur = Data.getModeJoueur();
        this.NbCaseCouleur = Integer.valueOf(Data.getNbCaseCouleur());
        this.NbCase = Integer.valueOf(Data.getNbCase());
        this.NbEssai = Integer.valueOf(Data.getNbEssai());
        this.NbCouleurSelec = Integer.valueOf(Data.getNbCouleur());
        int choix = 3;
        while (choix != 0) {
            System.out.println("\033[31m___________Accueil ___________");
            System.out.println("\033[35m1_________Nombre secret_______");
            System.out.println("\033[36m2________Mastermind___________");
            System.out.println("\033[33m0________Quitter______________");
            choix = Console.saisirInt();
            if (choix == 1) {
                this.JeuxSelec = "SecretNumber";
            }
            if (choix == 2) {

                this.JeuxSelec = "MasterMind";
            }
            if (choix < 3 && choix > 0) {
                this.ModeJeux();
            }
        }

    }


    public String ChoixJoueurSecretNumber() {
        String NbSecret = "";


        //définition du nombre secret
        boolean fait = false;
        while (fait == false) {
            System.out.println("Entrer  Le nombre secret");
            NbSecret += Console.saisirInt();
            System.out.println(NbSecret.length());
            this.NbCase = NbSecret.length();
            fait = true;
        }
        System.out.println("Developpeur".equalsIgnoreCase(this.ModeJoueur) ? "Le Nombre secret est:" + NbSecret : "Mode Joueur");
        //verification du code
        return NbSecret;

    }
/*
    public String AffichageChoixJ() {

    }

    public String ChoixCombinaison(int g) {


    }
*/
}

