package com.openclassrooms.mpancarte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUtils {
    private static BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));

    //READ INT
    static int intInput() {
        while (true) {
            try {
                //lecture de la chaine
                String chaine = entree.readLine();
                return Integer.parseInt(chaine);//conversion en un entier
            } catch (IOException exp) {
                System.out.println("Erreur de lecture clavier");
            } catch (NumberFormatException exp) {
                System.out.println("Erreur du format du nombre");
            }
        }
    }

    //READ FLOAT
    public static float floatInput() {
        float nb = 0;
        String chaine;
        boolean ok = false;
        do {
            try {
                chaine = entree.readLine();//lecture de la chaine
                nb = Float.parseFloat(chaine);//conversion en un entier
                ok = true;
            } catch (IOException exp) {
                System.out.println("Erreur de lecture clavier");
            } catch (NumberFormatException exp) {
                System.out.println("Erreur du format du nombre");
            }
        } while (!ok);
        return nb;

    }

    //READ sTRING
    public static String stringInput() {

        String chaine = "";
        try {
            chaine = entree.readLine();//lecture de la chaine
        } catch (IOException exp) {
            System.out.println("Erreur de lecture clavier");
        }
        return chaine;
    }

    //READ CHAR
    public static char charInput() {

        String chaine = "";
        try {
            chaine = entree.readLine();//lecture de la chaine
        } catch (IOException exp) {
            System.out.println("Erreur de lecture clavier");
        }
        return chaine.charAt(0);
    }
    //READ STRING WHO COUNTER ONLY INTEGER
    static String inputStringNumber() {

        String selection ;
        while (true) {
            try {
                String regex = "[0-9]+";
                selection = entree.readLine();//lecture de la chaine
                int nb = Integer.parseInt(selection);

                if (!selection.matches(regex)) {
                    System.out.println("Invalid number " + nb);
                }
                return selection;
            } catch (IOException exp) {
                System.out.println("Erreur de lecture clavier");
            } catch (NumberFormatException exp) {
                System.out.println("Erreur de nombre");
            }
        }
    }
}
