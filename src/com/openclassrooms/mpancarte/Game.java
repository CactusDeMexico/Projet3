package com.openclassrooms.mpancarte;

import java.util.ArrayList;
import java.util.Random;

abstract class Game {
    protected ArrayList<String> AlreadyDone = new ArrayList<>();
    protected ArrayList<String> OldAnswer = new ArrayList<>();
    protected String BannedColors;
    protected ArrayList<String> AlmostFind = new ArrayList<>();
    protected ArrayList<Integer> Indication = new ArrayList<>();

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

    private String antiDuplicateString2(String Selection, String test, int Bound) {
        Random Random = new Random();
        if (Selection.contains(test)) {
            while (Selection.contains(test)) {
                test = "";
                test += Random.nextInt(Bound);
            }
        }
        return test;
    }


    private String antiDuplicateChar(String Selection) {
        int count = 3;
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
                StringBuilder SelectionBuilder = new StringBuilder(Selection);
                while (SelectionBuilder.toString().equals("")) {
                    if (SelectionBuilder.toString().equals("")) {
                        System.out.println("Veuillez ne pas entrer les memes chiffres ");
                        SelectionBuilder.append(ConsoleUtils.saisirInt());
                    }
                }

                Selection = SelectionBuilder.toString();
            }
        }
        return Selection;
    }

    String selectionIA(String GameMode, int NbCase, Boolean PlayerMode) {
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

        //System.out.println(PlayerMode ? "La combinaison secrete est:" + Selection : "Mode Joueur");
        return Selection;
    }

    String selectionPlayer(String ModeJeux, Boolean PlayerMode) {
        String Selection = "";
        boolean Done = false;
        while (!Done) {
            String nb = "";
            System.out.println("Entrer  La combinaison ");
            //nb += Integer.toString(ConsoleUtils.saisirInt());
            nb = ConsoleUtils.inputStringNumber();
            //System.out.println("Entrer  La combinaison " + nb);

            if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                nb = antiDuplicateChar(nb);
            }
            Done = true;
            Selection = nb;
        }

        //System.out.println(PlayerMode ? "La selection  est:" + Selection : "Mode Joueur");
        return Selection;
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

    protected String lastAnswer() {
        String previousData = "";
        for (int z = 0; z < this.AlreadyDone.size(); z++) {
            previousData += this.AlreadyDone.get(z)+",";
        }
        return previousData;
    }
    String answerIA(String GameMode, int NbCase, int PerformedTest, String Indication, String LastAnswer) throws Exception {
        String Answer = "";
        String test = "";
        SecretNumber Jeux = new SecretNumber();
        MasterMind Jeux2 = new MasterMind();
        String AnswerToCheck;

        System.out.println(" GameMode " + GameMode + " NbCase " + NbCase + " Indication " + Indication + " Lastanswer " + LastAnswer);
        String last=lastAnswer();
        System.out.println(last+"__________________________");
        for (int i = 0; i < NbCase; i++) {   //1er essai de l'ordinateur
            if (GameMode.equalsIgnoreCase("MasterMind")) {
                String[] format = LastAnswer.split(",");
                int GoodColor = Integer.parseInt(String.valueOf(format[0]));
                int ColorExist = Integer.parseInt(String.valueOf(format[1]));

                AnswerToCheck = Jeux2.masterMindAnswer(Indication, GoodColor, ColorExist, PerformedTest, NbCase,last);
                if (ColorExist + GoodColor == 0 && PerformedTest>1) {
                    this.BannedColors+=last;
                    test +=this.BannedColors;
                    System.out.println("Les couleurs banni sont "+ test);
                }
                test += antiDuplicateString2(test, AnswerToCheck, Indication.length());
                //Answer += antiDuplicateChar(Answer, false);
                if (i == NbCase - 1) {
                    i = alreadyDone(i, test);
                }
            } else {
                test += Jeux.secretNumberAnswer(Indication, i, LastAnswer, PerformedTest);
            }
        }

        Answer = test;
        this.AlreadyDone.add(Answer);
        this.OldAnswer.add(Answer);
        // System.out.println("___" + this.AlreadyDone.size());
        return Answer;
    }

    String answerPlayer(String ModeJeux, int NbCase) {
        String Selection = "";
        String x = "";
        System.out.println("Entrer une combinaison de " + NbCase + " chiffres");
        x += ConsoleUtils.saisirInt();
        if (x.length() == NbCase) {
            Selection = x;
        } else {
            while (Selection.length() != NbCase) {
                x = "";
                x = ConsoleUtils.inputStringNumber();
                if (ModeJeux.equalsIgnoreCase("MasterMind")) {
                    while (!x.equals(antiDuplicateString(Selection, x))) {
                        x = antiDuplicateChar(x);
                        System.out.println("Veuillez ne pas entrer le meme");
                    }
                }
                Selection += x;
            }
        }
        System.out.println(Selection + "_____________");
        return Selection;
    }
}

