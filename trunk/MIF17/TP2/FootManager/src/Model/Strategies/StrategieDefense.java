package Model.Strategies;

import java.util.ArrayList;
import Model.ElementMobile.Ballon;
import Model.ElementMobile.Caracteristiques;
import Model.ElementMobile.Joueur;
import Model.ElementMobile.JoueurGoal;
import Model.Equipe;
import java.awt.Point;

/**
 * Meilleur defense : plus de défenseurs et de milieux de terrain
 */
public class StrategieDefense extends Strategie{

    public StrategieDefense() {
        formation = new ArrayList<Integer>(); //4-5-1
        formation.add(new Integer(1)); //1 attaquant
        formation.add(new Integer(3)); //3 milieux
        formation.add(new Integer(6)); //6 défenseurs
    }

    /**
     * Méthode à faire évoluer pour une meilleur défense
     * @param unJoueur le joueur auquel la stratégie va être appliquée
     */
    public void utiliserStrat(Joueur unJoueur) {


        Ballon ballonDuJeu = unJoueur.getBallonDuJeu();
        Joueur possesseur = ballonDuJeu.getPossesseur();
        Joueur ancienPoss= ballonDuJeu.getAncienPoss();
        Caracteristiques caractUnJoueur = unJoueur.getCaracteristiques();
        int distanceAuBallon = unJoueur.getDistance(new Point(unJoueur.getX(),unJoueur.getY()),ballonDuJeu);

        /** Les testes */

        //Le joueur ne possède pas le ballon
        if(unJoueur != possesseur){

            /** Conditions */
            boolean notreEquAPasLeballon = true;
            if(possesseur != null) notreEquAPasLeballon = unJoueur.getMonEquipe() != possesseur.getMonEquipe();

            boolean bonNbIntercepteurs = unJoueur.getMonEquipe().getNbIntercepteurs() <= 3;
            boolean jPeuTenterIntercep = unJoueur.getTentatIntercep() > 0;
            boolean jPeuPossederBallon = unJoueur != ancienPoss;
            boolean jPeuPrendreBallon = distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle();
            boolean jPeuCourirVersBallon = distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle()*2.5;

            //personne n'a le ballon
            if(jPeuCourirVersBallon && possesseur == null){
                unJoueur.setAngleSelonBallon();
                if(jPeuPrendreBallon)
                    ballonDuJeu.changerDePossesseur(unJoueur);
                unJoueur.avancer();
            }

            //tentative d'interception du ballon
            //on peut ajouter && aucunIntercepteur , pour avoir un seul intercepteur
            else if(notreEquAPasLeballon && jPeuTenterIntercep && bonNbIntercepteurs && jPeuPrendreBallon && jPeuPossederBallon)
            {

                unJoueur.setEnCoursInterc(true);

                //l'interception a réussie
                if(Math.random() < caractUnJoueur.getProbaRecupBallon()){
                    ballonDuJeu.changerDePossesseur(unJoueur);
                    unJoueur.setTentatIntercep(3);
                    unJoueur.setEnCoursInterc(false);
                }

                else{ //l'interception a échouée

                    //diminution du nombre de tentative restantes
                    unJoueur.setTentatIntercep(unJoueur.getTentatIntercep()-1);

                    //si toutes les tentatives d'interceptions ont échouées, le joueur fait demi-tour et s'éloigne
                    if(unJoueur.getTentatIntercep() <= 0){
                        unJoueur.setEnCoursInterc(false);
                        unJoueur.setAngle((unJoueur.getAngle()+180)%360); //demi-tour
                    }

                    else{ //sinon il continue de suivre le ballon
                        unJoueur.setAngleSelonBallon(); //(au cas où il aurai bougé)
                        unJoueur.avancer();
                    }

                }

            }

            //le joueur à fait demi-tour, mais il est toujours dans la zone ou il peut intercepter
            else if(notreEquAPasLeballon && jPeuPrendreBallon && !jPeuTenterIntercep){
                unJoueur.setAngleSelon(unJoueur.getPositionFormation());
                unJoueur.avancer(); //on le fait avancer pour le faire sortir de la zone
            }

            //si le joueur voit le ballon, qu'il peut tenter de l'intercepter, mais qu'il est un peu trop loin
            else if(notreEquAPasLeballon && !jPeuPrendreBallon && jPeuPossederBallon && jPeuTenterIntercep && jPeuCourirVersBallon && bonNbIntercepteurs){
                unJoueur.setTentatIntercep(3);
                //si il n'y a pas déjà un intercepteur de son équipe
                unJoueur.setAngleSelonBallon(); //il fonce jusqu'au ballon
                unJoueur.avancer();

            }

            //sinon, le joueur est bien trop loin du ballon, il reste en formation
            else{

                unJoueur.setTentatIntercep(3);

                //le joueur doit rester dans un rayon de 10m de sa position de formation
                if(unJoueur.getDistance(unJoueur.getPositionFormation(), unJoueur) > 30){
                    unJoueur.setAngleSelon(unJoueur.getPositionFormation()); //retour à la position de la formation
                    unJoueur.avancer();
                }
                else {
                    if(Math.random() > 0.5)
                        unJoueur.avancerAuHasard(); //modifie l'angle
                    else
                        unJoueur.avancer(); //conserve l'angle
                }

            }

        }

        else{ //le joueur possède le ballon

            Equipe equipeAdverse = unJoueur.getEquipeAdverse();
            Point coordCageAdverse = unJoueur.getEquipeAdverse().getCage().getCentreCoordonnees();
            JoueurGoal goalAdverse = equipeAdverse.getGoal();
            Joueur joueurPasse = unJoueur.passeAUnCoequipier();

            //le joueur est assez près pour tirer dans les cages
            if(unJoueur.getXY().distance(coordCageAdverse) < caractUnJoueur.getDistMaxTir()){

                //on récupère le point ou le ballon va se trouver (quand le joueur aura tiré)
                Point unPoint = unJoueur.preparerTirPrMarquer();

                //caracteristique du Goal
                int distMinPrPrendreBalle = goalAdverse.getCaracteristiques().getDistMinPrendreBalle();

                //balle arrêtée par le goal ou pas ?
                boolean interception = unPoint.distance(goalAdverse.getXY()) <= distMinPrPrendreBalle;

                if(interception){
                    System.out.println("Le ballon est intercepté par " + goalAdverse.getNom() + ".");
                    ballonDuJeu.changerDePossesseur(goalAdverse);
                }
                else{
                    System.out.println("L'équipe " + unJoueur.getMonEquipe().getNomEquipe() + " marque un but ! ");
                    ballonDuJeu.changerDePossesseur(null);
                    ballonDuJeu.setXY(unPoint);
                    ballonDuJeu.majXY();

                    unJoueur.getMonEquipe().setScore(unJoueur.getMonEquipe().getScore() + 1);
                    positionnerApresBut(unJoueur.getMonEquipe(), unJoueur.getEquipeAdverse(), ballonDuJeu);
                }


                //notifier le Jeu de Foot qu'il doit replacer les équipes et le ballon

            }

            //si il y a un joueur mieux placé, il fait une passe
            else if(joueurPasse != null && joueurPasse != ancienPoss)
                    ballonDuJeu.changerDePossesseur(joueurPasse);

            //sinon il avance où il y a le moins d'ennemis
            else{
                unJoueur.setAngle(unJoueur.getAngleSelon(coordCageAdverse));
                unJoueur.avancer();
            }

        }

    }

}
