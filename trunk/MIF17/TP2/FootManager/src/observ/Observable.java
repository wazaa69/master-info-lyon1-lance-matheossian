package observ;

public interface Observable {
	public void ajouterObserveur(Observer obs);
	public void supprimerObserveur(Observer obs);
	public void notifierObserveur(String str);
}