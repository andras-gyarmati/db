package xyz.codingmentor.pracice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author brianelete
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("somethingPU");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        CD cd0 = new CD();
        CD cd1 = new CD();
        CD cd2 = new CD();
        entityManager.persist(cd0);
        entityManager.persist(cd1);
        entityManager.persist(cd2);
        Artist ar0 = new Artist();
        entityManager.persist(ar0);
        ar0.getAppearsOnCDs().add(cd0);
        ar0.getAppearsOnCDs().add(cd1);
        ar0.getAppearsOnCDs().add(cd2);
        cd0.getCreatedByArtist().add(ar0);
        cd1.getCreatedByArtist().add(ar0);
        cd2.getCreatedByArtist().add(ar0);
        entityManager.merge(cd0);
        entityManager.merge(cd1);
        entityManager.merge(cd2);
        entityManager.merge(ar0);
        tx.commit();
        entityManager.close();
        factory.close();
    }
}
