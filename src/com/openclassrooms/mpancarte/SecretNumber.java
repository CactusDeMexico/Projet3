package com.openclassrooms.mpancarte;

import java.util.Random;

import static java.lang.Integer.valueOf;

class SecretNumber extends Game {

    private String lastAnswer;
    private String indication = "";
    private Random random = new Random();
    private boolean pcTurn = false;
    private boolean playerTurn = false;
    private boolean computerTurn = random.nextBoolean();

    String secretNumberAnswer(String indication, int i, String test, int performedTest, String lastAnswer) throws Exception {
        ConfigReader data = new ConfigReader();
        String selection = "";
        Random random = new Random();
        int remainingTest = data.getTrials() - performedTest;

        if (remainingTest == data.getTrials()) {
            selection += valueOf(random.nextInt(10));
        } else {

            switch (Character.toString(indication.charAt(i))) {
                case "=":
                    selection += Character.getNumericValue(valueOf(test.charAt(i)));
                    break;
                case "-":
                    selection += random.nextInt(Character.getNumericValue(valueOf(test.charAt(i))));
                    selection = antiDuplicateChar2(lastAnswer, selection, i);
                    break;
                case "+":
                    selection += random.nextInt(10 - Character.getNumericValue(valueOf(test.charAt(i))));
                    selection = antiDuplicateChar2(lastAnswer, selection, i);
                    break;
            }
        }

        return selection;
    }

    private String checkedAnswer(String nbSecret, int length, String answer) {
        StringBuilder clue = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (valueOf(nbSecret.charAt(i)).equals(valueOf(answer.charAt(i)))) {
                clue.append("=");
            } else if (valueOf(nbSecret.charAt(i)) < valueOf(answer.charAt(i))) {
                clue.append("-");
            } else {
                clue.append("+");
            }
        }
        return clue.toString();
    }

    private int iA(String nbSecret, String answer, String indication, int trials, String player) throws Exception {
        ConfigReader data = new ConfigReader();
        if (nbSecret.contentEquals(answer)) {
            System.out.println("Gagné la Reponse est " + answer + "C'est l" + player + "qui gagne");
            trials = data.getTrials();
        } else {
            System.out.println("Raté la Reponse du " + player + "est " + answer + " il reste " + (data.getTrials() - trials - 1));
        }
        System.out.println("indication" + indication);
        return trials;
    }

    private int playerTurn(String secretNumber, int trials, boolean gamerMode) throws Exception {
        ConfigReader data = new ConfigReader();
        System.out.println(gamerMode ? "\033[36m Le Nombre secret  de l'ordinateur est: " + secretNumber : "\033[36m C'est votre tour");
        String answer = this.answerPlayer("SecretNumber", data.getCases());
        String indication = checkedAnswer(secretNumber, data.getCases(), answer);
        return iA(secretNumber, answer, indication, trials, "e Joueur");
    }

    private int computerTurn(String secretNumber, int trials, boolean gameMode) throws Exception {
        System.out.println(gameMode ? "\033[33m Le Nombre secret  du Joueur est: " + secretNumber : "\033[33m C'est le tour de l'ordinateur:");
        int length = secretNumber.length();
        String answer = this.lastAnswer = this.answerIA("SecretNumber", length, trials, indication, this.lastAnswer);
        indication = checkedAnswer(secretNumber, length, answer);
        return iA(secretNumber, answer, indication, trials, "'ordinateur");
    }

    void boardGame(String mode) throws Exception {
        ConfigReader data = new ConfigReader();
        String secretNumber;
        int trials = 0;
        switch (mode) {
            case "challenger":
                secretNumber = selectionIA("SecretNumber", data.getCases());
                while (trials < data.getTrials()) {
                    trials = playerTurn(secretNumber, trials, data.isDeveloperMode());
                    trials++;
                }
                break;
            case "defenseur":
                secretNumber = this.selectionPlayer("SecretNumber");
                while (trials < data.getTrials()) {
                    trials = computerTurn(secretNumber, trials, data.isDeveloperMode());
                    trials++;
                }
                break;
            case "duel":
                String secretNumberPlayer = this.selectionPlayer("SecretNumber");
                secretNumber = selectionIA("SecretNumber", data.getCases());
                while (trials < data.getTrials()) {
                    while (!(pcTurn && playerTurn) || trials < data.getTrials()) {
                        if (computerTurn && trials < data.getTrials()) {
                            trials = computerTurn(secretNumberPlayer, trials, data.isDeveloperMode());
                            computerTurn = false;
                            pcTurn = true;
                        }
                        if (!computerTurn && trials < data.getTrials()) {
                            trials = playerTurn(secretNumber, trials, data.isDeveloperMode());
                            computerTurn = true;
                            playerTurn = true;
                        }
                    }
                    trials++;
                    pcTurn = playerTurn = false;
                }
                break;
        }
    }
}