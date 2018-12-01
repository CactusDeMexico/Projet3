package com.openclassrooms.mpancarte;

import java.util.ArrayList;
import java.util.Random;

abstract class Game {
    private ArrayList<String> AlreadyDone = new ArrayList<>();
    private String BannedColors = "";
    private ArrayList<Integer> wellPlaced = new ArrayList<>();
    private ArrayList<Integer> existColor = new ArrayList<>();
    private int indexChar = -1;
    private String colorsFound = "";
    private ArrayList<String> rightPlaced = new ArrayList<>();
    private String [] wrongPlaced;

    //todo: comment function
    //todo: jar file
    //todo: refactor
    String antiDuplicateChar2(String selection, String test, int performedTest) {
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

    //todo:supprimer fichier config et ragder lerreur envoyer puis catch cette erreur
    private String antiDuplicateChar2(String selection, String test, String selectionedColor) {
        Random random = new Random();
        if (selection.contains(test)) {
            System.out.println("ERREur " + "test" + test + " les couleur " + selectionedColor + "la selec" + selection);
            while (selection.contains(test)) {
                test = "";
                test += selectionedColor.charAt(random.nextInt(selectionedColor.length()));
            }
        }
        return test;
    }

    private String antiDuplicateChar(String selection) {
        int count = 3;
        while (count > 1) {
            count = 0;
            for (int i = 0; i < selection.length(); i++) {
                int index = 0;
                if (count == 1) {
                    count = 0;
                }
                while (index < selection.length()) {
                    if (selection.charAt(i) == selection.charAt(index)) {
                        count++;
                    }
                    index++;
                }
            }
            if (count > 1) {
                StringBuilder selectionBuilder = new StringBuilder(selection);
                while (selectionBuilder.toString().equals("")) {
                    if (selectionBuilder.toString().equals("")) {
                        System.out.println("Veuillez ne pas entrer les memes chiffres ");
                        selectionBuilder.append(ConsoleUtils.intInput());
                    }
                }

                selection = selectionBuilder.toString();
            }
        }
        return selection;
    }

    String selectionIA(String gameMode, int nbCase) {
        String selection = "";
        Random random = new Random();
        String nb;

        for (int i = 0; i < nbCase; i++) {
            nb = "";
            nb += random.nextInt(10);
            if (gameMode.equalsIgnoreCase("MasterMind")) {
                //nb = antiDuplicateString(selection, nb);
            }
            selection += nb;
        }

        //System.out.println(playerMode ? "La combinaison secrete est:" + selection : "Mode Joueur");
        return selection;
    }

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
                // nb = antiDuplicateChar(nb);
            } else {
                System.out.println("Entrer  La combinaison ");
                nb = ConsoleUtils.inputStringNumber();
            }
            done = true;
            selection = nb;
        }
        return selection;
    }

 /*/  private int countRightAnswer(String answer) {
        int count = 0;
        String regex;
        regex = "[0-9]+";
        for (int i = 0; i < answer.length(); i++) {
            if (Character.toString(answer.charAt(i)).matches(regex)) {
                count++;
            }
        }
        return count;
    }*/

    private String lastAnswer() {
        StringBuilder previousData = new StringBuilder();

        for (String aAlreadyDone : this.AlreadyDone) {
            previousData.append(aAlreadyDone).append(",");
        }
        return previousData.toString();
    }

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

    private void foundColorsF(String colors) {

        if (!this.colorsFound.contains(colors) && colors.length() == 1) {
            this.colorsFound += colors;
        } else if (this.colorsFound.contains(colors)) {
            for (int x = 0; x < colors.length(); x++) {

                if (!this.colorsFound.contains(Character.toString(colors.charAt(x)))) {
                    this.colorsFound += colors;
                }
            }
        }
    }

    private void rightPlaceColors(String colors, int place) {

        this.rightPlaced.set(place, colors);
    }

    private void wrongPlaceColors(String colors, int place) {

        this.wrongPlaced[place]+=colors;
    }

    String answerIA(String gameMode, int nbCase, int performedTest, String indication, String lastAnswer) throws Exception {
        String answer;
        StringBuilder test = new StringBuilder();
        SecretNumber jeux = new SecretNumber();
        MasterMind jeux2 = new MasterMind();
        String answerToCheck;
        String last = lastAnswer();
        int lastColorExist;
        int lastGoodColor;
        for (int i = 0; i < nbCase; i++) {   //1er essai de l'ordinateur

            if (gameMode.equalsIgnoreCase("MasterMind")) {
                if (this.indexChar == nbCase) {
                    this.indexChar = 0;
                }
                if (performedTest == 0) {
                    this.wrongPlaced=new String[nbCase];
                    for (int u = 0; u < nbCase; u++) {
                        this.rightPlaced.add("_");
                        this.wrongPlaced[u]="_";
                    }
                }
                String[] format = lastAnswer.split(",");
                int goodColor = Integer.parseInt(String.valueOf(format[0]));
                int colorExist = Integer.parseInt(String.valueOf(format[1]));
                //todo: fonction intit
                if (performedTest > 1 && i == 0) {
                    lastColorExist = this.existColor();
                    lastGoodColor = this.goodColor();
                    String[] colorsPrecision = (jeux2.iAFoundedColors(colorExist, nbCase, last, lastColorExist)).split(",");
                    this.foundColorsF(colorsPrecision[0]);
                    String[] wrongColors = jeux2.iAFindWrongPlacedColors(goodColor, performedTest, nbCase, last, lastGoodColor,lastColorExist,colorExist);
                    int[] rightPlaced = jeux2.iAFindPlacedColors(goodColor, performedTest, nbCase, last, lastGoodColor);
                    int rigthPlaceIndex = rightPlaced[1];
                    String wrongPlaceIndex = wrongColors[1];
                    String rightNumber = Integer.toString(rightPlaced[0]);
                    String wrongNumber = wrongColors[0];
                    if (rigthPlaceIndex >= 0) {
                        this.rightPlaceColors(rightNumber, rigthPlaceIndex);
                    }
                    if(!wrongNumber.equals("_")) {
                        this.wrongPlaceColors(wrongNumber,Integer.parseInt(wrongPlaceIndex));
                    }
                }
                StringBuilder foundedC = new StringBuilder();
                StringBuilder wrongAnswer= new StringBuilder();
                for (int u = 0; u < nbCase; u++) {
                    foundedC.append(this.rightPlaced.get(u));
                    wrongAnswer.append(this.wrongPlaced[u]).append(";");
                }
                answerToCheck = jeux2.masterMindAnswer(indication,  wrongAnswer.toString(), foundedC.toString(), performedTest, nbCase, last, this.indexChar);
                if (answerToCheck.length() != nbCase && performedTest == 0) {
                    test.append( answerToCheck);
                } else {
                    test = new StringBuilder(answerToCheck);
                    i = nbCase;
                }
                this.existColor.add(colorExist);
                this.wellPlaced.add(goodColor);
            } else {
                test.append(jeux.secretNumberAnswer(indication, i, lastAnswer, performedTest, last));
            }
        }
        indexChar++;
        answer = test.toString();
        this.AlreadyDone.add(answer);
        return answer;
    }

    String answerPlayer(String gameMode, int nbCase) {
        String selection = "";
        String x = "";
        System.out.println("Entrer une combinaison de " + nbCase + " chiffres");
        x += ConsoleUtils.intInput();
        if (x.length() == nbCase) {
            selection = x;
        } else {
            while (selection.length() != nbCase) {
                x = ConsoleUtils.inputStringNumber();
                if (gameMode.equalsIgnoreCase("MasterMind")) {
                    while (!x.equals(antiDuplicateString(selection, x))) {
                        x = antiDuplicateChar(x);
                        System.out.println("Veuillez ne pas entrer le meme");
                    }
                }
                selection += x;
            }
        }

        return selection;
    }
}

