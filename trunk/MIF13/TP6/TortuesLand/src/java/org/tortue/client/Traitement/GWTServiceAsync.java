package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Traitement descendant Server -> Client
 */
public interface GWTServiceAsync {
    public void getId(String s, AsyncCallback<java.lang.Integer> asyncCallback);
}
