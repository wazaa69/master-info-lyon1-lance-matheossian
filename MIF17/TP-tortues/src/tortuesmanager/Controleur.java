package tortuesmanager;

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
     * Récupération de l'attribut simpleLogo
     * @return retourne l'attribut simpleLogo
     */
    public SimpleLogo getSimpleLogo(){ return simpleLogo; }

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


    /* ************************************ */
    /* Les différentes figues réalisables   */
    /* ************************************ */

    /**
     * Déssine un carré
     */
    public void carre(){courante.carre();}

    /**
     * Dessine un polygone
     */
    public void poly(){courante.poly(60,8);}

    /**
     * Dessine une spirale
     */
    public void spiral(){courante.spiral(50,40,6);}

    /**
     * Dessine un immeuble
     */
    public void immeuble(){courante.immeuble();}

    /**
     * Dessine un astérisque
     */
    public void asterisque(){courante.asterisque(50,10);}

    
    /**************************************/
    /*         BOUTON-PROCEDURES          */
    /**************************************/

    /**
     * Crée la tortue de dessin et dessine un immeuble
     */
    public void procedureZero(){
        reset();
        courante = new Tortue(simpleLogo.getFeuille(), true);
        simpleLogo.setBarreOutilsVisible(true);
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
            jeuPousse = new JeuPousse(simpleLogo.getFeuille(), 10);
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
        jeuDeBalle = new JeuDeBalle(simpleLogo.getFeuille(), 30);
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
        jeuEquipe = new JeuEquipe(simpleLogo.getFeuille(), 30, 30,"EA","EB");
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
    public void reset(){ 
        simpleLogo.getFeuille().getListeTortues().clear();
        simpleLogo.getFeuille().reset();
        simpleLogo.getFeuille().repaint();
        courante = null;

        //stop les threads
        if(jeuDeBalle != null) jeuDeBalle.finPartie = true;
        if(jeuEquipe != null) jeuEquipe.finPartie = true;
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
