/**
 * CategoryDao.java
 */
package com.iudigital.data;

import com.iudigital.domain.Category;
import com.iudigital.exceptions.DatabaseException;
import com.iudigital.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    private static final String GET_CATEGORIES = "select * from categories";
    private static final String GET_CATEGORY_FOR_ID = "select * from categories where category_id = ? ";
    private static final String CREATE_CATEGORY = "insert into categories (name, description)"
            + "values (?, ?) ";
    private static final String UPDATE_CATEGORY = "update categories set name = ?, description = ? where category_id = ? ";
    private static final String DELETE_CATEGORY = "delete from categories where category_id = ? ";

    public List<Category> getCatogories() throws DatabaseException {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORIES);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar categorias", e);
        }
        return categories;
    }

    public Category getCategory(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_FOR_ID)){

             preparedStatement.setInt(1, id);

             try (ResultSet resultSet = preparedStatement.executeQuery()) {
                 if (resultSet.next()) {
                     Category category = new Category();
                     category.setCategoryId(resultSet.getInt("category_id"));
                     category.setName(resultSet.getString("name"));
                     category.setDescription(resultSet.getString("description"));
                     return category;
                 }
             }
        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar categoría", e);
        }
        return null;
        }

    public void createCategory (Category category) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(CREATE_CATEGORY, Statement.RETURN_GENERATED_KEYS)) {
//Prepara una sentencia SQL para ejecución, solicitando que se devuelvan las claves generadas automáticamente(como el ID de la nueva fila).
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();

// Recupera el ID generado automáticamente (por ejemplo, una columna autoincremental como id_categoria) y lo asigna al objeto categoria.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setCategoryId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar la categoria", e);
        }
    }

    public void updateCategory(Category category) throws DatabaseException  {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATEGORY)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getCategoryId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar la categoria", e);
        }
    }

    public void deleteCategory(int id) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar categoría", e);
        }
    }
}
