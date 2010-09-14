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


   /**
    * Retourne la distance entre la tortue et un point (x,y)
    * @param x coordonnee en abscisse
    * @param y coordonnee en ordonnée
    * @return distance entre la tortue et le point
    */
   public int distPoint(int x, int y)
   {
       return (int) Math.round(Math.sqrt(Math.pow((x - this.x),2) + Math.pow((y - this.y),2)));
   }


   /**
    * Calcul la distance entre deux tortues
    * @param tortue la deuxieme tortue
    * @return la distance entre les deux tortues
    */
   public int distTortue(Tortue tortue)
   {
       int coord_x = tortue.x;
       int coord_y = tortue.y;
       return distPoint(coord_x,coord_y);
   }

   
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
   public void listerAmis(){
        for(int i=0; i < listeAmis.size(); i++){
            System.out.println(listeAmis.get(i).nom);
        }
   }

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
    * Fait avancer la tortue sur une certaine distance
    * TODO :
    * - prendre en compte les bords
    * - la tortue ne doit pas être à la même position qu'une autre
    * - si la tortue ne bouge pas, rien ajouter à la liste d'amis
    * @param dist la distance à parcourir en pixel
    */
    @Override
    void avancer(int dist)
    {
        Graphics g = feuille.getImageGraphics();

        int newX = (int) Math.round(x+dist*Math.cos(convDegGrad*dir));
        int newY = (int) Math.round(y+dist*Math.sin(convDegGrad*dir));
        if (crayon) {
                g.setColor(feuille.decodeColor(coul));
                g.drawLine(x,y,newX,newY);
        }
        
        x = newX;
        y = newY;

        ajouterTortues();

        //if (getShowTurtle())
        //  this.drawTurtle(g);
        // System.out.println("dir: " + dir);
        feuille.drawIt();
        
        
    }

}
