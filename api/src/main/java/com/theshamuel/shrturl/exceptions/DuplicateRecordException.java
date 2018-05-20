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
 * The type of runtime exceptions when processing entity is duplicates class.
 *
 * @author Alex Gladkikh
 */
public class DuplicateRecordException extends RuntimeException{

    /**
     * Instantiates a new Entity DuplicateRecordException.
     *
     * @param message the message of error.
     */
    public DuplicateRecordException(String message) {
        super(message);
    }
}
