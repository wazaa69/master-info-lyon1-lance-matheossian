package ObservListe;

/**
 * Relation de dépendance Observable ---> Observateur, car des méthodes
 * de la classe Observable utilisent la classe Observateur
 */
public interface Observable {
	public void ajouterObservateur(Observateur obs);
	public void supprimerObservateur();
	public void notifierObservateur();
}