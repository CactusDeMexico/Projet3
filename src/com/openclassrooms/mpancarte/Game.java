package com.openclassrooms.mpancarte;


import java.util.ArrayList;

import java.util.Random;

import static java.lang.Integer.valueOf;

abstract class Game {
    private ArrayList<String> AlreadyDone = new ArrayList<>();
    private ArrayList<String> OldAnswer = new ArrayList<>();


    // IA______________
    String antiDuplicateString(String Selection, String test) {
        Random Random = new Random();
        if (Selection.contains(test)) {
            while (Selection.contains(test)) {
                test = "";
                test += Random.nextInt(10);
            }
        }
        return test;
    }

    private String antiDuplicateChar(String Selection, boolean Player) {
        int count = 3;
        Random random = new Random();
        while (count > 1) {
            count = 0;
            for (int i = 0; i < Selection.length(); i++) {
                int index = 0;
                if (count == 1) {
                    count = 0;
                }
                while (index < Selection.length()) {
                    if (Selection.charAt(i) == Selection.charAt(index)) {
                        count++;
                    }
                    index++;
                }
            }
            if (count > 1) {
                Selection = "";
                if (!Player) {
                    Selection += random.nextInt();
                } else {

                    System.out.println("Veuillez ne pas entrer les memes chiffres");
                    Selection += ConsoleUtils.saisirInt();
                }
            }
        }
        return Selection;
    }

    String selectionIA(String GameMode, int NbCase, String PlayerMode) {

        String Selection = "";
        Random Random = new Random();
        String nb;

        for (int i = 0; i < NbCase; i++) {
            nb = "";
            nb += Random.nextInt(10);
            if (GameMode.equalsIgnoreCase("MasterMind")) {
                nb = antiDuplicateString(Selection, nb);
            }
            Selection += nb;
        }


        System.out.println("Developpeur".equalsIgnoreCase(PlayerMode) ? "La combinaison secrete est:" + Selection : "Mode Joueur");
        return Selection;
    }

    String selectionPlayer(String ModeJeux, String PlayerMode) {
        String Selection = "";
        boolean Done = false;
        while (!Done) {
            String nb = "";
            System.out.println("Entrer  La combinaison ");
            nb += ConsoleUtils.saisirInt();
            if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                nb = antiDuplicateChar(nb, true);
            }
            Done = true;
            Selection = nb;
        }

        System.out.println("Developpeur".equalsIgnoreCase(PlayerMode) ? "La selection  est:" + Selection : "Mode Joueur");
        // System.out.println("Developpeur".equalsIgnoreCase(PlayerMode) ? "La combinaison secrete est:" + Selection : "Mode Joueur");
        return Selection;
    }


    private String secretNumberAnswer(String indication, int i, String test, int PerformedTest) throws Exception {
        ReadValues Data = new ReadValues();
        String Selection = "";
        Random Random = new Random();
        StringBuilder PreviousData = new StringBuilder();
        int RemainingTest = Data.getNbEssai() - PerformedTest;
        if (RemainingTest == Data.getNbEssai()) {
            Selection += valueOf(Random.nextInt(10));
        } else {
            for (int z = 0; z < this.OldAnswer.size(); z++) {
                String Char = "";
                Char += this.OldAnswer.get(z);
                if (z < Data.getNbCase()) {
                    PreviousData.append(Char.charAt(i));
                }
            }
            if (Character.toString(indication.charAt(i)).equals("=")) {
                Selection += Character.getNumericValue(valueOf(test.charAt(i)));
            }
            if (Character.toString(indication.charAt(i)).equals("-")) {

                Selection += Random.nextInt(Character.getNumericValue(valueOf(test.charAt(i))));

                Selection = antiDuplicateString(PreviousData.toString(), Selection);
            }
            if (Character.toString(indication.charAt(i)).equals("+")) {

                Selection += Random.nextInt(10 - Character.getNumericValue(valueOf(test.charAt(i))));
                Selection = antiDuplicateString(PreviousData.toString(), Selection);
            }
        }

        return Selection;
    }

    private String masterMindAnswer(String SelectedColor, int GoodColor, int ColorExist, int PerformedTest, int NbCase) throws Exception {
        ReadValues Data = new ReadValues();
        String Answer = "";
        String nb;
        int phy;
        Random Random = new Random();
        int RemainingTest = Data.getNbEssai() - PerformedTest;
        if (RemainingTest == Data.getNbEssai()) { //1er Test

            nb = "";
            nb += SelectedColor.charAt(Random.nextInt(SelectedColor.length()));
            nb = antiDuplicateString(Answer, nb);

        } else {
            if (ColorExist == NbCase || (GoodColor + ColorExist) == NbCase) {// recherche precise
                phy = this.AlreadyDone.size() - 1;
                SelectedColor += this.AlreadyDone.get(phy);
            }
            nb = "";
            nb += SelectedColor.charAt(Random.nextInt(SelectedColor.length()));
            nb = antiDuplicateString(Answer, nb);
        }
        Answer += nb;
        return Answer;
    }

    private int alreadyDone(int i, String Answer) {
        String PreviousData[] = new String[this.AlreadyDone.size()];
        for (int z = 0; z < this.AlreadyDone.size(); z++) {
            PreviousData[z] += this.AlreadyDone.get(z);
            if (PreviousData[z].equals(Answer)) {
                i = -1;
            }
        }
        return i;
    }

    String answerIA(String GameMode, int NbCase, int PerformedTest, String Indication, String LastAnswer) throws Exception {
        String Answer = "";
        for (int i = 0; i < NbCase; i++) {   //1er essai de l'ordinateur
            if (GameMode.equalsIgnoreCase("MasterMind")) {

                String[] format = LastAnswer.split(",");
                int GoodColor = Integer.parseInt(String.valueOf(format[0]));
                int ColorExist = Integer.parseInt(String.valueOf(format[1]));
                Answer += masterMindAnswer(Indication, GoodColor, ColorExist, PerformedTest, NbCase);
                Answer = antiDuplicateChar(Answer, false);
                i = alreadyDone(i, Answer);
            } else {
                Answer += secretNumberAnswer(Indication, i, LastAnswer, PerformedTest);
            }
        }
        this.AlreadyDone.add(Answer);
        this.OldAnswer.add(Answer);
        return Answer;
    }

    String answerPlayer(String ModeJeux, int NbCase) {
        String Selection = "";
        String x = "";
        for (int i = 0; i < NbCase; i++) {   //1er essai de l'ordinateur
            System.out.println("Entrer une combinaison");
            x += ConsoleUtils.saisirInt();
            System.out.println(x.length() + " " + NbCase);
            System.out.println(x.length() == NbCase);
            if (x.length() == NbCase) {
                Selection = x;
                i = NbCase + 1;
            }
            while (x.length() != NbCase) {
                x = "";
                x += ConsoleUtils.saisirInt();
                System.out.println(x);
                if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                    while (!x.equals(antiDuplicateString(Selection, x))) {
                        x = antiDuplicateChar(x, true);
                        System.out.println("Veuillez ne pas entrer le meme");
                    }
                }
            }
        }
        return Selection;
    }
}

