# Projet3 
##Mettez votre logique à l'épreuve

Dans ce projet, nous avons une application qui contient deux jeux de reflexion ils contiennent trois modes de jeux:
######Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur
######Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète
######Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné
### SecretNumber
Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur). Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+), plus petit (-) ou si c'est le bon chiffre (=).
L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
### MasterMind
Le but : découvrir la combinaison à x chiffres/couleurs de l'adversaire (le défenseur). Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque proposition le nombre de chiffre/couleur de la proposition qui apparaissent à la bonne place et à la mauvaise place dans la combinaison secrète.
L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
#### Instalation
Pour lancer le programme depuis l'invite de commande il faut(il faut télécharger le dosier META-INF):
Définir le chemin pour inclure le fichier JDK. Par exemple:
######C:..\Desktop\META-INF java> path c:\Program Files\Java\jdk1.7.0_25\bin;%path%
On Compile les class(es):
C:\mywork> javac *.java
et executer le jar avec cette commande
######C:..\Desktop\META-INF java> -jar Projet3.jar