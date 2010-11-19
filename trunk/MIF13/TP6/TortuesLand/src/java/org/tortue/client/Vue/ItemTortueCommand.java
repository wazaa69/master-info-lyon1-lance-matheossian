/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tortue.client.Vue;

import com.google.gwt.user.client.Command;
import org.tortue.client.MainEntryPoint;
import org.tortue.client.Modele.Tortue;

/**
 *
 */
public class ItemTortueCommand implements Command{

    Tortue tortueReference; /** La tortue liée à cette commande (donc à l'item) */

    public ItemTortueCommand(Tortue tortueReference) {
        this.tortueReference = tortueReference;
    }
 
    public void execute() {
        Outils.tortueCourante = tortueReference;
        MainEntryPoint.INFOMESS.setText(tortueReference.getNom() + " selectionnée !");
    }

}
