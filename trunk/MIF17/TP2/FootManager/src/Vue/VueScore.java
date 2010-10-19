package Vue;

import ObservListe.Observateur;
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
public class VueScore extends JFrame implements Observateur {

    JLabel scoreGauche = new JLabel("0", JLabel.CENTER);
    JLabel scoreDroit =  new JLabel("0", JLabel.CENTER);

    public VueScore(Vue vue) {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Score");
        setSize(100,100);
        setResizable(false);


        /** Lecture de la taille de l'écran */
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();


        setLocation(
            ((tailleEcran.width-getWidth())/2) + vue.getWidth(),
            ((tailleEcran.height-getHeight())/2)  + vue.getHeight()
            );


        //Layout type
        getContentPane().setLayout(new BorderLayout(10,10));
        

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


        getContentPane().add("North", titre);
        getContentPane().add("Center", conteneurScore);
        getContentPane().add("South", couleur);


        pack();
        setVisible(true);


    }

    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
