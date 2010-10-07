package Vue;

import Model.JeuDeFoot;
import ObservListe.ObservableBouton;
import ObservListe.Observateur;
import ObservListe.ObservateurBouton;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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

    private JeuDeFoot unJeuDeFoot;

    //Observateur
    private ObservateurBouton unObservateur;

    // Les différentes vues
    private VueTerrain vueTerrain;
   

    public Vue(JeuDeFoot unJeuDeFoot) {

        this.unJeuDeFoot = unJeuDeFoot;

        //Crée une Vue du Terrain. La vue du terrain connait le "Terrain modèle".
        vueTerrain = new VueTerrain(unJeuDeFoot.getUnTerrain());

        //Fenêtre et boutons
        initVue();
    }


    /**
    * Initialise le contenu de la fenêtre : créations des boutons et des actions associées
    */
    private void initVue(){

        getContentPane().setLayout(new BorderLayout(10,10));

        //taille de la fenêtre
        setSize(500,500);
        this.setResizable(false);
        
        // Centre l'interface
        setLocationRelativeTo(null);
        setTitle("Jeu de tortues");

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


        //BOUTONS BOTTOM--------------------->
        JPanel actions = new JPanel(new GridLayout());
        JButton b0 = new JButton("Start");
        actions.add(b0);
        b0.addActionListener(new ActionListener() {

            //On définit l'action directement
            public void actionPerformed(ActionEvent e) {
                notifierObserveur("Start");
            }
            
        });

        JButton b1 = new JButton("Pause");
        actions.add(b1);
        b1.addActionListener(new ActionListener() {

            //On définit l'action directement
            public void actionPerformed(ActionEvent e) {
                notifierObserveur("Pause");
            }

        });

        getContentPane().add(actions,"East");
        //--------------------------------------->

        pack();
        setVisible(true);
    }



    
    /**
    * Utilitaires pour installer des boutons et des menus
    * @param p un composant
    * @param name le nom du composant
    * @param tooltiptext chaine de caractère au survol du composant
    * @param imageName chemain jusqu'à l'image du composant
    */
    /*
    public void addButton(JComponent p, String name, String tooltiptext, String imageName) {

        JButton b;

        if ((imageName == null) || (imageName.equals("")))
            b = (JButton) p.add(new JButton(name));

        else {
            
            java.net.URL u = this.getClass().getResource(imageName);

            if (u != null){
                  ImageIcon im = new ImageIcon (u);
                  b = (JButton) p.add(new JButton(im));
            } else
                b = (JButton) p.add(new JButton(name));

            b.setActionCommand(name);
        }

        b.setToolTipText(tooltiptext);
        b.setBorder(BorderFactory.createRaisedBevelBorder());
        b.setMargin(new Insets(0,0,0,0));
        b.addActionListener(this);
    }
    */


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


    public void ajouterObserveur(ObservateurBouton unObs) {
        this.unObservateur = unObs;
    }

    public void supprimerObserveur() {
        unObservateur = null;
    }

    public void notifierObserveur(String action) {
        unObservateur.miseAJour(action);
    }

}
