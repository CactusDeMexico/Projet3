package com.openclassrooms.mpancarte;

import java.util.ArrayList;
import java.util.Random;

public class MasterMind extends Game {
    private boolean reset = false;
    private boolean Fin = false;
    private ArrayList Combin = new ArrayList();
    private String Couleur[] = {"Bleu", "Rouge", "Vert", "Jaune", "Orange", "Marron", "Blanc", "Violet", "Cyan", "Noir"};
    private String SelectEffec = "";
    private String ChoixPo[];
    private Random random = new Random();

    public String Doublon(String Selection, String test, int Max) {
        Random Random = new Random();
        if (Selection.contains(test)) {
            while (Selection.contains(test)) {
                test = "";
                test += Random.nextInt(Max - 0);
            }
        }
        return test;
    }

    public String selecComb(String Couleur, boolean Player) {
        String selection = "";
        String test = "";
        String nb = "";

        for (int g = 0; g < Couleur.length(); g++) {
            nb = "";
            if (!Player) {
                nb += random.nextInt(Couleur.length() - 0);
                test += Doublon(test, nb, Couleur.length());
            } else {
                System.out.println("Veuillez selectionner un chiffre dans "+Couleur);
                nb += ConsoleUtils.saisirInt();
                while(nb.length()!=1 ||Integer.valueOf(nb)>= Couleur.length()){
                    System.out.println("Veuillez selectionner un chiffre dans "+Couleur);
                    nb += ConsoleUtils.saisirInt();
                }
                while (nb != antiDuplicateString(test, nb)) {
                    System.out.println("Veuillez ne pas entrer le meme");
                }


                    //g=Couleur.length();

                test += nb;
            }
            selection += Couleur.charAt(Character.getNumericValue(test.charAt(g)));
        }
        return selection;
    }

    private static int[] color(String Answer, String Combinaison) throws Exception {
        ReadValues Data = new ReadValues();
        int GoodColor = 0;
        int ColorExist = 0;
        for (int i = 0; i < Combinaison.length(); i++) {
            if ((Answer.charAt(i)) == (Combinaison.charAt(i))) {
                GoodColor++;
            } else {
                if (Combinaison.contains(Character.toString(Answer.charAt(i)))) {
                    ColorExist++;
                }
            }
        }
        return new int[]{GoodColor, ColorExist};
    }

    private int iA(String Combinaison, String Answer, int GoodColor, int ColorExist, int Essai, String Player) throws Exception {
        ReadValues Data = new ReadValues();
        if (Combinaison.contentEquals(Answer)) {
            System.out.println("Gagné la Reponse est " + Answer + "C'est l" + Player + "qui gagne");
            Essai = Data.getNbEssai();
        } else {
            System.out.println("Raté la Reponse du " + Player + "est " + Answer + " il reste " + (Data.getNbEssai() - Essai - 1));
        }
        System.out.println("Il y a :" + GoodColor + " bien placé et : " + ColorExist + " couleur existantes");
        return Essai;
    }

    private int playerTurn(String Combinaison, int Essai, String ModeJoueur) throws Exception {
        ReadValues Data = new ReadValues();
        System.out.println("Developpeur".equalsIgnoreCase(ModeJoueur) ? "\033[36m Le Nombre secret  de l'ordinateur est: " + Combinaison : "\033[36m   C'est votre tour");
        String Answer = answerPlayer("MasterMind", Data.getNbCouleur());
        int Indication[] = color(Answer, Combinaison);
        int GoodColor = Indication[0];
        int ColorExist = Indication[1];
        return iA(Combinaison, Answer, GoodColor, ColorExist, Essai, "e joueur");

    }

    public void Plateau(String Mode) throws Exception {
        Menu LeMenu = new Menu();
        LeMenu.Init();
        ReadValues Data = new ReadValues();


        String Combinaison;
        String SelectedColor;
        switch (Mode) {
            case "challenger":
                int Essai = 0;
                SelectedColor = selectionIA("MasterMind", Data.getNbCouleur(), Data.getModeJoueur());
                Combinaison = selecComb(SelectedColor,false);
                while (Essai < Data.getNbEssai()) {
                    Essai = playerTurn(Combinaison, Essai, Data.getModeJoueur());
                    Essai++;
                }
                break;
            case "defenseur":
                Essai = 0;
                SelectedColor = selectionPlayer("MasterMind", Data.getModeJoueur());
                Combinaison = selecComb(SelectedColor, true);
                int length=Combinaison.length();
                String Answer = answerIA("MasterMind",length,Essai,SelectedColor,"0,0");
                int Indication[]= color(Answer, Combinaison);
                int GoodColor = Indication[0];
                int ColorExist = Indication[1];

                Essai=iA(Combinaison, Answer, GoodColor, ColorExist, Essai, "ordinateur");
                Essai =1;
                while (Essai < Data.getNbEssai()) {


                    String Clue= Integer.toString(GoodColor)+","+Integer.toString(ColorExist);
                    Answer = answerIA("MasterMind",length,Essai,SelectedColor,Clue);
                    Essai= iA(Combinaison, Answer, GoodColor, ColorExist, Essai, "ordinateur");

                    Essai++;
                }

                break;
            case "duel":

                break;
        }

    }


}
