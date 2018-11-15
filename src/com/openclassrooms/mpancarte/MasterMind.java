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

    String CouleurEffec = "";

    String selecCom;

    // choix pc
    private int ChoixCouleurJoueur() {
        int Selection;
        this.ModeJoueur = "Developpeur";
        System.out.println("0:" + Couleur[0] + " 1: " + Couleur[1] + " 2: " + Couleur[2] + " 3: " + Couleur[3] + " 4: " + Couleur[4] + " 5: " + Couleur[5] + " 6: " + Couleur[6] + " 7: " + Couleur[7] + " 8: " + Couleur[8] + " 9: " + Couleur[9] + " 10:FIN");
        Selection = ConsoleUtils.saisirInt();
        return Selection;
    }

    private String selectionCouleursJoeur(int x) {

        String test = "";
        while (!Fin) {
            int g = 0;
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

    private int ErreurChoix(int choix, int longueur, String Couleur) {
        while (choix >= longueur) {
            System.out.println("Veuillez Choisir la combinaison dans les choix disponible " + Couleur);
            choix = ConsoleUtils.saisirInt();
        }
        return choix;
    }

    private String SelectCombin() {
        MasterMind Lejeu = new MasterMind();
        String ListeCouleurPos = Lejeu.AffichCoulPos(selectionCouleursJoeur(0));
        String CouleurP[] = SelectEffec.split(",");//couleur posssible
        String couleurEffec = "";
        String selecCom = "";
        int k;
        for (int g = 0; g < CouleurP.length; g++) { //choix combinaison
            System.out.println("Choisir la combinaison  " + ListeCouleurPos + " :10 sortir");
            k = ConsoleUtils.saisirInt();
            if (k == 10) {
                g = CouleurP.length + 1;
            } else {
                k = ErreurChoix(k, CouleurP.length, ListeCouleurPos);
                selecCom = CouleurP[k];
                if (couleurEffec.contains(selecCom)) {
                    while (couleurEffec.contains(selecCom)) {
                        System.out.println("Veuillez ne pas choisir les memes couleurs  " + ListeCouleurPos + " :10 sortir");
                        k = ConsoleUtils.saisirInt();
                        if (k == 10) {
                            g = CouleurP.length + 1;
                        }
                        if (k > CouleurP.length) {
                            k = ErreurChoix(k, CouleurP.length, ListeCouleurPos);
                        } else {
                            selecCom = CouleurP[k];
                        }
                    }
                }
                couleurEffec += selecCom + ",";
                this.Combin.add(selecCom);
            }
        }
        return couleurEffec;
    }

    private String AffichageCombinaison() {
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

    String AffichCoulPos(String CouleurDisponble) {
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

    // choix ordi
    public String ChoixCouleur(int NbCouleur) throws Exception{
        String Choix[] = new String[NbCouleur];
        ReadValues Data = new ReadValues();
        SelectEffec =  selectionIA("MasterMind", NbCouleur,Data.getModeJoueur());
        int index;
        for (int a = 0; a < NbCouleur; a++) {
            index = Character.getNumericValue(Integer.valueOf(SelectEffec.charAt(a)));
            //attribution de la couleur dans ChoixPo
            System.out.println("char at" + SelectEffec.charAt(a));
            System.out.println("index" + index);

            ChoixPo[a] = Choix[a] = Couleur[index];
        }
        MasterMind Lejeu = new MasterMind();
        String ListeCouleurPos = Lejeu.AffichCoulPos(SelectEffec);
        System.out.println("Les cou" + ListeCouleurPos);
        return ListeCouleurPos;
    }

    public String Combinaison() {

        for (int g = 0; g < this.NbCaseCouleur; g++) {
            selecCom = ChoixPo[random.nextInt(NbCouleurSelec - 0)];
            if (CouleurEffec.contains(selecCom)) {
                while (CouleurEffec.contains(selecCom)) {
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

    public String getCouleurPossible(int NbCouleur) throws Exception {
        String CouleurPossible = ChoixCouleur(NbCouleur);
        System.out.println("Les couleurs possible sont " + CouleurPossible);
        return CouleurPossible;
    }

    public void Verification() {
    }

    public void Plateau(String Mode) throws Exception {
        Menu LeMenu = new Menu();
        LeMenu.Init();
        ReadValues Data = new ReadValues();
        this.ModeJoueur = Data.getModeJoueur();
        this.NbCaseCouleur = Data.getNbCaseCouleur();
        this.NbCase = Data.getNbCase();
        this.NbEssai = Data.getNbEssai();
        this.NbCouleurSelec = Data.getNbCouleur();


        switch (Mode) {
            case "challenger":
                int Essai = this.NbEssai;
                //this.getCouleurPossible(this.NbCaseCouleur);
                // UneIA.getCombinaison();
                selectionIA("MasterMind",Data.getNbCouleur(),Data.getModeJoueur());
                while (Essai != 0) {
                    // UnJoueur.getReponse();
                    Essai--;
                }

                break;
            case "defenseur":
                Essai = this.NbEssai;
                this.getCombinasion();

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
