package tortuesmanager;

import java.awt.Graphics;
import java.util.ArrayList;



public class TortueAmelioree extends Tortue {

    String nom;     /** Le nom de la tortue */

    /** La liste des tortues amies  */
    ArrayList<TortueAmelioree> listeAmis= new ArrayList<TortueAmelioree>();

   /**
    * Constructeur
    * @param tortueAmelioree la liste de toutes les tortues améliorée (du constructeur)
    * @param f la feuille de dessin
    * @param name le nom de la tortue
    */
    public TortueAmelioree(FeuilleDessin f, String name){

        super(f);

        if(name.equals("")) this.nom = "torAm" + feuille.tortueAmelioree.size();
        else this.nom = name;

        feuille.tortueAmelioree.add(this);
        
    }


   /**
    * Ajoute une tortue à la liste d'amis (distance entre les 2 tortues <= 15px)
    */
    /*
   public void ajouterTortues() {

        int nbrTortues = feuille.tortueAmelioree.size();

        //si toutes les tortues ne sont pas encore ses amis
        if(listeAmis.size() < nbrTortues){
            for(int i=0; i < nbrTortues; i++){
                TortueAmelioree tmp = feuille.tortueAmelioree.get(i);

                if(this != tmp){

                    int distance = this.distTortue(tmp);

                    //si la distance est <= 15px et la tortue n'est pas dans la liste
                    if((distance <= 15) && (!listeAmis.contains(tmp))){
                        listeAmis.add(tmp); //tortue ajoutée
                        System.out.println(this.nom + " a ajoutée " + tmp.nom + " en amie.");
                    }
                    else if(distance <= 15)
                    {
                      decalerTortueVoisine(tmp);
                    }
  
                }
            }
       }
    }
    */






   
   /**
    * Décale une tortue voisine dans le sense inverse de la tortue courante
    * @param t une tortue améliorée
    */
    void decalerTortueVoisine(TortueAmelioree t){
        t.dir=this.dir;
        Tortue tmp = (Tortue) t;
        System.out.println(t.nom + " c'est déplacé de 15 pixels");
        tmp.avancer(15); //pour ne pas relancer le decalage des autres tortues
    }


   /**
    * Lister les amies de cette tortue
    */
    /*
   public void listerAmis(){
        for(int i=0; i < listeAmis.size(); i++){
            System.out.println(listeAmis.get(i).nom);
        }
   }
   */

   /**
    * Ajouter une liste de tortues à la liste d'amis
    * @param t la liste de tortues amélioréees
    */
    public void ajouterListeTortues(ArrayList<TortueAmelioree> t){listeAmis.addAll(t);}
    
   /**
    * Retire une tortue de la liste d'amis
    * @param t la tortue amélioréee
    */
    public void enleverTortue(TortueAmelioree t){listeAmis.remove(t);}



    /**
     * Déplacement aléatoire, la position initial équivaut à un cercle
     * @param dist la distance à parcourire
     */
    public void deplacementAuHasard(int dist)
    {

        int angle;

        angle = (int)(Math.random()*360);
        if(Math.random() > 0.5)
            droite(angle);
        else gauche(angle);

        avancer(dist);


        TortueAmelioree uneTortue = null;
        ArrayList<TortueAmelioree> tortuesADeplacer = new ArrayList<TortueAmelioree>();

        for(int i=0; i < feuille.getTortueAmelioree().size(); i++){

            uneTortue = feuille.getTortueAmelioree().get(i);

            if(this != uneTortue){
                if(distPoint(uneTortue.x, uneTortue.y, this.x, this.y) <= 15)
                    //tortuesADeplacer.add(uneTortue);
                    uneTortue.pousserTortue(dist);
            }

        }

    }

    /**
     * Déplacement aléatoire, la position initial équivaut à un cercle
     * @param dist la distance à parcourire
     */
    public void pousserTortue(int dist){

        int newX, newY, angle, nbrEssais = 0;
        boolean placeDispo;

        TortueAmelioree uneTortue = null;
        ArrayList<TortueAmelioree> tortuesADeplacer = new ArrayList<TortueAmelioree>();

        while(nbrEssais <= 20){

            angle = (int) (Math.random() * 360);

            placeDispo = true;

            newX = (int) Math.round(x+dist*Math.cos(convDegGrad*angle));
            newY = (int) Math.round(y+dist*Math.sin(convDegGrad*angle));

            //si il y a une tortue à moins de 15px on sort de la boucle
            for(int i=0; i < feuille.getTortueAmelioree().size(); i++){

                uneTortue = feuille.getTortueAmelioree().get(i);
       
                if(this != uneTortue){
                    if(distPoint(uneTortue.x, uneTortue.y, this.x, this.y) <= 30)
                        placeDispo = false;
                }

            }

            if(placeDispo) {
                dir = angle; //on lui attribut la nouvelle dirrection
                avancer(dist); //on fait avancer la tortue dans cette dirrection
                break;
            }
            else if (nbrEssais == 20){
                avancer(dist);
                uneTortue.pousserTortue(dist);
                break;
            }

            nbrEssais++;
        }




    }

}
