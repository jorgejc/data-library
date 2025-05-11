/**
 * BookDao.java
 */
package com.iudigital.data;

import com.iudigital.domain.Author;
import com.iudigital.domain.Book;
import com.iudigital.domain.Category;
import com.iudigital.exceptions.DatabaseException;
import com.iudigital.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    private static final String GET_BOOKS = "select * from books";
    private static final String GET_BOOK_FOR_ID = "select * from books where book_id = ? ";
    private static final String CREATE_BOOK = "insert into books (title, author_id, category_id, year_publication, isbn) values (?, ?, ?, ?, ?) ";
    private static final String UPDATE_BOOK = "update books set title = ?, author_id = ?, category_id = ?, year_publication = ?, isbn = ? where book_id = ? ";
    private static final String DELETE_BOOK = "delete from books where book_id = ? ";
    public BookDao() {
        this.authorDao = new AuthorDao();
        this.categoryDao = new CategoryDao();
    }

    public List<Book> getBooks() throws DatabaseException {
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_BOOKS)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setYearPublication(resultSet.getInt("year_publication"));
                book.setIsbn(resultSet.getString("isbn"));

                // Obtener autor y categoría
                Author author = authorDao.getAuthor(resultSet.getInt("author_id"));
                Category category = categoryDao.getCategory(resultSet.getInt("category_id"));

                book.setAuthor(author);
                book.setCategory(category);

                books.add(book);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar libros", e);
        }

        return books;
    }

    public Book getBook(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_FOR_ID)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book();
                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setYearPublication(resultSet.getInt("year_publication"));
                    book.setIsbn(resultSet.getString("isbn"));

                    // Obtener autor y categoría
                    Author author = authorDao.getAuthor(resultSet.getInt("author_id"));
                    Category category = categoryDao.getCategory(resultSet.getInt("category_id"));

                    book.setAuthor(author);
                    book.setCategory(category);

                    return book;
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar libro", e);
        }

        return null;
    }

    public void createBook(Book book) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthor().getAuthorId());
            preparedStatement.setInt(3, book.getCategory().getCategoryId());
            preparedStatement.setInt(4, book.getYearPublication());
            preparedStatement.setString(5, book.getIsbn());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar libro", e);
        }
    }

    public void updateBook(Book book) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthor().getAuthorId());
            preparedStatement.setInt(3, book.getCategory().getCategoryId());
            preparedStatement.setInt(4, book.getYearPublication());
            preparedStatement.setString(5, book.getIsbn());
            preparedStatement.setInt(6, book.getBookId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar libro", e);
        }
    }

    public void deleteBook(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar libro", e);
        }
    }
}
