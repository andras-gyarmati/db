package xyz.codingmentor.hellodatabase.jpa;

import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import xyz.codingmentor.hellodatabase.api.BookDTO;
import xyz.codingmentor.hellodatabase.api.BookRepo;
import xyz.codingmentor.hellodatabase.api.BookRepoQualifyer;
import xyz.codingmentor.hellodatabase.api.BookRepoQualifyerValue;
import xyz.codingmentor.hellodatabase.api.RepositoryException;

/**
 *
 * @author brianelete
 */
@Stateless
@BookRepoQualifyer(BookRepoQualifyerValue.JPA)
public class JPABookRepo implements BookRepo {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public JPABookRepo() {
        factory = Persistence.createEntityManagerFactory("helloDBPU");
        entityManager = factory.createEntityManager();
    }

    @Override
    public BookDTO createBook(String isbn) throws RepositoryException {
        BookEntity book = new BookEntity();
        book.setIsbn(isbn);
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(book);
        tx.commit();
        return buildBook(book);
    }

    @Override
    public BookDTO findBook(String isbn) throws RepositoryException {
        BookEntity book = entityManager.find(BookEntity.class, isbn);
        if (null != book) {
            return buildBook(book);
        }
        return null;
    }

    @Override
    public List<BookDTO> getBookByTitle(String title) throws RepositoryException {
        String selectQuery = "SELECT b FROM BookEntity b WHERE b.title LIKE :title";
        TypedQuery<BookEntity> query = entityManager.createQuery(selectQuery, BookEntity.class);
        query.setParameter("title", "%" + title + "%");
        List<BookEntity> books = query.getResultList();
        List<BookDTO> result = new ArrayList<>();
        for (BookEntity book : books) {
            result.add(buildBook(book));
        }
        return result;
    }

    @Override
    public void updateBook(BookDTO book) throws RepositoryException {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        BookEntity entity = entityManager.find(BookEntity.class, book.getIsbn());
        entity.setAuthor(book.getAuthor());
        entity.setPagenum(book.getPagenum());
        entity.setTitle(book.getTitle());
        entityManager.merge(entity);
        tx.commit();
    }

    @Override
    public void removeBook(String isbn) throws RepositoryException {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        BookEntity book = entityManager.find(BookEntity.class, isbn);
        if (null != book) {
            entityManager.remove(book);
        }
        tx.commit();
    }

    @Override
    public void close() {
        factory.close();
        entityManager.close();
    }

    private BookDTO buildBook(BookEntity book) {
        BookDTO dto = new BookDTO();
        dto.setIsbn(book.getIsbn());
        dto.setAuthor(book.getAuthor());
        dto.setPagenum(book.getPagenum());
        dto.setTitle(book.getTitle());
        return dto;
    }
}
