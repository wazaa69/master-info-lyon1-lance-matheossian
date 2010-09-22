package tortuesmanager;


/**
 * Une équipe de tortue avec un nom, une couleur et une stratégie.
 */
public class Equipe extends TortueAmelioree {

    private int strat; /** la stratégie de jeu, -1;0;1 défensif/def-attaque/attaquant */

    public Equipe(FeuilleDessin feuille, String nom, int nbrJoueurs) {

        super(feuille, nom);

        TortueAmelioree uneTortue;

        for(int i = 0; i < nbrJoueurs; i++){
            uneTortue = new TortueAmelioree(feuille, nom+"-"+ nbrJoueurs);
            uneTortue.setSaluer(false);
        }
        
    }

    

}
