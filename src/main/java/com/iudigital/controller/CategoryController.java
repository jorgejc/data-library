/**
 * CategoryController.java
 */
package com.iudigital.controller;

import com.iudigital.data.CategoryDao;
import com.iudigital.domain.Category;
import com.iudigital.exceptions.DatabaseException;

import java.util.List;

public class CategoryController {

    private final CategoryDao categoryDao;

    public CategoryController() {
        this.categoryDao = new CategoryDao();
    }

    public List<Category> getCategories() throws DatabaseException {
        return categoryDao.getCatogories();
    }

    public Category getCategory(int id) throws DatabaseException {
        return categoryDao.getCategory(id);
    }

    public void createCategory(Category category) throws DatabaseException {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }

        categoryDao.createCategory(category);
    }

    public void updateCategory(Category category) throws DatabaseException {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }

        categoryDao.updateCategory(category);
    }

    public void deleteCategory(int id) throws DatabaseException {
        categoryDao.deleteCategory(id);
    }
}
