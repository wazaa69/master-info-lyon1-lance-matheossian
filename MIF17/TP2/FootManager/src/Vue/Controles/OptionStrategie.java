package Vue.Controls;

/**
 * Cette classe représente une option de la liste des stratégies
 * Elle permet de stocker le numéro de la stratégie
 */
public class OptionStrategie {

    private String nomStrategie;
    private int valeur;

    public OptionStrategie(String nomStrategie, int valeur) {
        this.nomStrategie = nomStrategie;
        this.valeur = valeur;
    }

    @Override
    public String toString(){
        return nomStrategie;
    }

    public int getValeur() {
        return valeur;
    }

}
