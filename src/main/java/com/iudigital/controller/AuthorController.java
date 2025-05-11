/**
 * AuthorController.java
 */
package com.iudigital.controller;

import com.iudigital.data.AuthorDao;
import com.iudigital.domain.Author;
import com.iudigital.exceptions.DatabaseException;

import java.util.List;

public class AuthorController {

    private final AuthorDao authorDao;

    public AuthorController() {
        this.authorDao = new AuthorDao();
    }

    public List<Author> getAuthors() throws DatabaseException {
        return authorDao.getAuthors();
    }

    public Author getAuthor(int id) throws DatabaseException {
        return authorDao.getAuthor(id);
    }

    public void create(Author author) throws DatabaseException {
        if (author.getFirstname() == null || author.getFirstname().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor no puede estar vacío");
        }
        if (author.getLastname() == null || author.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del autor no puede estar vacío");
        }

        authorDao.create(author);
    }

    public void update(Author author) throws DatabaseException {
        if (author.getFirstname() == null || author.getFirstname().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor no puede estar vacío");
        }
        if (author.getLastname() == null || author.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del autor no puede estar vacío");
        }

        authorDao.update(author);
    }

    public void delete(int id) throws DatabaseException {
        authorDao.delete(id);
    }
}
