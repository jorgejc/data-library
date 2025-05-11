/**
 * Category.java
 */
package com.iudigital.domain;

public class Category {

    private int categoryId;
    private String name;
    private String description;

    /**
     *
     */
    public Category() {
    }

    /**
     * @param categoryId
     * @param name
     * @param description
     */
    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    /**
     * Método encargado de retornar el valor del atributo categoryId
     *
     * @return El categoryId asociado a la clase
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Método encargado de modificar el valor del atributo categoryId.
     *
     * @param categoryId El nuevo categoryId a modificar
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Método encargado de retornar el valor del atributo name
     *
     * @return El name asociado a la clase
     */
    public String getName() {
        return name;
    }

    /**
     * Método encargado de modificar el valor del atributo name.
     *
     * @param name El nuevo name a modificar
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método encargado de retornar el valor del atributo description
     *
     * @return El description asociado a la clase
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método encargado de modificar el valor del atributo description.
     *
     * @param description El nuevo description a modificar
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
