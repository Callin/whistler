package xyz.codeark.whistler.exceptions;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;

public class WhistlerRestException extends RuntimeException{
    private String path;
    private String name;
    private HttpStatus status;

    /**
     * Creates a custom REST exception to deal with unexpected errors
     *
     * @param message Exception message
     */
    public WhistlerRestException(String message) {
        super(message);
    }

    /**
     * Creates a custom REST exception to deal with unexpected errors
     *
     * @param message Exception message
     */
    public WhistlerRestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Creates a custom REST exception to deal with unexpected errors
     *
     * @param message Exception message
     * @param status  HTTP Response status
     * @param name    Name of the git repository or maven module
     * @param path    Path of the git repository or maven module
     */
    public WhistlerRestException(String message, HttpStatus status, String path, String name) {
        super(message);
        this.path = path;
        this.name = name;
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
