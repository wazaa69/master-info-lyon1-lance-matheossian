package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Traitement descendant Server -> Client
 */
public interface GWTServiceAsync {
    public void getId(String nomClient, AsyncCallback<java.lang.Integer> asyncCallback);
    public void addTortue(int idClient, String nomTortue, AsyncCallback<Boolean> asyncCallback);
}
