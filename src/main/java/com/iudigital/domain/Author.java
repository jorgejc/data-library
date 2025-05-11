/**
 * Author.java
 */
package com.iudigital.domain;

import java.util.Date;

public class Author {

    private int authorId;
    private String firstname;
    private String lastname;
    private Date birthDate;

    public Author() {
    }

    public Author(int authorId, String firstname, String lastname, Date birthDate) {
        this.authorId = authorId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
    }

    /**
     * Método encargado de retornar el valor del atributo authorId
     *
     * @return El authorId asociado a la clase
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Método encargado de modificar el valor del atributo authorId.
     *
     * @param authorId El nuevo authorId a modificar
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Método encargado de retornar el valor del atributo firstname
     *
     * @return El firstname asociado a la clase
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Método encargado de modificar el valor del atributo firstname.
     *
     * @param firstname El nuevo firstname a modificar
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Método encargado de retornar el valor del atributo lastname
     *
     * @return El lastname asociado a la clase
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Método encargado de modificar el valor del atributo lastname.
     *
     * @param lastname El nuevo lastname a modificar
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Método encargado de retornar el valor del atributo birthDate
     *
     * @return El birthDate asociado a la clase
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Método encargado de modificar el valor del atributo birthDate.
     *
     * @param birthDate El nuevo birthDate a modificar
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
