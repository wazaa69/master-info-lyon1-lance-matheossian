package Gestion;

import java.util.ArrayList;

/**
 * Classe qui gère les messages
 */
public class GestionMessages {

    private ArrayList<Message> listeMessages; /** stockage de la liste des messages */
    private int lastModified; /** dernière modification */

    public GestionMessages() {
        listeMessages = new ArrayList<Message>();
        lastModified = 0;
    }

    public void ajouterMessage(Message message){
        listeMessages.add(message);
    }

    /**
     * @return retourne le nombre de messages stockées
     */
    public int size(){
            return listeMessages.size();
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
        return getMessage(size());
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

    public void setLastModified() {
        this.lastModified++;
    }

    public int getLastModified() {
        return lastModified;
    }
    
    public String getStringLastModified() {
        return ""+lastModified;
    }

    
}
