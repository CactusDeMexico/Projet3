package com.openclassrooms.mpancarte;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Integer.valueOf;

public abstract class Game {
    public int NbCase;
    public int NbEssai;
    public String ModeJoueur = "";
    public String ModeJeux = "";
    public int NbCouleurSelec;
    public int NbCaseCouleur;
    public ArrayList OldAnswer = new ArrayList();
    public int max[], min[];


    // IA______________
    public String Doublon(String Selection, String test) {
        Random Random = new Random();
        if (Selection.contains(test)) {
            while (Selection.contains(test)) {
                test = "";
                test += Random.nextInt(10);
            }
        }
        return test;
    }

    public String selectionIA(String ModeJeux, int NbCase, String PlayerMode) {

        String Selection = "";
        Random Random = new Random();
        String nb = "";

        for (int i = 0; i < NbCase; i++) {
            nb = "";
            nb += Random.nextInt(10);
            if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                nb = Doublon(Selection, nb);
            }
            Selection += nb;
        }


        System.out.println("Developpeur".equalsIgnoreCase(PlayerMode) ? "La combinaison secrete est:" + Selection : "Mode Joueur");
        return Selection;
    }

    public String selectionPlayer(String ModeJeux, String PlayerMode) {
        String Selection = "";

        boolean fait = false;
        while (fait == false) {
            String nb = "";
            System.out.println("Entrer  La combinaison secret");
            nb += ConsoleUtils.saisirInt();
            if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                nb = Doublon(Selection, nb);
            }
            Selection += nb;
            System.out.println(Selection.length());
            fait = true;
        }
        System.out.println("Developpeur".equalsIgnoreCase(ModeJeux) ? "Le Nombre secret est:" + Selection : "Mode Joueur");
       // System.out.println("Developpeur".equalsIgnoreCase(PlayerMode) ? "La combinaison secrete est:" + Selection : "Mode Joueur");
        return Selection;
    }

    public static int getMaxValue(int[] array) {
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    // getting the miniumum value
    public static int getMinValue(int[] array) {
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    public String secretNumberAnswer(String indication, int i, String test, int PerformedTest) throws Exception {
        //this.min et max
        ReadValues Data = new ReadValues();
        String reponse = "";
        Random Random = new Random();
        String PreviousData = "";
        this.max = new int[Data.getNbEssai()];
        this.min = new int[Data.getNbEssai()];
        int[] min = new int[this.OldAnswer.size()];
        int RemainingTest = Data.getNbEssai() - PerformedTest;
        if (RemainingTest == Data.getNbEssai()) {
            reponse += valueOf(Random.nextInt(10));
        } else {

            for (int z = 0; z < this.OldAnswer.size(); z++) {
                String cc = "";
                cc += this.OldAnswer.get(z);
                min[z] = Character.getNumericValue(cc.charAt(i));
                if (z < Data.getNbCase()) {
                    PreviousData = (String) (PreviousData + cc.charAt(i));
                }
            }
            Arrays.sort(min);
            if (Character.toString(indication.charAt(i)).equals("=")) {
                reponse += Character.getNumericValue(valueOf(test.charAt(i)));
            }
            if (Character.toString(indication.charAt(i)).equals("-")) {

                reponse += Random.nextInt(Character.getNumericValue(valueOf(test.charAt(i))) - 0);

                reponse = Doublon(PreviousData, reponse);
            }
            if (Character.toString(indication.charAt(i)).equals("+")) {

                reponse += Random.nextInt(10 - Character.getNumericValue(valueOf(test.charAt(i))));
                reponse = Doublon(PreviousData, reponse);
            }
/*
            if (Character.toString(indication.charAt(i)).equals("=")) {
                reponse += Character.getNumericValue(valueOf(test.charAt(i)));
            }
            if (Character.toString(indication.charAt(i)).equals("-")) {
                this.max[i] = Character.getNumericValue(PreviousData.charAt(i));
                reponse += Random.nextInt(this.max[i] - 0);

                reponse=Doublon(PreviousData,reponse);
            }
            if (Character.toString(indication.charAt(i)).equals("+")) {
                this.min[i] = Character.getNumericValue(PreviousData.charAt(i));
                reponse += Random.nextInt(10 - this.min[i]);
                reponse=Doublon(PreviousData,reponse);
            }         System.out.println("LE Min "+this.max[i]+"LE max "+this.min[i]);
            System.out.println("ancienne valeur donné"+PreviousData);*/
        }

        return reponse;
    }

    public String masterMindAnswer() {
        String reponse = "";
        Random Random = new Random();
        return reponse;
    }

    public String answerIA(String ModeJeux, int NbCase, int PerformedTest, String Indication, String LastAnswer) throws Exception {
        //this.ModeJoueur = "Developpeur";
        ArrayList NombreSecret = new ArrayList();
        String AntiDuplicate = "";
        Random Random = new Random();
        String reponse = "";
        ReadValues Data = new ReadValues();
        SecretNumber SC = new SecretNumber();
        for (int i = 0; i < NbCase; i++) {   //1er essai de l'ordinateur
            if (ModeJeux.equalsIgnoreCase("MasterMind")) {

            } else {
                reponse += secretNumberAnswer(Indication, i, LastAnswer, PerformedTest);
            }

        }
        this.OldAnswer.add(reponse);
        return reponse;
    }

    public String answerPlayer(String ModeJeux, int NbCase) throws Exception {
        String reponse = "";
        String x = "";

        for (int i = 0; i < NbCase; i++) {   //1er essai de l'ordinateur
            if (ModeJeux.equalsIgnoreCase("MasterMind")) {

            } else {

                System.out.println("Entrer un chiffre");
                x = "";
                while (x.length() != 1) {
                    x += ConsoleUtils.saisirInt();
                    if (x.length() > 1) {
                        x = "";
                        System.out.println("Entrer un SEUL  chiffre");
                    }
                }
                reponse += x;
            }

        }
        return reponse;
    }
}

