package tortuesmanager;

import java.util.ArrayList;


public class Controleur {



    SimpleLogo simpleLogo;

    private Tortue courante; /** La tortue en cours de déplacement */
    int procTortueAmelioree = 0; /** stopper ou lancer la procédure pour les tortues améliorées */

    /** la liste des tortues enregistrees */
    ArrayList<Tortue> tortues = new ArrayList<Tortue>();

    /** La liste de toute les tortues améliorées */
    ArrayList<TortueAmelioree> tortueAmelioree= new ArrayList<TortueAmelioree>();




    public Controleur(){}

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

        //Création des N tortues améliorées
        for(int i=0; i < nbrTortues; i++) {
            TortueAmelioree tmp = new TortueAmelioree(simpleLogo.getFeuille(),"");
            courante=tmp;
            courante.leverCrayon();
            courante.droite(16*i);
            courante.avancer(80);
        }

        /* tester la liste des amis
         TortueAmelioree tmp =(TortueAmelioree) courante;
        tmp.listerAmis();*/


        /*
        int i=0;
        while(true){
            courante=(TortueAmelioree) feuille.TortueAmelioree.get(i);
            courante.deplacementsHasardeux();

            try {
                    Thread.sleep(100);
            } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }

            i++;
            if(i%feuille.TortueAmelioree.size() == 0) i=0;
        }
         */

    }

   /* on pourra delete cette fonction quand on aura trouvé comment déplacer les tortu et stoper leur mouvement*/
   void bougerTortAmel(){
            TortueAmelioree tmp = tortueAmelioree.get(procTortueAmelioree);
            courante = (Tortue) tmp;
            tmp.deplacementAuHasard(15);
            procTortueAmelioree++;
            if(procTortueAmelioree%tortueAmelioree.size() == 0) procTortueAmelioree=0;
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


    public SimpleLogo getSimpleLogo() {
        return simpleLogo;
    }

    public void setSimpleLogo(SimpleLogo simpleLogo) {
        this.simpleLogo = simpleLogo;
    }

    public Tortue getCourante() {
        return courante;
    }

    public void setCourante(Tortue courante) {
        this.courante = courante;
    }

}
