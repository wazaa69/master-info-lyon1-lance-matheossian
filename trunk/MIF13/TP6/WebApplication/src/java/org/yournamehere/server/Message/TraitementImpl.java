package org.yournamehere.server.Message;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.Date;

import org.yournamehere.client.Message.Traitement;

/**
 *
 */
public class TraitementImpl extends RemoteServiceServlet implements Traitement {
    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: \"" + s + "\" at " + (new Date());
    }
}
