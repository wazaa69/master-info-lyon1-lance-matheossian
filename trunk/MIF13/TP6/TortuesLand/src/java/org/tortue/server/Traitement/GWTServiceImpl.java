package org.tortue.server.Traitement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.tortue.client.Traitement.GWTService;
import org.tortue.server.Factory;

/**
 *
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

    Factory factory = new Factory();

    public int getId(String nomClient) {
        Factory.getListeClients().addClient(nomClient);
        return Factory.getListeClients().size();
    }


}
