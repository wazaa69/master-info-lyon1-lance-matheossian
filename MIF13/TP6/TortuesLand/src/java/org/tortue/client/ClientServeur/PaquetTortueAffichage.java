/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tortue.client.ClientServeur;

/**
 * 
 */
public class PaquetTortueAffichage {

    int idClient; /** l'identifiant du client à qui appartient la tortue */
    int idTortue; /** l'identifiant de la tortue chez le client */

    public PaquetTortueAffichage(int idClient, int idTortue) {
        this.idClient = idClient;
        this.idTortue = idTortue;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdTortue() {
        return idTortue;
    }

}
