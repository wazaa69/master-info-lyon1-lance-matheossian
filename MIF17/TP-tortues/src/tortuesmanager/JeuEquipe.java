package tortuesmanager;


public class JeuEquipe extends JeuDeBalle {

    public  JeuEquipe(FeuilleDessin feuille, int nbJA, int nbJB) { 

        super(feuille, 0);

        //Création des équipes
        EquipeA equipeA = new EquipeA(feuille, nbJA);
        EquipeB equipeB = new EquipeB(feuille, nbJB);

        

    }




//Deux équipes
//
//Créez deux sous-classes de TortueAmelioree, qui correspondent aux joueurs de deux équipes.
//        Le jeu peut être une simple "passe à dix" (faire 10 passes au sein d'une équipe sans
//        se faire prendre la balle). Donnez un comportement à chaque type de tortue
//        (déplacement sans la balle, déplacement avec la balle, passe à une autre tortue de
//        la même équipe assez proche, prise de la balle à une tortue adverse assez proche).
//        Créez une classe JeuEquipe, qui crée les équipes et gère le jeu, testez.

    //void placerTortuesTerrain (surcharge) (eA,eB);
    //void lancerPartie (surcharge)
    //
    //void passeA10(comportement,eA,eB)




}
