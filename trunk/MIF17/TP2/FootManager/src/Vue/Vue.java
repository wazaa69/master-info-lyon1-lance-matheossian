package Vue;

import Model.Terrain;
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

public class Vue extends JFrame implements ActionListener{


    // Les différentes vues
    private VueTerrain vueTerrain;


    public Vue(Terrain unTerrain) {
        this.vueTerrain = new VueTerrain(unTerrain);
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
        JMenuBar menubar=new JMenuBar();
        setJMenuBar(menubar);
        JMenu menuFile=new JMenu("Fichier");
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
        b0.addActionListener(this);
        JButton b1 = new JButton("Pause");
        actions.add(b1);
        b1.addActionListener(this);
        getContentPane().add(actions,"EAST");
        //--------------------------------------->

        pack();
        setVisible(true);
    }

    /**
    * TODO -> ACTION ? CONTROLEUR OU VUE ?
    * Gestion des actions des boutons
    * @param e l'action à effectuer
    */
    public void actionPerformed(ActionEvent e)
    {
        String c = e.getActionCommand();

        if (c.equals("Start"))  controleur.procedureZero();
        else if (c.equals("Stop"))  ;
        else if (c.equals("Quitter")) controleur.quitter();
    }



    
    /**
    * Utilitaires pour installer des boutons et des menus
    * @param p un composant
    * @param name le nom du composant
    * @param tooltiptext chaine de caractère au survol du composant
    * @param imageName chemain jusqu'à l'image du composant
    */
    private void addButton(JComponent p, String name, String tooltiptext, String imageName) {

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


    /**
    * Utilitaires pour installer des boutons et des menus
    * @param m un menu
    * @param label intitulé de l'item
    * @param command la commande associé à l'item
    * @param key touche du clavier pour recevoir un évènement
    */
    private void addMenuItem(JMenu m, String label, String command, int key) {

        JMenuItem menuItem;
        menuItem = new JMenuItem(label);
        m.add(menuItem);

        menuItem.setActionCommand(command);
        menuItem.addActionListener(this);

        if (key > 0) {
            if (key != KeyEvent.VK_DELETE)
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, Event.CTRL_MASK, false));
            else
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, 0, false));
        }
    }


}