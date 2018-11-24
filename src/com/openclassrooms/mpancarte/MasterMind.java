package com.openclassrooms.mpancarte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class MasterMind extends Game {

    //private String Color[] = {"Bleu", "Rouge", "Vert", "Jaune", "Orange", "Marron", "Blanc", "Violet", "Cyan", "Noir"};
    private Random random = new Random();
    private boolean PcTurn = false;
    private boolean PlayerTurn = false;
    private boolean ComputerTurn = random.nextBoolean();
    private String Clue;
    private String liste[] = new String[10];
    private ArrayList<String> LastAnswers = new ArrayList<>();
    //Map<Integer, String> LastAnswers = new HashMap<>();

    private String antiDuplicate(String Selection, String test, int Max) {
        Random Random = new Random();
        if (Selection.contains(test)) {
            while (Selection.contains(test)) {
                test = "";
                test += Random.nextInt(Max);
            }
        }
        return test;
    }

    protected String masterMindAnswer(String SelectedColor, int GoodColor, int ColorExist, int PerformedTest, int NbCase,String last) throws Exception {

        ConfigReader Data = new ConfigReader();
        String Answer = "";
        String nb;
        Random Random = new Random();
        int RemainingTest = Data.getTrials() - PerformedTest;
        if (RemainingTest == Data.getTrials()) { //1er Test
            nb = "";
            nb += SelectedColor.charAt(Random.nextInt(SelectedColor.length()));
        } else {

            if (ColorExist == NbCase || (GoodColor + ColorExist) == NbCase) {// recherche precise

                String phy[] =last.split(",");

                System.out.println("toutes les couleur on été trouvé " +  phy.length);
                SelectedColor = phy[ phy.length-1];
              } else if (ColorExist + GoodColor == 0) {
                String phy[] =last.split(",");

                this.BannedColors.add( phy[ phy.length-1]);
            }/*
            else if(ColorExist + GoodColor >this.Indication.get(0)){
                int phy = this.AlreadyDone.size() - 1;
                this.AlmostFind.add( this.AlreadyDone.get(phy));
                this.Indication.add(ColorExist + GoodColor);
            }
            else if (ColorExist + GoodColor > (NbCase / 2)) {
                int phy = this.AlreadyDone.size() - 1;
                this.AlmostFind.add( this.AlreadyDone.get(phy));
                this.Indication.add(ColorExist + GoodColor);
                //todo:ne changer qu'un chiffre dans array almost

            }*/

            nb = "";
            nb += SelectedColor.charAt(Random.nextInt(SelectedColor.length()));
        }
        Answer += nb;
        return Answer;
    }

    private String combinationSelection(String Couleur, boolean Player) {
        StringBuilder selection = new StringBuilder();
        String test = "";
        int nb;
        for (int g = 0; g < Couleur.length(); g++) {
            if (!Player) {
                nb = random.nextInt(Couleur.length());
                test += antiDuplicate(test, String.valueOf(nb), Couleur.length());
            } else {
                System.out.println("Veuillez selectionner un chiffre dans " + Couleur);
                nb = ConsoleUtils.saisirInt();

                while (String.valueOf(nb).length() != 1 || nb >= Couleur.length()) {
                    System.out.println("Veuillez selectionner un chiffre dans " + Couleur);
                    nb = ConsoleUtils.saisirInt();
                }
                while (nb != Integer.parseInt(antiDuplicateString(test, String.valueOf(nb)))) {
                    System.out.println("Veuillez ne pas entrer le meme");
                }
                test += nb;
            }
            selection.append(Couleur.charAt(Character.getNumericValue(test.charAt(g))));
        }
        return selection.toString();
    }

    private static int[] color(String Answer, String Combination) {
        int GoodColor = 0;
        int ColorExist = 0;
        for (int i = 0; i < Combination.length(); i++) {
            if ((Answer.charAt(i)) ==
                    (Combination.charAt(i))) {
                GoodColor++;
            } else {
                if (Combination.contains(Character.toString(Answer.charAt(i)))) {
                    ColorExist++;
                }
            }
        }
        return new int[]{GoodColor, ColorExist};
    }

    private int iA(String Combination, String Answer, int GoodColor, int ColorExist, int Essai, String Player) throws Exception {
        ConfigReader Data = new ConfigReader();
        if (Combination.contentEquals(Answer)) {
            System.out.println("Gagné la Reponse est " + Answer + " C'est l" + Player + " qui gagne");
            Essai = Data.getTrials();
        } else {
            System.out.println("Raté la Reponse du " + Player + " est " + Answer + " il reste " + (Data.getTrials() - Essai - 1));
        }
        System.out.println("Il y a :" + GoodColor + " bien placé et : " + ColorExist + " couleur existantes");
        return Essai;
    }

    private int playerTurn(String Combination, int Essai, boolean ModeJoueur) throws Exception {
        ConfigReader Data = new ConfigReader();
        System.out.println(ModeJoueur ? "\033[36m Le Nombre secret  de l'ordinateur est: " + Combination : "\033[36m   C'est votre tour");
        String Answer = answerPlayer("MasterMind", Data.getColors());
        int Indication[] = color(Answer, Combination);
        int GoodColor = Indication[0];
        int ColorExist = Indication[1];
        return iA(Combination, Answer, GoodColor, ColorExist, Essai, "e joueur");
    }

    private int computerTurn(String Combination, String SelectedColor, int trials, boolean ModeJoueur) throws Exception {
        int goodColor = 0;
        int colorExist = 0;
        String answer;
        int length;
        length = Combination.length();
        if (trials > 0) {
            System.out.println("this.liste" + this.liste[trials] + " la reponse de this.LastAnswer " + this.LastAnswers.get(this.LastAnswers.size() - 1));
        }
        System.out.println(ModeJoueur ? "\033[33m La combinaison  du Joueur est: " + Combination : "\033[33m C'est le tour de l'ordinateur:");
        if (trials == 0) {
            answer = answerIA("MasterMind", length, trials, SelectedColor, "0,0");

            int Indication[] = color(answer, Combination);
            goodColor = Indication[0];
            colorExist = Indication[1];
            this.Clue = Integer.toString(goodColor) + "," + Integer.toString(colorExist);
            trials = iA(Combination, answer, goodColor, colorExist, trials, "ordinateur");
        } else {

            System.out.println(this.Clue + " LINDICATION");
            answer = answerIA("MasterMind", length, trials, SelectedColor, this.Clue);
            int Indic[] = color(answer, Combination);
            goodColor = Indic[0];
            colorExist = Indic[1];
            this.Clue = Integer.toString(goodColor) + "," + Integer.toString(colorExist);
            trials = iA(Combination, answer, goodColor, colorExist, trials, "ordinateur");
        }
        this.LastAnswers.add(answer);

        this.liste[trials] = answer;

         return trials;
    }

    //todo: ameliorer IA si 2 couleur sont trouver chercher une autre et couleur finirr les proet 45 en 2 jours
    void boardGame(String Mode) throws Exception {
        ConfigReader Data = new ConfigReader();
        int trials = 0;
        String Combination;
        String SelectedColor;
        switch (Mode) {
            case "challenger":
                SelectedColor = selectionIA("MasterMind", Data.getColors(), Data.isDeveloperMode());
                Combination = combinationSelection(SelectedColor, false);
                while (trials < Data.getTrials()) {
                    trials = playerTurn(Combination, trials, Data.isDeveloperMode());
                    trials++;
                }
                break;
            case "defenseur":
                SelectedColor = selectionPlayer("MasterMind", Data.isDeveloperMode());
                Combination = combinationSelection(SelectedColor, true);
                while (trials < Data.getTrials()) {
                    trials = computerTurn(Combination, SelectedColor, trials, Data.isDeveloperMode());
                    trials++;
                }
                break;
            case "duel"://todo: si la reponse commmence par 0 lla repnse bug et ne prend pas en compte le 0
                //todo:
                String SelectedColorPlayer = selectionPlayer("MasterMind", Data.isDeveloperMode());
                String CombinaisonPlayer = combinationSelection(SelectedColorPlayer, true);
                SelectedColor = selectionIA("MasterMind", Data.getColors(), Data.isDeveloperMode());
                Combination = combinationSelection(SelectedColor, false);

                while (Data.getTrials() > trials) {
                    while (!(PcTurn && PlayerTurn) || trials < Data.getTrials()) {
                        if (ComputerTurn && trials < Data.getTrials()) {
                            trials = computerTurn(CombinaisonPlayer, SelectedColorPlayer, trials, Data.isDeveloperMode());
                            ComputerTurn = false;
                            PcTurn = true;
                        }
                        if (!ComputerTurn && trials < Data.getTrials()) {
                            trials = playerTurn(Combination, trials, Data.isDeveloperMode());
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
