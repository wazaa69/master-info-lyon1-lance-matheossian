package Gestion;

import java.util.ArrayList;

/**
 * Classe qui gère les messages
 */
public class GestionMessages {

    private ArrayList<Message> listeMessages; /** stockage de la liste des messages */

    public int compteurAffichage; // pour les teste : vérifier que l'affichage ne se fait pas plusieurs fois

    public GestionMessages() {
        listeMessages = new ArrayList<Message>();
        compteurAffichage = 0;
    }

    public void ajouterMessage(Message message){
        listeMessages.add(message);
    }

    /**
     * @return retourne un entier, représentant le nombre de messages stockées
     */
    public int intSize(){
            return listeMessages.size();
    }

    /**
     * @return retourne un string, représentant le nombre de messages stockées
     */
    public String stringSize(){
            return "" + listeMessages.size();
    }

    /**
     * @return retourne la liste de tous les messages
     */
    public ArrayList<Message> getListeMessages() {
        return listeMessages;
    }

    /**
     * @return retourne le dernier message inséré
     */
    public Message getLastMessage(){
        return getMessage(intSize());
    }

    /**
     * Retourne le ième message
     * @param i le numéro du message
     */
    public Message getMessage(int i){
        if(!listeMessages.isEmpty())
            return listeMessages.get(i);
        else
            return new Message("Bot","Bienvenue !");
 
    }


}
