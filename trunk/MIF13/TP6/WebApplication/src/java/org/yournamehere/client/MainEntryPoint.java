package org.yournamehere.client;

import com.gargoylesoftware.htmlunit.html.InputElementFactory;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;




/**
 * Main entry point.
 */
public class MainEntryPoint implements EntryPoint {


    private final Label texteUtilisateur = new Label("Votre texte !"); //le texte sur lequel les action

    /** 
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {}

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     * @see http://examples.roughian.com/
     */
    public void onModuleLoad() {

        
        final Label titre = new Label("Ma première application GWT");
        titre.setStylePrimaryName("pageTitre bleu");

        //--------------------------Conteneurs

        String menu = "<div id='menu'></div>";
        HTMLPanel menuHTML = new HTMLPanel(menu);


        String article = "<div id='article'></div>";
        HTMLPanel articleHTML = new HTMLPanel(article);
        

        //---------------------------menu

        final Label menuTitre = new Label("Menu");
        menuTitre.setStylePrimaryName("menuTitre rouge");
        menuHTML.add(menuTitre, "menu");


        MenuBar  menuWidget = new MenuBar();
        menuWidget.addStyleName("menuWidget");
        
        //Création du menu => création d'items et de leurs commandes
        Command comAfficherTexte = new Command() {
            public void execute() {
                texteUtilisateur.setVisible(!texteUtilisateur.isVisible());
            }
        };

        Command comEffacerTexte = new Command() {
            public void execute() {
                texteUtilisateur.setText("");
            }
        };

        MenuItem afficherTexte = new MenuItem("Afficher le texte", comAfficherTexte);
        afficherTexte.setStyleName("menuItem bleuLien");
        MenuItem effacerTexte = new MenuItem("Effacer le texte", comEffacerTexte);
        effacerTexte.setStyleName("menuItem bleuLien");
        menuWidget.addItem(afficherTexte);
        menuWidget.addItem(effacerTexte);

        //ajout du menu
        menuHTML.add(menuWidget, "menu");

        //---------------------------formulaire

        //balise formulaire
        FormPanel formulaire = new FormPanel();
        formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
        formulaire.setMethod(FormPanel.METHOD_POST);

        //label + input text + bouton
        Label headerChamps = new Label("Tapez votre text ici");
        final TextBox champsUtilisateur = new TextBox();
        Button envoyer = new Button("Envoyer");

        //action du bouton
        envoyer.addClickHandler(new ClickBouton(){
            @Override
            public void onClick(ClickEvent event) {
                texteUtilisateur.setText(champsUtilisateur.getText());
            }
        });
        
        //positionnement dans le formulaire
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(headerChamps);
        vPanel.add(champsUtilisateur);
        vPanel.add(envoyer);

        //ajout du contenu, dans le formulaire
        formulaire.add(vPanel);
        articleHTML.add(formulaire, "article");


        //<----------------------------END Formulaire


        articleHTML.add(texteUtilisateur, "article");


        //AJOUT DU CONTENU AU DOCUMENT
        RootPanel.get().add(menuHTML);
        RootPanel.get().add(articleHTML);
    }
}
