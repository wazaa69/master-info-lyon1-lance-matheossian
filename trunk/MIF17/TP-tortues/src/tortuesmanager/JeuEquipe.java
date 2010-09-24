package tortuesmanager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JeuEquipe extends JeuDeBalle {

    private ArrayList<TortueEquipe> equipeA;
     private ArrayList<TortueEquipe> equipeB;

     private int scoreEquipeA;
     private int scoreEquipeB;
             
    private int nombreDePassesEnchainees;


     //######################################################################################################      CONSTRUCTEURS

    public  JeuEquipe(FeuilleDessin feuille, int nbJA, int nbJB, String nomEquipe1, String nomEquipe2) {

        super(feuille, 0);

        equipeA = new ArrayList<TortueEquipe>();
        equipeB = new ArrayList<TortueEquipe>();
        scoreEquipeA = 0;
        scoreEquipeB = 0;
        nombreDePassesEnchainees = 0;
        
        TortueEquipe uneTortue = null;


        //Création des équipes
        for(int i = 0; i < nbJA; i++) {
            uneTortue = new TortueEquipe(feuille, nomEquipe1, i, false);
            uneTortue.setCouleur(Color.green);
            equipeA.add(uneTortue);

        }


        for(int i = 0; i < nbJB; i++){
            uneTortue = new TortueEquipe(feuille, nomEquipe2, i, false);
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

    @Override
    public void placerTortuesTerrain(){

            for(int i = 0; i < equipeA.size(); i++)
                for(int j = 0; j < 50; j++)
                    equipeA.get(i).deplacementAuHasard(15);

            for(int i = 0; i < equipeB.size(); i++)
                for(int j = 0; j < 50; j++)
                    equipeB.get(i).deplacementAuHasard(15);
    }

    @Override
    public TortueEquipe randomJoueuse(){

        if(!feuille.getListeTortuesEquipe().isEmpty()){
            int joueuse = (int) Math.round(Math.random() * (feuille.getListeTortuesEquipe().size()-1));
            return feuille.getListeTortuesEquipe().get(joueuse);
        }
        else return null;
     }


    @Override
    public void lancerPartie(){

        nombreDePassesEnchainees = 0;
        int distanceDeplacement = 3;
        boolean interception = false;
        placerTortuesTerrain();

        TortueEquipe EquipePossesseurBalle = null;
        TortueEquipe EquipeSansBalle = null ;

        //On récupère la tortue la plus proche de la balle et on downcast
        TortueEquipe tortueEquipeProprio = randomJoueuse();
        if(tortueEquipeProprio == null) return; //si il n'y a aucune tortue on quitte

        //On met à jour la propriétaire de la balle et on l'affiche
       // balle.setPositionSelonTortue(tortueProprio);
        System.out.println(tortueEquipeProprio.getNom()+ " a la balle !");

        //Tortue pripriétaire de la balle et l'ancienne
        TortueEquipe ancienneEquipeProprio = null;
        TortueEquipe tortueEquipeProche = null;
        TortueEquipe tortueInterceptee = null;
        
        while(!finPartie) {

            //On fait bouger la balle et son propriétaire
//            tortueEquipeProprio.deplacementAuHasard(distanceDeplacement);

             if (tortueEquipeProprio.getNomEquipe() == equipeA.get(1).getNomEquipe())
             {
                 tortueEquipeProprio.deplacementTortueProprioBalle(distanceDeplacement, 30, equipeB);
             }
            else
             {
                  tortueEquipeProprio.deplacementTortueProprioBalle(distanceDeplacement, 30, equipeA);
             }



            balle.setPositionSelonTortue(tortueEquipeProprio);



            //On fait bouger les autres tortues
            Tortue uneTortue = null;
            for (int j = 0; j < feuille.getListeTortuesAmeliorees().size(); j++) {

                uneTortue = feuille.getListeTortuesAmeliorees().get(j);

               
                if (uneTortue != tortueEquipeProprio){uneTortue.deplacementAuHasard(distanceDeplacement);}
 
            }

            //après le déplacement des tortues
            feuille.drawIt();



          
            
  // INTERCEPTION

            if (tortueEquipeProprio.getNomEquipe() == equipeA.get(1).getNomEquipe())
            {
                EquipeSansBalle = equipeB.get(1);
                EquipePossesseurBalle =  equipeA.get(1);

                 for (int i = 0; i < equipeB.size(); i++ )
                 {
                   
                         if (tortueEquipeProprio.distTortue(equipeB.get(i)) <15 && (equipeB.get(i) != tortueInterceptee))
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
            else if (tortueEquipeProprio.getNomEquipe() == equipeB.get(1).getNomEquipe())
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




            //// PASSES
            //La tortue propriétaire de la balle cherche à faire une passe

            if (!interception)
            {

                tortueEquipeProche = (TortueEquipe)tortueEquipeProprio.tortueAmieLaPlusProche();

                if (tortueEquipeProche != null) {
                    if (tortueEquipeProprio.distTortue(tortueEquipeProche) >= distMinPourPasse && tortueEquipeProche != ancienneEquipeProprio) {

                        ancienneEquipeProprio = tortueEquipeProprio;
                        tortueEquipeProprio = tortueEquipeProche;

                        System.out.println(ancienneEquipeProprio.getNom() + " passe la balle à " + tortueEquipeProche.getNom());

                        balle.setPositionSelonTortue(tortueEquipeProprio); //il faudrait utiliser la méthode passerLaBalleA
                        nombreDePassesEnchainees++;
                    }
                }
            }

            interception = false;
            feuille.drawIt();

            // CONDITION MARQUAGE DE POINT: 10 PASSES D'AFFILE

            if (nombreDePassesEnchainees == 10)
            {

               System.out.println("L'équipe " + EquipePossesseurBalle.getNomEquipe() + " a marqué un point !!!");

                if (tortueEquipeProprio.getNomEquipe() == equipeA.get(1).getNomEquipe())
                {
                     scoreEquipeA++;
                }
               else if (tortueEquipeProprio.getNomEquipe() == equipeB.get(1).getNomEquipe())
                {
                    scoreEquipeB++;
                }


              System.out.println("SCORE:" + equipeA.get(1).getNomEquipe() + " : " +scoreEquipeA + " / " + equipeB.get(1).getNomEquipe()  + " : " + scoreEquipeB);
               nombreDePassesEnchainees = 0;
            }
 

            try {

                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(JeuDeBalle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }


}
