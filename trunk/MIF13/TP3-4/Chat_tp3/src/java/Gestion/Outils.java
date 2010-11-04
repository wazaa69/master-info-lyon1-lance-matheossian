package Gestion;

import javax.servlet.http.Cookie;

/**
 * Quelques fonctions utiles
 */
public class Outils {

    /**
     * Recherche le cookie "cookieCherche" et le renvoie
     * @param listeCookies la liste de tous les cookies
     * @param cookieCherche le cookie recherché
     * @return revoie un cookie
     */
    public static Cookie getCookie(Cookie[] listeCookies, String cookieCherche){

        //liste des cookies
        Cookie[] cookies = listeCookies;

        //recherche du bon cookie
        for(int i = 0; i < cookies.length; i++) {
            Cookie unCookie = cookies[i];
            if (unCookie.getName().equals(cookieCherche))
                return unCookie;
        }

        return null;
    }

}
