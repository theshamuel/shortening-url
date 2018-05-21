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

import com.theshamuel.shrturl.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ShrtUrl exceptions handler class.
 *
 * @author Alex Gladkikh
 */
@ControllerAdvice
public class ShortUrlExceptionHandler {

    private static Logger logger =  LoggerFactory.getLogger(ShortUrlExceptionHandler.class);

    /**
     * Duplicate login response error.
     *
     * @param e the exceptions
     * @return the response error
     */
    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    ResponseError duplicateLogin(DuplicateRecordException e) {
        logger.error("ShrtUrlError:", e);
        return new ResponseError( HttpStatus.CONFLICT.value(), e.getMessage());
    }

    /**
     * Not found entity response error.
     *
     * @param e the exceptions
     * @return the response error
     */
    @ExceptionHandler(NotFoundParamsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ResponseError notFoundEntity(NotFoundParamsException e) {
        logger.error("ShrtUrlError:", e);
        return new ResponseError( HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


}