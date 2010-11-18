package org.tortue.server.Traitement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.tortue.client.Modele.Point;
import org.tortue.client.Modele.Tortue;


import org.tortue.client.Traitement.GWTService;
import org.tortue.server.Factory;

/**
 *
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

    private Factory factory = new Factory();

    public int getId(String nomClient){
        Factory.getListeClients().addClient(nomClient);
        return Factory.getListeClients().size();
    }

    public String addTortue(int idClient, String nomTortue) {
        Factory.getListeClients().getClient(idClient-1).addTortue(nomTortue);
        return "";
    }


    public String deplacerTortue(int idClient, int idTortueClient, Point coordonnees, float angle) {
        Tortue uneTortue = Factory.getListeClients().getClient(idClient-1).getTortue(idTortueClient);
        uneTortue.setPosition(coordonnees);
        uneTortue.setAngle(angle);
        return "";
    }
    
}
