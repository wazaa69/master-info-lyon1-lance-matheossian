package tortuesmanager;

import java.util.ArrayList;

/**
 * Crée N tortues améliorées et les laisse se promener
 * Si une tortue en rencontre une autre, elle lui demande de se pousser
 * Il faut appuyer plusieurs fois sur le bouon pour changer la tortue
 * courante.
 */
public class JeuPousse implements JeuInterface {

    FeuilleDessin feuille; /** la feuille de dessin */

    TortueAmelioree courante; /** la tortue courante */

    ArrayList<TortueAmelioree> liste = new ArrayList<TortueAmelioree>(); /** la liste des tortues */

    /**
     * Constructeur
     * @param feuille la feuille de dessin
     * @param nbr le nombre de tortues à utiliser
     */
    public JeuPousse(FeuilleDessin feuille, int nbr) {

        this.feuille = feuille;

        TortueAmelioree uneTortue = null;

        //Création des N tortues améliorées
        for(int i=0; i < nbr; i++) {
            uneTortue = new TortueAmelioree(feuille,"Tortue" + feuille.getListeTortues().size());
            uneTortue.leverCrayon();
            uneTortue.setSaluer(true);
            uneTortue.droite(16*i);
            uneTortue.avancer(80);
        }

        liste.addAll(feuille.getListeTortuesAmeliorees());

        //Toutes les tortues doivent se connaître
        for(TortueAmelioree t : liste){
             t.ajouterDesAmies();
        }

        if(!liste.isEmpty())
            courante = liste.get(0);

    }

    /**
     * Lance la partie
     */
    public void lancerPartie() {

        
        if(courante != null){

            //position de la tortue courante dans le tableau
            int encours = liste.indexOf(courante)+1;

            if(encours%liste.size() != 0){
                courante = liste.get(encours);
                courante.deplaceHasardEtPousse(5);
            }
            else{
                if(!liste.isEmpty()){
                    courante = liste.get(0);
                    courante.deplaceHasardEtPousse(5);
                }
            }

            feuille.drawIt();
        }

    }

    /**
     * lance les tortues sur le terrain (non utilisée).
     */
    public void placerTortuesTerrain() {}
    
}
