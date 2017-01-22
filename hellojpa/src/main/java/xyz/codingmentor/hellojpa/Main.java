package xyz.codingmentor.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import xyz.codingmentor.hellojpa.entity.Book;

/**
 *
 * @author brianelete
 */
public class Main {

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Hello JPA");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("helloPU");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(book);
        tx.commit();
        entityManager.close();
        factory.close();
    }
}
