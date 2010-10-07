package ObservListe;

public interface ObservableBouton {
    public void ajouterObserveur(ObservateurBouton obs);
    public void supprimerObserveur();
    public void notifierObserveur(String action);
}
