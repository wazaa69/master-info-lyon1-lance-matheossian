package Vue.Controls;


import Model.Equipe;
import Model.JeuDeFoot;
import Model.Strategies.Strategie;
import Model.Strategies.StrategieFactory;
import ObservListe.ObservableBouton;
import ObservListe.ObserveurBouton;
import ObservListe.ObserveurComboBox;
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


    private ObserveurBouton unObservateurBouton; /** celui qui observe les actions sur les boutons */
    private ObserveurComboBox unObservateurComboBox;  /** celui qui observe les changements de stratégies */

    private JComboBox comboBoxEqG = new JComboBox(); /** liste déroulante de l'équipe à gauche sur le terrain */
    private JComboBox comboBoxEqD = new JComboBox(); /** liste déroulante de l'équipe à droite sur le terrain */

    private Equipe equipeGauche; /** Référence sur l'équipe Gauche (pour les changements de stratégies) */
    private Equipe equipeDroite; /** Référence sur l'équipes Droite (pour les changements de stratégies) */

/*******************************  CONSTRUCTEUR  *******************************/

    
    public FenetreControls(JeuDeFoot unJeuDeFoot){

        getContentPane().setLayout(new BorderLayout(2,1));

        //taille de la fenêtre
        setPreferredSize(new Dimension(400,150));
        this.setResizable(false);

        // Centre l'interface
        setLocationRelativeTo(null);
        setTitle("Controles");

        //ferme la (les) fenêtres
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        equipeGauche = unJeuDeFoot.getEquipeGauche();
        equipeDroite = unJeuDeFoot.getEquipeDroite();


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
     * Crée la liste déroulante des stratégies, pour chaque équipe
     * @return retourne le conteneur des listes déroulantes et des labels
     */
    private JPanel creerChoixStrategies(){


        JPanel grille = new JPanel(new GridLayout(1,4));
 
        //Création des listes
        comboBoxEqG = new JComboBox();
        comboBoxEqD = new JComboBox();

        //Ajout des stratégies
        OptionStrategie uneStrat = null;
        String[] stringStrat = {"Attaque", "Neutre", "Défense"};
        
        for(int i = 0; i < stringStrat.length; i++){
            uneStrat = new OptionStrategie(stringStrat[i], i);
            comboBoxEqG.addItem(uneStrat);
            comboBoxEqD.addItem(uneStrat);
        }


        //Premier élément selectionné, et ajout d'un écouteur sur les listes
        comboBoxEqG.setSelectedIndex(1); //Neutre par défaut
        comboBoxEqD.setSelectedIndex(1); //Neutre par défaut
        comboBoxEqG.addActionListener(new NouvelleStrat(equipeGauche));
        comboBoxEqD.addActionListener(new NouvelleStrat(equipeDroite));
  

        //Ajout au panel
        grille.add(new JLabel(" Equipe Gauche : "));
        grille.add(comboBoxEqG);
        grille.add(new JLabel("  Equipe Droite : "));
        grille.add(comboBoxEqD);

        return grille;
    }


    /**
     * Cette classe va permettre d'informer l'obervateur, du choix de l'utilisateur
     */
    class NouvelleStrat implements ActionListener{


        Equipe eqGeqD; /** soit l'équipe de gauche, soit celle de droite */


        /**
         * @param eqGeqD équipe dont la comboBox à été modifiée
         */
        public NouvelleStrat(Equipe eqGeqD) {this.eqGeqD = eqGeqD;}
 
         /* Va avertir ses observateurs qu'il faut modifier la stratégie d'une des deux équipes
         * @param e un évènement
         */
        public void actionPerformed(ActionEvent e) {

            OptionStrategie choixEqu;

            if(eqGeqD == equipeGauche)
                choixEqu = (OptionStrategie) comboBoxEqG.getSelectedItem();
            else
                choixEqu = (OptionStrategie) comboBoxEqD.getSelectedItem();

            //notifie l'observateur, en précisant l'équipe dont la stratégie doit être modifiée
            unObservateurComboBox.miseAJour(eqGeqD, new StrategieFactory().creerStrategie(choixEqu.getValeur()));

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


    //Pour les JButton et la JComboBox------------------->
    public void supprimerObserveur() {
        unObservateurBouton = null;
        unObservateurComboBox = null;
    }

    //Pour les JButton------------------->
    public void ajouterObserveur(ObserveurBouton unObs) {
        this.unObservateurBouton = unObs;
    }


    public void notifierObserveur(String action) {
        unObservateurBouton.miseAJour(action);
    }


    //Pour la JComboBox------------------->
    public void ajouterObserveur(ObserveurComboBox unObs) {
        this.unObservateurComboBox = unObs;
    }


    public void notifierObserveur(Equipe uneEquipe, Strategie strategie) {
        unObservateurComboBox.miseAJour(uneEquipe, strategie);
    }

}