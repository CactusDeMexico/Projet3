package com.openclassrooms.mpancarte.SecretNumber;

import com.openclassrooms.mpancarte.Choix;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Choix {
    public String ChoixIA() {
        this.ModeJoueur="Developpeur";

        ArrayList NombreSecret = new ArrayList();

        String NbSecret = "";
        Random Random = new Random();

        //définition du nombre secret
        for (int i = 0; i < this.NbCase; i++) {
            NombreSecret.add(Random.nextInt(10));
            NbSecret += NombreSecret.get(i);
        }
        System.out.println(NbSecret);
        System.out.println("Developpeur".equalsIgnoreCase(this.ModeJoueur) ? "Le Nombre secret est:" + NbSecret : "Mode Joueur");
        return NbSecret;
    }
}
