package ObservListe;

import Model.Equipe;
import Model.Strategies.Strategie;

/**
 * Observateur pour les listes déroulantes
 */
public interface ObserveurComboBox {

    /*
     * Utilisée pour la mise à jour de la stratégie d'équipe
     */
    public void miseAJour(Equipe uneEquipe, Strategie strategie);
}
