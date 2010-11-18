package org.tortue.client.Vue;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import org.tortue.client.MainEntryPoint;
import org.tortue.client.Modele.Tortue;

/**
 * La vue du terrain
 */
public class VueTerrain extends HTMLPanel {

    private static String ID;

    /**
     * Crée la vue du terrain à partir des paramètres
     * @param longueur la longeur du terrain
     * @param largeur la largeur du terrain
     */
    public VueTerrain(String id, int longueur, int largeur) {
        super("<div id='" + id + "'></div>");
        setStyleName("conteneur"+id);
        setPixelSize(longueur, largeur);
        ID = id;
    }

    /**
     * Une tortue du client est ajoutée sur le terrain
     * @param listeVuTortue la vue de la tortue va être ajoutée dans ce panel
     * @param uneTortue la tortue ajoutée
     */
    public static void addTortueTerrain(ArrayList<HTMLPanel> listeVuTortue,Tortue uneTortue){

        HTMLPanel vueTortue = new HTMLPanel("<div id='" + uneTortue.getNom() + "'class='vueTortue'></div>");
        Label nomTortue = new Label(uneTortue.getNom());
        nomTortue.setStyleName("nomTortue");
        vueTortue.add(nomTortue, uneTortue.getNom());  //ajout du nom de la tortue

        vueTortue.getElementById(uneTortue.getNom()).getStyle().setMarginLeft(uneTortue.getCoordonees().getX(), Unit.PX);
        vueTortue.getElementById(uneTortue.getNom()).getStyle().setMarginTop(uneTortue.getCoordonees().getY(), Unit.PX);

        listeVuTortue.add(vueTortue);
        MainEntryPoint.VUETERRAIN.add(vueTortue, ID);
    }



    /**
     * Suppression des anciennes tortues des clients à l'affichage
     */
    public static void suppAnciennesAutresTortues(){

        int nbAutresTortues = MainEntryPoint.VUEAUTRESTORTUES.size();

        //on supprime toutes les div des tortues des autres clients
        for(int i = 0; i < nbAutresTortues; i++)
            MainEntryPoint.VUETERRAIN.remove(MainEntryPoint.VUEAUTRESTORTUES.get(i));
        

        MainEntryPoint.VUEAUTRESTORTUES.clear();
    }

}