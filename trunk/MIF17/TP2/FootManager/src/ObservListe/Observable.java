package ObservListe;

/**
 * Relation de dépendance Observable ---> Observateur, car des méthodes
 * de la classe Observable utilisent la classe Observateur
 */
public interface Observable {
	public void ajouterObserveur(Observateur obs);
	public void supprimerObserveur(Observateur obs);
	public void notifierObserveur();
}