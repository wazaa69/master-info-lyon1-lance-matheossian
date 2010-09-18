package tortuesmanager;

import java.util.ArrayList;


public class Controleur {


 //######################################################################################################      ATTRIBUTS


    private SimpleLogo simpleLogo;

    private Tortue courante; /** La tortue en cours de déplacement */
    int procTortueAmelioree = 0; /** stopper ou lancer la procédure pour les tortues améliorées */

 //######################################################################################################      CONSTRUCTEURS
    
    public Controleur(){}

 //######################################################################################################      ACCESSEURS

    public SimpleLogo getSimpleLogo(){ return simpleLogo; }

    public Tortue getCourante() { return courante; }
        
 //######################################################################################################      MUTATEURS

    public void setSimpleLogo(SimpleLogo simpleLogo) { this.simpleLogo = simpleLogo; }
        
    public void setCourante(Tortue courante) { this.courante = courante; }
        
 //######################################################################################################      METHODES

    /**
     *  Création de la tortue qui dessine
     */
    public void creertortueDessin(){courante = new Tortue(simpleLogo.getFeuille());}


    /**
    * Les différentes figues réalisables
    */
    void carre(){courante.carre();}
    void poly(){courante.poly(60,8);}
    void spiral(){courante.spiral(50,40,6);}
    void immeuble(){courante.immeuble();}
    void asterisque(){courante.asterisque(50,10);}

    


    /**************************************/
    /*         BOUTON-PROCEDURES          */
    /**************************************/
    
    /**
     * Crée N tortues améliorée et les laisse se promener
     * Si une tortue en rencontre une autre, elle l'ajoute à sa liste d'amis
     */
    void tortueAmelioree(){

        int nbrTortues=10;

        if(getSimpleLogo().getFeuille().getListeTortuesAmeliorees().size() < 10){
            //Création des N tortues améliorées
            for(int i=0; i < nbrTortues; i++) {
                TortueAmelioree uneTortue = new TortueAmelioree(simpleLogo.getFeuille(),"");
                courante = uneTortue;
                courante.leverCrayon();
                courante.droite(16*i);
                courante.avancer(80);
            }
        }

        
        TortueAmelioree uneTortue = getSimpleLogo().getFeuille().getListeTortuesAmeliorees().get(procTortueAmelioree);
        courante = (Tortue) uneTortue;
        uneTortue.deplacementAuHasard(30);
        procTortueAmelioree++;
        if(procTortueAmelioree%getSimpleLogo().getFeuille().getListeTortuesAmeliorees().size() == 0) procTortueAmelioree=0;
        

    }



    /**
     * Efface tout et réinitialise la tortue
     */
    void effacer()
    {
        simpleLogo.getFeuille().reset();
        courante.reset();
        simpleLogo.getFeuille().repaint();
    
    }

    /**
    * Méthode qui ferme la fenêtre
    */
    void quitter(){System.exit(0);}



}
