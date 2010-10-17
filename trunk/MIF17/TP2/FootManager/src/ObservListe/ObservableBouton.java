package ObservListe;

/**
 * Pour gérer les actions de clic de l'utilisateur
 */
public interface ObservableBouton {
    public void ajouterObserveur(ObservateurBouton obs);
    public void supprimerObserveur();
    public void notifierObserveur(String action);
}
