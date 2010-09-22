package tortuesmanager;


public class JeuEquipe extends JeuDeBalle {


    public  JeuEquipe(FeuilleDessin feuille, int nbJA, int nbJB) { 

        super(feuille, 0);

        //Création des équipes
        Equipe equipeA = new Equipe(feuille, "Equipe A", nbJA);
        Equipe equipeB = new Equipe(feuille, "Equipe B", nbJB);

    }

    @Override
    public void placerTortuesTerrain(){

        for(TortueAmelioree uneTortue : feuille.getListeTortuesAmeliorees())
            for(int i = 0; i < 50; i++) uneTortue.deplacementAuHasard(15);

    }

    @Override
    public void lancerPartie(){
        
        placerTortuesTerrain();

        //On récupère la tortue la plus proche de la balle et on downcast
        TortueAmelioree tortueProprio = randomJoueuse();
        if(tortueProprio == null) return; //si il n'y a aucune tortue on quitte

        //On met à jour la propriétaire de la balle et on l'affiche
        balle.setPositionSelonTortue(tortueProprio);
        System.out.println(tortueProprio.getNom()+ " a la balle !");

        feuille.drawIt();
    }
    

//Deux équipes
//
//Créez deux sous-classes de TortueAmelioree, qui correspondent aux joueurs de deux équipes.
//        Le jeu peut être une simple "passe à dix" (faire 10 passes au sein d'une équipe sans
//        se faire prendre la balle). Donnez un comportement à chaque type de tortue
//        (déplacement sans la balle, déplacement avec la balle, passe à une autre tortue de
//        la même équipe assez proche, prise de la balle à une tortue adverse assez proche).
//        Créez une classe JeuEquipe, qui crée les équipes et gère le jeu, testez.




}
