package tortuesmanager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


    public class JeuDeBalle extends Thread {

     //######################################################################################################      ATTRIBUTS

    private FeuilleDessin feuille;

    private TortueBalle balle;

    private int nbrJoueurs;

    public boolean finPartie = false;

    final private int distMinPasse = 15;


    //######################################################################################################      CONSTRUCTEURS

    /**
     * Crée les tortues de jeu, les fait se connaître entre-elles et ajoute une balle
     * @param feuille la feuille de dessin.
     */
    public JeuDeBalle(FeuilleDessin feuille, int nbrJoueurs) {

        this.feuille = feuille;
        balle = new TortueBalle(feuille);
        this.nbrJoueurs = nbrJoueurs;


        TortueAmelioree uneTortue = null;
        ArrayList<Tortue> listeTortues =  feuille.getListeTortues();
        

        //Création des tortues Améliorées (uniquement)
        for (int i = 0; i < nbrJoueurs; i++) {
            uneTortue = new TortueAmelioree(feuille,"");

            uneTortue.setSaluer(false); //les tortues ne doivent pas se saluer
            uneTortue.setX(0);
            uneTortue.setY(0);
            uneTortue.setDir((int) Math.random() * 360);
            
            listeTortues.add(uneTortue);

            System.out.println(feuille.getListeTortues().size() + "////////////");
        }

        System.out.println("////////////");

        ArrayList<TortueAmelioree> listeJoueuses =  feuille.getListeTortuesAmeliorees();

        //Toutes les tortues doivent se connaître
        for (int i = 0; i < listeJoueuses.size(); i++) {
             listeJoueuses.get(i).ajouterDesAmies(); //downcast
        }

    }

    //######################################################################################################      METHODES

    /**
     * Place les tortues sur le terrain de jeu.
     */
  protected void placerTortuesTerrain() {


        int nbTortues = feuille.getListeTortuesAmeliorees().size();
        Tortue uneTortue = null;

        int largeurTerrain = feuille.drawingImage.getWidth(feuille);
        int hauteurTerrain = feuille.drawingImage.getHeight(feuille);

        int rl = largeurTerrain/(nbTortues+1);
        int rh = hauteurTerrain/4;

        boolean  unSurDeux = true;

        for (int i = 0; i < nbTortues; i++){

            uneTortue = feuille.getTortue(i);

            if(uneTortue.distTortue(balle) != 0){

                uneTortue.setX(((i+1)*rl)/2);

                if(unSurDeux){ uneTortue.setY(rh); unSurDeux=false;}
                else {uneTortue.setY(3*rh); unSurDeux=true;}

            }

            else{
                balle.setPositionSelonTortue(uneTortue);
                balle.dessinerTortue(feuille.getGraphics());
            }
        }

        feuille.drawIt();
  }



    public TortueAmelioree randomJoueuse(){

        if(!feuille.getListeTortuesAmeliorees().isEmpty()){
            int joueuse = (int) Math.round(Math.random() * (feuille.getListeTortuesAmeliorees().size()-1));
            return feuille.getListeTortuesAmeliorees().get(joueuse);
        }
        else return null;
    }


    /**
     * Lance la partie dans le thread courant
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


        //Distance de déplacement
        int distanceDep = 15;


        while(!finPartie) {
            
            try {

                //On fait bouger la balle et son propriétaire
                tortueProprio.deplaceHasardEtPousse(distanceDep);
                balle.setPositionSelonTortue(tortueProprio);


                //On fait bouger les autres tortues
                Tortue uneTortue = null;
                for (int j = 0; j < feuille.getListeTortuesAmeliorees().size(); j++) {

                    uneTortue = feuille.getListeTortuesAmeliorees().get(j);

                    //le propriétaire de la balle à déjà bougé
                    if (uneTortue != tortueProprio) {uneTortue.deplacementAuHasard(distanceDep);}

                }


                //La tortue propriétaire de la balle cherche à faire une passe
                tortueProche = tortueProprio.tortueAmieLaPlusProche();

                if (tortueProche != null) {
                    if (tortueProprio.distTortue(tortueProche) >= distMinPasse && tortueProche != ancienneProprio) {

                        ancienneProprio = tortueProprio;
                        tortueProprio = tortueProche;

                        System.out.println(ancienneProprio.getNom() + " passe la balle à " + tortueProche.getNom());

                        balle.setPositionSelonTortue(tortueProprio); //il faudrait utiliser la méthode passerLaBalleA

                    }
                }

                testFin();

                try {
                    Thread.sleep(700);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JeuDeBalle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            catch( InterruptedException e ) {}

        }
    }


    /**
     * La fonction du thread
     */
    @Override public void run() {lancerPartie();}

    /**
     * Lance la partie
     */
    public void lancerPartieThread() {(new Thread(this)).start();}

    

    public synchronized void testFin() throws InterruptedException {
            if( finPartie ) {
                    throw new InterruptedException();
            }
    }

    //public synchronized void stop() {finPartie = true;}


}
