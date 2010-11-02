package Application;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Validation du formulaire
 */
public class Formulaire extends Action {

    /**
     * @param nom le nom du profil
     * @param sexe le sexe du profil
     * @return retourne vrai si les deux chaines en paramètres ne sont pas vide
     */
    private boolean isValide(Profil p){

        if(!p.getNom().isEmpty() && !p.getSexe().isEmpty())
            return true;
        else
            return false;
    }

    private boolean isHomme(Profil p){
        return p.getSexe().equals("Homme");
    }


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String cible = "succes";
        
        String nom, sexe = null;
        boolean formValide = false;
        boolean homme = false;


        if(form != null){
            //le formulaire reçu est de type Profil
             Profil unProfil = (Profil) form;
            formValide = isValide(unProfil);
            homme = isHomme(unProfil);

              if(formValide && homme)
                  cible = "succesHomme";
              else if (formValide && !homme)
                  cible = "succesFemme";
              else
                  cible="echec";

        }

        return(mapping.findForward(cible));
    }
}
