package main.java.com.banking.exceptions;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String message) {
        super(message);
    }
}