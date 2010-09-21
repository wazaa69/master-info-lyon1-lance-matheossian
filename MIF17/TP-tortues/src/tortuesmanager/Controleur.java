package tortuesmanager;


public class Controleur {


 //######################################################################################################      ATTRIBUTS


    private SimpleLogo simpleLogo; /** Référence sur la Vue */


    //Procédure 1
    private Tortue courante; /** La tortue en cours de déplacement */
    private int procTortueAmelioree = 0; /** stopper ou lancer la procédure pour les tortues améliorées */
    
    //Procédure 2
    private JeuDeBalle jeuDeBalle;

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
    public void creerTortueDessin(){
        //courante = new Tortue(simpleLogo.getFeuille(), true);
    }


    /**************************************/
    /* Les différentes figues réalisables */
    /**************************************/

    public void carre(){courante.carre();}
    public void poly(){courante.poly(60,8);}
    public void spiral(){courante.spiral(50,40,6);}
    public void immeuble(){courante.immeuble();}
    public void asterisque(){courante.asterisque(50,10);}

    
    /**************************************/
    /*         BOUTON-PROCEDURES          */
    /**************************************/
    
    /**
     * Crée N tortues améliorées et les laisse se promener
     * Si une tortue en rencontre une autre, elle lui demande de se pousser
     */
    public void procedureUne(){

        int nbrTortues=10;

        if(getSimpleLogo().getFeuille().getListeTortuesAmeliorees().size() < 10){

            resetVueEtTortues();

            //Création des N tortues améliorées
            for(int i=0; i < nbrTortues; i++) {
                TortueAmelioree uneTortue = new TortueAmelioree(simpleLogo.getFeuille(),"");
                courante = uneTortue;
                courante.leverCrayon();
                courante.droite(16*i);
                courante.avancer(80);
            }
        }

         else{
            TortueAmelioree uneTortue = getSimpleLogo().getFeuille().getListeTortuesAmeliorees().get(procTortueAmelioree);
            courante = (Tortue) uneTortue;
            uneTortue.deplaceHasardEtPousse(30);
            procTortueAmelioree++;
            if(procTortueAmelioree%getSimpleLogo().getFeuille().getListeTortuesAmeliorees().size() == 0) procTortueAmelioree=0;
         }
    }

    
    /**
     * Crée N tortues améliorée ainsi qu'une balle
     * Si une tortue en rencontre une autre, elle l'ajoute à sa liste d'amis
     */
    public void procedureDeux(){
        resetVueEtTortues();
        simpleLogo.creerProc2();
        JeuDeBalle jeuDeBalle = new JeuDeBalle(simpleLogo.getFeuille(), 24);
        jeuDeBalle.start();
    }

    public void stopProcDeux(){
        jeuDeBalle.stop();
        simpleLogo.effProc2();
    }



    /**
     * Crée N tortues améliorée dans 2 équipes ainsi qu'une balle
     */
    public void procedureTrois(){
        resetVueEtTortues();
        //simpleLogo.procedureTrois();
        //jeuEquipe.start();
    }


    /**************************************/
    /*          AUTERS METHODES           */
    /**************************************/

    /**
     * Supprime toutes les tortues existantes dans le Model et Controleur, et
     * repaint la zone de dessin en blanc
     */
    public void resetVueEtTortues(){
        //on 
        simpleLogo.getFeuille().getListeTortues().clear();
        simpleLogo.getFeuille().reset();
        simpleLogo.getFeuille().repaint();
        courante = null;
    }


    /**
     * Efface tout et réinitialise la tortue
     */
    public void effacer()
    {
        simpleLogo.getFeuille().reset();
        simpleLogo.getFeuille().repaint();
    }

    /**
    * Méthode qui ferme la fenêtre
    */
    public void quitter(){System.exit(0);}



}
