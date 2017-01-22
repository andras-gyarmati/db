package xyz.codingmentor.hellodatabase.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
@BookRepoQualifyer(BookRepoQualifyerValue.JDBC)
public class JDBCBookRepo implements BookRepo {

    final String URL = "jdbc:postgresql://localhost/course";
    final String USER = "postgres";
    final String PASSWORD = "postgres";

    private Connection connection;

    public JDBCBookRepo() throws SQLException, RepositoryException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCBookRepo.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryException(ex);
        }
    }

    @Override
    public BookDTO createBook(String isbn) throws RepositoryException {
        String insertSql = "INSERT INTO Book (isbn) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, isbn);
            statement.execute();
            BookDTO book = new BookDTO();
            book.setIsbn(isbn);
            return book;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public BookDTO findBook(String isbn) throws RepositoryException {
        String selectQuery = "SELECT isbn, title, author, pagenum FROM Book WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return buildBook(result);
            }
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<BookDTO> getBookByTitle(String title) throws RepositoryException {
        String selectQuery = "SELECT isbn, title, author, pagenum FROM Book WHERE title LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, "%" + title + "%");

            List<BookDTO> books = new ArrayList<>();
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                books.add(buildBook(result));
            }
            return books;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateBook(BookDTO book) throws RepositoryException {
        String updateSQL = "UPDATE Book SET title = ?, author = ?, pagenum = ?, WHERE isbn = ";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPagenum());
            statement.setString(4, book.getIsbn());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void removeBook(String isbn) throws RepositoryException {
        String deleteSQL = "DELETE FROM Book WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setString(1, isbn);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCBookRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BookDTO buildBook(ResultSet result) throws SQLException {
        BookDTO book = new BookDTO();
        book.setIsbn(result.getString("isbn"));
        book.setTitle(result.getString("title"));
        book.setAuthor(result.getString("author"));
        book.setPagenum(result.getInt("pagenum"));
        return book;
    }
}
