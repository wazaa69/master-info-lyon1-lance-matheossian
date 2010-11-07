package Gestion;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Message {

    private String utilisateur;
    private String contenu;
    private Date date; /** stockage de la date d'arrivé du message */

    
    public Message(String utilisateur, String contenu) {

        this.utilisateur = utilisateur;
        this.contenu = contenu;

        date = new Date(System.currentTimeMillis());
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Donne la date d'arrivée du message :
     * @return une date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Donne l'Heure, les Minutes et les Secondes de l'arrivée du message
     * @return une chaine de caractères
     */
    public String getStringHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * Donne le Jour, le Mois et l'Année d'arrivée du message
     * @return une chaine de caractères
     */
    public String getStringJMA() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
  
}
