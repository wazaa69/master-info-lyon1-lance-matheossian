package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.tortue.client.Modele.Point;


/**
 * Flux ascendant Client -> Server
 */
@RemoteServiceRelativePath("traitement/gwtservice")
public interface GWTService extends RemoteService {
    /**
     * Si ca n'a pas été fait, le serveur s'initialise. Puis il envoie l'identifiant du client.
     * @param nomClient le nom du client
     * @return retourne un entier qui resprésente l'identifiant du client sur le serveur
     */
     public int getId(String nomClient);

    /**
     * Ajoute une tortue dans la liste des tortue du client
     * @param idClient l'identifiant du client à qui appartient la tortue
     * @param idTortueClient l'identifiant de la tortue chez le client
     * @param nomTortue le nom de la tortue à créer
     * @return retourne vrai si la tortue à été ajoutée, faux sinon
     */
     public String addTortue(int idClient, int idTortueClient, String nomTortue);


    /**
    * Déplace la tortue côté serveur
    * @param idClient le numéro qui identifie le client
    * @param idTortueClient le numéro qui identifie la tortue du client
    * @param choixDeplacement 0 = avancer, 1 = tourner à gauche,
    */
    public String deplacerTortue(int idClient, int idTortueClient, Point coordonnees, float angle);
}
