package tortuesmanager;

import java.awt.Color;

/**
 * Controleur du Jeu
 */
public class Controleur {


 //######################################################################################################      ATTRIBUTS


    private SimpleLogo simpleLogo; /** Référence sur la Vue */

    private Tortue courante;  /** Dessins : La tortue de base */

    private JeuPousse jeuPousse; /** Procédure 1 */

    private JeuDeBalle jeuDeBalle; /** Procédure 2 */

    private JeuEquipe jeuEquipe; /** Procédure 3 */

 //######################################################################################################      CONSTRUCTEURS

    /**
     * Controleur
     */
    public Controleur(){}

 //######################################################################################################      ACCESSEURS

    /**
     * Récupérer la tortue courante
     * @return retourne la tortue courante
     */
    public Tortue getCourante() { return courante; }

 //######################################################################################################      MUTATEURS

    /**
     * Mise à joure de l'attribut simpleLogo
     * @param simpleLogo la valeur à assigner à l'attribut simpleLogo
     */
    public void setSimpleLogo(SimpleLogo simpleLogo) { this.simpleLogo = simpleLogo; }

        
 //######################################################################################################      METHODES

    /**
     * Initialise tout ce qu'il faut pour pouvoir dessiner
     */
    private void procedureBasePrDessin(){
        if(courante == null){
            reset();

            courante = new Tortue(simpleLogo.getFeuille(), true, simpleLogo.getAffichageInformationsTortues());
            simpleLogo.setBarreOutilsVisible(true);
        }
    }

    
    /* ************************************ */
    /* Les différentes figues réalisables   */
    /* ************************************ */

    /**
     * Déssine un carré
     */
    public void carre(){procedureBasePrDessin(); courante.carre();}

    /**
     * Dessine un polygone
     */
    public void poly(){procedureBasePrDessin(); courante.poly(60,8);}

    /**
     * Dessine une spirale
     */
    public void spiral(){procedureBasePrDessin(); courante.spiral(50,40,6);}

    /**
     * Dessine un immeuble
     */
    public void immeuble(){procedureBasePrDessin(); courante.immeuble();}

    /**
     * Dessine un astérisque
     */
    public void asterisque(){procedureBasePrDessin(); courante.asterisque(50,10);}

    
    /**************************************/
    /*         BOUTON-PROCEDURES          */
    /**************************************/

    /**
     * Crée la tortue de dessin et dessine un immeuble
     */
    public void procedureZero(){
        procedureBasePrDessin();
        immeuble();
    }

    /**
     * Crée N tortues améliorées et les laisse se promener
     * Si une tortue en rencontre une autre, elle lui demande de se pousser
     * Il faut appuyer plusieurs fois sur le bouon pour changer la tortue
     * courante.
     */
    public void procedureUne(){
        simpleLogo.setBarreOutilsVisible(false);
        if(jeuPousse == null){
            reset();
             simpleLogo.getAffichageInformationsTortues().setText("");
             simpleLogo.getAffichageInformationsTortues().setForeground(Color.blue);
    
            jeuPousse = new JeuPousse(simpleLogo.getFeuille(), 15, simpleLogo.getAffichageInformationsTortues());
            jeuPousse.lancerPartie();
        }
        else jeuPousse.lancerPartie();
    }


    /**
     * Crée N tortues améliorées ainsi qu'une balle.
     * Les tortues se font des passes.
     */
    public void procedureDeux(){
        reset();
        simpleLogo.setBarreOutilsVisible(false);
         simpleLogo.getAffichageInformationsTortues().setText("");
         simpleLogo.getAffichageInformationsTortues().setForeground(Color.red);

        jeuDeBalle = new JeuDeBalle(simpleLogo.getFeuille(), 30, simpleLogo.getAffichageInformationsTortues());
        jeuDeBalle.start();
    }

    /**
     * Crée une balle et 2 équipes de N tortueEquipe.
     * Les tortues jouent à la passe à dix, si les 10 passes sont réussies,
     * l'équipe gagne 1 point, sinon le score repart à zéro.
     */
    public void procedureTrois(){
        reset();
        simpleLogo.setBarreOutilsVisible(false);
        simpleLogo.getAffichageInformationsTortues().setText("");
        simpleLogo.getAffichageInformationsTortues().setForeground(Color.MAGENTA);

        jeuEquipe = new JeuEquipe(simpleLogo.getFeuille(), 30, 30,"eVerte","eBleu", simpleLogo.getAffichageInformationsTortues());
        jeuEquipe.start();
    }



    /**************************************/
    /*          AUTERS METHODES           */
    /**************************************/

    /**
     * Supprime toutes les tortues existantes dans le Model et Controleur,
     * Repaint la zone de dessin en blanc,
     * Stop les thread de Jeu en cours.
     */
    private void reset(){
        simpleLogo.getFeuille().getListeTortues().clear();
        simpleLogo.getFeuille().reset();
        simpleLogo.getFeuille().repaint();
        courante = null;

        //stop les threads
        if(jeuDeBalle != null) {jeuDeBalle.setFinPartie(true); jeuDeBalle = null;}
        if(jeuEquipe != null) {jeuEquipe.setFinPartie(true); jeuEquipe = null;}
    }


    /**
     * Recrée le support de dessin
     */
    public void effacer()
    {
        simpleLogo.getFeuille().reset();
        simpleLogo.getFeuille().repaint();
    }

    /**
    * Ferme la fenêtre
    */
    public void quitter(){System.exit(0);}



}
