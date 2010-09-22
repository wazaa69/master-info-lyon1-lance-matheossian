package tortuesmanager;


/**
 * Une équipe de tortue avec un nom, une couleur et une stratégie.
 */
public class TortueEquipere extends TortueAmelioree {

    private int comportement; /** comportement de la tortue, -1;0;1 défensif/def-attaque/attaquant */

    public TortueEquipere(FeuilleDessin feuille, String nom, int nbrJoueurs) {

        super(feuille, nom);

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
