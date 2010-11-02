package Application;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Le profil de la personne connectée
 */
public class Profil extends ActionForm {

    private String nom;
    private String sexe;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request){
        nom = null;
        sexe = null;
    }

    /************ GETTERS/SETTERS ************/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
  
}
