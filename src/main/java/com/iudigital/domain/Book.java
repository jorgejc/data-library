/**
 * Book.java
 */
package com.iudigital.domain;

public class Book {

    private int bookId;
    private String title;
    private Author author;
    private Category category;
    private int yearPublication;
    private String isbn;

    public Book() {
    }

    public Book(int bookId, String title, Author author, Category category, int yearPublication, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublication = yearPublication;
        this.isbn = isbn;
    }

    /**
     * Método encargado de retornar el valor del atributo bookId
     *
     * @return El bookId asociado a la clase
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Método encargado de modificar el valor del atributo bookId.
     *
     * @param bookId El nuevo bookId a modificar
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Método encargado de retornar el valor del atributo title
     *
     * @return El title asociado a la clase
     */
    public String getTitle() {
        return title;
    }

    /**
     * Método encargado de modificar el valor del atributo title.
     *
     * @param title El nuevo title a modificar
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Método encargado de retornar el valor del atributo author
     *
     * @return El author asociado a la clase
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Método encargado de modificar el valor del atributo author.
     *
     * @param author El nuevo author a modificar
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Método encargado de retornar el valor del atributo category
     *
     * @return El category asociado a la clase
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Método encargado de modificar el valor del atributo category.
     *
     * @param category El nuevo category a modificar
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Método encargado de retornar el valor del atributo yearPublication
     *
     * @return El yearPublication asociado a la clase
     */
    public int getYearPublication() {
        return yearPublication;
    }

    /**
     * Método encargado de modificar el valor del atributo yearPublication.
     *
     * @param yearPublication El nuevo yearPublication a modificar
     */
    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }

    /**
     * Método encargado de retornar el valor del atributo isbn
     *
     * @return El isbn asociado a la clase
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Método encargado de modificar el valor del atributo isbn.
     *
     * @param isbn El nuevo isbn a modificar
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return title;
    }
}
