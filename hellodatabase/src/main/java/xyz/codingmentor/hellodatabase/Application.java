package xyz.codingmentor.hellodatabase;

import java.util.List;
import javax.inject.Inject;
import xyz.codingmentor.hellodatabase.api.BookDTO;
import xyz.codingmentor.hellodatabase.api.RepositoryException;
import xyz.codingmentor.hellodatabase.service.BookService;

/**
 *
 * @author brianelete
 */
public class Application {

    private BookService bookService;

    public Application() {
    }

    @Inject
    public Application(BookService bookService) {
        this.bookService = bookService;
    }

    void execute() throws RepositoryException {
        BookDTO book = bookService.createBook("isbn-1");
        book.setAuthor("asdasdasd");
        book.setTitle("asdsada");
        book.setPagenum(25);
        bookService.updateBook(book);
        List<BookDTO> books = bookService.getBookByTitle("asd");
        for (BookDTO asdBook : books) {
            System.out.println(asdBook.getTitle());
        }
    }
}
