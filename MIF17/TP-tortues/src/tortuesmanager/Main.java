package tortuesmanager;

/**
 * Classe Main
 */
public class Main {

    /**
     * La procedure principale
     */
    public static void main(String[] args)
    {
        System.out.println( "Logo demarre!" );
        Controleur controleur = new Controleur();
        SimpleLogo simpleLogo = new SimpleLogo(controleur);
    }

}
