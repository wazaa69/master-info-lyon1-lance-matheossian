package Gestion;

import java.util.ArrayList;

/**
 * Classe qui gère les messages
 */
public class GestionMessages {

    private ArrayList<Message> listeMessages; /** stockage de la liste des messages */

    
    public GestionMessages() {
        listeMessages = new ArrayList<Message>();
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
     * Retourne le ième message
     * @param i le numéro du message
     */
    public Message getMessage(int i){
        if(!listeMessages.isEmpty())
            return listeMessages.get(i);
        else return null;
 
    }
}
