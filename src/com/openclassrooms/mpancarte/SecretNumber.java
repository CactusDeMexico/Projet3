package com.openclassrooms.mpancarte;

import java.util.Random;

import static java.lang.Integer.valueOf;

class SecretNumber extends Game {

    private String LastAnswer;
    private String Indication = "";
    private Random random = new Random();
    private boolean PcTurn = false;
    private boolean PlayerTurn = false;
    private boolean ComputerTurn = random.nextBoolean();

    protected String secretNumberAnswer(String indication, int i, String test, int PerformedTest) throws Exception {
        ConfigReader Data = new ConfigReader();
        String Selection = "";
        Random Random = new Random();
        StringBuilder PreviousData = new StringBuilder();
        int RemainingTest = Data.getTrials() - PerformedTest;

        if (RemainingTest == Data.getTrials()) {
            Selection += valueOf(Random.nextInt(10));
        } else {
            for (int z = 0; z < this.OldAnswer.size(); z++) {
                String Char = "";
                Char += this.OldAnswer.get(z);
                if (z < Data.getCases()) {
                    PreviousData.append(Char.charAt(i));
                }
            }

            switch (Character.toString(indication.charAt(i))) {
                case "=":
                    Selection += Character.getNumericValue(valueOf(test.charAt(i)));
                    break;
                case "-":
                    Selection += Random.nextInt(Character.getNumericValue(valueOf(test.charAt(i))));
                    Selection = antiDuplicateString(PreviousData.toString(), Selection);
                    break;
                case "+":
                    Selection += Random.nextInt(10 - Character.getNumericValue(valueOf(test.charAt(i))));
                    Selection = antiDuplicateString(PreviousData.toString(), Selection);
                    break;
            }
        }

        return Selection;
    }
    private String checkAnswer(String NbSecret, int length, String Answer) {
        StringBuilder indice = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (valueOf(NbSecret.charAt(i)).equals(valueOf(Answer.charAt(i)))) {
                indice.append("=");
            } else if (valueOf(NbSecret.charAt(i)) < valueOf(Answer.charAt(i))) {
                indice.append("-");
            } else {
                indice.append("+");
            }
        }
        return indice.toString();
    }

    private int iA(String NbSecret, String Answer, String indication, int Try, String Player) throws Exception {
        ConfigReader Data = new ConfigReader();
        if (NbSecret.contentEquals(Answer)) {
            System.out.println("Gagné la Reponse est " + Answer + "C'est l" + Player + "qui gagne");
            Try = Data.getTrials();
        } else {
            System.out.println("Raté la Reponse du " + Player + "est " + Answer + " il reste " + (Data.getTrials() - Try - 1));
        }
        System.out.println("indication" + indication);
        return Try;
    }

    private int playerTurn(String SecretNumber, int Try, boolean GamerMode) throws Exception {
        ConfigReader Data = new ConfigReader();
        System.out.println(GamerMode ? "\033[36m Le Nombre secret  de l'ordinateur est: " + SecretNumber : "\033[36m C'est votre tour");
        String Answer = this.answerPlayer("SecretNumber", Data.getCases());

        String Indication = checkAnswer(SecretNumber, Data.getCases(), Answer);
        return iA(SecretNumber, Answer, Indication, Try, "e Joueur");
    }

    private int computerTurn(String SecretNumber, int Try, boolean GameMode) throws Exception {
        System.out.println(GameMode ? "\033[33m Le Nombre secret  du Joueur est: " + SecretNumber : "\033[33m C'est le tour de l'ordinateur:");
        int length = SecretNumber.length();
        String Answer = this.LastAnswer = this.answerIA("SecretNumber", length, Try, Indication, this.LastAnswer);
        Indication = checkAnswer(SecretNumber, length, Answer);
        return iA(SecretNumber, Answer, Indication, Try, "'ordinateur");
    }

    void boardGame(String Mode) throws Exception {
        ConfigReader Data = new ConfigReader();
        String SecretNumber;
        int trials = 0;
        switch (Mode) {
            case "challenger":
                SecretNumber = selectionIA("SecretNumber", Data.getCases(), Data.isDeveloperMode());
                while (trials < Data.getTrials()) {
                    trials = playerTurn(SecretNumber, trials, Data.isDeveloperMode());
                    trials++;
                }
                break;
            case "defenseur":
                SecretNumber = this.selectionPlayer("SecretNumber", Data.isDeveloperMode());
                while (trials < Data.getTrials()) {
                    trials = computerTurn(SecretNumber, trials, Data.isDeveloperMode());
                    trials++;
                }
                break;
            case "duel":
                String SecretNumberPlayer = this.selectionPlayer("SecretNumber", Data.isDeveloperMode());
                SecretNumber = selectionIA("SecretNumber", Data.getCases(), Data.isDeveloperMode());
                while (trials < Data.getTrials()) {
                    while (!(PcTurn && PlayerTurn) || trials < Data.getTrials()) {
                        if (ComputerTurn && trials < Data.getTrials()) {
                            trials = computerTurn(SecretNumberPlayer, trials, Data.isDeveloperMode());
                            ComputerTurn = false;
                            PcTurn = true;
                        }
                        if (!ComputerTurn && trials < Data.getTrials()) {
                            trials = playerTurn(SecretNumber, trials, Data.isDeveloperMode());
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
