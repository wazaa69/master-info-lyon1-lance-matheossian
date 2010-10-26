package Vue;


import ObservListe.ObservableBouton;
import ObservListe.ObservateurBouton;
import ObservListe.ObservateurComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;




/**
 * La vue qui permet à l'utilisateur de choisir la startégie de chaque équipe
 */
public class FenetreControls extends JFrame implements ObservableBouton {


    private ObservateurComboBox unObservateurComboBox; /** celui qui observe les actions sur les boutons */
    private ObservateurBouton unObservateurBouton;

    private JComboBox stratEqG = new JComboBox();
    private JComboBox stratEqD = new JComboBox();


/*******************************  CONSTRUCTEUR  *******************************/

    
    public FenetreControls(){

        getContentPane().setLayout(new BorderLayout(2,1));

        //taille de la fenêtre
        setPreferredSize(new Dimension(400,150));
        this.setResizable(false);

        // Centre l'interface
        setLocationRelativeTo(null);
        setTitle("Controles");

        //ferme la (les) fenêtres
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //MENUS TOP--------------------->
        /*
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menuFile = new JMenu("Fichier");
        menubar.add(menuFile);

        addMenuItem(menuFile, "Quitter", "Quitter", KeyEvent.VK_Q);
         */
        //--------------------------------------->

        
        getContentPane().add(creerBoutons(), BorderLayout.NORTH);
        getContentPane().add(creerChoixStrategies(), BorderLayout.CENTER);

        pack();
        setVisible(true);
    }


/***************************  CREATION COMPOSANTS  ****************************/


    /**
     * Crée les boutons pour démarrer, mettre en pause/relancer un jeu de foot
     * @return retourne le conteneur des boutons (sous forme de Grille)
     */
    private JPanel creerBoutons(){

        JPanel boutons = new JPanel(new GridLayout(1,2));
        boutons.setPreferredSize(new Dimension(400,70));

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

        return boutons;
    }



    /**
     * 
     * @return
     */
    private JPanel creerChoixStrategies(){

        JPanel grille = new JPanel(new GridLayout(1,4));
        String[] starts = {"Attaque", "Neutre", "Défense"};



        //EQUIPE DE GAUCHE----------------------------->

        grille.add(new JLabel(" Equipe Gauche : "));

        this.stratEqG = new JComboBox(starts);
        stratEqG.setSelectedIndex(1); //Neutre par défaut
        stratEqG.addActionListener(new NouvelleStrat(0));
        grille.add(stratEqG);

        //EQUIPE DE DROITE----------------------------->
        
        grille.add(new JLabel("  Equipe Droite : "));

        this.stratEqD = new JComboBox(starts);
        stratEqD.setSelectedIndex(1);  //Neutre par défaut
        stratEqD.addActionListener(new NouvelleStrat(1));
        grille.add(stratEqD);

        return grille;
    }


    /**
     * Cette classe va permettre de connaitre le choix de l'utilisateur
     */
    class NouvelleStrat implements ActionListener{

        int eqGOueqD; /** cet attribut permet de savoir quelle équipe à été modifiée*/

        public NouvelleStrat(int eqGOueqD) {this.eqGOueqD = eqGOueqD;}

        /**
         * Va avertir ses observateurs qu'il faut modifier la stratégie d'une des deux équipes
         * @param e un évènement
         */
        public void actionPerformed(ActionEvent e) {
            /*
            if(eqGOueqD == 0)
                //il faut renvoyer l'équipe + la nouvelle strat
                unObservateurComboBox.miseAJour(0,stratEqG.get);
            else
                unObservateurComboBox.miseAJour(1, (String) stratEqD.getSelectedItem());
             */
        }
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

    /*
    public void ajouterObserveur(ObservateurComboBox unObs) {
        this.unObservateurComboBox = unObs;
    }

    public void supprimerObserveur() {
        unObservateurComboBox = null;
    }

    public void notifierObserveur(int element, String action) {
        unObservateurComboBox.miseAJour(element, action);
    }
     */

}
