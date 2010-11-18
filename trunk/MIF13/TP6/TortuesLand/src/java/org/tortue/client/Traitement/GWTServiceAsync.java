package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.tortue.client.ClientServeur.ListeClients;
import org.tortue.client.Modele.Point;
import org.tortue.client.Modele.Tortue;
/**
 * Traitement descendant Server -> Client
 */
public interface GWTServiceAsync {

    public void getId(String nomClient, AsyncCallback<java.lang.Integer> asyncCallback);

    public void addTortue(int idClient, int idTortueClient, Tortue uneTortue, AsyncCallback<String> asyncCallback);

    public void deplacerTortue(int idClient, int idTortueClient, Point coordonnees, float angle, AsyncCallback<String> asyncCallback);

    public void getListeClients(AsyncCallback<ListeClients> asyncCallback);

}
