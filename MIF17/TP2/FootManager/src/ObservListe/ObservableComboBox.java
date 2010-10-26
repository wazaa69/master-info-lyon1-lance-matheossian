package ObservListe;

/**
 * L'observable pour les listes déroulantes
 */
public interface ObservableComboBox {
    public void ajouterObserveur(ObservateurComboBox obs);
    public void supprimerObserveur();
    public void notifierObserveur(int element, String action);
}
