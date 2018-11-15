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


    private String checkAnswer(String NbSecret, int length, String reponse) {
        StringBuilder indice = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (valueOf(NbSecret.charAt(i)).equals(valueOf(reponse.charAt(i)))) {
                indice.append("=");
            } else if (valueOf(NbSecret.charAt(i)) < valueOf(reponse.charAt(i))) {
                indice.append("-");
            } else {
                indice.append("+");
            }
        }
        return indice.toString();
    }

    private int iA(String NbSecret, String reponse, String indice, int Essai, String Player) throws Exception {
        ReadValues Data = new ReadValues();
        if (NbSecret.contentEquals(reponse)) {
            System.out.println("Gagné la Reponse est " + reponse + "C'est l" + Player + "qui gagne");
            Essai = Data.getNbEssai();
        } else {
            System.out.println("Raté la Reponse du "+Player+"est " + reponse + " il reste " + (Data.getNbEssai() - Essai - 1));
        }
        System.out.println("indice" + indice);
        return Essai;
    }

    private int playerTurn(String SecretNumber, int Essai,String ModeJoueur) throws Exception {
        ReadValues Data = new ReadValues();
        System.out.println("Developpeur".equalsIgnoreCase(ModeJoueur) ? "\033[36m Le Nombre secret  de l'ordinateur est: " + SecretNumber : "\033[36m C'est votre tour");
        String Anwser = this.answerPlayer("SecretNumber", Data.getNbCase());
        String Indication = checkAnswer(SecretNumber, Data.getNbCase(), Anwser);
        return iA(SecretNumber, Anwser, Indication, Essai, "e Joueur");
    }

    private int computerTurn(String SecretNumber, int Essai,String ModeJoueur) throws Exception {
        System.out.println("Developpeur".equalsIgnoreCase(ModeJoueur) ? "\033[33m Le Nombre secret  du Joueur est: " + SecretNumber : "\033[33m C'est le tour de l'ordinateur:" );
        int length = SecretNumber.length();
        String Anwser = this.LastAnswer = this.answerIA("SecretNumber", length, Essai, Indication, this.LastAnswer);
        Indication = checkAnswer(SecretNumber, length, Anwser);
        return iA(SecretNumber, Anwser, Indication, Essai, "'ordinateur");
    }

    void Plateau(String Mode) throws Exception {
        ReadValues Data = new ReadValues();
        String SecretNumber;
        switch (Mode) {
            case "challenger":
                int Essai = 0;
                SecretNumber = selectionIA("SecretNumber", Data.getNbCase(), Data.getModeJoueur());
                while (Essai < Data.getNbEssai()) {
                    Essai = playerTurn(SecretNumber, Essai,Data.getModeJoueur());
                    Essai++;
                }
                break;
            case "defenseur":
                Essai = 0;
                SecretNumber = this.selectionPlayer("SecretNumber", Data.getModeJoueur());
                while (Essai < Data.getNbEssai()) {
                    Essai = computerTurn(SecretNumber, Essai,Data.getModeJoueur());
                    Essai++;
                }
                break;
            case "duel":
                Essai = 0;
                String SecretNumberPlayer = this.selectionPlayer("SecretNumber", Data.getModeJoueur());
                SecretNumber = selectionIA("SecretNumber", Data.getNbCase(), Data.getModeJoueur());
                while (Essai < Data.getNbEssai()) {
                    while (!(PcTurn && PlayerTurn)||Essai== Data.getNbEssai()) {
                        if (ComputerTurn) {
                            Essai = computerTurn(SecretNumberPlayer, Essai,Data.getModeJoueur());
                            ComputerTurn = false;
                            PcTurn = true;
                        } else {
                            Essai = playerTurn(SecretNumber, Essai,Data.getModeJoueur());
                            ComputerTurn = true;
                            PlayerTurn = true;
                        }
                    }
                    Essai++;
                    PcTurn = PlayerTurn = false;
                }
                break;
        }
    }
}
