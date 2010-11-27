package m1info.mif18.orm;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;

public class ConfigurationTest extends TestCase {

    public void testEntityManager() {

        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();

        Membre toto = new Membre();
        toto.setNom("Toto");
        toto.setEmail("toto@nowhere.net");

        em.getTransaction().begin();

        em.persist(toto);

        em.getTransaction().commit();

        List<Membre> lesMembres = em.createQuery("SELECT m FROM Membre m").getResultList();

        for (Membre p : lesMembres) {
            System.out.println("Nom: " + p.getNom()
                    + ", email: " + p.getEmail()
                    + ", id: " + p.getId());
        }
        
    }

}