package Vue;

import Model.JeuDeFoot;
import Model.ElementMobile.Joueur;
import ObservListe.ObservableBouton;
import ObservListe.ObservateurBouton;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * Créer la fenêtre et les boutons
 */
public class Vue extends JFrame implements ObservableBouton {

    private JeuDeFoot unJeuDeFoot; /** référence sur le jeu de foot*/

    private ObservateurBouton unObservateurBouton; /**  un observateur de boutons */

    private VueTerrain vueTerrain;  /** la vue du terrain */
    private VueScore vueScore; /** la vue du score et de la couleur de l'équipe en possession de la balle */
    private VueChoixStrategie vueChoixStrategie; /** la vue qui perme de choisir une stratégie par équipe */



/*******************************  CONSTRUCTEUR  *******************************/
    
    /**
     * Constructeur, initialise le terrain et la fenêtre
     * @param unJeuDeFoot un jeu de foot
     */
    public Vue(JeuDeFoot unJeuDeFoot) {

        this.unJeuDeFoot = unJeuDeFoot;

        vueTerrain = new VueTerrain(unJeuDeFoot);

        vueScore = new VueScore();

        vueChoixStrategie = new VueChoixStrategie();

        initVue();
    }


/**************************** Initialisation des vues *************************/

    /**
    * Initialise le contenu de la fenêtre : créations des boutons et des actions associées
    */
    private void initVue(){

        getContentPane().setLayout(new BorderLayout(10,10));

        //taille de la fenêtre
        setSize(800,800);
        //this.setResizable(false);
        
        // Centre l'interface
        setLocationRelativeTo(null);
        setTitle("Jeu de Foot");

        //ferme la (les) fenêtres
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //MENUS TOP--------------------->
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menuFile = new JMenu("Fichier");
        menubar.add(menuFile);

        addMenuItem(menuFile, "Quitter", "Quitter", KeyEvent.VK_Q);
        //--------------------------------------->


        //AJOUT DU TERRAIN ------------->
        getContentPane().add(vueTerrain,"Center");
        //--------------------------------------->
        

        //BOUTONS --------------------->
        JPanel boutons = new JPanel(new GridLayout(2,1));

        JButton demarrer = new JButton("Démarrer");
        boutons.add(demarrer);
        demarrer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                notifierObserveur("Démarrer");
            }
            
        });

        JButton pause = new JButton("Pause/Repartir");
        boutons.add(pause);
        pause.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                notifierObserveur("Pause/Repartir");
            }

        });


        getContentPane().add(boutons,"East");
        //--------------------------------------->

        pack();
        setVisible(true);
    }



    /**
    * Utilitaires pour installer des boutons et des menus
    * @param m un menu
    * @param label intitulé de l'item
    * @param command la commande associé à l'item
    * @param key touche du clavier pour recevoir un évènement
    */
    private void addMenuItem(JMenu m, final String label, String command, int key) {

        JMenuItem menuItem;
        menuItem = new JMenuItem(label);
        m.add(menuItem);

        menuItem.setActionCommand(command);
        menuItem.addActionListener(new ActionListener() {

            //On définit l'action directement
            public void actionPerformed(ActionEvent e) {
                notifierObserveur(label);
            }
        });

        if (key > 0) {
            if (key != KeyEvent.VK_DELETE)
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, Event.CTRL_MASK, false));
            else
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, 0, false));
        }
    }


/***************************** Méthodes de l'observé **************************/

    
    public void ajouterObserveur(ObservateurBouton unObs) {
        this.unObservateurBouton = unObs;
    }

    public void supprimerObserveur() {
        unObservateurBouton = null;
    }

    public void notifierObserveur(String action) {
        unObservateurBouton.miseAJour(action);
    }

}
