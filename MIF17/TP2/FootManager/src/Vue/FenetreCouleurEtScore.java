package Vue;

import Model.ElementMobile.Ballon;
import Model.Equipe;
import Model.JeuDeFoot;
import ObservListe.Observeur;
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
public class FenetreCouleurEtScore extends JFrame {
    
    private Equipe equipeGauche; /** Référence sur l'équipe Gauche (pour les changements de score) */
    private Equipe equipeDroite; /** Référence sur l'équipes Droite (pour les changements de score) */
    private JLabel scoreGauche = new JLabel("0", JLabel.CENTER); /** le score de l'équipe de gauche */
    private JLabel scoreDroit =  new JLabel("0", JLabel.CENTER); /** le score de l'équipe de droite */

    
    private Ballon unBallon; /** Référence sur le ballon pour connaître la couleur du pocesseur */
    private JPanel couleurPossesseur; /** la couleur actuel du pocesseur de la balle */


/*******************************  CONSTRUCTEUR  *******************************/

    public FenetreCouleurEtScore(JeuDeFoot unJeuDeFoot) {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Score");
        setSize(100,100);
        setResizable(false);


        setLocationRelativeTo(null);
        setLocation(getX() + 325, getY() - 125);


        //Layout type
        getContentPane().setLayout(new BorderLayout(10,10));


        
        //Références sur les équipes et ajout des observeurs
        equipeGauche = unJeuDeFoot.getEquipeGauche();
        equipeDroite = unJeuDeFoot.getEquipeDroite();

        equipeGauche.ajouterObserveur(new Observeur() {
            public void miseAJour() {majScores();}
        });

        equipeDroite.ajouterObserveur(new Observeur() {
            public void miseAJour() {majScores();} 
        });


        unBallon = unJeuDeFoot.getUnBallon();
        unBallon.ajouterObserveur(new Observeur() {
            public void miseAJour() {majScores();}
        });
        

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
        couleurPossesseur = couleur;
        couleur.setBackground(Color.WHITE);
        couleur.setPreferredSize(new Dimension(100,80));


        getContentPane().add(BorderLayout.NORTH, titre);
        getContentPane().add(BorderLayout.CENTER, conteneurScore);
        getContentPane().add(BorderLayout.SOUTH, couleur);


        pack();
        setVisible(true);


    }


/*********************************  Méthode  **********************************/

    public void majScores() {
        scoreGauche.setText((new Integer(equipeGauche.getScore()).toString()));
        scoreDroit.setText((new Integer(equipeDroite.getScore()).toString()));

        if(unBallon.getPossesseur() != null)
            couleurPossesseur.setBackground(unBallon.getPossesseur().getMonEquipe().getCouleur());
        else
            couleurPossesseur.setBackground(Color.WHITE);
    }

}
