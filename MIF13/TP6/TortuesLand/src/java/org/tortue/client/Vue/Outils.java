package org.tortue.client.Vue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import org.tortue.client.MainEntryPoint;
import org.tortue.client.Modele.Tortue;
import org.tortue.client.Traitement.GWTService;
import org.tortue.client.Traitement.GWTServiceAsync;

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
        angleTodo = new TextBox();
        angleTodo.setText("0");
        angleTodo.setSize("40","20");
        Button avancer = new Button("Avancer");
        Button horaire = new Button("Horaire");
        Button antihoraire = new Button("Anti-Horaire");


        //ACTIONS
        avancer.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deplacerTortue(0);
            }
        });

        horaire.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deplacerTortue(1);
            }
        });

        antihoraire.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deplacerTortue(2);
            }
        });


        //disposition
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(avancer);
        hPanel.add(new Label("|"));
        hPanel.add(new Label("Tourner de"));
        hPanel.add(angleTodo);
        hPanel.add(new Label("° dans le sens"));
        hPanel.add(horaire);
        hPanel.add(new Label("ou"));
        hPanel.add(antihoraire);


        //ajout dans le formulaire
        formulaire.add(hPanel);

        //ajout dans la div d'id = "outils"
        add(formulaire, id);
    }


    private void deplacerTortue(final int choixDeplacement){

        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);


        //récupération de l'angle
        final Integer angle;
        if(!angleTodo.getText().isEmpty())
            angle = new Integer(angleTodo.getText());
        else
            angle = 0;

        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {

                String message = tortueCourante.getNom();
                
                switch (choixDeplacement) {

                    //avancer
                    case 0: {
                        tortueCourante.avancer(MainEntryPoint.UNTERRAIN);
                        avancerTortue();
                        message +=  " a avancée (côté server et client).";
                        break;
                    }
                    
                    //horaire
                    case 1: {
                        tortueCourante.setAngle(tortueCourante.getAngle() + angle);
                        message += " a tourné dans le sens horaire, de " + angle + "° (côté server et client).";
                        break;
                    }

                    //anti-horaire
                    case 2: {
                        tortueCourante.setAngle(tortueCourante.getAngle() - angle);
                        message += " a tourné dans le sens anti-horaire de " + angle + "° (côté server et client).";
                        break;
                    }
                }

                MainEntryPoint.MESSAGES.setText(message);

            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.MESSAGES.setText("Serveur erreur : Ajout de Tortue Echoué (l'ajout côté client a aussi été annulé).");
            }
        };

        int indexTortueCourante = MainEntryPoint.MESTORTUES.indexOf(tortueCourante);
        svc.deplacerTortue(MainEntryPoint.IDCLIENT, indexTortueCourante , tortueCourante.getCoordonees(), angle, callback);

    }

    
    private void avancerTortue(){
        int index = MainEntryPoint.MESTORTUES.indexOf(tortueCourante);
        HTMLPanel vueTortue = MainEntryPoint.VUEMESTORTUES.get(index);
        vueTortue.getElementById(tortueCourante.getNom()).getStyle().setMarginLeft(tortueCourante.getCoordonees().getX(), Unit.PX);
        vueTortue.getElementById(tortueCourante.getNom()).getStyle().setMarginTop(tortueCourante.getCoordonees().getY(), Unit.PX);
    }

}