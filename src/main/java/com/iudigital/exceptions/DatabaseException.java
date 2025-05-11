/**
 * DatabaseException.java
 */
package com.iudigital.exceptions;

// 3. PAQUETE EXCEPTIONS (Excepciones personalizadas)

public class DatabaseException extends Exception {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
