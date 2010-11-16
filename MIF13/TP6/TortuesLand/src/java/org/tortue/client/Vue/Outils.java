package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Les outils mis à disposition de l'utilisateur.
 */
public class Outils extends HTMLPanel {

    public Outils(){

        super("<div id='outils'></div>");
        setStyleName("conteneurOutils");


        //balise formulaire
        FormPanel formulaire = new FormPanel();
        formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
        formulaire.setMethod(FormPanel.METHOD_POST);

        //label + input text + boutons
        Label angle = new Label("Angle : ");
        final TextBox angleTodo = new TextBox();
        angleTodo.setSize("70","20");
        Button avancer = new Button("Avancer");
        Button gauche = new Button("Gauche");
        Button droite = new Button("Droite");

        Button ajouterTortue = new Button("Ajouter Tortue");

        //disposition
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(angle);
        hPanel.add(angleTodo);
        hPanel.add(avancer);
        hPanel.add(gauche);
        hPanel.add(droite);
        hPanel.add(ajouterTortue);

        //ajout dans le formulaire
        formulaire.add(hPanel);

        //ajout dans la div d'id = "outils"
        add(formulaire, "outils");
    }
}
