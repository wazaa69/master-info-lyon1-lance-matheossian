package tortuesmanager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crée une balle et 2 équipes de N tortueEquipe.
 * Les tortues jouent à la passe à dix, si les 10 passes sont réussies,
 * l'équipe gagne 1 point, sinon le score repart à zéro.
 */
public class JeuEquipe extends JeuDeBalle {

    private ArrayList<TortueEquipe> equipeA; /** l'équipe A */
    private ArrayList<TortueEquipe> equipeB; /** l'équipeB */

    private int scoreEquipeA;  /** score de l'équipe A */
    private int scoreEquipeB;  /** score de l'équipe B */

    private int nombreDePassesEnchainees; /** le compteur de passes */


     //######################################################################################################      CONSTRUCTEURS

    /**
     * Crée les équipes et fait en sorte que chaque tortue d'une équipe,
     * connaisse ses coéquipières
     * @param feuille la feuille de dessin
     * @param nbJA le nombre de joueurs de l'équipe A
     * @param nbJB  le nombre de joueurs de l'équipe B
     * @param nomEquipeA le nom de l'équipe A
     * @param nomEquipeB le nom de l'équipe B
     */
    public  JeuEquipe(FeuilleDessin feuille, int nbJA, int nbJB, String nomEquipeA, String nomEquipeB) {

        super(feuille, 0);

        equipeA = new ArrayList<TortueEquipe>();
        equipeB = new ArrayList<TortueEquipe>();

        scoreEquipeA = 0;
        scoreEquipeB = 0;

        nombreDePassesEnchainees = 0;

        
        TortueEquipe uneTortue = null;

        //Création des équipes
        for(int i = 0; i < nbJA; i++) {
            uneTortue = new TortueEquipe(feuille, nomEquipeA, i, false);
            uneTortue.setCouleur(Color.green);
            equipeA.add(uneTortue);

        }


        for(int i = 0; i < nbJB; i++){
            uneTortue = new TortueEquipe(feuille, nomEquipeB, i, false);
            uneTortue.setCouleur(Color.blue);
            equipeB.add(uneTortue);
        }


        //Toutes les tortues doivent se connaître
        for (int i = 0; i < nbJA; i++){
             equipeA.get(i).ajouterDesAmies(equipeA);
        }

           for (int i = 0; i < nbJB; i++){
             equipeB.get(i).ajouterDesAmies(equipeB);
        }


    }


 //######################################################################################################      ACCESSEURS




 //######################################################################################################      MUTATEURS


  
 

 //######################################################################################################      METHODES

    /**
     * Place les tortues sur le terrain de jeu : au hasard
     */
    @Override
    public void placerTortuesTerrain(){

            for(int i = 0; i < equipeA.size(); i++)
                for(int j = 0; j < 50; j++)
                    equipeA.get(i).deplacementAuHasard(15);

            for(int i = 0; i < equipeB.size(); i++)
                for(int j = 0; j < 50; j++)
                    equipeB.get(i).deplacementAuHasard(15);
    }


    /**
     * Choisit une tortue au hasard
     * @return retourne une "tortue équipe" présente dans la liste des tortues
     */
    @Override
    protected TortueEquipe randomJoueuse(){

        if(!feuille.getListeTortuesEquipe().isEmpty()){
            int joueuse = (int) Math.round(Math.random() * (feuille.getListeTortuesEquipe().size()-1));
            return feuille.getListeTortuesEquipe().get(joueuse);
        }
        else return null;
     }


    /**
     * La boucle de jeu qui gère les déplacements, les passes et les interceptions
     */
    @Override
    public void lancerPartie(){

        nombreDePassesEnchainees = 0;
        int distanceDeplacement = 3;
        boolean interception = false;


        placerTortuesTerrain();


        //On récupère la tortue la plus proche de la balle et on downcast
        TortueEquipe tortueEquipeProprio = randomJoueuse();
        if(tortueEquipeProprio == null) return; //si il n'y a aucune tortue on quitte


        System.out.println(tortueEquipeProprio.getNom()+ " a la balle !");


        //Tortue pripriétaire de la balle et l'ancienne
        TortueEquipe ancienneEquipeProprio = null;
        TortueEquipe EquipePossesseurBalle = null;

        TortueEquipe EquipeSansBalle = null ;
        
        TortueEquipe tortueEquipeProche = null;
        TortueEquipe tortueInterceptee = null;


        while(!finPartie) {

            //On fait bouger la balle et son propriétaire

             if (equipeA.contains(tortueEquipeProprio))
                 tortueEquipeProprio.deplacementTortueProprioBalle(distanceDeplacement, 30, equipeB);
            else
                tortueEquipeProprio.deplacementTortueProprioBalle(distanceDeplacement, 30, equipeA);


            balle.setPositionSelonTortue(tortueEquipeProprio);


            //On fait bouger les autres tortues
            Tortue uneTortue = null;
            for (int j = 0; j < feuille.getListeTortuesAmeliorees().size(); j++) {
                uneTortue = feuille.getListeTortuesAmeliorees().get(j);
                if (uneTortue != tortueEquipeProprio)
                    uneTortue.deplacementAuHasard(distanceDeplacement);
            }


            feuille.drawIt();
          
            //// INTERCEPTION

            if (equipeA.contains(tortueEquipeProprio))
            {
                EquipeSansBalle = equipeB.get(1);
                EquipePossesseurBalle =  equipeA.get(1);

                 for (int i = 0; i < equipeB.size(); i++ )
                 {
                     if (tortueEquipeProprio.distTortue(equipeB.get(i)) < 15 && (equipeB.get(i) != tortueInterceptee))
                     {
                         // la tortue qui avait la balle devient la tortue interceptée et la tortue intercepteur devient propriétaire de la balle
                         tortueInterceptee = tortueEquipeProprio;
                         tortueEquipeProprio = equipeB.get(i);

                         System.out.println(tortueInterceptee.getNom() + " est interceptée par " + tortueEquipeProprio.getNom());

                         balle.setPositionSelonTortue(tortueEquipeProprio);
                         interception = true;
                         nombreDePassesEnchainees = 0;
                         break;
                     }
                 }
            }

            else if (equipeB.contains(tortueEquipeProprio))
            {

                 EquipeSansBalle = equipeA.get(1);
                 EquipePossesseurBalle =  equipeB.get(1);

                for (int i = 0; i < equipeA.size(); i++ )
                 {
                     if (tortueEquipeProprio.distTortue(equipeA.get(i)) < 15 && (equipeA.get(i) != tortueInterceptee))
                     {
                         tortueInterceptee = tortueEquipeProprio;
                         tortueEquipeProprio = equipeA.get(i);

                         System.out.println(tortueInterceptee.getNom() + " est interceptée par " + tortueEquipeProprio.getNom());

                         balle.setPositionSelonTortue(tortueEquipeProprio);
                         interception = true;
                         nombreDePassesEnchainees = 0;
                         break;
                     }
                 }
            }


            feuille.drawIt();

            //// PASSES
            //La tortue propriétaire de la balle cherche à faire une passe

            if (!interception)
            {
                tortueEquipeProche = (TortueEquipe)tortueEquipeProprio.tortueAmieLaPlusProche();

                if (tortueEquipeProche != null) {
                    if (tortueEquipeProprio.distTortue(tortueEquipeProche) >= distMinPourPasse && tortueEquipeProche != ancienneEquipeProprio) {

                        ancienneEquipeProprio = tortueEquipeProprio;
                        tortueEquipeProprio = tortueEquipeProche;

                        System.out.println(ancienneEquipeProprio.getNom() + " passe la balle à " + tortueEquipeProche.getNom() + " Passe : " + nombreDePassesEnchainees);

                        balle.setPositionSelonTortue(tortueEquipeProprio);
                        nombreDePassesEnchainees++;
                    }
                }
            }

            interception = false;


            feuille.drawIt();
            

            //// CONDITION MARQUAGE DE POINT : 10 PASSES D'AFFILE

            if (nombreDePassesEnchainees == 10)
            {

                System.out.println("L'équipe " + EquipePossesseurBalle.getNomEquipe() + " a marqué un point !!!");


                if (equipeA.contains(tortueEquipeProprio)) scoreEquipeA++;
                else if (equipeB.contains(tortueEquipeProprio))  scoreEquipeB++;


                System.out.println("SCORE : " + equipeA.get(1).getNomEquipe() + " : " +scoreEquipeA + " / " + equipeB.get(1).getNomEquipe()  + " : " + scoreEquipeB);

                nombreDePassesEnchainees = 0;
            }


            try {Thread.sleep(100);} catch (InterruptedException ex) {
                System.err.println("Problème avec le sleep dans JeuEquipe.");
                Logger.getLogger(JeuDeBalle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }


}
