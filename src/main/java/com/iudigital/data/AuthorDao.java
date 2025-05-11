/**
 * AuthorDao.java
 */
package com.iudigital.data;

import com.iudigital.domain.Author;
import com.iudigital.exceptions.DatabaseException;
import com.iudigital.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

    private static final String GET_AUTHORS = "select * from authors";
    private static final String GET_AUTHOR_FOR_ID = "select * from authors where author_id = ? ";
    private static final String CREATE_AUTHOR = "insert into authors (firstname, lastname, birth_date) values (?, ?, ?) ";
    private static final String UPDATE_AUTHOR = "update authors set firstname = ?, lastname = ?, birth_date = ? where category_id = ? ";
    private static final String DELETE_AUTHOR = "delete from authors where author_id = ? ";

    public List<Author> getAuthors() throws DatabaseException {
        List<Author> authors = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_AUTHORS)) {

            while (resultSet.next()) {
                Author author = new Author();
                author.setAuthorId(resultSet.getInt("author_id"));
                author.setFirstname(resultSet.getString("firstname"));
                author.setLastname(resultSet.getString("lastname"));
                author.setBirthDate(resultSet.getDate("birth_date"));
                authors.add(author);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar autores", e);
        }

        return authors;
    }

    public Author getAuthor(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_FOR_ID)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Author author = new Author();
                    author.setAuthorId(resultSet.getInt("author_id"));
                    author.setFirstname(resultSet.getString("firstname"));
                    author.setLastname(resultSet.getString("lastname"));
                    author.setBirthDate(resultSet.getDate("birth_date"));
                    return author;
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar autor", e);
        }

        return null;
    }

    public void create(Author author) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, author.getFirstname());
            preparedStatement.setString(2, author.getLastname());
            preparedStatement.setDate(3, new java.sql.Date(author.getBirthDate().getTime()));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setAuthorId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar autor", e);
        }
    }

    public void update(Author author) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)) {

            preparedStatement.setString(1, author.getFirstname());
            preparedStatement.setString(2, author.getLastname());
            preparedStatement.setDate(3, new java.sql.Date(author.getBirthDate().getTime()));
            preparedStatement.setInt(4, author.getAuthorId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar autor", e);
        }
    }

    public void delete(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar autor", e);
        }
    }
}
