package com.openclassrooms.mpancarte.Mastermind;

import com.openclassrooms.mpancarte.Choix;
import com.openclassrooms.mpancarte.Console;

import java.util.ArrayList;
import java.util.Random;

public class Joueur extends Choix {
    public ArrayList Combin = new ArrayList();
    String Couleur[] = {"Bleu", "Rouge", "Vert", "Jaune", "Orange", "Marron", "Blanc", "Violet", "Cyan", "Noir"};
    String SelectEffec = "";
    String CouleurEffec = "";
    String ReponseOrdi = "";
    String selecCom;
    int couleurBien = 0;
    int couleurExist = 0;
    boolean reset = false;
    boolean PresTrou = false;
    boolean Fin = false;
    public String ChoixPo[] = new String[this.NbCouleurSelec];
    Random random = new Random();
    public int ChoixCouleurJoueur() {
        int Selection;
        this.ModeJoueur = "Developpeur";
        System.out.println("0:" + Couleur[0] + " 1: " + Couleur[1] + " 2: " + Couleur[2] + " 3: " + Couleur[3] + " 4: " + Couleur[4] + " 5: " + Couleur[5] + " 6: " + Couleur[6] + " 7: " + Couleur[7] + " 8: " + Couleur[8] + " 9: " + Couleur[9] + " 10:FIN");
        Selection = Console.saisirInt();
        return Selection;
    }

    public String SelectionCouleursJoeur(int x, int g) {

        String test = "";
        while (Fin == false) {
            g = 0;
            System.out.println("Choisir les couleurs disponible ");
            g = ChoixCouleurJoueur();
            while (g > 11) {
                System.out.println("Veuillez selectionner ");
                g = ChoixCouleurJoueur();
            }// si il ya moins de 4 couleurs
            if (x < 4) {
                if (g == 10) {// si l'utilisateur demande pour sortir
                    System.out.println("Choisir au minimum 4 couleurs , il n'y a que : " + (x) + " couleurs ");//refus
                    while (g > 9) {
                        System.out.println("Choisir les couleurs disponible ");
                        g = ChoixCouleurJoueur();
                    }
                }
            }
            if (g < 10) {
                test = Couleur[g];
                if (SelectEffec.contains(test)) {
                    while (SelectEffec.contains(test) || reset == false) {
                        if (SelectEffec.contains(test)) {
                            System.out.println("Veuillez ne pas choisir les memes couleurs");
                            System.out.println("Voici les  couleurs selectionnnée " + test);
                            g = ChoixCouleurJoueur();

                            if (g == 10) {
                                reset = true;
                            }
                            test = Couleur[g];
                        } else {
                            reset = true;
                        }
                    }
                }
                SelectEffec += test + ",";
            }
            if (x > 3 && g == 10 || g == 10) {
                // si l'utilisateur demande pour sortir
                System.out.println("Fin de Selection ");
                Fin = true;// on sort
            }
            x++;
        }
        return SelectEffec;
    }



    public int ErreurChoix(int choix,int longueur,String Couleur) {
        while (choix >= longueur) {
            System.out.println("Veuillez Choisir la combinaison dans les choix disponible " + Couleur);
            choix = Console.saisirInt();
        }
        return choix;
    }

    public String SelectCombin() {
        MasterMind Lejeu = new MasterMind();
        String ListeCouleurPos = Lejeu.AffichCoulPos(SelectionCouleursJoeur(0, 0));
        String CouleurP[] = SelectEffec.split(",");//couleur posssible
        CouleurEffec = ""; selecCom = "";
        int k;
        for (int g = 0; g < CouleurP.length; g++) { //choix combinaison
            System.out.println("Choisir la combinaison  " + ListeCouleurPos + " :10 sortir");
            k = Console.saisirInt();
            if (k == 10) {
                g = CouleurP.length + 1;
            } else {
                k=ErreurChoix(k,CouleurP.length,ListeCouleurPos);
                selecCom = CouleurP[k];
                if (CouleurEffec.contains(selecCom)) {
                    while (CouleurEffec.contains(selecCom) ) {
                        System.out.println("Veuillez ne pas choisir les memes couleurs  " + ListeCouleurPos + " :10 sortir");
                        k = Console.saisirInt();
                        if (k == 10) {
                            g = CouleurP.length + 1;
                        }
                        if (k > CouleurP.length) {
                            k=ErreurChoix(k,CouleurP.length,ListeCouleurPos);
                        } else {
                            selecCom = CouleurP[k];
                        }
                    }
                }
                CouleurEffec += selecCom + ",";
                this.Combin.add(selecCom);
            }
        }
        return CouleurEffec;
    }

    public String AffichageCombinaison() {
        String BonneReponse = SelectCombin();
        //System.out.println(Combin.size());
        String Comb[] = new String[this.Combin.size()];
        String RepFin = "";
        String Transf = "";
        for (int i = 0; this.Combin.size() > i; i++) {
            Transf = "";
            Transf += this.Combin.get(i);

            Comb[i] = Transf;

            RepFin += i + ":" + Transf + " ";
        }
        //definiton de la combinaison gagnante
        String Combinaison[] = BonneReponse.split(",");
        System.out.println("Developpeur".equalsIgnoreCase(this.ModeJoueur) ? "La combinaison est:" + RepFin : "Mode Joueur");

        return BonneReponse;

    }

    public String getCombinasion() {
        String Selection = AffichageCombinaison();
        return Selection;

    }
   // public String getReponse(){}

}
