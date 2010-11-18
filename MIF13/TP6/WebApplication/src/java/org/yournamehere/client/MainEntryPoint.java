package org.yournamehere.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import org.yournamehere.client.Message.formCommunication;




/**
 * Main entry point.
 */
public class MainEntryPoint implements EntryPoint {

    
    /** 
     * Crée une nouvelle instance du point d'entrée
     */
    public MainEntryPoint() {}

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     * @see http://examples.roughian.com/
     * @see http://moritan.developpez.com/tutoriels/java/gwt/premier/projet/
     */
    public void onModuleLoad() {


       final Label texteUtilisateur = new Label("Votre texte !"); //le texte sur lequel les action


        //--------------------------Conteneurs

            String menu = "<h1 id='titreMenu'></h1><br/><div id='menu'></div>";
            HTMLPanel menuHTML = new HTMLPanel(menu);
            menuHTML.setStyleName("conteneurMenu");


            String article = "<h1 id='titreArticle'></h1><div id='article'></div>";
            HTMLPanel articleHTML = new HTMLPanel(article);
            articleHTML.setStyleName("conteneurArticle");

        //---------------------------MENU
        //Création du menu => création d'items et de leurs commandes
        
            final Label menuTitre = new Label("Menu");
            menuTitre.setStylePrimaryName("menuTitre rouge");
            menuHTML.add(menuTitre, "titreMenu");


            MenuBar menuWidget = new MenuBar();
            menuWidget.addStyleName("menuWidget");


            Command comAfficherTexte = new Command() {
                public void execute() {
                    texteUtilisateur.setVisible(!texteUtilisateur.isVisible());
                }
            };

            Command comEffacerTexte = new Command() {
                public void execute() {
                    texteUtilisateur.setText("________________");
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

            //création et ajout d'un formulaire
            articleHTML.add(new formCommunication(texteUtilisateur), "article");

        
        //<------------END ARTICLE


        //on ajoute le texte qui sera affiché après l'envoie et reception du server
        articleHTML.add(texteUtilisateur, "article");

        
        //AJOUT DU CONTENU AU DOCUMENT
        RootPanel.get().add(menuHTML);
        RootPanel.get().add(articleHTML);
    }
}
