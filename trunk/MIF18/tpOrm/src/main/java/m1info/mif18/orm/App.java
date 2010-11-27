package m1info.mif18.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            
            OracleDataSource ods = new OracleDataSource();

            // type de pilote oracle
            ods.setDriverType("thin");

            // nom de la machine sur laquelle se trouve la base
            ods.setServerName("b710ntb.univ-lyon1.fr");

            // numero du port pour se connecter à la base
            ods.setPortNumber(1521);

            // nom de la base
            ods.setDatabaseName("ora10g");

            // Pour ouvrir une session (représentée par l'objet connect
            Connection connect = ods.getConnection("M1IF057","M1IF057");

            // Travail sur la base
            // Ici, on écrira du code pour, par exemple, interroger la base

            // Ne pas oublier de fermer la session quand on a fini de manipuler la base
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}