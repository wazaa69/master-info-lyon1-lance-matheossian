package Model;

public class Joueur {

    private int x; /** Coordonnée polaire */
    private int y; /** Coordonnée polaire */
    private String nom; /** nom du joueur */

    private Equipe monEquipe; /** l'équipe du joueur */

    /**
     * Initialise un joueur avec un nom et son Equipe
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     */
    public Joueur(String nom, Equipe monEquipe) {
        initPosition();
        this.nom = nom;
        this.monEquipe = monEquipe;
    }

    /**
     * Initialise un joueur avec des coordonnées, un nom et une Equipe
     * @param x coordonnées polaires
     * @param y coordonnées polaires
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     */
    public Joueur(int x, int y, String nom, Equipe monEquipe) {
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.monEquipe = monEquipe;
    }


    /**
     * Initialise la position du joueur
     */
    private void initPosition(){
        x = 0;
        y = 0;
    }

}
