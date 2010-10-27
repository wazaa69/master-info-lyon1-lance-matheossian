package ObservListe;

/**
 * Relation de dépendance Observable ---> Observeur, car des méthodes
 * de la classe Observable utilisent la classe Observeur
 */
public interface Observable {
	public void ajouterObserveur(Observeur obs);
	public void supprimerObserveur();
	public void notifierObserveur();
}