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

        factory.getListeClients().addClient(nomClient);
        int nbClients = factory.getListeClients().size();

       factory.getListeDTD().setNbClient(nbClients);

        return nbClients;
    }

    public String addTortue(int idClient, int idTortueClient, String nomTortue) {

        Tortue uneTortue = new Tortue(nomTortue, 0, 0);
        factory.getListeClients().getClient(idClient-1).addTortue(uneTortue);
        
        factory.getListeDTD().addTortueAAfficher(idClient,idTortueClient);

        return "";
    }


    public String deplacerTortue(int idClient, int idTortueClient, Point coordonnees, float angle) {

        Tortue uneTortue = factory.getListeClients().getClient(idClient-1).getTortue(idTortueClient);
        
        uneTortue.setPosition(coordonnees);
        uneTortue.setAngle(angle);

        factory.getListeDTD().addTortueAAfficher(idClient,idTortueClient);

        return "";
    }
    
}
