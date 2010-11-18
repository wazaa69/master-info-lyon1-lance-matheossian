package org.tortue.client.Vue;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import org.tortue.client.MainEntryPoint;
import org.tortue.client.Modele.Tortue;

/**
 * Les outils mis à disposition de l'utilisateur.
 */
public class Outils extends HTMLPanel {

    public static Tortue tortueCourante; /** la tortue courante controlée par l'utilisateur */
    private TextBox angleTodo = new TextBox();
    
    public Outils(String id){

        super("<div id='" + id + "'></div>");
        setStyleName("conteneur"+id);

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


        //ACTIONS
        avancer.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tortueCourante.avancer(MainEntryPoint.UNTERRAIN);
                majVueTortue();
            }
        });

        gauche.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tortueCourante.setAngle(tortueCourante.getAngle() +  new Integer(angleTodo.getText()));
                majVueTortue();
            }
        });

        droite.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tortueCourante.setAngle(tortueCourante.getAngle() - new Integer(angleTodo.getText()));
                majVueTortue();
            }
        });


        //disposition
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(angle);
        hPanel.add(angleTodo);
        hPanel.add(avancer);
        hPanel.add(gauche);
        hPanel.add(droite);

        //ajout dans le formulaire
        formulaire.add(hPanel);

        //ajout dans la div d'id = "outils"
        add(formulaire, id);
    }

    private void majVueTortue(){
        int index = MainEntryPoint.MESTORTUES.indexOf(tortueCourante);
        HTMLPanel vueTortue = MainEntryPoint.VUETORTUES.get(index);
        vueTortue.getElementById(tortueCourante.getNom()).getStyle().setMarginLeft(tortueCourante.getCoordonees().getX(), Unit.PX);
        vueTortue.getElementById(tortueCourante.getNom()).getStyle().setMarginTop(tortueCourante.getCoordonees().getY(), Unit.PX);
    }

    private boolean faireDeplacement(String choix){
        return false;
    }

}
