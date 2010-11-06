package Gestion;

import javax.servlet.http.Cookie;

/**
 * Quelques fonctions utiles
 */
public class Outils {

    /**
     * Recherche le cookie "cookieCherche" et le renvoie
     * @param cookies un tableau de cookies
     * @param cookieCherche le cookie recherché
     * @return revoie le cookie si il existe, null sinon
     */
    public static Cookie getCookie(Cookie[] cookies, String cookieCherche){

        if(cookies != null){
            
            //recherche du bon cookie
            for(int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieCherche))
                    return cookies[i];
            }

        }

        return null;
    }

}
