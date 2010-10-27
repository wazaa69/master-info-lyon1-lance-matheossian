package ObservListe;

import Model.Equipe;
import Model.Strategies.Strategie;

/**
 * L'observable pour les listes déroulantes
 */
public interface ObservableComboBox {
    public void ajouterObserveur(ObserveurComboBox obs);
    public void supprimerObserveur();

    /*
     * Utilisée pour la norification de mise à jour, de la stratégie d'équipe
     */
    public void notifierObserveur(Equipe uneEquipe, Strategie strategie);
}
