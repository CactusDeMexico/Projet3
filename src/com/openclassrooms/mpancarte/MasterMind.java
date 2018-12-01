package com.openclassrooms.mpancarte;

import java.util.Random;

class MasterMind extends Game {

    private String color[] = {"Bleu", "Rouge", "Vert", "Jaune", "Orange", "Marron", "Blanc", "Violet", "Cyan", "Noir"};
    private Random random = new Random();
    private boolean PcTurn = false;
    private boolean PlayerTurn = false;
    private boolean ComputerTurn = random.nextBoolean();
    private String Clue;

    void printColors() {
        System.out.println("0:" + this.color[0] + " 1: " + this.color[1] + " 2: " + this.color[2] + " 3: " + this.color[3] + " 4: " + this.color[4] + " 5: " + this.color[5] + " 6: " + this.color[6] + " 7: " + this.color[7] + " 8: " + this.color[8] + " 9: " + this.color[9] + " 10:FIN");
    }

    private String printSelectedColors(String selection) {
        int h = 0;
        StringBuilder selectionBuilder = new StringBuilder(selection);
        while (h < selection.length()) {
            selectionBuilder.append(h).append(": ").append(this.color[Character.getNumericValue(selectionBuilder.charAt(h))]).append(" ");
            h++;
        }
        selection = selectionBuilder.toString();
        return selection;
    }

    private String antiDuplicate(String selection, String test, int max) {
        Random random = new Random();
        if (selection.contains(test)) {
            while (selection.contains(test)) {
                test = "";
                test += random.nextInt(max);
            }
        }
        return test;
    }
/*
    String iAFindColors(int goodColor, int colorExist, int nbCase, String last) {
        String selectedColor = "";
        if (colorExist == nbCase || (goodColor + colorExist) == nbCase) {// recherche precise
            String lastAnswers[] = last.split(",");
            selectedColor = lastAnswers[lastAnswers.length - 1];
            System.out.println("toutes les couleur on été trouvé voici les BONNES COULEURS IA " + selectedColor);
        }
        return selectedColor;
    }*/

    String iAFoundedColors(int colorExist, int nbCase, String last, int existColors) {
        String lastAnswers[] = last.split(",");
        String answer;
        StringBuilder selection = new StringBuilder();
        String bestAnswer;
        int count = 0;
        int charSelected = 0;
        String banned = "_";
        for (int i = 0; i < nbCase; i++) {
            if (!(Character.toString(lastAnswers[lastAnswers.length - 2].charAt(i)).equals(Character.toString(lastAnswers[lastAnswers.length - 1].charAt(i))))) {
                count++;
                charSelected = i;
            }
        }
        System.out.println(" la couleur exisantte actu :" + colorExist + " et l'ancienne :" + this.existColor());
        if (colorExist > existColors) {
            bestAnswer = lastAnswers[lastAnswers.length - 1];
            answer = lastAnswers[lastAnswers.length - 2];
            if (count == 1) {
                selection.append(Character.toString(bestAnswer.charAt(charSelected)));
                banned = Character.toString(answer.charAt(charSelected));
            }
        } else if (colorExist < existColors) {
            answer = lastAnswers[lastAnswers.length - 1];
            bestAnswer = lastAnswers[lastAnswers.length - 2];
            if (count == 1) {
                selection.append(Character.toString(bestAnswer.charAt(charSelected)));
                banned = Character.toString(answer.charAt(charSelected));
            }
        } else if (colorExist == nbCase - 1) {
            answer = lastAnswers[lastAnswers.length - 1];
            bestAnswer = lastAnswers[lastAnswers.length - 2];
            for (int i = 0; i < nbCase; i++) {
                if ((bestAnswer.charAt(i)) == answer.charAt(i)) {
                    selection.append(Character.toString(bestAnswer.charAt(i)));
                    banned = Character.toString(answer.charAt(charSelected));
                }
            }
        } else {
            answer = lastAnswers[lastAnswers.length - 1];
            bestAnswer = lastAnswers[lastAnswers.length - 2];
            if (count == 1) {

                banned = Character.toString(answer.charAt(charSelected)) + Character.toString(bestAnswer.charAt(charSelected));
            }
        }
        return selection.toString() + "," + banned;
    }

    int[] iAFindPlacedColors(int goodColor, int performedTest, int nbCase, String last, int oldGoodColor) {
        int number = -1;
        int index = -1;
        String bestAnswer = "";
        String lastAnswers[] = last.split(",");
        System.out.println("____Good color :" + goodColor + " old good color " + oldGoodColor);
        //verifier match
        int count = 0;
        int charSelected = 0;
        if (performedTest > 1) {
            for (int i = 0; i < nbCase; i++) {
                if (!(Character.toString(lastAnswers[lastAnswers.length - 2].charAt(i)).equals(Character.toString(lastAnswers[lastAnswers.length - 1].charAt(i))))) {
                    count++;
                    charSelected = i;
                }
            }
            if (goodColor > oldGoodColor) {
                bestAnswer = lastAnswers[lastAnswers.length - 1];
                if (count == 1) {
                    number = Character.getNumericValue(bestAnswer.charAt(charSelected));
                    index = charSelected;
                }
                return new int[]{number, index};
            } else if (goodColor < oldGoodColor) {
                bestAnswer = lastAnswers[lastAnswers.length - 2];
                if (count == 1) {
                    number = Character.getNumericValue(bestAnswer.charAt(charSelected));
                    index = charSelected;
                }
            }
            System.out.println("XXX COULEUR TROUVER " + number + " index " + index);
        }
        return new int[]{number, index};
    }

    String[] iAFindWrongPlacedColors(int goodColor, int performedTest, int nbCase, String last, int oldGoodColor, int oldExistColor, int existColor) {
        String number = "_";
        String index = "_";
        String bestAnswer = "";
        String lastAnswers[] = last.split(",");

        int count = 0;
        int charSelected = 0;
        if (performedTest > 1) {
            for (int i = 0; i < nbCase; i++) {
                if (!(Character.toString(lastAnswers[lastAnswers.length - 2].charAt(i)).equals(Character.toString(lastAnswers[lastAnswers.length - 1].charAt(i))))) {
                    count++;
                    charSelected = i;
                }
            }
            if (goodColor < oldGoodColor) {
                bestAnswer = lastAnswers[lastAnswers.length - 1];
                if (count == 1) {
                    number = Character.toString(bestAnswer.charAt(charSelected));
                    index = Integer.toString(charSelected);
                }
            } else if (goodColor > oldGoodColor) {
                bestAnswer = lastAnswers[lastAnswers.length - 2];
                if (count == 1) {
                    number = Character.toString(bestAnswer.charAt(charSelected));
                    index = Integer.toString(charSelected);
                }
            }
            if (goodColor == oldGoodColor && oldExistColor == existColor) {
                bestAnswer = lastAnswers[lastAnswers.length - 2];
                String answer = lastAnswers[lastAnswers.length - 1];
                if (count == 1) {
                    number = Character.toString(bestAnswer.charAt(charSelected)) + Character.toString(answer.charAt(charSelected));
                    index = Integer.toString(charSelected);
                }
            }
        }
        return new String[]{number, index};
    }

    String masterMindAnswer(String selectedColor, String wrongAnswer, String righPlacedColor, int performedTest, int nbCase, String last, int indexChar) throws Exception {
        ConfigReader data = new ConfigReader();
        String answer = "";
        String nb ;
        String test ;
        Random random = new Random();

        int remainingTest = data.getTrials() - performedTest;
        System.out.println(performedTest+"-------------");
        if (remainingTest == data.getTrials()) {
            nb = "";
            nb += selectedColor.charAt(random.nextInt(selectedColor.length()));
            answer += nb;
        } else {
            if (performedTest > 0) {
                String oldAnwsers[] = last.split(",");
                String almost = oldAnwsers[oldAnwsers.length - 1];
                String bannedAnswer[] = wrongAnswer.split(";");
                test = "";
                    for (int i = 0; i < nbCase; i++) {
                        if (Character.toString(righPlacedColor.charAt(i)).equals("_")) {
                            if (i == indexChar) {
                                nb = "";
                                while (bannedAnswer[i].contains(nb)) {
                                    nb = "";
                                    nb += selectedColor.charAt(random.nextInt(selectedColor.length()));
                                }
                            } else {
                                nb = "";
                                nb += almost.charAt(i);
                            }
                        } else {
                            nb = "";
                            nb += (righPlacedColor.charAt(i));
                        }
                        test += nb;
                        if (test.length() == nbCase && last.contains(test)) {
                            StringBuilder newTest = new StringBuilder();
                            boolean done = false;
                            while (last.contains(test) && !done) {
                                newTest.append(test);
                                while (newTest.toString().equals(test) && !done) {
                                    newTest.delete(0, newTest.length());
                                    newTest.append(test);
                                    for (int h = 0; h < nbCase; h++) {
                                        nb = "";
                                        if (Character.toString(righPlacedColor.charAt(h)).equals("_")) {
                                            nb += selectedColor.charAt(random.nextInt(selectedColor.length()));
                                            while (bannedAnswer[h].contains(nb)) {
                                                nb = "";

                                                nb += selectedColor.charAt(random.nextInt(selectedColor.length()));
                                            }
                                            newTest.setCharAt(h, nb.charAt(0));

                                            if (!last.contains(newTest.toString())) {
                                                h = nbCase + 1;
                                                done = true;
                                            }
                                        }
                                        if (!righPlacedColor.contains("_")) {
                                            h = nbCase + 1;
                                            done = true;
                                        }
                                    }
                                    test = newTest.toString();
                                }
                            }
                        }
                    }
                    answer = test;
            }
        }
        return answer;
    }

    private String combinationSelection(String color, boolean player) {
        StringBuilder selection = new StringBuilder();
        String test = "";
        int nb;
        for (int g = 0; g < color.length(); g++) {
            if (!player) {
                nb = random.nextInt(color.length());
                test += antiDuplicate(test, String.valueOf(nb), color.length());
            } else {
                String print = printSelectedColors(color);
                System.out.println("Veuillez selectionner une couleur dans " + print + " :10 sortie");
                nb = ConsoleUtils.intInput();
                if (nb == 10) {
                    g = color.length() + 1;
                } else {
                    while (String.valueOf(nb).length() != 1 || nb >= color.length() || nb == 10) {
                        System.out.println("Veuillez selectionner une couleur  dans " + print);
                        nb = ConsoleUtils.intInput();
                    }
                   /* while (nb != Integer.parseInt(antiDuplicateString(test, String.valueOf(nb))) || nb == 10) {
                        System.out.println("Veuillez ne pas entrer la meme couleur");
                        nb = ConsoleUtils.intInput();
                    }*/
                    test += nb;
                }
            }
            if (nb < color.length()) {
                selection.append(color.charAt(Character.getNumericValue(test.charAt(g))));
            }
        }
        return selection.toString();
    }

    private static int[] color(String answer, String combination) {
        int goodColor = 0;
        int colorExist = 0;
        for (int i = 0; i < combination.length(); i++) {

            if ((answer.charAt(i)) == (combination.charAt(i))) {
                goodColor++;
            } else {
                if (combination.contains(Character.toString(answer.charAt(i)))) {
                    colorExist++;
                }
            }
        }
        return new int[]{goodColor, colorExist};
    }

    private int iA(String combination, String answer, int goodColor, int colorExist, int trials, String player) throws Exception {
        ConfigReader Data = new ConfigReader();
        if (combination.contentEquals(answer)) {
            System.out.println("Gagné la Reponse est " + answer + " C'est l" + player + " qui gagne");
            trials = Data.getTrials();
        } else {
            System.out.println("Raté la Reponse du " + player + " est " + answer + " il reste " + (Data.getTrials() - trials - 1));
        }
        System.out.println("Il y a :" + goodColor + " bien placé et : " + colorExist + " couleur existantes");
        return trials;
    }

    private int playerTurn(String combination, int trials, boolean gamerMode) throws Exception {
        ConfigReader Data = new ConfigReader();
        System.out.println(gamerMode ? "\033[36m Le Nombre secret  de l'ordinateur est: " + combination : "\033[36m   C'est votre tour");
        String Answer = answerPlayer("MasterMind", Data.getColors());
        int Indication[] = color(Answer, combination);
        int GoodColor = Indication[0];
        int ColorExist = Indication[1];
        return iA(combination, Answer, GoodColor, ColorExist, trials, "e joueur");
    }

    private int computerTurn(String combination, String selectedColor, int trials, boolean gamerMode) throws Exception {
        int goodColor = 0;
        int colorExist = 0;
        String answer;
        int length;
        length = combination.length();
        System.out.println(gamerMode ? "\033[33m La combinaison  du Joueur est: " + combination : "\033[33m C'est le tour de l'ordinateur:");
        if (trials == 0) {
            answer = answerIA("MasterMind", length, trials, selectedColor, "0,0");

            int Indication[] = color(answer, combination);
            goodColor = Indication[0];
            colorExist = Indication[1];
            this.Clue = Integer.toString(goodColor) + "," + Integer.toString(colorExist);
            trials = iA(combination, answer, goodColor, colorExist, trials, "ordinateur");
        } else {
            System.out.println(this.Clue + " LINDICATION");
            answer = answerIA("MasterMind", length, trials, selectedColor, this.Clue);
            int Indic[] = color(answer, combination);
            goodColor = Indic[0];
            colorExist = Indic[1];
            this.Clue = Integer.toString(goodColor) + "," + Integer.toString(colorExist);
            trials = iA(combination, answer, goodColor, colorExist, trials, "ordinateur");
        }

        return trials;
    }

    void boardGame(String mode) throws Exception {
        ConfigReader data = new ConfigReader();
        int trials = 0;
        String combination;
        String selectedColor;
        switch (mode) {
            case "challenger":
                selectedColor = selectionIA("MasterMind", data.getColors());
                combination = combinationSelection(selectedColor, false);
                while (trials < data.getTrials()) {
                    trials = playerTurn(combination, trials, data.isDeveloperMode());
                    trials++;
                }
                break;
            case "defenseur":
                selectedColor = selectionPlayer("MasterMind");
                combination = combinationSelection(selectedColor, true);
                while (trials < data.getTrials()) {
                    trials = computerTurn(combination, selectedColor, trials, data.isDeveloperMode());
                    trials++;
                }
                break;
            case "duel":

                String selectedColorPlayer = selectionPlayer("MasterMind");
                String combinaisonPlayer = combinationSelection(selectedColorPlayer, true);
                selectedColor = selectionIA("MasterMind", data.getColors());
                combination = combinationSelection(selectedColor, false);

                while (data.getTrials() > trials) {
                    while (!(PcTurn && PlayerTurn) || trials < data.getTrials()) {
                        if (ComputerTurn && trials < data.getTrials()) {
                            trials = computerTurn(combinaisonPlayer, selectedColorPlayer, trials, data.isDeveloperMode());
                            ComputerTurn = false;
                            PcTurn = true;
                        }
                        if (!ComputerTurn && trials < data.getTrials()) {
                            trials = playerTurn(combination, trials, data.isDeveloperMode());
                            ComputerTurn = true;
                            PlayerTurn = true;
                        }
                    }
                    trials++;
                    PcTurn = PlayerTurn = false;
                }
                break;
        }
    }
}
