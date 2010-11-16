package org.yournamehere.server.Message;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.Date;

import org.yournamehere.client.Message.Traitement;

/**
 * Cette classe est un Servelet, elle aussi doit répondre à plusieurs critères :
 *
 *   - Le nom de la servlet doit être le même que celui pour le flux ascendant avec à la fin "Impl".
 *   - La servlet doit implémenter RemoteServiceServlet.
 *   - La servlet doit implémenter l'interface du service ascendant.
 *
 * Explications :
 *
 * Hériter de la classe RemoteServiceServlet permet de faire abstraction de
 * l'élaboration de l'objet AsyncCallback.
 *
 * Le mécanisme RCP fonctionne comme un miroir, les méthodes appelées coté client
 * seront exécutées coté serveur sur notre servlet (car on implémante Traitement).
 *
 * Ce servelet à été ajouté automatiquement au fichier web.xml !
 *
 */
public class TraitementImpl extends RemoteServiceServlet implements Traitement {
    
    /*
     * C'est donc dans le corps des méthodes issues de l'implémentation de notre
     * interface, que l'on placera le code que l'on souhaite exécuter sur le serveur.
     */

    /**
     * @return Retourne le message du client auquel on a ajouté l'heure
     */
    public String getMessage(String s) {
        return "Server says: \"" + s + "\" at " + (new Date());
    }
    
}
