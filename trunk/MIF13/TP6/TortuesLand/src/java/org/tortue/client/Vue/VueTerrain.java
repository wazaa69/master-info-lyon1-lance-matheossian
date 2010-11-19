package org.tortue.client.Vue;

import com.google.gwt.dom.client.SpanElement;
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

    private MainEntryPoint mep;
    private String id;

    /**
     * Crée la vue du terrain à partir des paramètres
     * @param longueur la longeur du terrain
     * @param largeur la largeur du terrain
     */
    public VueTerrain(String id, MainEntryPoint mep, int longueur, int largeur) {
        super("");
        getElement().setId(id);
        setPixelSize(longueur, largeur);
        this.mep = mep;
        this.id = id;
    }

    /**
     * Une tortue du client est ajoutée sur le terrain
     * @param listeVuTortue la vue de la tortue va être ajoutée dans ce panel
     * @param uneTortue la tortue ajoutée
     */
    public void addTortueTerrain(ArrayList<HTMLPanel> listeVuTortue, Tortue uneTortue){

        HTMLPanel vueTortue = new HTMLPanel("");
        vueTortue.getElement().setId(uneTortue.getNom());
        vueTortue.setStyleName("vueTortue");

        Label nomTortue = new Label(uneTortue.getNom());
        nomTortue.setStyleName("nomTortue");
        vueTortue.add(nomTortue, uneTortue.getNom());  //ajout du nom de la tortue

        vueTortue.getElementById(uneTortue.getNom()).getStyle().setMarginLeft(uneTortue.getCoordonees().getX(), Unit.PX);
        vueTortue.getElementById(uneTortue.getNom()).getStyle().setMarginTop(uneTortue.getCoordonees().getY(), Unit.PX);

        listeVuTortue.add(vueTortue);
        mep.getVueTerrain().add(vueTortue, id);
    }



    /**
     * Suppression des anciennes tortues des clients à l'affichage
     */
    public void suppAnciennesAutresTortues(){

        int nbAutresTortues = mep.getVueAutresTortues().size();

        //on supprime toutes les div des tortues des autres clients
        for(int i = 0; i < nbAutresTortues; i++)
            mep.getVueTerrain().remove(mep.getVueAutresTortues().get(i));
        
        mep.getVueAutresTortues().clear();
    }

}