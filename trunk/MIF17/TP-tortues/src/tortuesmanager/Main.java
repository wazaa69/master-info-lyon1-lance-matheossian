package tortuesmanager;

/**
 * La classe Main, crée le controleur et la vue.
 */
public class Main {

    /**
     * La procedure principale
     */
    public static void main(String[] args)
    {
        System.out.println( "Tortues Manager demarré !" );
        Controleur controleur = new Controleur();
        SimpleLogo simpleLogo = new SimpleLogo(controleur);
    }

}
