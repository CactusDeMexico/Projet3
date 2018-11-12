package com.openclassrooms.mpancarte.Mastermind;

import com.openclassrooms.mpancarte.Choix;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Choix {
    public ArrayList Combin = new ArrayList();
    String Couleur[] = {"Bleu", "Rouge", "Vert", "Jaune", "Orange", "Marron", "Blanc", "Violet", "Cyan", "Noir"};
    String SelectEffec = "";
    String CouleurEffec = "";
    String ReponseOrdi = "";
    String selecCom;

    public String ChoixPo[] = new String[this.NbCouleurSelec];
    Random random = new Random();
    public String ChoixCouleur()
    {
        SelectEffec="";
        int selection ;
        //insertion des couleur choisi au hasard
        for (int a = 0; this.NbCouleurSelec - 1 >= a; a++) {
            selection = random.nextInt(10 - 0);
            if (SelectEffec.contains(Integer.toString(selection))) {
                while (SelectEffec.contains(Integer.toString(selection)) ) {
                    selection = random.nextInt(10 - 0);

                }
            }
            SelectEffec += selection + ",";
            //attribution de la couleur dans ChoixPo
            this.ChoixPo[a] = Couleur[selection];
        }
        MasterMind Lejeu = new MasterMind();
        String ListeCouleurPos = Lejeu.AffichCoulPos(SelectEffec);
        return ListeCouleurPos;
    }
    public String Combinaison(){

        for (int g = 0; g < this.NbCaseCouleur; g++) {
            selecCom = ChoixPo[random.nextInt(NbCouleurSelec - 0)];
            if (CouleurEffec.contains(selecCom)) {
                while (CouleurEffec.contains(selecCom) ) {
                    //Selection de couleur de facon alea dans la limite selectionné
                    selecCom = ChoixPo[random.nextInt(NbCouleurSelec - 0)];
                }
            }
            CouleurEffec += selecCom + ",";
        }
        String ListeSelectionner[] = CouleurEffec.split(",");
        String BonneReponseO = "";
        for (int i = 0; i < ListeSelectionner.length; i++) {
            BonneReponseO += i + ":" + ListeSelectionner[i] + " ";
        }
        return BonneReponseO;
    }
    public String getCouleurPossible()
    {
        String CouleurPossible=ChoixCouleur();
        System.out.println("Les couleurs possible sont "+ CouleurPossible);
        return CouleurPossible;
    }
    /*

     public String AntiDoublonCouleur(String ReponseOrdi) {
        int doublon = 0;
        if (CouleurEffec.contains(ReponseOrdi) || doublon >= 1) {
            reset = false;
            while (reset == false) {
                if (CouleurEffec.contains(ReponseOrdi) || doublon > 0) {
                    //System.out.println("voicie les doublon"+doublon+"voici la repo "+ReponseOrdi);
                    if (PresTrou == true) {
                        ReponseOrdi = BonChoix[random.nextInt(NbCaseCouleur - 0)];
                    } else {
                        ReponseOrdi = CouleurP[random.nextInt(NbCouleurSelec - 0)];
                    }
                    doublon = 0;
                    if (CouleurEffec.contains(ReponseOrdi)) {
                        //System.out.println("les doublons" + doublon);
                        doublon++;
                    }
                    if (doublon == 0) {
                        reset = true;
                    }
                }
            }
        }
        return ReponseOrdi;
    }

    public Boolean AntiDoublonReponse(String CouleurEffec, int a) {
        boolean Doublon = false;
        for (int size = 0; size < ArReponseDonnee.size(); size++) {
            //System.out.println(ArReponseDonnee.get(size).equals(CouleurEffec));
            //System.out.println("deja"+ArReponseDonnee.get(size)+"  "+CouleurEffec);
            if (ArReponseDonnee.get(size).equals(CouleurEffec)) {
                Doublon = true;
                a = -1;
                size = ArReponseDonnee.size() + 1;
            }
        }

        return Doublon;
    }

    public String InitialisChoixPc() {
        CouleurEffec = "";
        String IndiceBonne[] = new String[NbCaseCouleur];
        String Bonne[] = new String[NbCaseCouleur];
        String BonChoix[] = new String[NbCaseCouleur];
        PresTrou = false;
        do {
            int doublon;
            int a = 0;
            a = 0;
            while (a < this.NbCaseCouleur) {
                ReponseOrdi = "";
                if (!(IndiceBonne[a] == null)) {
                    ReponseOrdi = Bonne[a] = IndiceBonne[a];
                }
                if (IndiceBonne[a] == null) {
                    if (PresTrou == true) {
                        ReponseOrdi = BonChoix[random.nextInt(this.NbCaseCouleur - 0)];
                    } else {
                        ReponseOrdi = CouleurP[random.nextInt(this.NbCouleurSelec - 0)];
                    }//verification
                    ReponseOrdi = AntiDoublonCouleur(ReponseOrdi);
                }
                CouleurEffec += ReponseOrdi + ",";
                //si Deja proposé reset des  couleurs effectuées _______fonction
                if (a == this.NbCaseCouleur - 1) {
                    boolean Doublon = AntiDoublonReponse(CouleurEffec, a);
                    if (Doublon == true) {
                        ReponseOrdi = "";
                    }
                }
                a++;
            }
            //serialisation ordi __________________________
            String TabReponseOrdi[] = CouleurEffec.split(",");
            ArReponseDonnee.add(CouleurEffec);
            String ReponOrdi = "";
            for (int i = 0; this.NbCaseCouleur > i; i++) {
                ReponOrdi += i + ":" + TabReponseOrdi[i];
            }
            boolean win = false;
            //Verification _______________________________
            if (BonneReponse.equals(CouleurEffec)) {
                System.out.println("La reponse de l'ordinateur " + ReponOrdi);
                System.out.println(" Gagné");
                EssaiRes = 0;
                win = true;
            }
            couleurBien = 0;
            couleurExist = 0;
            for (int i = 0; i < this.NbCaseCouleur; i++) {
                if (TabReponseOrdi[i].equals(Comb[i])) {
                    couleurBien++;
                    IndiceBonne[i] = TabReponseOrdi[i];
                } else {
                    if (CouleurEffec.contains(TabReponseOrdi[i])) {
                        couleurExist++;
                    }
                }
            }
            //affichage __
            System.out.println("La reponse de l'ordinateur " + ReponOrdi);
            System.out.println("Developpeur".equalsIgnoreCase(this.ModeJoueur) ? "\033[35mLa combinaison  est secrete:" + BonneReponse : "Mode Joueur");
            //bleu
            System.out.println("\033[34m Vous avez :" + couleurBien + " Couleurs bien placé ");
            //vert
            System.out.println("\033[32m Vous avez: " + couleurExist + " Couleurs existante");
            //precision de la selection___________
            if (couleurExist + couleurBien == this.NbCaseCouleur) {
                for (int i = 0; i < this.NbCaseCouleur; i++) {
                    BonChoix[i] = TabReponseOrdi[i];
                }
                PresTrou = true;
            }
            if (win == true) {
            } else {
                System.out.println("\033[33m Raté, il reste:" + (EssaiRes - 1));
            }
            CouleurEffec = "";
            EssaiRes--;

        } while (EssaiRes > 0);
        System.out.println(" FIN:");
    }
     */
    //public String getCombinaison(){}
   // public String getReponse(){}
}
