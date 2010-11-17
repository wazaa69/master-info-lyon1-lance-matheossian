package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
     * @param idClient le numéro qui identifie le client
     * @param nomTortue le nom de la tortue à créer
     * @return retourne vrai si la tortue à été ajoutée, faux sinon
     */
     public Boolean addTortue(int idClient, String nomTortue);
}
