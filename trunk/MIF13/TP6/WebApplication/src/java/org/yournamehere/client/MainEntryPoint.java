package org.yournamehere.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.yournamehere.client.Message.TraitementUsage;




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


        //--------------------------Conteneurs

        String menu = "<h1 id='titreMenu'></h1><br/><div id='menu'></div>";
        HTMLPanel menuHTML = new HTMLPanel(menu);
        menuHTML.setStyleName("conteneurMenu");


        String article = "<h1 id='titreArticle'></h1><div id='article'></div>";
        HTMLPanel articleHTML = new HTMLPanel(article);
        articleHTML.setStyleName("conteneurArticle");

        //---------------------------MENU

        final Label menuTitre = new Label("Menu");
        menuTitre.setStylePrimaryName("menuTitre rouge");
        menuHTML.add(menuTitre, "titreMenu");


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
                texteUtilisateur.setText("Entrez un texte !");
            }
        };

        MenuItem afficherTexte = new MenuItem("Afficher le texte", comAfficherTexte);
        afficherTexte.setStyleName("menuItem bleuLien");
        MenuItem effacerTexte = new MenuItem("Effacer le texte", comEffacerTexte);
        effacerTexte.setStyleName("menuItem bleuLien");
        menuWidget.addItem(afficherTexte);
        menuWidget.addItem(effacerTexte);

        //ajout du menu dans la balise d'id="menu"
        menuHTML.add(menuWidget, "menu");


        //------------ARTICLE

        Label titreArticle = new Label("Ma première application GWT");
        titreArticle.setStylePrimaryName("pageTitre bleu");
        articleHTML.add(titreArticle, "titreArticle");

            //---------------------------formulaire

            //balise formulaire
            FormPanel formulaire = new FormPanel();
            formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
            formulaire.setMethod(FormPanel.METHOD_POST);

            //label + input text + bouton
            Label enTeteChamps = new Label("Entrez un texte !");
            final TextBox champsUtilisateur = new TextBox();
            Button envoyer = new Button("Envoyer");

            /*
            //action du bouton
            envoyer.addClickHandler(new ClickBouton(){
                @Override
                public void onClick(ClickEvent event) {
                    texteUtilisateur.setText(champsUtilisateur.getText());
                }
            });*/

            //positionnement dans le formulaire
            VerticalPanel vPanel = new VerticalPanel();
            vPanel.add(enTeteChamps);
            vPanel.add(champsUtilisateur);
            vPanel.add(envoyer);

            //ajout du contenu, dans le formulaire
            formulaire.add(vPanel);
            articleHTML.add(formulaire, "article");


            //<----------------------------END Formulaire

        //<------------END ARTICLE


        articleHTML.add(texteUtilisateur, "article");

        //initialisation et sauvegarde, de l'action du bouton
        TraitementUsage initTraitement = new TraitementUsage(envoyer,champsUtilisateur,texteUtilisateur);

        //AJOUT DU CONTENU AU DOCUMENT
        RootPanel.get().add(menuHTML);
        RootPanel.get().add(articleHTML);
    }
}
