package org.tortue.server;

import java.util.ArrayList;

/**
 * Les autres clients : pour avoir en permanance la liste de leurs tortues
 */
public class ListeClients {

    ArrayList<Client> listeClients = new ArrayList<Client>();

    public void addClient(String nom){
        listeClients.add(new Client(nom));
    }

    public Client getClient(int id){
        return listeClients.get(id);
    }

    public void deleteClient(int id){
        listeClients.remove(id);
    }

}
