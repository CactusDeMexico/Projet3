package com.openclassrooms.mpancarte.Mastermind;

import com.openclassrooms.mpancarte.Choix;
import com.openclassrooms.mpancarte.Console;

import java.util.ArrayList;
import java.util.Random;

public class MasterMind extends Choix {
    int couleurBien = 0;
    int couleurExist = 0;
    boolean reset = false;
    boolean PresTrou = false;
    boolean Fin = false;

    public String AffichCoulPos(String CouleurDisponble) {

        int h = 0;

        String ListeCouleurPos = "";
        String CouleurP[] = CouleurDisponble.split(",");
        this.NbCouleurSelec = CouleurP.length;
        //affichage propre dans la console
        while (h < this.NbCouleurSelec) {
            ListeCouleurPos += h + ": " + CouleurP[h] + " ";
            h++;
        }
        return ListeCouleurPos;

    }

    public void Verification() {
    }

    public void Plateau(String Mode) {
        Joueur UnJoueur = new Joueur();
        IA UneIA = new IA();
        switch (Mode) {
            case "challenger":
                int Essai = this.NbEssai;
                UneIA.getCouleurPossible();
                // UneIA.getCombinaison();
                while (Essai != 0) {
                    // UnJoueur.getReponse();
                    Essai--;
                }

                break;
            case "defenseur":
                Essai = this.NbEssai;
                UnJoueur.getCombinasion();

                while (Essai != 0) {
                    //UneIA.getReponse();
                    Essai--;
                }

                break;
            case "duel":

                break;
        }

    }



}
