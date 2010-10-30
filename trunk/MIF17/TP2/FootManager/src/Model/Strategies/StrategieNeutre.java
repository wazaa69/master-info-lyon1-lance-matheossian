package Model.Strategies;

import Model.ElementMobile.Ballon;
import Model.ElementMobile.Caracteristiques;
import Model.ElementMobile.Joueur;
import Model.ElementMobile.JoueurGoal;
import Model.Equipe;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 */
public class StrategieNeutre extends Strategie {

    public StrategieNeutre() {
        formation = new ArrayList<Integer>(); //4-5-1
        formation.add(new Integer(1)); //1 attaquant
        formation.add(new Integer(5)); //5 milieux
        formation.add(new Integer(4)); //4 défenseurs
    }


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

            boolean aucunIntercepteur = unJoueur.getMonEquipe().getNbIntercepteurs() <= 1;
            boolean jPeuTenterIntercep = unJoueur.getTentatIntercep() > 0;
            boolean jPeuPossederBallon = unJoueur != ancienPoss;
            boolean jPeuPrendreBallon = distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle();
            boolean jPeuCourirVersBallon = distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle()*3;


            //tentative d'interception du ballon
            if(notreEquAPasLeballon &&  jPeuTenterIntercep && jPeuPrendreBallon && aucunIntercepteur && jPeuPossederBallon)
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
            else if(jPeuPrendreBallon && !jPeuTenterIntercep){
                unJoueur.avancer(); //on le fait avancer pour le faire sortir de la zone
            }

            //si le joueur voit le ballon, qu'il peut tenter de l'intercepter, mais qu'il est un peu trop loin
            else if(notreEquAPasLeballon && !jPeuPrendreBallon && jPeuPossederBallon && jPeuTenterIntercep && jPeuCourirVersBallon && aucunIntercepteur){

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
                    ballonDuJeu.changerDePossesseur(goalAdverse);
                }
                else{
                    unJoueur.getMonEquipe().setScore(unJoueur.getMonEquipe().getScore() + 1);
                    remiseEnJeu(unJoueur.getMonEquipe(), unJoueur.getEquipeAdverse(), ballonDuJeu);
                }


                //notifier le Jeu de Foot qu'il doit replacer les équipes et le ballon

            }

            else if(joueurPasse != null && joueurPasse != ancienPoss)
                    ballonDuJeu.changerDePossesseur(joueurPasse);


            else{
                unJoueur.setAngleSelon(coordCageAdverse);
                unJoueur.avancer();
            }

        }

    }
    
}
