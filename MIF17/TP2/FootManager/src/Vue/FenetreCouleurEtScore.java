package Vue;

import Model.Equipe;
import Model.JeuDeFoot;
import Model.Strategies.Strategie;
import ObservListe.ObserveurComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Affichage des scores
 */
public class FenetreCouleurEtScore extends JFrame implements ObserveurComboBox {


    private Equipe equipeGauche; /** Référence sur l'équipe Gauche (pour les changements de stratégies) */
    private Equipe equipeDroite; /** Référence sur l'équipes Droite (pour les changements de stratégies) */

    JLabel scoreGauche = new JLabel("0", JLabel.CENTER); /** le score de l'équipe de gauche */
    JLabel scoreDroit =  new JLabel("0", JLabel.CENTER); /** le score de l'équipe de droite */

/*******************************  CONSTRUCTEUR  *******************************/

    public FenetreCouleurEtScore(JeuDeFoot unJeuDeFoot) {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Score");
        setSize(100,100);
        setResizable(false);


        /** Lecture de la taille de l'écran */
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();


        //Layout type
        getContentPane().setLayout(new BorderLayout(10,10));
        

        equipeGauche = unJeuDeFoot.getEquipeGauche();
        equipeDroite = unJeuDeFoot.getEquipeDroite();

        Font font = new Font(Font.SERIF, Font.PLAIN, 24);


        //Titre
        JLabel titre = new JLabel("Score :", JLabel.CENTER);
        titre.setBackground(Color.red);
        titre.setFont(font);

        //Score
        scoreGauche.setFont(font);
        JLabel separateur = new JLabel(" - ", JLabel.CENTER);
        separateur.setFont(font);
        scoreDroit.setFont(font);
        JPanel conteneurScore = new JPanel(new GridLayout(1,3));
        conteneurScore.setPreferredSize(new Dimension(100,20));
        conteneurScore.add(scoreGauche);
        conteneurScore.add(separateur);
        conteneurScore.add(scoreDroit);

        //Couleur de possession de balle
        JPanel couleur = new JPanel();
        couleur.setBackground(Color.BLACK);
        couleur.setPreferredSize(new Dimension(100,80));


        getContentPane().add(BorderLayout.NORTH, titre);
        getContentPane().add(BorderLayout.CENTER, conteneurScore);
        getContentPane().add(BorderLayout.SOUTH, couleur);


        pack();
        setVisible(true);


    }


/**************************  Méthode de l'observeur  **************************/


    public void miseAJour(Equipe uneEquipe, Strategie strategie) {
        scoreGauche.setText(null);
        scoreDroit.setText(null);
    }


}
