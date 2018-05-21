/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.exceptions;

/**
 * The own NotFound shrlurl application exception.
 *
 * @author Alex Gladkikh
 */
public class NotFoundParamsException extends RuntimeException{

    /**
     * Instantiates a new UserNotFoundException.
     *
     * @param message the message of error.
     */
    public NotFoundParamsException(String message) {
        super(message);
    }
}
