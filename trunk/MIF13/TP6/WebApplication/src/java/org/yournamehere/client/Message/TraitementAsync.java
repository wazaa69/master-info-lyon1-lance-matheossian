package org.yournamehere.client.Message;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Traitement descendant Server -> Client
 *
 *   Cette interface doit répondre à plusieurs critères :
 *
 *       - Le nom de l'interface doit être le même que celui pour le flux
 *         ascendant avec à la fin "Async".
 *       - Toutes ses méthodes sont de type void.
 *       - Le nombre et le nom de ses méthodes sont identiques à ceux pour le flux ascendant.
 *       - Chacune de ses méthodes doit prendre les mêmes paramètres que pour le
 *         flux montant plus un paramètre supplémentaire de la classe AsyncCallback.
 *
 *   C'est au travers de l'objet AsyncCallback que le serveur retournera sa réponse au client.
 */
public interface TraitementAsync {

    /**
     *  a réponse du serveur au client
     * @param s le message envoyé par l'uilisateur
     * @param asyncCallback la (les) réponse(s) à envoyer au client
     */
    public void getMessage(String s, AsyncCallback<String> asyncCallback);
}
