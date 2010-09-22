package tortuesmanager;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JeuEquipe extends JeuDeBalle {

    ArrayList<TortueEquipere> equipeA = new ArrayList<TortueEquipere>();
    ArrayList<TortueEquipere> equipeB = new ArrayList<TortueEquipere>();

    public  JeuEquipe(FeuilleDessin feuille, int nbJA, int nbJB) { 

        super(feuille, 0);

        //Création des équipes
        for(int i = 0; i < nbJA; i++) 
            equipeA.add(new TortueEquipere(feuille, "Equipe A", nbJA));

        for(int i = 0; i < nbJB; i++)
            equipeB.add(new TortueEquipere(feuille, "Equipe B", nbJB));

    }

    @Override
    public void placerTortuesTerrain(){

            for(int i = 0; i < equipeA.size(); i++)
                for(int j = 0; j < 50; j++)
                    equipeA.get(i).deplacementAuHasard(15);


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

        //Tortue pripriétaire de la balle et l'ancienne
        TortueAmelioree ancienneProprio = null;
        TortueAmelioree tortueProche = null;

        
        while(!finPartie) {

            //On fait bouger la balle et son propriétaire
            tortueProprio.deplacementAuHasard(15);
            balle.setPositionSelonTortue(tortueProprio);


            //On fait bouger les autres tortues
            Tortue uneTortue = null;
            for (int j = 0; j < feuille.getListeTortuesAmeliorees().size(); j++) {

                uneTortue = feuille.getListeTortuesAmeliorees().get(j);

                //le propriétaire de la balle à déjà bougé
                if (uneTortue != tortueProprio) {uneTortue.deplacementAuHasard(15);}
 
            }

            //après le déplacement des tortues
            feuille.drawIt();


            //La tortue propriétaire de la balle cherche à faire une passe
            tortueProche = tortueProprio.tortueAmieLaPlusProche();

            if (tortueProche != null) {
                if (tortueProprio.distTortue(tortueProche) >= distMinPourPasse && tortueProche != ancienneProprio) {

                    ancienneProprio = tortueProprio;
                    tortueProprio = tortueProche;

                    System.out.println(ancienneProprio.getNom() + " passe la balle à " + tortueProche.getNom());

                    balle.setPositionSelonTortue(tortueProprio); //il faudrait utiliser la méthode passerLaBalleA

                }
            }

            feuille.drawIt();
 

            try {

                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(JeuDeBalle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }




//Créez deux sous-classes de TortueAmelioree, qui correspondent aux joueurs de deux équipes.
//        Le jeu peut être une simple "passe à dix" (faire 10 passes au sein d'une équipe sans
//        se faire prendre la balle). Donnez un comportement à chaque type de tortue
//        (déplacement sans la balle, déplacement avec la balle, passe à une autre tortue de
//        la même équipe assez proche, prise de la balle à une tortue adverse assez proche).
//        Créez une classe JeuEquipe, qui crée les équipes et gère le jeu, testez.




}
