    package tortuesmanager;
    import java.util.ArrayList;


    public class JeuDeBalle implements Runnable {

     //######################################################################################################      ATTRIBUTS

    private FeuilleDessin feuille;

    private TortueBalle balle;

    private int nbrJoueurs;

    private boolean finPartie = false;


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
            listeTortues.add(uneTortue); //TortueAmelioree --> Tortue
        }

        ArrayList<TortueAmelioree> listeJoueuses =  feuille.getListeTortuesAmeliorees();

        //Toutes les tortues doivent se connaître
        for (int i = 0; i < listeJoueuses.size(); i++) {
             listeJoueuses.get(i).ajouterDesAmies(); //downcast
        }
        
    }


    //######################################################################################################      ACCESSEURS ET MUTATEURS

    public int getTourDeBoucles() {return nbrJoueurs;}
    public void setTourDeBoucles(int tourDeBoucles) {this.nbrJoueurs = tourDeBoucles;}


    //######################################################################################################      METHODES

    /**
     * Place les tortues sur le terrain de jeu.
     */
    protected void placerTortuesTerrain() {

        int r = 50;
        int nb = feuille.getListeTortues().size();

        for (int i = 0; i < nb - 1; i++) {
            Tortue uneTortue = feuille.getTortue(i);
            uneTortue.setX((int) (r * Math.cos(i * (2 * Math.PI / nb))));
            uneTortue.setY((int) (r * Math.cos(i * (2 * Math.PI / nb))));
            uneTortue.setDir(0);
            uneTortue.droite(180 + i * (360 / nb));
        }
    }



    /**
     * Lance la partie dans le thread courant
     */
    public void lancerPartieNormal() {

        //On place les tortues sur le terrain
        placerTortuesTerrain();

        //On récupère la tortue la plus proche de la balle et on downcast
        TortueAmelioree tortueProprio = (TortueAmelioree) balle.tortueLaPlusProche();

        //On met à jour la propriétaire de la balle et on l'affiche
        balle.setNouvelleProprio(tortueProprio);
        System.out.println(tortueProprio.getNom()+ " a la balle !");


        TortueAmelioree last = null;
        TortueAmelioree proche = null;


        for (int i = 0; i < nbrJoueurs; i++) {

            //On fait bouger la balle et son propriétaire sans sortir de la feuille
            int val = getAnglePourEviterBord(tortueProprio);
            balle.droite(val);
            tortueProprio.droite(val);

            val = (int) (5 + Math.random() * 10);
            balle.avancer(val);
            tortueProprio.avancer(val);

            Tortue uneTortue = null;

            //De même pour les autres tortues
            for (int j = 0; j < feuille.getListeTortues().size(); j++) {

                uneTortue = feuille.getListeTortues().get(j);

                if (uneTortue instanceof TortueAmelioree && (TortueAmelioree) uneTortue != tortueProprio) {
                    uneTortue.droite(getAnglePourEviterBord(tortueProprio));
                    uneTortue.avancer((int) (5 + Math.random() * 10));
                }

            }

            // la joueuse regarde si elle peut faire la passe
            proche = (TortueAmelioree) tortueProprio.tortueLaPlusProche();
            if (proche != null) {
                if (proche != last && tortueProprio.distTortue(proche) < 15) {
                    System.out.println(tortueProprio.getNom() + " : Tiens " + proche.getNom() + ", prend la balle !!!");

                    last = tortueProprio;
                    tortueProprio = proche;
                    
                    balle.setNouvelleProprio(tortueProprio);

                }
            }
        }
    }


    /**
     * La fonction du thread
     */
    @Override public void run() {lancerPartieNormal();}

    /**
     * Lance la partie
     */
    public void lancerPartie() {
        Thread tmp = new Thread(this);
        tmp.start();
    }

    /** 
     * Arrete la partie
     */
    public void stop() {
        nbrJoueurs = 0;
        finPartie = true;
    }



    /**
     * Calcule un angle de virage pour eviter les bord.
     * @param t La tortue en mouvement.
     * @return L'angle.
     */

    public int getAnglePourEviterBord(Tortue uneTortue) {

        int val = (int) (-40 + Math.random() * 80);
        if(uneTortue.getX() < 10)
        {
            if(uneTortue.getDir() < 359 || uneTortue.getDir() > 181)
            {
                val = 30;
            }
        }
        if(uneTortue.getX() > feuille.drawingImage.getWidth(feuille)-10)
        {
            if(uneTortue.dir > 0 || uneTortue.dir < 179)
            {
                val = 30;
            }
        }
        if(uneTortue.getY() < 10)
        {
            if(uneTortue.getDir() > 271 || uneTortue.getDir() < 89)
            {
                val = 30;
            }
        }
        if(uneTortue.getY() > feuille.drawingImage.getHeight(feuille)-10)
        {
            if(uneTortue.dir > 91 || uneTortue.dir < 269)
            {
                val = 30;
            }
        }

        return val;
    }

}