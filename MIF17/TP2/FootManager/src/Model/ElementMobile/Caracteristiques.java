package Model.ElementMobile;

/**
 * Liste des caractéristiques d'un joueur.
 */
public class Caracteristiques {

    /* Caractéristiques personnelles, non modifiables */
    private int distDep;  /** c'est la distance maximal qui peut être parcourue en un deplacement */
    private int distMaxTir; /** la distance maximal que le joueur peut tirer */
    private int distMinPrendreBalle; /** distance minimum pour prendre la balle */
    private double probaRecupBallon; /** probabilité de récupérer le ballon */


    public Caracteristiques() {
        distDep = 1;
        distMaxTir = 80;
        distMinPrendreBalle = 25;
        probaRecupBallon = Math.random();
    }



/******************************  GETTER/SETTERS  ******************************/


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


}
