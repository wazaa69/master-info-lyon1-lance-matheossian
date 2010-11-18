package org.tortue.client.ClientServeur;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 * Les autres clients : pour avoir en permanance la liste de leurs tortues
 * Cette classe est pour le moment utilisé par le Client et le serveur.
 * Il faudrait créer une classe spécialisé uniquement dans le stockage de client et de leur tortues.
 */
public class ListeClients implements IsSerializable{

    ArrayList<Client> listeClients = new ArrayList<Client>();

    public ListeClients() {}

    public void addClient(String nom){
        listeClients.add(new Client(nom));
    }

    public void addClients(ListeClients uneListeClients){
        listeClients.addAll(uneListeClients.getListeClients());
    }

    public Client getClient(int id){
        return listeClients.get(id);
    }

    public void deleteClient(int id){
        listeClients.remove(id);
    }

    public int size(){
        return listeClients.size();
    }

    public ArrayList<Client> getListeClients() {
        return listeClients;
    }

    public void clear() {
        listeClients.clear();
    }

}
