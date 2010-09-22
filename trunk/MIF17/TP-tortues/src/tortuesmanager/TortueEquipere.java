package tortuesmanager;


/**
 * Une équipe de tortue avec un nom, une couleur et une stratégie.
 */
public class TortueEquipere extends TortueAmelioree {

    private int strat; /** la stratégie de jeu, -1;0;1 défensif/def-attaque/attaquant */

    public TortueEquipere(FeuilleDessin feuille, String nom, int nbrJoueurs) {

        super(feuille, nom);

        TortueAmelioree uneTortue;

            this.nom = nom + "-" + nbrJoueurs;
            saluer = false;
        }

    public void passeADix(){

    }

    public void deplacementSansBalle(){

    }
    
    public void deplacementAvecBalle(){

    }
    
    public void faitLaPasse(){

    }
    
    public void priseDeBalle(){

    }

}
