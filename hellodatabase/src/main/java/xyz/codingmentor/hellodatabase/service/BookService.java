package xyz.codingmentor.hellodatabase.service;

import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import xyz.codingmentor.hellodatabase.api.BookDTO;
import xyz.codingmentor.hellodatabase.api.BookRepo;
import xyz.codingmentor.hellodatabase.api.BookRepoQualifyer;
import static xyz.codingmentor.hellodatabase.api.BookRepoQualifyerValue.JDBC;
import xyz.codingmentor.hellodatabase.api.RepositoryException;

/**
 *
 * @author brianelete
 */
public class BookService {

    private BookRepo bookRepo;

    public BookService() {

    }

    @Inject
    public BookService(@BookRepoQualifyer(JDBC) BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public BookDTO createBook(String isbn) throws RepositoryException {
        return bookRepo.createBook(isbn);
    }

    public BookDTO findBook(String isbn) throws RepositoryException {
        return bookRepo.findBook(isbn);
    }

    public List<BookDTO> getBookByTitle(String title) throws RepositoryException {
        return bookRepo.getBookByTitle(title);
    }

    public void updateBook(BookDTO book) throws RepositoryException {
        bookRepo.updateBook(book);
    }

    public void removeBook(String isbn) throws RepositoryException {
        bookRepo.removeBook(isbn);
    }

    @PreDestroy
    private void preDestroy() {
        bookRepo.close();
    }
}
