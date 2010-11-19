package org.tortue.server.Traitement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.tortue.client.ClientServeur.ListeClients;
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

       int nbClients = factory.getListeClients().size();
       factory.getListeClients().addClient(nomClient+"-"+nbClients);

        return nbClients;
    }


    public ListeClients getListeClients() {
        return factory.getListeClients();
    }


    public String addTortue(int idClient, int idTortueClient, Tortue uneTortue) {

        Tortue ajoutTortue = new Tortue(uneTortue);
        ajoutTortue.setNom(ajoutTortue.getNom()+factory.getNbTortues());
        factory.getListeClients().getClient(idClient).addTortue(ajoutTortue);
        
        factory.incremanteNbTortues(idTortueClient);
        
        return ajoutTortue.getNom();
    }


    public String deplacerTortue(int idClient, int idTortueClient, Point coordonnees, float angle) {

        Tortue uneTortue = factory.getListeClients().getClient(idClient).getTortue(idTortueClient);
        
        uneTortue.setPosition(coordonnees);
        uneTortue.setAngle(angle);

        return "";
    }


    
}
