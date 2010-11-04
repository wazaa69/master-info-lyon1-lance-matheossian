package Gestion;

import javax.servlet.http.Cookie;

/**
 *
 */
public class Outils {

    public static Cookie getStringParam(Cookie[] c, String s){

        //récupération du cookie chez le client
        Cookie[] cookies = c;

        //recherche du cookie
        for(int i = 0; i < cookies.length; i++) {
            Cookie unCookie = cookies[i];
            if (unCookie.getName().equals(s))
                return unCookie;
        }

        return null;
    }

}
