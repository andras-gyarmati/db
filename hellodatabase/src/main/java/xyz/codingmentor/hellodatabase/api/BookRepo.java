package xyz.codingmentor.hellodatabase.api;

import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author brianelete
 */
@Stateless
public interface BookRepo {

    BookDTO createBook(String isbn) throws RepositoryException;

    BookDTO findBook(String isbn) throws RepositoryException;

    List<BookDTO> getBookByTitle(String title) throws RepositoryException;

    void updateBook(BookDTO book) throws RepositoryException;

    void removeBook(String isbn) throws RepositoryException;

    void close();

}
