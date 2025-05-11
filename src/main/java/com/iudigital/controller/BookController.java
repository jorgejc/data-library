/**
 * BookController.java
 */
package com.iudigital.controller;

import com.iudigital.data.BookDao;
import com.iudigital.domain.Book;
import com.iudigital.exceptions.DatabaseException;

import java.util.List;

public class BookController {

    private BookDao bookDao;

    public BookController() {
        this.bookDao = new BookDao();
    }

    public List<Book> getBooks() throws DatabaseException {
        return bookDao.getBooks();
    }

    public Book getBook(int id) throws DatabaseException {
        return bookDao.getBook(id);
    }

    public void createBook(Book book) throws DatabaseException {
        validateBook(book);
        bookDao.createBook(book);
    }

    public void updateBook(Book book) throws DatabaseException {
        validateBook(book);
        bookDao.updateBook(book);
    }

    public void deleteBook(int id) throws DatabaseException {
        bookDao.deleteBook(id);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío");
        }
        if (book.getAuthor() == null) {
            throw new IllegalArgumentException("El autor del libro es obligatorio");
        }
        if (book.getCategory() == null) {
            throw new IllegalArgumentException("La categoría del libro es obligatoria");
        }
        if (book.getYearPublication() <= 0) {
            throw new IllegalArgumentException("El año de publicación debe ser válido");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN del libro no puede estar vacío");
        }
    }
}
