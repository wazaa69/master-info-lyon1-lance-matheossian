package tortuesmanager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Crée N tortues améliorées ainsi qu'une balle.
 * Les tortues se font des passes.
 */
public class JeuDeBalle extends Thread implements JeuInterface {

     //######################################################################################################      ATTRIBUTS

    protected FeuilleDessin feuille;  /** la feuille de dessins */

    protected  TortueBalle balle; /** la balle de jeu*/

    protected boolean finPartie = false; /** faux si la partie n'est pas terminée, vrai sinon */

    final protected  int distMinPourPasse = 15; /** la distance minimum pour faire une passe, au dessus, la tortue ne fait rien */

    //######################################################################################################      CONSTRUCTEURS

    /**
     * Crée les tortues de jeu, les fait se connaître entre-elles et ajoute une balle
     * @param feuille la feuille de dessin.
     * @param nbrJoueurs le nombre de joueurs
     */
    public JeuDeBalle(FeuilleDessin feuille, int nbrJoueurs) {

        this.feuille = feuille;

        TortueAmelioree uneTortue = null;

        //Création des tortues Améliorées (uniquement)
        for (int i = 0; i < nbrJoueurs; i++) {

            uneTortue = new TortueAmelioree(feuille,"");

            uneTortue.setSaluer(false); //les tortues ne doivent pas se saluer
            uneTortue.setX(5);
            uneTortue.setY(5);
            uneTortue.setDir((int) Math.random() * 360); 
        }

        balle = new TortueBalle(feuille);
    
        
        ArrayList<TortueAmelioree> listeJoueuses =  feuille.getListeTortuesAmeliorees();

        //Toutes les tortues doivent se connaître
        for (int i = 0; i < listeJoueuses.size(); i++){
             listeJoueuses.get(i).ajouterDesAmies();
        }

    }


    //######################################################################################################      ACCESSEUR


    /**
     * Mise à jour du booléen qui déisgne la fin de la partie
     * @param finPartie vrai si la partie doit se finir, faux sinon
     */
    public void setFinPartie(boolean finPartie) {this.finPartie = finPartie;}

    //######################################################################################################      METHODES

    /**
     * Place les tortues sur le terrain de jeu : une ligne en haut, et une en bas.
     */
    public void placerTortuesTerrain() {


        int nbTortues = feuille.getListeTortuesAmeliorees().size();
        Tortue uneTortue = null;

        int largeurTerrain = feuille.getDrawingImage().getWidth(feuille)-20;
        int hauteurTerrain = feuille.getDrawingImage().getHeight(feuille);

        int rl = largeurTerrain/(nbTortues+1);
        int rh = hauteurTerrain/4;

        int  j = 0;
        boolean premLigne = true;

        for (int i = 0; i < nbTortues; i++){

            uneTortue = feuille.getTortue(i);

            if(uneTortue.distTortue(balle) != 0){

                uneTortue.setX(((j+1)*rl*2));
                j++;

                if(i < (nbTortues/2))
                    uneTortue.setY((int) 1.5*rh);
                else uneTortue.setY(3*rh);


                if(i >= (nbTortues/2) && premLigne){
                    j = 0;
                    premLigne = false;
                }

            }

            else balle.setPositionSelonTortue(uneTortue);
        }
  }


    /**
     * Choisit une tortue au hasard
     * @return retourne une tortue améliorée présente dans la liste des tortues
     */
    protected TortueAmelioree randomJoueuse(){

        if(!feuille.getListeTortuesAmeliorees().isEmpty()){
            int joueuse = (int) Math.round(Math.random() * (feuille.getListeTortuesAmeliorees().size()-1));
            return feuille.getListeTortuesAmeliorees().get(joueuse);
        }
        else return null;
    }


    /**
     * La boucle de jeu qui gère les déplacements et les passes
     */
    public void lancerPartie() {

        //On place les tortues sur le terrain
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
            tortueProprio.deplacementAuHasard(5);
            balle.setPositionSelonTortue(tortueProprio);


            //On fait bouger les autres tortues
            Tortue uneTortue = null;
            for (int j = 0; j < feuille.getListeTortuesAmeliorees().size(); j++) {

                uneTortue = feuille.getListeTortuesAmeliorees().get(j);

                //le propriétaire de la balle à déjà bougé
                if (uneTortue != tortueProprio) uneTortue.deplacementAuHasard(5);
 
            }


            //La tortue propriétaire de la balle cherche à faire une passe
            tortueProche = tortueProprio.tortueAmieLaPlusProche();

            if (tortueProche != null) {
                if (tortueProprio.distTortue(tortueProche) >= distMinPourPasse && tortueProche != ancienneProprio) {

                    ancienneProprio = tortueProprio;
                    tortueProprio = tortueProche;

                    System.out.println(ancienneProprio.getNom() + " passe la balle à " + tortueProche.getNom());

                    balle.setPositionSelonTortue(tortueProprio);

                }
            }

            feuille.drawIt();
 

            try {Thread.sleep(100);} catch (InterruptedException ex) {
                System.err.println("Problème avec le sleep dans JeuDeBalle.");
                Logger.getLogger(JeuDeBalle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


    /**
     * Lance la partie
     */
    @Override public void run() {lancerPartie();}

    /**
     * Crée et lance un thread, à partir de cette instance
     */
    public void lancerPartieThread() {(new Thread(this)).start();}
 

}
