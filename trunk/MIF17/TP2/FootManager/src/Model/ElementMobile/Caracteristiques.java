package Model.ElementMobile;

/**
 * Liste des caractéristiques d'un joueur.
 * On ne separera pas en deux classes tel que :
 *  - Caractéristiques personnelles
 *  - Caractéristiques de jeu en équipe
 * Car pour le moment, il n'y a pas assez d'attributs.
 */
public class Caracteristiques {

    /* Caractéristiques personnelles, non modifiables */
    private int distDep;  /** c'est la distance maximal qui peut être parcourue en un deplacement */
    private int distMaxTir; /** la distance maximal d'où le joueur peut tirer */
    private int distMinPrendreBalle; /** distance minimum pour prendre la balle */
    private double probaRecupBallon; /** probabilité de récupérer le ballon */
    private int boost; /** acceleration du joueur quand il a le ballon */


    /* Caractéristiques de jeu en équipe, dépendant de la stratégie */
    private boolean defenseur; /** vrai si le joueur est en défense, faux sinon */
    private boolean attaquant; /** vrai si le joueur est en attaque, faux sinon */

    public Caracteristiques() {
        distDep = 1;
        distMaxTir = 80;
        distMinPrendreBalle = 25;
        probaRecupBallon = Math.random();
        boost = (int) (Math.random()*5);
        defenseur = false;
        attaquant = false;
    }






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

    public int getDistDep() {
        return distDep;
    }

    public void setDistDep(int distDep) {
        this.distDep = distDep;
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

    public double getProbaRecupBallon() {
        return probaRecupBallon;
    }

    public void setProbaRecupBallon(double probaRecupBallon) {
        this.probaRecupBallon = probaRecupBallon;
    }

    public void setActiveBoost() {
        distDep += distDep*boost;
    }

    public void setEneleveBoost() {
        distDep -= distDep*boost;
    }

}
