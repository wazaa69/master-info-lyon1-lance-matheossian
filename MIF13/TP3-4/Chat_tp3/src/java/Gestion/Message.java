package Gestion;

public class Message {

    private String utilisateur;
    private String contenu;

    public Message(String utilisateur, String contenu) {
        this.utilisateur = utilisateur;
        this.contenu = contenu;
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

    
}
