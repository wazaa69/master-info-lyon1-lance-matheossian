package tortuesmanager;

import java.util.ArrayList;

/**
 * Une tortue équipière
 */
public class TortueEquipe extends TortueAmelioree {


    //######################################################################################################      ATTRIBUTS

    /*
     * Le nom de l'équipe de la tortue
     */
    private String nomEquipe;

    //######################################################################################################      CONSTRUCTEURS

    /**
     *
     * @param feuille la feuille de dessin
     * @param nom le nom de la tortue
     * @param nbrJoueurs le nombre de joueurs (pour le nom : "nomNBRJR")
     * @param utilisationCrayon un booléen
     */
    public TortueEquipe(FeuilleDessin feuille, String nom, int nbrJoueurs, boolean utilisationCrayon) {

        super(feuille, nom);
        crayon = utilisationCrayon;
         listeAmis  = new ArrayList<TortueAmelioree>();
         nomEquipe = nom;
        if(nom.equals(""))
            this.nom = this.getClass().getName() + this.getListeAmis().size();
        else this.nom = nom;

        this.nom = nom + "-" + nbrJoueurs;
        saluer = false;

        
    }

    //######################################################################################################      ACCESSEURS


    /**
     * Récupère le nom de l'équipe
     * @return récupère le nom de l'équipe
     */
    public String getNomEquipe() {return nomEquipe;}

    /*
    * Ajoute les coéquipières de la tortue dans sa liste
    * @param liste une liste de tortues coéquipières
    */
   public void ajouterDesAmies(ArrayList<TortueEquipe> liste)
    {
       for(int i = 0; i < liste.size(); i++)
            this.ajouterUneAmie(liste.get(i));
    }



//######################################################################################################      MUTATEURS



   /**
    * Déplace la tortue proriétaire de la balle
    * @param dist la distance que al tortue doit parcrourir
    * @param distanceEvitement la distance entre chaque tortue
    * @param equipeEnnemie la liste des tortues ennemies
    */
    public void deplacementTortueProprioBalle(int dist, int distanceEvitement, ArrayList<TortueEquipe> equipeEnnemie){
  /* Variables ------------------------------------->*/

        int newX = 0, newY = 0; //les prochaines coordonnées
        int angle = 0; //la direction
        boolean placeDispo = true; //aucune tortue ennemie aux nouvelles coordonnées ?
         TortueEquipe uneTortue;
         int meilleurAngle = 0;


        /*------------------------------------------------->*/


       for(int nbrEssais = 0; nbrEssais <= 5; nbrEssais++) {


            angle = dir;
            if(Math.random() > 0.5)
                angle += (int) (Math.random() * 90);
            else angle -= (int) (Math.random() * 90);

          
            //Calcul de la nouvelle position, après déplacement
            newX = (int) Math.round(getX()+dist*Math.cos(convDegGrad*angle));
            newY = (int) Math.round(getY()+dist*Math.sin(convDegGrad*angle));


            placeDispo = true; //pour le moment la place est disponible

            for(int i=0; i < equipeEnnemie.size(); i++){

                uneTortue = equipeEnnemie.get(i);

                if((distPoint(uneTortue.getX(), uneTortue.getY(), newX, newY) <= distanceEvitement && uneTortue != this) || emplacementValide(newX,newY) ){
                    placeDispo = false;
                    
                }

            }

            if (placeDispo)
            {
              meilleurAngle = angle;
              
              break;

            }
        }

         for (int i = 0; i < 5 ; i++){
             if(!emplacementValide(newX,newY))
             {
                     angle = dir;
                if(Math.random() > 0.5)
                    angle += (int) (Math.random() * 90);
                else angle -= (int) (Math.random() * 90);

                newX = (int) Math.round(getX()+dist*Math.cos(convDegGrad*angle));
                newY = (int) Math.round(getY()+dist*Math.sin(convDegGrad*angle));

             }
            else
             {
                 break;
             }
        }

         if (!placeDispo)
         { 
             meilleurAngle = angle;
           
         }

          avancer(meilleurAngle, newX, newY);
    }


    /**
     * Surcharge : teste si lemplacement est valide (si la tortue ne sort pas de la feuille)
     * @param newX coordonnée en abscisse
     * @param newY coordonnée en ordonnée
     * @return retourne vrai si l'emplacement est bon, faux sinon
     */
    @Override
      public boolean emplacementValide(int newX, int newY){

        int largeurTerrain = feuille.drawingImage.getWidth(feuille)-10;
        int hauteurTerrain = feuille.drawingImage.getHeight(feuille)-10;

        int distBord = 50;

        if((newX <= distBord )|| (newX >= largeurTerrain )||( newY <= distBord )|| (newY >= hauteurTerrain))
        {


            return false;
        }
        return true;
    }




    /**
     * Fait avance la tortue (sans vérification)
     * @param angle l'angle de rotation (la direction)
     * @param newX coordonnée en abscisse
     * @param newY coordonnée en ordonnée
     */
     public void avancer(int angle, int newX, int newY)
    {

        if (crayon) {

            feuille.getGraphics().setColor(decodeColor(traitCouleur));
            feuille.getGraphics().drawLine(getX(),getY(),newX,newY);
        }

             setX(newX);
             setY(newY);

             dir = angle;



     }



    /*
     * Recherche la tortue amie la plus proche
     * @return retourne la tortue améliorée amie la plus proche
     */
     @Override
     public TortueAmelioree tortueAmieLaPlusProche(){

        TortueAmelioree uneTortue = null;
        TortueAmelioree tortueProche = null;


        int distance = 0;
        int distMinimal = 10000; //une grande valeur


        for(int i=0; i < getListeAmis().size(); i++){

            uneTortue = getListeAmis().get(i);
            distance = distPoint(getX(), getY(), uneTortue.getX(), uneTortue.getY());

            if(distance < distMinimal && uneTortue != this){
                distMinimal = distance;
                tortueProche = uneTortue;
             }
        }
        return tortueProche;
    }


}
