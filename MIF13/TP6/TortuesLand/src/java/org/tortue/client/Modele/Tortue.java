package org.tortue.client.Modele;

/**
 * Une tortue
 */
public class Tortue {

    private String nom;
    private int numero;

    private Point coordonnees;
    private float angle;

    int distDep;
 
  
    public Tortue(String nom, int numero, int x, int y) {
        this.nom = nom;
        this.numero = numero;
        coordonnees = new Point(x,y);
        distDep = 15;
    }



    //------------------------------DEPLACEMENT------------------->

    public void avancer(Terrain unTerrain){

        Point tmpCoordonnees = coordApresDep();

        if(isEmplacementValide(unTerrain, tmpCoordonnees))
            coordonnees.clone(tmpCoordonnees);
        /*else
            angle = (angle + 180) % 360;  */ //demi-tour
    }


     /**
     * Calcul des nouvelles coordonnées selon un point de départ et un angle
     * @param x point de départ en abscisse
     * @param y point de départ en ordonnée
     * @param angle l'angle de départ
     * @return retourne la position du nouveau point
     */
    protected  Point coordApresDep(){

        int x = coordonnees.getX();
        int y = coordonnees.getY();

        return new Point(
                (int) Math.round(x + distDep * Math.cos(Math.toRadians(angle))),
                (int) Math.round(y + distDep * Math.sin(Math.toRadians(angle)))
                );
    }


     /*
     * Teste si le point ne sort pas du terrain
     * @param coordonnees
     * @return retourne vrai si l'emplacement est valide, faux sinon
     */
    protected boolean isEmplacementValide(Terrain unTerrain,Point coordonnees){

        int x = coordonnees.getX();
        int y = coordonnees.getY();

        if(( x < 0 || x > unTerrain.getLongueur()) || ( y < 0  || y > unTerrain.getLargeur()))
            return false;

        return true;
    }


    //------------------------------GETTERS/SETTERS------------->

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPosition(int x, int y){
        coordonnees.setXY(x, y);
    }

    public Point getCoordonees(){
        return coordonnees;
    }
    
    
}
