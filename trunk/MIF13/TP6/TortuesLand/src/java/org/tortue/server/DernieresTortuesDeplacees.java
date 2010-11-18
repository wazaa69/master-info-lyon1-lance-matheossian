package org.tortue.server;

import java.util.ArrayList;
import org.tortue.client.ClientServeur.PaquetTortueAffichage;

/**
 * Cette classe stocke une liste des dernières tortues déplacées par les clients.
 * Chaque client met à jour son affichage toutes les N secondes, chacun va
 * récupérer cette liste, puis mettre à jour ses données et son affichage.
 */
public class DernieresTortuesDeplacees {

    ArrayList<PaquetTortueAffichage> listeTortuesDelpacees = new ArrayList<PaquetTortueAffichage>();

    int nbClients; /** le nombre de client(s) connecté(s) au serveur */
    int nbClientsDejaMaj; /** le nombre de client(s) ayant déjà fait la maj de leur affichage */

    /**
     * Initialisation
     * @param nbClients le nombre de clients dont laffichage doit être mis à jour
     */
    public DernieresTortuesDeplacees(int nbClients) {
        this.nbClients = nbClients;
        this.nbClientsDejaMaj = 0;
    }


    /**
     * Tant que tous les uilisateurs n'auront pas récupéré la liste des dernières tortues
     * déplacée, cette liste ne sera pas vidée.
     * @return retourne la liste des dernières tortues déplacées
     */
    public ArrayList<PaquetTortueAffichage> getCollection(){

        nbClientsDejaMaj++;

        if(nbClientsDejaMaj == nbClients){
            nbClientsDejaMaj = 0;
            ArrayList<PaquetTortueAffichage> tmpListe = new ArrayList<PaquetTortueAffichage>(listeTortuesDelpacees);
            listeTortuesDelpacees.clear(); //tous les clients ont fais la maj de leur affichage, on vide la liste
            return tmpListe;
        }
        else return listeTortuesDelpacees;
    }

    
    /**
     * Ajoute une tortue à la liste
     * @param uneTortue la dernière tortue qui c'est déplacée sur le serveur
     */
    public void addTortueAAfficher(int idClient, int idTortue){
        PaquetTortueAffichage unPaquet = new PaquetTortueAffichage(idClient, idTortue);
        listeTortuesDelpacees.add(unPaquet);
    }

    /**
     * A utiliser si le nombre de client(s) augmente ou diminue
     * @param nbClients le nouveau nombre de client(s)
     */
    public void setNbClient(int nbClients) {
        this.nbClients = nbClients;
    }

    public int getNbClients() {
        return nbClients;
    }
 
}
