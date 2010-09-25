package tortuesmanager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Cette classe crée la Vue et associe des boutons à des actions
 */
public class SimpleLogo extends JFrame implements ActionListener {


    //######################################################################################################      ATTRIBUTS

	private FeuilleDessin feuille; /** la feuille de dessins */
        private Controleur controleur; /** référence sur le controleur */
	JTextField inputValue; /** champs de texte pour l'angle de rotation de la tortue */

	public static final Dimension VGAP = new Dimension(1,5); /** constante de dimenssion */
	public static final Dimension HGAP = new Dimension(5,1); /** constante de dimenssion */


        /* ---------------------- */
        /*   ELEMENTS GRAPHIQUES  */
        /* ---------------------- */

        private JToolBar barreOutils = null; /** la barre d'outils en haute de la fenêtre*/

    //######################################################################################################      CONSTRUCTEURS
        
    /*
     * Constructeur
     * @param c un controleur
     */
    public SimpleLogo(Controleur c)
    {
        // Titre de l'appli, super appel un constructeur de JFrame et envoie la chaîne en paramètre, super doit toujours être placé en 1er dans un constructeur de clase dérivé
        super("Feuille de dessin");

        //relation Vue Controleur
        controleur = c;
        controleur.setSimpleLogo(this);
        
        logoInit();
    }

 //######################################################################################################      ACCESSEURS

    /**
    * Récupère l'angle de rotation de la tortue dans l'affichage
    * @return retourne une chaine de caractère qui correspond à l'angle de rotation de la tortue
    */
    String getInputValue(){
        String s = inputValue.getText();
        return(s);
    }


    public FeuilleDessin getFeuille() {return feuille;}


 //######################################################################################################      MUTATEURS


    /**
    * Mise à jour du controleur
    * @param controleur le controleur
    */
    public void setControleur(Controleur controleur) {this.controleur = controleur;}

    /**
     * Mise à jour de la feuilel de dessin
     * @param feuille une feuille de dessin
     */
    public void setFeuille(FeuilleDessin feuille) {this.feuille = feuille;}

    /**
     * Mise à jour de la visibilié de la barre d'outils
     * @param b si vrai alors la barre d'outils sera afficher, faux sinon
     */
    public void setBarreOutilsVisible(boolean b) {barreOutils.setVisible(b);}


 //######################################################################################################      METHODES

    /**
    * Initialise le contenu de la fenêtre : créations des boutons et des actions associées
    */
    public void logoInit(){


            getContentPane().setLayout(new BorderLayout(10,10));


            //BOUTONS TOP--------------------->
            JPanel buttonPanel = new JPanel();
            JToolBar toolBar = new JToolBar();
            buttonPanel.add(toolBar);
            barreOutils = toolBar; // sauvegarde
            toolBar.setVisible(false);

            getContentPane().add(buttonPanel,"North");



            addButton(toolBar,"Effacer","Nouveau dessin","/icons/00.gif");

            toolBar.add(Box.createRigidArea(HGAP));
            inputValue=new JTextField("45",5); // contenu et taille du champ de texte
            toolBar.add(inputValue);

            addButton(toolBar, "Avancer", "Avancer 50", null);
            addButton(toolBar, "Droite", "Droite 45", null);
            addButton(toolBar, "Gauche", "Gauche 45", null);
            addButton(toolBar, "Lever", "Lever Crayon", null);
            addButton(toolBar, "Baisser", "Baisser Crayon", null);
            //--------------------------------------->




            //COULEURS DU TRAIT--------------------->
            String[] colorStrings = {"noir", "bleu", "cyan",
                                     "gris foncé","rouge",
                                     "vert", "gris clair", "magenta",
                                     "orange","gris", "rose", "jaune"};


            toolBar.add(Box.createRigidArea(HGAP));
            JLabel colorLabel = new JLabel("   Couleur: ");
            toolBar.add(colorLabel);
            JComboBox colorList = new JComboBox(colorStrings);
            toolBar.add(colorList);

            colorList.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            JComboBox cb = (JComboBox)e.getSource();
                            int n = cb.getSelectedIndex();
                            controleur.getCourante().setColor(n);
                    }
            });

            //--------------------------------------->


            //LES MENUS TOP--------------------->
            JMenuBar menubar=new JMenuBar();
            setJMenuBar(menubar);	// on installe le menu bar
            JMenu menuFile=new JMenu("Fichier"); // on installe le premier menu
            menubar.add(menuFile);

            
            addMenuItem(menuFile, "Quitter", "Quitter", KeyEvent.VK_Q);



            JMenu menuCommandes=new JMenu("Commandes"); // on installe le premier menu
            menubar.add(menuCommandes);
            addMenuItem(menuCommandes, "Effacer", "Effacer", KeyEvent.VK_N);
            addMenuItem(menuCommandes, "Avancer", "Avancer", -1);
            addMenuItem(menuCommandes, "Droite", "Droite", -1);
            addMenuItem(menuCommandes, "Gauche", "Gauche", -1);
            addMenuItem(menuCommandes, "Lever Crayon", "Lever", -1);
            addMenuItem(menuCommandes, "Baisser Crayon", "Baisser", -1);


            menuCommandes.setVisible(false);


            /*
            JMenu menuHelp=new JMenu("Aide"); // on installe le premier menu
            menubar.add(menuHelp);
            addMenuItem(menuHelp, "Aide", "Help", -1);
            addMenuItem(menuHelp, "A propos", "About", -1);
             */
            //--------------------------------------->


            setDefaultCloseOperation(EXIT_ON_CLOSE);




            //INITIALISATION DE LA FEUILLE------------->
            feuille = new FeuilleDessin();
            feuille.setBackground(Color.white);
            feuille.setPreferredSize(new Dimension(500,400));
            //--------------------------------------->


            getContentPane().add(feuille,"Center");




            //BOUTONS BOTTOM--------------------->
            JPanel p2 = new JPanel(new GridLayout());
            /*JButton b19 = new JButton("Carré");
            p2.add(b19);
            b19.addActionListener(this);
            JButton b20 = new JButton("Polygone");
            p2.add(b20);
            b20.addActionListener(this);
            JButton b21 = new JButton("Spirale");
            p2.add(b21);
            b21.addActionListener(this);
            JButton b22 = new JButton("Immeuble");
            p2.add(b22);
            b22.addActionListener(this);
            JButton b23 = new JButton("Asterisque");
            p2.add(b23);
            b23.addActionListener(this); */
            JButton b24 = new JButton("Proc_0");
            p2.add(b24);
            b24.addActionListener(this);
            JButton b25 = new JButton("Proc_1");
            p2.add(b25);
            b25.addActionListener(this);
            JButton b26 = new JButton("Proc_2");
            p2.add(b26);
            b26.addActionListener(this);
            JButton b27 = new JButton("Proc_3");
            p2.add(b27);
            b27.addActionListener(this);
            getContentPane().add(p2,"South");

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
    void addButton(JComponent p, String name, String tooltiptext, String imageName) {
        JButton b;
        if ((imageName == null) || (imageName.equals(""))) {
          b = (JButton) p.add(new JButton(name));
        }
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
    void addMenuItem(JMenu m, String label, String command, int key) {
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



    /**
    * Gestion des actions des boutons
    * @param e l'action à effectuer
    */
    public void actionPerformed(ActionEvent e)
    {
        String c = e.getActionCommand();

        // actions des boutons du haut
        if (c.equals("Avancer")) {
            try {
              int v = Integer.parseInt(inputValue.getText());
              controleur.getCourante().avancer(v);
            }
            catch (NumberFormatException ex){System.err.println("ce n'est pas un nombre : " + inputValue.getText());}
        }

        else if (c.equals("Droite"))  {
            try {
              int v = Integer.parseInt(inputValue.getText());
              controleur.getCourante().droite(v);
            }
            catch (NumberFormatException ex){System.err.println("ce n'est pas un nombre : " + inputValue.getText());}
        }

        else if (c.equals("Gauche"))  {
            try {
              int v = Integer.parseInt(inputValue.getText());
              controleur.getCourante().gauche(v);
            }
            catch (NumberFormatException ex){System.err.println("ce n'est pas un nombre : " + inputValue.getText());}
        }

        else if (c.equals("Lever"))  controleur.getCourante().leverCrayon();
        else if (c.equals("Baisser"))  controleur.getCourante().baisserCrayon();
        // actions des boutons bottom
        else if (c.equals("Carré"))  controleur.carre();
        else if (c.equals("Polygone"))  controleur.poly();
        else if (c.equals("Spirale"))  controleur.spiral();
        else if (c.equals("Immeuble"))  controleur.immeuble();
        else if (c.equals("Asterisque"))  controleur.asterisque();
        else if (c.equals("Proc_0"))  controleur.procedureZero();
        else if (c.equals("Proc_1"))  controleur.procedureUne();
        else if (c.equals("Proc_2"))  controleur.procedureDeux();
        else if (c.equals("Proc_3"))  controleur.procedureTrois();
        else if (c.equals("Effacer")) controleur.effacer();
        else if (c.equals("Quitter")) controleur.quitter();
    }

}
