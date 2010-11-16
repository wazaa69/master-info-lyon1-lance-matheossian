package org.yournamehere.client.Message;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Cette classe représente le Flux ascendant Client -> Server (lire la suite).
 *
 * Le framework GWT dispose de sa propre solution de communication entre le
 * client et le serveur, basée sur le principe d'appel de procédure distante (RCP).
 *
 * Le serveur et le client dialoguent en Asynchrone (AJAX).
 *
 * Il faut donc deux flux de communication, le premier direct vers le serveur pour
 * émettre les requêtes, le second asynchrone pour recevoir les réponses du serveur.
 * 
 * Le flux ascendant (Cette classe !) est modélisé par une interface qui doit étendre l'interface RemoteService.
 * On définit dessus les différentes méthodes que l'on souhaite obtenir du serveur.
 *
 * @see voir les commentaires concernant la classe TraitementAsync
 *
 */
@RemoteServiceRelativePath("message/traitement")
public interface Traitement extends RemoteService {
    public String getMessage(String s);
}
