package ObservListe;

/**
 * Pour gérer les actions de clic de l'utilisateur, sur les boutons simples
 */
public interface ObservableBouton {
    public void ajouterObserveur(ObserveurBouton obs);
    public void supprimerObserveur();
    public void notifierObserveur(String action);
}
