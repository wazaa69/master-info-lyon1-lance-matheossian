package tortuesmanager;

import java.awt.Graphics;
import java.util.ArrayList;



public class TortueAmelioree extends Tortue {

    private String nom;     /** Le nom de la tortue */

    /** La liste des tortues amies  */
    private ArrayList<TortueAmelioree> listeAmis= new ArrayList<TortueAmelioree>();

    final int distMin; /** distance minimum entre deux tortues */


   /**
    * Constructeur
    * @param tortueAmelioree la liste de toutes les tortues améliorée (du constructeur)
    * @param f la feuille de dessin
    * @param name le nom de la tortue
    */
    public TortueAmelioree(FeuilleDessin f, String name){

        super(f);

        if(name.equals(""))
            this.nom = "torAm" + feuille.tortueAmelioree.size();
        else this.nom = name;

        distMin = 15;

        feuille.tortueAmelioree.add(this);
        
    }

   /**
    * Retire une tortue de la liste d'amis
    * @param t la tortue amélioréee
    */
    public void enleverTortue(TortueAmelioree t){listeAmis.remove(t);}



    /*-----------------------------------*/
    /*     DEPLACEMENT DES TORTUES
    /*-----------------------------------*/

    /**
     * La tortue se déplace aléatoirement, chaque tortue rencontrée doit se pousser
     * @param dist la distance à parcourire
     */
    public void deplacementAuHasard(int dist)
    {

        //Respect de la distance minimale ?
        int distMinimale = distMin;
        if(dist > distMin) distMinimale = dist;

        //déplacement aléatoire
        int angle = (int)(Math.random()*360);
        if(Math.random() > 0.5) droite(angle); else gauche(angle);
        avancer(distMinimale);

       //System.out.print(">-----DEBUT - Déplacement de " + nom + "\n");

        //pousser les tortues à proximité
        TortueAmelioree uneTortue = null;
        for(int i=0; i < feuille.getTortueAmelioree().size(); i++){

            uneTortue = feuille.getTortueAmelioree().get(i);

            if(this != uneTortue){
                if(distPoint(uneTortue.x, uneTortue.y, this.x, this.y) <= distMin)
                {
                    System.out.print(nom + " salut " + uneTortue.getNom() + " et lui demande de se déplacer !\n");
                    uneTortue.pousserTortue(dir, distMinimale);
                }
            }
        } //fin for

       //System.out.print(">-----FIN - Déplacement de " + nom + "\n");

    }



    /**
     * Déplace aléatoirement une tortue et pousse ses voisines si nécessaire
     * A la différence de la version précédante, la tortue va chercher une place
     * disponible, sans devoir pousser d'autres tortues.
     * @param dir la direction de la tortue pousseuse
     * @param dist la distance à parcourire pour la tortue pousseuse
     */
    public void pousserTortue(int dir, int dist){


        /* Variables ------------------------------------->*/

        int newX, newY; //les prochaines coordonnées
        int angle; //la direction
        boolean placeDispo; //aucune tortue voisine aux nouvelles coordonnées ?

        TortueAmelioree uneTortue = null;
        ArrayList<TortueAmelioree> liste = new ArrayList<TortueAmelioree>();
        ArrayList<TortueAmelioree> tortuesADeplacer = new ArrayList<TortueAmelioree>();

        //OPTIMISATION
        /* le nombre de tortues à pousser pour dégager le chemin */
        int nbrTortuesApousser = feuille.getTortueAmelioree().size();
        /* la meilleur direction en fonction du nombre de tortues à pousser */
        int meilleurAngle = dir;

        /*------------------------------------------------->*/


       for(int nbrEssais = 0; nbrEssais <= 20; nbrEssais++) {

            /* Direction de la tortue "pousseuse" +/-90° */
            angle = dir;
            if(Math.random() > 0.5)
                angle += (int) (Math.random() * 90);
            else angle -= (int) (Math.random() * 90);


            //Calcul de la nouvelle position, après déplacement
            newX = (int) Math.round(x+dist*Math.cos(convDegGrad*angle));
            newY = (int) Math.round(y+dist*Math.sin(convDegGrad*angle));


            placeDispo = true; //pour le moment la place est disponible
            liste.clear(); //on vide la liste de la boucle précédante

            for(int i=0; i < feuille.getTortueAmelioree().size(); i++){

                uneTortue = feuille.getTortueAmelioree().get(i);
       
                if(uneTortue != this){
                    if(distPoint(uneTortue.x, uneTortue.y, newX, newY) <= distMin){
                        placeDispo = false;
                        liste.add(uneTortue); //une tortue en plus
                    }
                }
            }


            if(nbrTortuesApousser > liste.size()){
                nbrTortuesApousser = liste.size();
                meilleurAngle = angle;
                tortuesADeplacer.clear();
                tortuesADeplacer.addAll(liste);
            }

            //déplacement "aléatoire" (à faire avant de pousser les tortues)
            if((placeDispo) || (nbrEssais == 20)){
                dir = meilleurAngle;
                avancer(dist);
            }


            if(placeDispo){break; }
            else if (nbrEssais == 20){

                //aucune place trouvée, on pousse les tortues voisines
                for(int i = 0; i < tortuesADeplacer.size(); i++){
                    uneTortue = tortuesADeplacer.get(i);
                    System.out.print(nom + " salut " + uneTortue.getNom() + " et lui demande de se déplacer !\n");
                    tortuesADeplacer.get(i).pousserTortue(dir,dist);
                }

                break; //on sort de la boucle
            }

        }
    }

    /*-----------------------------------*/
    /*         RUN DU THREAD
    /*-----------------------------------*/


    @Override
    public void run() {
        while(true){
            deplacementAuHasard(dir);
        }
    }

    /*-----------------------------------*/
    /*         GETTERS/SETTERS
    /*-----------------------------------*/

    public String getNom() {return nom;}
    public int getDistMin() {return distMin;}

}
