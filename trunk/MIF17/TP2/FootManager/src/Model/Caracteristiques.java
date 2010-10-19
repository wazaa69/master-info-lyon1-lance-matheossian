package Model;

/**
 * Liste des caractéristiques d'un joueur.
 * On ne separera pas en deux classes tel que :
 *  - Caractéristiques personnelles
 *  - Caractéristiques de jeu en équipe
 * Car pour le moment, il n'y a pas assez d'attributs.
 */
public class Caracteristiques {

    /* Caractéristiques personnelles, non modifiables */
    private int vitesseMaxDep; /** c'est la distance maximal que peut parcourir le joueur en un deplacement */
    private int distMaxTir; /** la distance maximal d'où le joueur peut tirer */
    private int distMinPrendreBalle; /** distance minimum pour prendre la balle */


    /* Caractéristiques de jeu en équipe, dépendant de la stratégie */
    private boolean defenseur; /** vrai si le joueur est en défense, faux sinon */
    private boolean attaquant; /** vrai si le joueur est en attaque, faux sinon */
    private boolean jeuSolitaire; /** vrai si le joueur joue solitaire, faux sinon */


/******************************  GETTER/SETTERS  ******************************/
    
    public boolean isAttaquant() {
        return attaquant;
    }

    public void setAttaquant(boolean attaquant) {
        this.attaquant = attaquant;
    }

    public boolean isDefenseur() {
        return defenseur;
    }

    public void setDefenseur(boolean defenseur) {
        this.defenseur = defenseur;
    }

    public int getDistMaxTir() {
        return distMaxTir;
    }

    public void setDistMaxTir(int distMaxTir) {
        this.distMaxTir = distMaxTir;
    }

    public int getDistMinPrendreBalle() {
        return distMinPrendreBalle;
    }

    public void setDistMinPrendreBalle(int distMinPrendreBalle) {
        this.distMinPrendreBalle = distMinPrendreBalle;
    }

    public boolean isJeuSolitaire() {
        return jeuSolitaire;
    }

    public void setJeuSolitaire(boolean jeuSolitaire) {
        this.jeuSolitaire = jeuSolitaire;
    }

    public int getVitesseMaxDep() {
        return vitesseMaxDep;
    }

    public void setVitesseMaxDep(int vitesseMaxDep) {
        this.vitesseMaxDep = vitesseMaxDep;
    }


}
