package com.openclassrooms.mpancarte;

import java.util.ArrayList;
import java.util.Random;

abstract class Game {
    private ArrayList<String> AlreadyDone = new ArrayList<>();
    private ArrayList<Integer> wellPlaced = new ArrayList<>();
    private ArrayList<Integer> existColor = new ArrayList<>();
    private int indexChar = -1;
    private ArrayList<String> rightPlaced = new ArrayList<>();
    private String[] wrongPlaced;

    String antiDuplicateChar(String selection, String test, int performedTest) {
        Random random = new Random();
        String lastAnswers[] = selection.split(",");
        for (String lastAnswer : lastAnswers) {
            if (test.equals((Character.toString(lastAnswer.charAt(performedTest))))) {
                while (test.equals((Character.toString(lastAnswer.charAt(performedTest))))) {
                    test = "";
                    test += random.nextInt(10);
                }
            }
        }
        return test;
    }

    String antiDuplicateString(String selection, String test) {
        Random random = new Random();
        if (selection.contains(test)) {
            while (selection.contains(test)) {
                test = "";
                test += random.nextInt(10);
            }
        }
        return test;
    }

    //set and return combinaison pc
    String selectionIA(int nbCase, String gameMode) {
        String selection = "";
        Random random = new Random();
        String nb;

        for (int i = 0; i < nbCase; i++) {
            nb = "";
            nb += random.nextInt(10);
            if (gameMode.equalsIgnoreCase("MasterMind")) {
                nb = antiDuplicateString(selection, nb);
            }
            selection += nb;
        }

        return selection.toString();
    }

    //set and return combinaison player
    String selectionPlayer(String gameMode) {
        String selection = "";
        boolean done = false;
        while (!done) {
            String nb;
            if (gameMode.equalsIgnoreCase("MasterMind")) {
                MasterMind jeux = new MasterMind();
                System.out.println("Selectionner  les couleurs ");
                jeux.printColors();
                nb = ConsoleUtils.inputStringNumber();
            } else {
                System.out.println("Entrer  La combinaison ");
                nb = ConsoleUtils.inputStringNumber();
            }
            done = true;
            selection = nb;
        }
        return selection;
    }

    // return last answer
    private String lastAnswer() {
        StringBuilder previousData = new StringBuilder();

        for (String aAlreadyDone : this.AlreadyDone) {
            previousData.append(aAlreadyDone).append(",");
        }
        return previousData.toString();
    }

    // return last existColor
    int existColor() {
        StringBuilder previousData = new StringBuilder();
        if (this.existColor.size() == 0) {

            previousData = new StringBuilder("0");
        } else {
            for (Integer anExistColor : this.existColor) {
                previousData.append(anExistColor).append(",");
            }
        }
        String existColors[] = previousData.toString().split(",");
        return Integer.parseInt(existColors[existColors.length - 1]);
    }

    // return last goodColor
    private int goodColor() {
        StringBuilder previousData = new StringBuilder();
        if (this.wellPlaced.size() == 0) {
            previousData = new StringBuilder("0");
        } else {
            for (Integer aWellPlaced : this.wellPlaced) {
                previousData.append(aWellPlaced).append(",");
            }
        }
        String goodExist[] = previousData.toString().split(",");
        return Integer.parseInt(goodExist[goodExist.length - 1]);
    }

    // set good answer
    private void rightPlaceColors(String colors, int place) {

        this.rightPlaced.set(place, colors);
    }

    // set banned  answer
    private void wrongPlaceColors(String colors, int place) {

        this.wrongPlaced[place] += colors;
    }

    // define banned answer and good answer
    private void ia(int lastColorExist, int lastGoodColor, String lastAnswer, int performedTest, int nbCase, String last, int index) {
        if (performedTest == 0) {
            this.wrongPlaced = new String[nbCase];
            for (int u = 0; u < nbCase; u++) {
                this.rightPlaced.add("_");
                this.wrongPlaced[u] = "_";
            }
        }
        String[] format = lastAnswer.split(",");
        int goodColor = Integer.parseInt(String.valueOf(format[0]));
        int colorExist = Integer.parseInt(String.valueOf(format[1]));
        if (performedTest > 1 && index == 0) {
            MasterMind game = new MasterMind();
            String[] wrongColors = game.iAFindWrongPlacedColors(goodColor, nbCase, last, lastGoodColor, lastColorExist, colorExist);
            int[] rightPlaced = game.iAFindPlacedColors(goodColor, nbCase, last, lastGoodColor);
            int rightPlaceIndex = rightPlaced[1];
            String wrongPlaceIndex = wrongColors[1];
            String rightNumber = Integer.toString(rightPlaced[0]);
            String wrongNumber = wrongColors[0];
            if (rightPlaceIndex >= 0) {
                this.rightPlaceColors(rightNumber, rightPlaceIndex);
            }
            if (!wrongNumber.equals("_")) {
                this.wrongPlaceColors(wrongNumber, Integer.parseInt(wrongPlaceIndex));
            }
        }
        this.existColor.add(colorExist);
        this.wellPlaced.add(goodColor);
    }

    String answerIA(String gameMode, int nbCase, int performedTest, String indication, String lastAnswer) throws Exception {
        String answer;
        StringBuilder test = new StringBuilder();
        SecretNumber jeux = new SecretNumber();
        MasterMind jeux2 = new MasterMind();
        String answerToCheck;
        for (int i = 0; i < nbCase; i++) {   //1er essai de l'ordinateur

            if (gameMode.equalsIgnoreCase("MasterMind")) {
                if (this.indexChar == nbCase) {
                    this.indexChar = 0;
                }
                this.ia(this.existColor(), this.goodColor(), lastAnswer, performedTest, nbCase, lastAnswer(), i);
                StringBuilder foundedC = new StringBuilder();
                StringBuilder wrongAnswer = new StringBuilder();
                for (int u = 0; u < nbCase; u++) {
                    foundedC.append(this.rightPlaced.get(u));
                    wrongAnswer.append(this.wrongPlaced[u]).append(";");
                }
                answerToCheck = jeux2.masterMindAnswer(indication, wrongAnswer.toString(), foundedC.toString(), performedTest, nbCase, lastAnswer(), this.indexChar);
                if (answerToCheck.length() != nbCase && performedTest == 0) {
                    test.append(answerToCheck);
                } else {
                    test = new StringBuilder(answerToCheck);
                    i = nbCase;
                }
            } else {
                test.append(jeux.secretNumberAnswer(indication, i, lastAnswer, performedTest, lastAnswer()));
            }
        }
        indexChar++;
        answer = test.toString();
        this.AlreadyDone.add(answer);
        return answer;
    }

    String answerPlayer(int nbCase) {
        StringBuilder selection = new StringBuilder();
        String x = "";
        System.out.println("Entrer une combinaison de " + nbCase + " chiffres");
        x += ConsoleUtils.intInput();
        if (x.length() == nbCase) {
            selection = new StringBuilder(x);
        } else {
            while (selection.length() != nbCase) {
                x = ConsoleUtils.inputStringNumber();
                selection.append(x);
            }
        }

        return selection.toString();
    }
}

